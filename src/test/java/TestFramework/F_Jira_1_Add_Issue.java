package TestFramework;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import jiraAPI.payloads;
import jiraAPI.resources;

public class F_Jira_1_Add_Issue {

	public static Logger log = LogManager.getLogger(F_Jira_1_Add_Issue.class.getName());

	Properties prop = new Properties();

	@BeforeTest
	public void getDataProp() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\env.properties");
		prop.load(fis);
	}

	@Test
	public void JiraAPICreateIssue() {

		// First we need to create the session
		log.info("Here starts the CreateIssue Request Test");
		RestAssured.baseURI = prop.getProperty("HOSTJIRA");
		log.info("The host is: " + prop.getProperty("HOSTJIRA"));
		Response res = given().header("Content-Type", "application/json")
				.header("cookie", "JSESSION=" + reusableMethods.getSessionID()).body(payloads.bodyCreateIssue()).log()
				.body().when().post(resources.resCreateIssue()).then().log().body().assertThat().statusCode(201)
				.extract().response();
		
		log.info("Body sent on the POST request:");
		log.info(payloads.bodyCreateIssue());
		log.info(res);

		JsonPath jsp = reusableMethods.rawToJson(res);
		log.info(jsp);
		String IssueID = jsp.get("id");
		log.info("Issue ID is: " + IssueID);
		log.info("Here ends the CreateIssue Request Test");
		//return IssueID;

	}

}
