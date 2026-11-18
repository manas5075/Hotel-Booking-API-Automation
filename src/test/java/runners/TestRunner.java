
package runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.core.options.Constants.FILTER_TAGS_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")

@ConfigurationParameter(
    key = GLUE_PROPERTY_NAME,
    value = "stepdefinitions"
)

@ConfigurationParameter(
    key = PLUGIN_PROPERTY_NAME,
    value = "pretty, html:target/cucumber-reports.html, json:target/cucumber.json"
)

@ConfigurationParameter(
    key = FILTER_TAGS_PROPERTY_NAME,
    value = "@BookingAPI"
)

public class TestRunner {

}