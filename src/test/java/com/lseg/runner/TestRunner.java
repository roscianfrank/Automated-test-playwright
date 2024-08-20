package com.lseg.runner;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.lseg.step_definitions"},
        publish = false,
        plugin = {"pretty","json:target/json-report.json","html:target/cucumber-reports.html"},
        tags = "@API"

)
public class TestRunner {

}

