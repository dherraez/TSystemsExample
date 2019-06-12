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


public class G_Jira_2_Add_Comment {
	
	public static Logger log = LogManager.getLogger(F_Jira_1_Add_Issue.class.getName());
	Properties prop = new Properties();
	
	
	@BeforeTest
	public void getDataProp() throws IOException {
		
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\env.properties");
		prop.load(fis);
	}
	
	
	@Test
	public void JiraAPICreateComment() throws IOException {
		
		reusableMethods rum = new reusableMethods();
		String IssueID = rum.getIssueID();
		//String IssueID = reusableMethods.JiraAPICreateIssue();
		log.info("Here starts the CreateComment Request Test");
		RestAssured.baseURI = prop.getProperty("HOSTJIRA");
		log.info("The host is: " + prop.getProperty("HOSTJIRA"));
		Response res = given().
		header("Content-Type","application/json").
		header("cookie", "JSESSIONID=" + reusableMethods.getSessionID()).
		body(payloads.bodyCreateComment()).
		log().body().when().
		post(resources.resCreateComment(IssueID)).
		then().log().body().
		assertThat().statusCode(201).
		extract().response();
		
		JsonPath jsp = reusableMethods.rawToJson(res);
		String CommentID = jsp.get("id");
		log.info("Comment ID is: " + CommentID);
		//return CommentID;
		
	}

}
