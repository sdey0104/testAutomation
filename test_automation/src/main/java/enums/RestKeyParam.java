package enums;

public enum RestKeyParam {
    CLIENT_ID("clientId"),
    PASSWORD("password"),
    USERNAME("username"),
    CARDCODE("cardCode"),
    COUNTRY_CODE("countryCode"),
    LICENSE_PLATE("licensePlate"),
    ZONE_CODE("zoneCode"),
    LATITUDE("latitude"),
    LONGITUDE("longitude"),
    TRANSACTION_ID("transactionId"),
    ZONECODE_ID("zoneCodeId"),
    MAXRESULTS("maxResults"),
    EMAIL("email"),
    APP_VERSION("appVersion"),
    MESSAGE_VERSION("messageVersion"),

    HOSTNAME("hostname");


    private final String text;
    RestKeyParam(final String text){
        this.text = text;
    }

    @Override
    public String toString(){
        return text;
    }
}
