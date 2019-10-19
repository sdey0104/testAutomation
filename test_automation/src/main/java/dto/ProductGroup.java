package dto;

import base.WebBase;
import lombok.Data;

import java.util.ArrayList;

@Data
public abstract class ProductGroup extends WebBase {

    private String productGroupId;
    private String providerCode;
    private String payplanType;
    private String lblProductgroupName;
    private String minTransactionFee;
    private String maxTransactionFree;
    private String subscriptionFee;
    private ArrayList<String> switchingOptions;
    private ArrayList<String> CutomerNumberList;

    public ProductGroup(String providerCode) {
        super("data.properties");
        this.providerCode = providerCode;
    }

    public abstract boolean isEquivalentTo(String path) throws InterruptedException;

    protected void loadDataFromProperty() {
        setProductGroupId( getValue(COUNTRY + "_" + getProviderCode() + "_" + getPayplanType() + "_ProductgroupId", "") );
        setLblProductgroupName( getValue(COUNTRY + "_" + getProviderCode() + "_" + getPayplanType() + "_DisplayLabelText") );
        setMinTransactionFee( getValue(COUNTRY + "_" + getProviderCode() + "_" + getPayplanType() + "_MinTransactionFee") );
        setMaxTransactionFree( getValue(COUNTRY + "_" + getProviderCode() + "_" + getPayplanType() + "_MaxTransactionFee") );
        setSubscriptionFee( getValue(COUNTRY + "_" + getProviderCode() + "_" + getPayplanType() + "_SubscriptionFee") );
    }
}
