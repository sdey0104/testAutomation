package factory;

import base.WebBase;
import testObjects.Basic;
import testObjects.Flex;
import testObjects.Subscription;
import dto.ProductGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductgroupFactory extends WebBase {

    private static class FactoryHolder {
        private static final ProductgroupFactory holder = new ProductgroupFactory();
    }

    private ProductgroupFactory() {
        //almost empty!
    }

    public static ProductgroupFactory getInstance() {
        return FactoryHolder.holder;
    }

    /**
     *
     * @param providerCode
     * @return
     */
    public ArrayList<ProductGroup> createAllProductgroup(String providerCode) {
        ArrayList<ProductGroup> allProductgroup = new ArrayList<>();
        ArrayList<String> payplanTypeList = new ArrayList<>(Arrays.asList(getValue(providerCode + "_ALL_PAYPLAN").split(",")));
        for (String payplanType : payplanTypeList) {
            allProductgroup.add( createProductgroup(providerCode, payplanType) );
        }
        return allProductgroup;
    }

    public ProductGroup createProductgroup(String providerCode, String payplanType) {
        ProductGroup productGroup  = null;
        switch (payplanType) {
            case "BASC" :
                productGroup = new Basic(providerCode);
                break;
            case "FLEX" :
                productGroup = new Flex(providerCode);
                break;
            case "SUBC" :
                productGroup = new Subscription(providerCode);
                break;
            default:
                productGroup = new Basic(providerCode);
        }
        return productGroup;
    }
}
