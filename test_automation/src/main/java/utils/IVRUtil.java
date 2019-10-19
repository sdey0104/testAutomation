package utils;

public class IVRUtil {

    public static String generateCallUUID() {
        String callUUID = null;
        if (callUUID == null) {
            callUUID = "mockedPhone" + (int) (Math.random() * 1000000);
        }
        return callUUID;
    }

    public static String queryParameterFrom(String phonenumber) {
        String From = phonenumber;
        From = From.replace("+", "%2B");
        return From;
    }
}


