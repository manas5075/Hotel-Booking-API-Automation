
package runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")

@ConfigurationParameter(
    key = GLUE_PROPERTY_NAME,
    value = "stepdefinitions,hooks"
)

public class TestRunner {

}
