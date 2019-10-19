package frameworkComponents;

import base.WebBase;
import config.CommonConfig;
import constants.CommonConstants;
import cucumber.api.DataTable;
import dto.*;
import enums.PaymentMethod;
import dto.ProductGroup;
import factory.ProductgroupFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dto.Customer;
import dto.DirectDebitDetails;
import dto.IVRCustomerdetails;
import enums.PaymentMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.slf4j.LoggerFactory;
import org.testng.Assert;
import stepDefinitions.SmokeStepDefinition;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.Date;

public class DatabaseConnections extends WebBase {

    public DatabaseConnections() {
        super(CommonConfig.getInstance().getValue(CommonConstants.WEB_AUTOMATION_FILE));
    }

    private static Logger log = LogManager.getLogger(WebBase.class.getName());
    private static String SCHEMA_BRICKPARKING = "BRICKPARKING";
    private static String SCHEMA_BILLING = "BILLING";
    Statement statement = null;
    ResultSet resultset = null;
    ResultSet resultset2 = null;
    Connection conn;
    IVRCustomerdetails ivrCustomerdetails = new IVRCustomerdetails();
    org.slf4j.Logger logger = LoggerFactory.getLogger(SmokeStepDefinition.class);

    public Connection connection(String schema) throws SQLException, IOException {
        String host = getValue("databasehost");
        String user = getValue(schema + "_databaseuser");
        String password = getValue(schema + "_databasepassword");

        try {
            Class.forName("oracle.jdbc.OracleDriver").newInstance();
            conn = DriverManager.getConnection(host, user, password);
            log.info("connection succesfully made");
            System.out.println("connection succesfully made");
        } catch (Exception e) {
            log.info("connection failed", e);
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        return conn;
    }

    //  Checking if a Business customer registers with a blacklisted Name, DateofBirth and License Plate
    public void Administrator_checkCustomerDetails(DataTable userdata, String fieldtovalidate) throws InterruptedException, SQLException, IOException, ParseException {
        String query = "";
        Customer customer = new Customer();
        for (Map<String, String> userDetails : userdata.asMaps(String.class, String.class)) {
            // Checking blacklisting conditions for a Private customer
            if (fieldtovalidate.equals("Private-Blacklisted")) {
                //Converting a string to date format
                Date birthdate = new SimpleDateFormat("dd-MM-yyyy").parse(userDetails.get("Birthday"));
                DateFormat outputDF = new SimpleDateFormat("dd MMM yyyy");
                String dateofbirth = outputDF.format(birthdate);
                query = "select * from customer,Customer_cars_details where CUSTOMERSTATUSIDFK=" + userDetails.get("Status") + " " +
                        "and FIRSTNAME='" + userDetails.get("Firstname") + "' " +
                        "and LASTNAME='" + userDetails.get("Lastname") + "' " +
                        "and DATEOFBIRTH='" + dateofbirth + "' " +
                        "and Licenseplate='" + userDetails.get("Licenseplate") + "' " +
                        "and Customer_ID=CustomerID";
                statement = connection(SCHEMA_BRICKPARKING).createStatement();
                resultset = statement.executeQuery(query);
                while (resultset.next()) {
                    if (userDetails.get("Status").equals("1")) {
                        try {
                            customer.setNumber(resultset.getString("CUSTOMERNR"));
                            // Check if the retrieved Customer number is empty
                            Assert.assertEquals(customer.getNumber(), null);
                            logger.info("Customer number is null for customer registered with Blacklisted Name, DateofBirth and LicensePlate");
                        } catch (Exception ex) {
                            logger.info("Customer number is not null for customer registered with Blacklisted Name, DateofBirth and LicensePlate");
                        }
                    }
                    // Initial check if the mentioned details are present in DB
                    else {
                        logger.info("Customer with Blacklisted Name, Date of Birth and LicensePlate is present in database");
                    }
                }
            }
            // Checking if a Business customer registers with a blacklisted email/Phone number
            if (fieldtovalidate.equals("Business-Blacklisted")) {
                query = "select * from customer where CUSTOMERSTATUSIDFK=" + userDetails.get("Status") + " " +
                        "and PHONENR='" + userDetails.get("Phonenumber") + "' ";
                statement = connection(SCHEMA_BRICKPARKING).createStatement();
                resultset = statement.executeQuery(query);
                // Check if the query has returned a row from DB
                while (resultset.next()) {
                    if (userDetails.get("Status").equals("1")) {
                        try {
                            customer.setNumber(resultset.getString("CUSTOMERNR"));
                            // Check if the retrieved Customer number is empty
                            Assert.assertEquals(customer.getNumber(), null);
                            logger.info("Customer number is null for customer registered with Blacklisted Email and PhoneNumber");
                        } catch (Exception ex) {
                            logger.info("Customer number is not null for customer registered with Blacklisted Email and PhoneNumber");
                        }
                    }
                    // Initial check if the mentioned details are present in DB
                    else {
                        logger.info("Customer with Blacklisted Email and Phone number is present in database");
                    }
                }
            }
            // Checking if customer has registered with an empty VAT/COC
            if (fieldtovalidate.equals("VAT-COC-Empty")) {
                query = "select value from customer_identification " +
                        "where customeridfk=(select customerid from customer " +
                        "where mutation_date=(select max(mutation_date) from customer) " +
                        "and FIRSTNAME='" + userDetails.get("Firstname") + "' and LASTNAME='" + userDetails.get("Lastname") + "')";
                statement = connection(SCHEMA_BRICKPARKING).createStatement();
                resultset = statement.executeQuery(query);
                // Check if the query has returned a row from DB
                while (resultset.next()) {
                    customer.setValue(resultset.getString("VALUE"));
                    // Check if the value of COC/VAT is empty in database
                    try {
                        Assert.assertEquals(customer.getValue(), null);
                        logger.info("Value of mentioned COC/VAT is null in database");
                    } catch (Exception ex) {
                        logger.info("Value of mentioned COC/VAT is not null in database");
                    }
                }
            }

            //Checking if customer has registered with a suspect license plate and has given status
            if (fieldtovalidate.equals("SUSPECT_LICENSE_PLATE")) {
                // Check if the Suspect license plate is present in the customer data registered in database with status mentioned
                if (userDetails.get("Status").equals("1")) {
                    query = "select * from customer,Customer_cars_details " +
                            "and Licenseplate='" + userDetails.get("Licenseplate") + "' " +
                            "and customerid=(select max(customerid) from customer where customerstatusidfk='1') " +
                            "and Firstname='" + userDetails.get("Firstname") + "' and Lastname='" + userDetails.get("Lastname") + "' " +
                            "and Customer_ID=CustomerID";
                    statement = connection(SCHEMA_BRICKPARKING).createStatement();
                    resultset = statement.executeQuery(query);
                    // Check if the customer number is null from the retrieved row
                    while (resultset.next()) {
                        try {
                            customer.setNumber(resultset.getString("CUSTOMERNR"));
                            // Check if the retrieved Customer number is empty
                            Assert.assertEquals(customer.getNumber(), null);
                            logger.info("Customer number is null for customer registered with Suspect license plate");
                        } catch (Exception ex) {
                            logger.info("Customer number is not null for customer registered with Suspect license plate");
                        }
                    }
                }
                // Initial check if the Suspect license plate is present in suspect list in Database
                else {
                    query = "select * from suspect_lp where License_plate='" + userDetails.get("Licenseplate") + "' ";
                    statement = connection(SCHEMA_BRICKPARKING).createStatement();
                    resultset = statement.executeQuery(query);
                    // Check if the query has returned a row from DB
                    while (resultset.next()) {
                        logger.info("License plate mentioned is present in suspect list");
                    }
                }
            }
            //Checking if customer has registered with an existing COC/VAT and has given status
            if (fieldtovalidate.equals("COC/VAT_EXISTS")) {
                // Retrieve the customer details with COC Value
                query = "select * from customer_identification,customer  where " +
                        "FIRSTNAME='" + userDetails.get("Firstname") + "' " +
                        "and LASTNAME='" + userDetails.get("Lastname") + "'  " +
                        "and fieldidfk='1' " +
                        "and value='" + userDetails.get("COC") + "' " +
                        "and CUSTOMER.CUSTOMERID=CUSTOMER_IDENTIFICATION.CUSTOMERIDFK";
                statement = connection(SCHEMA_BRICKPARKING).createStatement();
                resultset = statement.executeQuery(query);
                // Check if the query has returned a row from DB
                while (resultset.next()) {
                    logger.info("Value of mentioned COC is present in database with status " + userDetails.get("Status") + "");
                }
                // Retrieve the customer details with VAT Value
                query = "select * from customer_identification,customer  where " +
                        "FIRSTNAME='" + userDetails.get("Firstname") + "' " +
                        "and LASTNAME='" + userDetails.get("Lastname") + "'  " +
                        "and fieldidfk='3' " +
                        "and value='" + userDetails.get("VAT") + "' " +
                        "and CUSTOMER.CUSTOMERID=CUSTOMER_IDENTIFICATION.CUSTOMERIDFK";
                resultset = statement.executeQuery(query);
                // Check if the query has returned a row from DB
                while (resultset.next()) {
                    logger.info("Value of mentioned VAT is present in database with status " + userDetails.get("Status") + "");
                }
            }

            // Checking if customer has registered with blacklisted keyword
            if (fieldtovalidate.equals("KEYWORDS_CHECK")) {
                // module to check if customer status is 1 for registered customer
                if (userDetails.containsKey("Status")) {
                    query = "select * from customer,customer_cars_details where " +
                            "licenseplate='"+userDetails.get("Keyword")+"' and " +
                            "customerid=(select max(customerid) from customer where customerstatusidfk='1') " +
                            "and customer_id=customerid";
                    statement = connection(SCHEMA_BRICKPARKING).createStatement();
                    resultset = statement.executeQuery(query);
                    while (resultset.next()) {
                        try {
                            customer.setNumber(resultset.getString("CUSTOMERNR"));
                            // Check if the retrieved Customer number is empty
                            Assert.assertEquals(customer.getNumber(), null);
                            logger.info("Customer number is null for customer registered with Blacklisted keyword");
                        } catch (Exception ex) {
                            logger.info("Customer number is not null for customer registered with Blacklisted keyword");
                        }
                    }
                }
                // module to check if keyword is present in blacklist
                else
                {
                    // Retrieve the customer details with COC Value
                    query = "select * from validation_keywords where keyword='"+userDetails.get("Keyword")+"'";
                    statement = connection(SCHEMA_BRICKPARKING).createStatement();
                    resultset = statement.executeQuery(query);
                    // Check if the query has returned a row from DB
                    while (resultset.next()) {
                        logger.info("Keyword "+userDetails.get("Keyword")+" is Blacklisted in Database");
                    }
                }
            }
            //Checking if customer has registered with an existing IBAN and has given status
            if (fieldtovalidate.equals("IBAN_EXISTS")) {
                if (userDetails.get("Status").equals("98")) {
                    query = "select * from customer where " +
                            "customerstatusidfk='98' ";
                    statement = connection(SCHEMA_BRICKPARKING).createStatement();
                    resultset = statement.executeQuery(query);
                    while (resultset.next()) {
                        query = "select * from payment_direct_debit_details where " +
                                "customerid='" + resultset.getString("CUSTOMERID") + "' ";
                        statement = connection(SCHEMA_BILLING).createStatement();
                        resultset2 = statement.executeQuery(query);
                        if (resultset2.next()) {
                            logger.info("Customer exists with mentioned IBAN and status 98");
                            break;
                        }
                        else
                        {
                            continue;
                        }
                    }
                } else {
                    query = "select * from customer where " +
                            "customerID=(select MAX(Customerid) from customer " +
                            "where customerstatusidfk=1 and business='N')";
                    statement = connection(SCHEMA_BRICKPARKING).createStatement();
                    resultset = statement.executeQuery(query);
                    while (resultset.next()) {
                        query = "select * from payment_direct_debit_details where " +
                                "customerid='" + resultset.getString("CUSTOMERID") + "' ";
                        statement = connection(SCHEMA_BILLING).createStatement();
                        resultset = statement.executeQuery(query);
                        try {
                            while (resultset.next()) {
                                Assert.assertEquals(resultset.getString("SEPANUMBER"), userDetails.get("IBAN"));
                                logger.info("Customer has registered with mentioned IBAN and status 1");
                            }
                        } catch (Exception ex) {
                            logger.info("Customer has registered with mentioned IBAN and status is not 1");
                        }
                    }
                }
            }
        }
    }

    public String CustomerDetails(String columnName, String value) throws SQLException, IOException {
        String customerNR = "";
        statement = connection(SCHEMA_BRICKPARKING).createStatement();
        resultset = statement.executeQuery("select * from customer where " + " " + columnName + "=" + value);

        while (resultset.next()) {
            customerNR = resultset.getString("CUSTOMERNR");
        }
        return customerNR;
    }

    public Customer getCustomer(String customerNr) throws SQLException, IOException {
        Customer customer = new Customer();
        statement = connection(SCHEMA_BRICKPARKING).createStatement();
        resultset = statement.executeQuery("select * from customer where CUSTOMERNR=" + customerNr);

        while (resultset.next()) {
            customer.setCustomerId(resultset.getLong("CUSTOMERID"));
            customer.setProductGroupId(resultset.getString("PRODUCTGROUP_ID"));
            customer.setPPluscount(resultset.getString("NUMBEROFQCARDS"));

        }
        return customer;
    }

    public Customer getCustomerDetails(String customerNr) throws SQLException, IOException, Exception {
        Customer customer = new Customer();
        statement = connection(SCHEMA_BRICKPARKING).createStatement();

        // Get general info
        resultset = statement.executeQuery("select * from customer where CUSTOMERNR=" + customerNr);
        while (resultset.next()) {
            customer.setId(resultset.getInt("CUSTOMERID"));
            customer.setNumber(resultset.getString("CUSTOMERNR"));
            customer.setProductGroupId(resultset.getString("productgroup_id"));
            customer.setCustomerId(resultset.getLong("CUSTOMERID"));
            customer.setBusiness(resultset.getString("BUSINESS"));
            customer.setBusinessName(resultset.getString("BUSINESSNAME"));
            customer.setGender(resultset.getString("GENDER"));
            customer.setInitials(resultset.getString("INITIALS"));
            customer.setFirstName(resultset.getString("FIRSTNAME"));
            customer.setInfix(resultset.getString("INFIX"));
            customer.setLastName(resultset.getString("LASTNAME"));
            String birthday = resultset.getString("DATEOFBIRTH");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(birthday);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            customer.setDateOfBirth(dateFormat.format(date));
            customer.setEmail(resultset.getString("EMAIL"));
            customer.setPhoneNr(resultset.getString("PHONENR"));
            customer.setCorrespondenceLocale(resultset.getString("CORRESPONDENCE_LOCALE"));
        }

        // Get KvK-nummer and BTW-identificatienummer
        resultset = statement.executeQuery("select * from customer_identification where customeridfk =" + customer.getCustomerId());
        while (resultset.next()) {
            if (resultset.getString("FIELDIDFK").equals("1")) {
                customer.setBusinessIds0(resultset.getString("VALUE"));
            }
            if (COUNTRY.equals("BE")){
                if (resultset.getString("FIELDIDFK").equals("2")) {
                    customer.setBusinessIds1(resultset.getString("VALUE"));
                }
            } else if (COUNTRY.equals("NL")){
                if (resultset.getString("FIELDIDFK").equals("3")) {
                    customer.setBusinessIds1(resultset.getString("VALUE"));
                }
            }

        }

        // Get address
        resultset = statement.executeQuery("select * from customeraddress where customeridfk =" + customer.getCustomerId());
        while (resultset.next()) {
            if (resultset.getString("ADDRESSTYPEIDFK").equals("1")) {
                customer.setLocationAddress1(resultset.getString("ADDRESSLINE1"));
                customer.setLocationAddress2(resultset.getString("ADDRESSLINE2"));
                customer.setZipCode(resultset.getString("ZIPCODE"));
                customer.setCity(resultset.getString("CITY"));
                customer.setCountryDropdown(resultset.getString("COUNTRYCODE"));
            }
            if (resultset.getString("ADDRESSTYPEIDFK").equals("2")) {
                customer.setHavingBillingAddress1("false");
                customer.setHavingBillingAddress2("true");
                customer.setBillingAddressAddressLine1(resultset.getString("ADDRESSLINE1"));
                customer.setBillingAddressAddressLine2(resultset.getString("ADDRESSLINE2"));
                customer.setBillingAddressZipCode(resultset.getString("ZIPCODE"));
                customer.setBillingAddressCity(resultset.getString("CITY"));
                customer.setBillingAddressCountryCode(resultset.getString("COUNTRYCODE"));
            }
        }

        return customer;
    }

    public DirectDebitDetails getDirectDebitDetails(long customerId) throws SQLException, IOException {
        DirectDebitDetails details = new DirectDebitDetails();
        statement = connection(SCHEMA_BILLING).createStatement();
        resultset = statement.executeQuery("select * from PAYMENT_DIRECT_DEBIT_DETAILS where CUSTOMERID = "
                + customerId);
        while (resultset.next()) {
            details.setAccountholder(resultset.getString("ACCOUNTHOLDERNAME"));
            details.setSepa(resultset.getString("SEPANUMBER"));
        }
        return details;
    }

    public boolean creditCardReferenceExists(long customerId) throws SQLException, IOException {
        statement = connection(SCHEMA_BILLING).createStatement();
        resultset = statement.executeQuery("select * from BILLING_TABLE_1 where TELEPAY_CUSTOMER_ID = "
                + customerId);

        return resultset.next();
    }

    public PaymentMethod getPaymentMethod(long customerId) throws SQLException, IOException {
        statement = connection(SCHEMA_BILLING).createStatement();
        resultset = statement.executeQuery("select * from PAYMENT_DETAILS where ID_CUSTOMER = " + customerId);

        while (resultset.next()) {
            return PaymentMethod.fromInt(Integer.valueOf(resultset.getString("ID_PAYMENT_METHOD")));
        }
        return null;
    }

    public IVRCustomerdetails getCustomerDetailsForIVR() throws SQLException, IOException {

        statement = connection(SCHEMA_BRICKPARKING).createStatement();
        resultset = statement.executeQuery("select c.customernr, t.licenseplate, m.mobilenr from customer c inner join mobile m on c.customerid=m.customeridfk inner join transpondercard t on c.customerid=t.customeridfk" +
                "   and not exists (select * from mobile m2 where m2.mobileid<>m.mobileid and m2.mobilenr=m.mobilenr)" +
                "   and t.transpondercardid = m.transpondercardidfk" +
                "   and t.cardstatusidfk=1" +
                "   and c.customerstatusidfk=2" +
                "   and t.licenseplate is not null and length(trim(t.licenseplate)) > 3" +
                "   and m.mobilenr is not null and trim(m.mobilenr) is not null and m.mobilenr not like '++%' and m.mobilenr not like '+00%' and length(trim(m.mobilenr)) > 6" +
                "   and rownum=1");
        while (resultset.next()) {
            ivrCustomerdetails.setCustomerNr(resultset.getString("CUSTOMERNR"));
            ivrCustomerdetails.setLicensePlate(resultset.getString("LICENSEPLATE"));
            ivrCustomerdetails.setMobileNr(resultset.getString("MOBILENR"));
        }
        return ivrCustomerdetails;
    }

    public void getZoneCode() throws SQLException, IOException {

        statement = connection(SCHEMA_BRICKPARKING).createStatement();
        resultset = statement.executeQuery(" select z.zonecode from tblparkeerzone z inner join tbltariefperiode p on p.tariefidfk=z.tariefidfk inner join tbltariefregel r on r.tariefperiodeidfk=p.tariefperiodeid" +
                " where p.tariefgeldigvanaf < sysdate and p.tariefgeldigtot > sysdate" +
                "   and to_char(sysdate,'DAY','NLS_DATE_LANGUAGE=''numeric date language''') = r.tariefDagTypeIdFK" +
                "   and to_char(sysdate,'hh24:mi') between to_char(r.tariefbegintijd,'hh24:mi') and to_char(r.tariefeindtijd,'hh24:mi')" +
                "   and rownum=1");
        while (resultset.next()) {
            ivrCustomerdetails.setZoneCode(resultset.getString("ZONECODE"));
        }

    }

    public Mobile getMobileDetails(String mobileNumber) throws Exception {
        Mobile mobile = new Mobile();
        statement = connection(SCHEMA_BRICKPARKING).createStatement();
        String a="select * from mobile " +
        "where mobileNr = '" + mobileNumber +"' ";
        resultset = statement.executeQuery(
                "select * from mobile,customer " +
                        "where mobileNr = '" + mobileNumber +"' and MOBILE.CUSTOMERIDFK=CUSTOMER.CUSTOMERID ");
        while (resultset.next()) {
            mobile.setType(resultset.getString("BUSINESS"));
            mobile.setSms(resultset.getString("SMS"));
            mobile.setMobileNr(resultset.getString("MOBILENR"));
            mobile.setSmsInterval(resultset.getFloat("SMSINTERVAL"));
            mobile.setSmsBeforeEnd(resultset.getLong("SMSBEFOREEND"));
            mobile.setTransponderCardIdFk(resultset.getLong("TRANSPONDERCARDIDFK"));
            mobile.setConfirmZone(resultset.getLong("CONFIRM_ZONE"));
            mobile.setTCardSwitchable(resultset.getLong("TCARD_SWITCHABLE"));
        }
        return mobile;
    }

    public ArrayList<ProductGroup> getProductgroupList() throws Exception {
        ArrayList<ProductGroup> productGroupList = new ArrayList<ProductGroup>();
        statement = connection(SCHEMA_BRICKPARKING).createStatement();
        resultset = statement.executeQuery(
                "select * from ( select " +
                        "pg.id productgroup_id, pg.payplan_type," +
                        "(select listagg(payplan_type,', ') within group (order by payplan_type)" +
                        "from productgroup_provider_relation r inner join product_group p on r.productgroup_id = p.id " +
                        "where r.productgroup_provider_id in (select productgroup_provider_id from productgroup_provider_relation where productgroup_id = pg.id)" +
                        "and r.productgroup_id <> pg.id) switching_options," +
                        "(select listagg(customernr,', ') within group (order by customernr)" +
                        "from (" +
                        "select customernr " +
                        "from customer " +
                        "where productgroup_id = pg.id and customerstatusidfk = 2 and business = 'N'" +
                        "order by SYS.dbms_random.value " +
                        ") where rownum <= 5) customer_list " +
                        "from product_group pg " +
                        "where pg.start_date <= sysdate AND  (pg.end_date is null or pg.end_date > sysdate) " +
                        "order by productgroup_id) qr " +
                        "where qr.switching_options is not null and qr.customer_list is not null"

        );
        try {
            while (resultset.next()) {

                ProductGroup productGroup = ProductgroupFactory.getInstance().createProductgroup(
                        BusinessComponent.productgroupToProviderMap.get( resultset.getString("productgroup_id") ),
                        resultset.getString("payplan_type")
                );

                String customerNrString = resultset.getString("customer_list");
                String switchOptionString = resultset.getString("switching_options");
                productGroup.setSwitchingOptions(new ArrayList<String>(Arrays.asList(switchOptionString.split(","))));
                productGroup.setCutomerNumberList(new ArrayList<String>(Arrays.asList(customerNrString.split(","))));
                productGroupList.add(productGroup);
            }
        } catch (Exception e) {
            System.out.println("checkvalue");
        }
        return productGroupList;
    }
    // returns the order details of a transponder card/sleeve/p+pass order from DB
    public CardOrders getcardOrderDetails(String customerNr, String cardType) throws SQLException, IOException {
        String orderStatus="";
        CardOrders order = new CardOrders();
        cardType=cardType.replace("Extra gebruiker","Transponderkaart")
                .replace("RaamSticker","Hoesje")
                .replace("P+ pas","QCARD");
        statement = connection(SCHEMA_BRICKPARKING).createStatement();
        resultset = statement.executeQuery(
                "select * from customer,cardorder where" +
                        " customer.customerid=cardorder.customerid " +
                        " and cardorder.orderID=(SELECT MAX(orderid) FROM cardorder,customer where " +
                        " customer.customernr=" + customerNr + " " +
                        " and cardtype='"+cardType+"'" +
                        " and customer.customerid=cardorder.customerid)"
        );
        while (resultset.next()) {
            order.setStatus(resultset.getString("ORDERSTATUS"));
            order.setAmount(resultset.getString("AMOUNT"));
        }
        return order;
    }

    public void updateemail_blacklisted(String Status) throws SQLException, IOException {
        try {
            statement = connection(SCHEMA_BRICKPARKING).createStatement();
            // retrieve rows having blacklisted email
            resultset = statement.executeQuery("select * from customer where email='" + getValue("blacklisted_email")+"' ORDER BY customernr asc");
            while (resultset.next()) {
                int rs = statement.executeUpdate("update customer set customerstatusidfk='" + Status + "' where customernr='" + resultset.getString("CUSTOMERNR") + "'");
                Assert.assertEquals(rs, 1);
                logger.info("Update of email to blacklisted is successfull");
                // getting out of the loop when one customer (having blacklisted email) status has been updated/removed to blacklisted
                break;
            }
        }
        catch (Exception ex) {
            logger.info("Update of email to blacklisted is unsuccessfull");
        }
    }
}


