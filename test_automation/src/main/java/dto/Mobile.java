package dto;

import lombok.Data;

@Data
public class Mobile {

    private String sms;
    private long customerIdFk;
    private String type;
    private String mobileNr;
    private float smsInterval;
    private long smsBeforeEnd;
    private long transponderCardIdFk;
    private long confirmZone;
    private long tCardSwitchable;
}
