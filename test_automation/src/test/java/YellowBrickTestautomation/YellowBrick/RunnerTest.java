package YellowBrickTestautomation.YellowBrick;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.*;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/features",
        glue = "stepDefinitions", tags = "@regression", monochrome = true,//dryRun=true,
        plugin = {"pretty", "html:target/cucumber", "json:target/cucumber.json", "junit:target/cukes.xml"}
)
public class RunnerTest {

}
