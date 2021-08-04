package uk.ac.herc.bcra.domain.enumeration;

import org.apache.commons.lang.StringUtils;

import java.util.Locale;

public enum RequestSource {
    ADMIN,
    QUESTIONNAIRE;

    public static RequestSource getRequestSource(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }else{
            try{
                return RequestSource.valueOf(str.toUpperCase(Locale.ROOT));
            }catch (Exception e){
                return null;
            }
        }
    }
}
