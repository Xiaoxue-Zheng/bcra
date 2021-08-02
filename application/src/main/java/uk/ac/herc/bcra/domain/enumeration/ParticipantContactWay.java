package uk.ac.herc.bcra.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Stream;

public enum ParticipantContactWay {
    EMAIL("Email", 1), SMS("SMS",1<<1), CALL("Call",  1<<2), MAIL("Mail", 1<<3);

    private final String desc;
    private final int code;

    ParticipantContactWay(String desc, int code) {
        this.desc = desc;
        this.code = code;
    }

    @JsonCreator
    public static ParticipantContactWay decode(final String desc) {
        return Stream.of(ParticipantContactWay.values()).filter(targetEnum -> targetEnum.desc.equals(desc)).findFirst().orElse(null);
    }

    @JsonValue
    public String getDesc() {
        return desc;
    }

    public static int calculateCodes(List<ParticipantContactWay> wayList) {
        if(CollectionUtils.isEmpty(wayList)) return 0;
        int result = 0;
        for(ParticipantContactWay way: wayList){
            result = result | way.code;
        }
        return result;
    }
}
