package dto;

import lombok.Data;

@Data
public class IVRCustomerdetails {

    private String customerNr;
    private String licensePlate;
    private String mobileNr;
    private String zoneCode;
    private String callUUID;
    private String from;
    private String redirectBaseUrl;
}
