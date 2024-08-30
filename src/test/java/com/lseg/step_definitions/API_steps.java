package com.lseg.step_definitions;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class API_steps {

    private Response response;
    private static final Logger log = LogManager.getLogger(API_steps.class);

    @Given("Get call to {string}")
    public void getCallTo(String url) throws URISyntaxException {
        RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1/";
        RequestSpecification requestSpecification = RestAssured.given();
        response = requestSpecification.when().get(new URI(url));
        log.info("Get call {}", url);

    }

    @Then("Response is {string}")
    public void responseIs(String statusCode) {
        int actualResponseCode = response.then().extract().statusCode();
        Assert.assertEquals(statusCode, String.valueOf(actualResponseCode));
        response.prettyPrint();
    }

    @And("The Employee {string}")
    public void theEmployee(String employeeName) {
        String actualEmployeeName = response.then().extract().body().jsonPath().getString("data.employee_name");
        System.out.println(actualEmployeeName);
        Assert.assertEquals(actualEmployeeName, employeeName);
    }
}
