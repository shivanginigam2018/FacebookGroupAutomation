package com.test.testrunner;

import cucumber.api.CucumberOptions;
//import cucumber.api.junit.Cucumber;
import cucumber.api.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        tags = {"@run1"},
        glue = {"com/test/stepDefs/"},
        features = "src/test/resources/Features",
        plugin = {"json:target/cucumber-report.json"})

public class TestRunner {
}
