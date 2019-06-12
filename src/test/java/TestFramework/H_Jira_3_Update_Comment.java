package TestFramework;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import TestFramework.reusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jiraAPI.payloads;
import jiraAPI.resources;

import static io.restassured.RestAssured.given;

public class H_Jira_3_Update_Comment {

	public static Logger log = LogManager.getLogger(F_Jira_1_Add_Issue.class.getName());
	Properties prop = new Properties();

	@BeforeTest
	public void getDataProp() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\env.properties");
		prop.load(fis);
	}

	@Test
	public void JiraAPIUpdate() throws IOException {

		// We create the issue
		log.info("Here starts the CreateIssue Request Test");
		RestAssured.baseURI = prop.getProperty("HOSTJIRA");
		log.info("The host is: " + prop.getProperty("HOSTJIRA"));
		Response resIss = given().header("Content-Type", "application/json")
				.header("cookie", "JSESSIONID=" + reusableMethods.getSessionID()).body(payloads.bodyCreateIssue()).log()
				.body().when().post(resources.resCreateIssue()).then().log().body().assertThat().statusCode(201)
				.extract().response();

		log.info("Body sent on the POST request:");
		log.info(payloads.bodyCreateIssue());
		log.info(resIss);

		JsonPath jspIss = reusableMethods.rawToJson(resIss);
		log.info(jspIss);
		String IssueID = jspIss.get("id");
		log.info("Issue ID is: " + IssueID);
		log.info("Here ends the CreateIssue Request Test");

		// Then we create a comment on than issue
		log.info("Here starts the CreateComment Request Test");
		RestAssured.baseURI = prop.getProperty("HOSTJIRA");
		log.info("The host is: " + prop.getProperty("HOSTJIRA"));
		Response resCom = given().header("Content-Type", "application/json")
				.header("cookie", "JSESSIONID=" + reusableMethods.getSessionID()).body(payloads.bodyCreateComment())
				.log().body().when().post(resources.resCreateComment(IssueID)).then().log().body().assertThat()
				.statusCode(201).extract().response();

		JsonPath jspCom = reusableMethods.rawToJson(resCom);
		String CommentID = jspCom.get("id");
		log.info("Comment ID is: " + CommentID);

		// First we need to create the session
		RestAssured.baseURI = prop.getProperty("HOSTJIRA");
		Response res = given().header("Content-Type", "application/json")
				.header("cookie", "JSESSIONID=" + reusableMethods.getSessionID()).body(payloads.bodyUpdateComment())
				.log().body().when().log().all().put("/rest/api/2/issue/" + IssueID + "/comment/" + CommentID).then()
				.log().all().assertThat().statusCode(200).extract().response();

		JsonPath jsp = reusableMethods.rawToJson(res);
		// String IssueID = jsp.get("id");
		// System.out.println(IssueID);

	}

}
