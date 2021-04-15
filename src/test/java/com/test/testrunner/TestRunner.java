package com.test.testrunner;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        plugin = {"html: target/cucumber.html"},
        tags = {"@run"},
        glue = {"com/test/stepDefs/"},
        features = "src/test/resources/Features")

public class TestRunner {
}
