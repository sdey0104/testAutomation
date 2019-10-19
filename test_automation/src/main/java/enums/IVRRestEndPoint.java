package enums;

public enum IVRRestEndPoint {
    IVR_RECEIVE("ivr_receive"),
    IVR_AFTERWELCOME("ivr_afterwelcome"),
    IVR_STORELANGUAGEFROMMENU("ivr_storelanguagefromMenu"),
    IVR_GETTRANSPONDERCARDNR("ivr_gettranspondercardnr"),
    IVR_CHOOSETRANSPONDERCARDFROMMENU("ivr_choosetranspondercardfrommenu"),
    IVR_CHOOSE1TOSTOPTRANSACTION("ivr_choose1tostoptransaction"),
    IVR_CHOOSE0FORMAINMENU("ivr_choose0formainmenu"),
    IVR_GETZONECODE("ivr_getzonecode"),
    IVR_CHOOSE1TOCONTINUE0TOGOBACK("ivr_choose1tocontinue0togoback"),
    IVR_CALLENDED("ivr_callended");




    private final String text;


    IVRRestEndPoint(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
