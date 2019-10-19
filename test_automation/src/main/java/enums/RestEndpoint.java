package enums;

public enum RestEndpoint {
    LOGIN("login"),
    START_TRANSACTION("start_transaction"),
    OPEN_TRANSACTION("open_transactions"),
    STOP_TRANSACTION("stop_transaction"),
    TRANSACTION_HISTORY("transaction_history"),
    PARKING_ZONE_DETAILS("parking_zone_details"),
    NEARBY_PARKING_ZONES("nearby_parking_zones"),
    NEARBY_PARKING_ADVICE("nearby_parking_advice"),
    PASSWORD_REST("password_rest"),
    USER_DETAILS("user_details"),
    DISCLAIMER("disclaimer"),
    LOCATIONS("locations"),
    LICENSE_PLATE_COUNTRIES("license_plate_countries"),
    SIGNUP_REQUEST("signup_request"),
    NEWS("news"),
    LOCALIZED_MESSAGES("localized_messages"),
    UPDATE_USER("update_user");



    private final String text;

    RestEndpoint(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
