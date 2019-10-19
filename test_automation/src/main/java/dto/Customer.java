package dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Customer {
    private long id;
    private String number;
    private String productGroupId;
    private long customerId;
    private String business;
    private String businessName;
    private String businessIds0;
    private String businessIds1;
    private String gender;
    private String initials;
    private String firstName;
    private String infix;
    private String lastName;
    private String dateOfBirth;
    private String email;
    private String phoneNr;
    private String correspondenceLocale;
    private String locationAddress1;
    private String locationAddress2;
    private String zipCode;
    private String city;
    private String countryDropdown;
    private String havingBillingAddress1;
    private String havingBillingAddress2;
    private String billingAddressAddressLine1;
    private String billingAddressAddressLine2;
    private String billingAddressZipCode;
    private String billingAddressCity;
    private String billingAddressCountryCode;
    private String pPluscount;
    private int statusCode;
    private String value;
    private ArrayList<String> customerNumberList;

}
