package enums;

public enum IVRRestKeyParam {
    HOSTNAME("hostname"),
    IVRRESPONSERECIEVE("response.recieve"),
    IVRAFTERWELCOME("response.afterwelcome"),
    IVRGETZONECODE("response.getzonecode"),
    IVRCHOOSE1TOCONTINUE0TOGOBACK("response.choose1ToContinue0ToGoBack"),
    IVRCHOOSE1TOSTOPTRANSACTION("response.choose1ToStopTransaction");


    /*response.recieve=007_welcome.wav
    response.afterwelcome=102_dialZoneCode.wav
    =246_BrickInZone.wav
    response.choose1ToContinuw0ToGoBack=115_parkingZone.wav*/



    private final String text;
    IVRRestKeyParam(final String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return text;
    }
}
