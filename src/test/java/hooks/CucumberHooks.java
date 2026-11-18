
package hooks;

import context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class CucumberHooks {

    private final ScenarioContext scenarioContext;

    public CucumberHooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println("Starting scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println("Finished scenario: " + scenario.getName());
        System.out.println("Scenario status: " + scenario.getStatus());

        scenarioContext.clear();
    }
}
