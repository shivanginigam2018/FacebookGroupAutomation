package com.test.testrunner;

import cucumber.api.CucumberOptions;
//import cucumber.api.junit.Cucumber;
import cucumber.api.junit.Cucumber;
//import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        tags = {"@run"},
        glue = {"com/test/stepDefs/"},
        features = "src/test/resources/Features",
        plugin = {"json:target/cucumber.json"})

public class TestRunner {
}
