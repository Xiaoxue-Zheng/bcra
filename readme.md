# BCRA - Breast Cancer Risk Assesment in Young Women

## Build Prerequisites
NB: It is recommended to use nvm for installing (and switching between) node versions

Below is a list of software we use in the running of the BCRA application.
The versions listed by each of them is recommended for successful building.
- node.js (10.16.3)
- PostGreSQL (13.1.0)

## Quality Assurance / Current Project Status
NB: This section is to be updated with every change that is made.

- Currently, we are awaiting an executable that is compatible with Mac and Linux operating systems. Because of this, two integration tests will fail on these platforms. These are runTyrerCuzickExecutable and readTyrerCuzickOutput. Before running the integration tests on these platforms, be sure to comment out these tests.
- Some tests make use of cy.url() to test that the page has not navigated away. Whilst this works fine, it is not the prettiest of solutions and so CLIN-1228 has been created to remedy this.

## Database

### Configure Postgres for Local Development
Install postgres on your machine, check it is running, and create the database and user with the following commands:
```
psql -U postgres
create user bcra with encrypted password 'bcra123';

create database bcra;
grant all privileges on database bcra to bcra;

create database bcratest;
grant all privileges on database bcratest to bcra;
```

### Clearing the Database
```
psql -U postgres
drop database bcra;
create database bcra;
grant all privileges on database bcra to bcra;

drop database bcratest;
create database bcratest;
grant all privileges on database bcratest to bcra;
\q
```

### Recreate Database
 * Clear the database (see above)
 * Copy .yo-rc.json and database-model.jdl to a new folder
 * `jhipster`
 * `jhipster import-jdl database-model.jdl`
 * copy to project: `/src/main/resources/config/liquibase/`

### Create Liquibase Changeset for Hibernate Changeset
 * Clear database
 * `mvnw` (creates liquibase DB)
 * Make hibernate changes
 * `mvnw compile`
 * `mvnw liquibase:diff`
 
 
## Application

The **application** project contains the back-end server, and the interface for all users except participants. It is generated by JHipster.

### Launch Local Build
Install packages: `npm install`

Build & run back-end: `mvnw`

Launch front-end: `npm start`

### Run Back-End Tests
`mvnw verify`

#### Run a Single Back-End Tests
`mvnw surefire:test -Dtest=ClassNameOfTest`

#### Run all Back-End Tests in a Package
`mvnw surefire:test -Dtest="uk.ac.herc.bcra.algorithm.**"`

### Run Front-End Tests
`npm test`

### Run Front-End E2E Tests
Navigate to the /application in two console windows.

In one window run `npm test`

In the other window run `npm run e2e`

### Build for Production
`mvnw -Pprod,war clean verify`

### Add New Entity to Model
`jhipster entity Question`

### Tyrercuzick process

#### Set-up

When a risk assessment is completed, it is ran through our tyrercuzick process. This requires a directory structure to be set up.

Before the application is started, be sure that you have the following directory structure:

/home/tyrercuzick/input/
/home/tyrercuzick/output/
/home/tyrercuzick/extract/

As well as this, make sure to place the tyrercuzick.exe application (which is found in the ./application directory), as well as the extract sql file (found in the ./scripts/sql directory) into the following directory:

/home/tyrercuzick/tyrercuzick.exe
/home/tyrercuzick/extract/risk_assessment_extract.sql

If you are on an iOS system, then use the following directory structure for the tyrer cuzick files (tcuzick file also found in the ./application directory):

/usr/local/share/tyrercuzick/input/
/usr/local/share/tyrercuzick/output/
/usr/local/share/tyrercuzick/extract/

And place the mac appropriate files into the following directory:

/usr/local/share/tyrercuzick/tcuzick
/usr/local/share/tyrercuzick/extract/risk_assessment_extract_ios.sql

#### Manual testing

##### Testing the TC process

The tyrercuzick process can be manually tested from the admin dashboard of the web application. To carry this out, follow these steps:

1. Run the backend application.
2. Run the questionnaire application.
3. Log into the backend application.
4. Select entities > Study IDs from the navigation bar.
5. Select "create new study ids".
6. Enter a comma separated list of study ids (take note of these).
    - i.e. TST_01, TST_02, TST_03, etc.
7. Head to the questionnaire application.
8. Register with one of the created study ids.
9. Complete all questionnaires.
10. Log back into the backend application.
11. Select Tyrer Cuzick Test from the navigation bar.
12. Select the "Trigger TC Process" button.
13. Open the database using the psql console.
14. Check the risk_assessment_response table for the new record.

##### Running a TC extract

We can run an extract of our TC data from the admin dashboard of the web application. This is carried out from the same page as the TC process test.

This requires some setup that will be done in the setup phase of the server. If this is the first time you have ran the software on your local machine, you will have to do this.

To set up:
1. Enter the tyrercuzick directory.
2. Create the subdirectory /extract/
3. From the <PROJECT_DIR>/scripts/sql directory, copy the risk_assessment_extract.sql file
4. Place this file in the newly created /extract/ directory.

To run this process:
1. Run the backend application.
2. Run the questionnaire application.
3. Log into the backend application.
4. Head to the Tyrer Cuzick Test page from the navigation bar.
5. Select the "Trigger TC Extract Test" button.
6. This should create an extract file in the tyrercuzick directory under a subdirectory titled /extract/.

### CanRisk report folder

When setting up a new server, be sure that you have created the CanRisk report directory for these files to be stored.
This is shown below:

/home/canrisk/

or, for iOS:

/usr/local/share/canrisk/
 
## Questionnaire

The **questionniare** project contains the front-end for participants. It is generated by vue-cli.

### Logging in for development/testing/inspecting
If you want to login to the questionnaire interface:
1. Open the Admin Interface (localhost:8080) and import **Participant1111111111.csv**
2. Open the Participant Interface (localhost:3210) and register as this participant (NHS number: 1111111111 and DOB 11/11/1111)
3. Sign in with the chosen username/password


### Launch Local Build
Install packages: `npm install`

Launch front-end: `npm run serve`

### Build for Production

Build front-end: `npm run build -Pprod`

<del>This will place a **.war** file in the **dist** folder.</del>

(The warfile plugin is temporarily deactivated, because it conflicts with Cypress end-to-end tests.

### Run Tests

#### Unit Tests
`npm run test:unit`

#### End-to-End Tests
1. Clear the database (see above) if there have been any model changes.
2. Launch back-end with `mvnw` from the /application directory
3. Run `npm run test:e2e` from the /questionnaire directory

To run end-to-end tests in a headless environment, use: `npm run test:headless`

#### Test StudyId
A default study ID is created to test the new sign up functionality. 
This id is 'TST_1', and has a consent and risk assessment questionnaire automatically assigned.

## Deploying on server
The following instructions guide you through the process of uploading both the backend and front-end to a server.

Build and package the server backend and admin console:
`cd <PROJECT_DIR>/application`
`mvnw -Pprod,war clean verify`

Build and package the front end: 
`cd <PROJECT_DIR>/questionnaire`
`npm run build -Pprod`
`mv dist hryws`
Zip in Windows: `tar.exe -a -c -f hryws.zip hryws`
Zip in Mac and Linux: `zip hryws.zip hryws` 

Copy the generated files to the server of choice.

Place both the zip files into the tomcat webapps folder.
(Manually unzip the hryws.zip file)

Restart tomcat:
`service tomcat restart`