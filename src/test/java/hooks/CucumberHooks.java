
package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class CucumberHooks {

    @Before
    public void beforeScenario() {
        System.out.println("Starting API test scenario...");
    }

    @After
    public void afterScenario() {
        System.out.println("Finished API test scenario.");
    }
}
