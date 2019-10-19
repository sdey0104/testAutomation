package testObjects;

import dto.ProductGroup;
import frameworkComponents.AutomationComponent;
import lombok.Data;
import org.openqa.selenium.By;

@Data
public class Basic extends ProductGroup {

    AutomationComponent automationComponent = new AutomationComponent();

    public Basic(String providerCode) {

        super(providerCode);

        setPayplanType("BASC");
        loadDataFromProperty();
    }

    @Override
    public boolean isEquivalentTo(String path) throws InterruptedException {
        boolean ret = true;
        ret &= automationComponent.retrieveTextFromElement(By.xpath(path + "/div[2]/div[2]/h2") ).equals(getMaxTransactionFree());
        return ret;
    }
}
