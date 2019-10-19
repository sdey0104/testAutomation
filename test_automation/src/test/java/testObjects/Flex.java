package testObjects;

import dto.ProductGroup;
import frameworkComponents.AutomationComponent;
import lombok.Data;
import org.openqa.selenium.By;

@Data
public class Flex extends ProductGroup {

    AutomationComponent automationComponent = new AutomationComponent();

    public Flex(String providerCode) {

        super(providerCode);

        setPayplanType("FLEX");
        loadDataFromProperty();
    }


    @Override
    public boolean isEquivalentTo(String path) throws InterruptedException {
        boolean ret = true;
        ret &= automationComponent.retrieveTextFromElement( By.xpath(path + "/div[2]/div[1]/div[2]/h2") ).equals(getMinTransactionFee());
        ret &= automationComponent.retrieveTextFromElement( By.xpath(path + "/div[2]/div[2]/div[2]/h2") ).equals(getMinTransactionFee());
        return ret;
    }
}
