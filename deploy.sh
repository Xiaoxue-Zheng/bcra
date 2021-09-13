#!/bin/bash
deploy_questionnaire=false
deploy_clinician=false
active_profile=test
new_version="0.01"
for i in "$@"
do
case $i in
    -d=*|--project_dir=*)
    project_dir="${i#*=}"
    ;;
    -v=*|--new_version=*)
    new_version="${i#*=}"
    ;;
    -q=*|--deploy_questionnaire=*)
    deploy_questionnaire="${i#*=}"
    ;;
    -c=*|--deploy_clinician=*)
    deploy_clinician="${i#*=}"
    ;;
    -p=*|--active_profile=*)
    active_profile="${i#*=}"
    ;;
    
    *)
            # unknown option
    ;;
esac
done

if [ -z "$new_version" ]
then
      echo "please set new version with the argument -v new_version"
      exit 1
fi

if [ -z "$project_dir" ]
then
      echo "please set your application directory with the argument -d directory"
      exit 1
fi
host=hubble.mhealthherc.com
remote_catalina_dir=/opt/tomcat/app_base/app_v1/webapps/
dest_user_dir=/home/$USER

# change me
if [ "$active_profile"='prod' ]
then
      host=hubble.mhealthherc.com
      remote_catalina_dir=/opt/tomcat/app_base/app_v1/webapps/
      dest_user_dir=/home/$USER
fi
echo "we will deploy to host "${host}" new version(-v) is "${new_version}" active profile(-p) is "${active_profile}", clinician will be deployed "${deploy_clinician}", questionnaire will be deployed "${deploy_questionnaire}
echo "${deploy_clinician}"
if [ "${deploy_clinician}" = "true" ]
then
  echo "deploying clinician interface start"
  cd $project_dir
  # only master branch can do the release
  #git checkout master
  # upgrade version
  # TODO should commit the verson after release
  cd application
  # mvn versions:set -DnewVersion=${new_version}
  mvn -P${active_profile},war clean verify
  scp ${project_dir}/application/target/bcra-${new_version}.war hubble.mhealthherc.com:${dest_user_dir}/bcra.war
  cmmd="sudo -u root cp ${dest_user_dir}/bcra.war ${remote_catalina_dir}"
  ssh -t $host "$cmmd"
  echo "deploying clinician interface end"
fi

if [ "${deploy_questionnaire}" = "true" ]
then
  echo "deploying questionnaire start"
  cd $project_dir
	cd ./questionnaire
  rm -rf hryws*
	npm run build -Pprod
	mv dist hryws
	zip -r hryws.zip hryws
	scp hryws.zip $host:$dest_user_dir
    cmmd2="sudo -u root cp ${dest_user_dir}/hryws.zip $remote_catalina_dir; sudo -u root unzip -o ${remote_catalina_dir}/hryws.zip -d $remote_catalina_dir"
	ssh -t $host "$cmmd2"
  echo "deploying questionnaire end"
fi
echo "restart tomcat"
ssh $host "sudo -u root service tomcat restart"
echo "deploy success"