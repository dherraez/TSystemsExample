package TestFramework;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import jiraAPI.payloads;
import jiraAPI.resources;

public class reusableMethods {

	static Properties prop = new Properties();

	public static Properties getDataProp() throws IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\env.properties");
		prop.load(fis);
		return prop;
	}

	public static XmlPath rawToXml(Response rawdata) {
		String Sresponse = rawdata.asString();
		XmlPath xp = new XmlPath(Sresponse);
		return xp;

	}

	public static JsonPath rawToJson(Response rawdata) {
		String Sresponse = rawdata.asString();
		JsonPath xp = new JsonPath(Sresponse);
		return xp;

	}

	public static String getSessionID() {

		// First we need to create the session
		RestAssured.baseURI = "http://localhost:8080";
		Response res = given().header("Content-Type", "application/json")
				.body("{ \"username\": \"davidhg\", \"password\": \"davo1986\" }").when().post("/rest/auth/1/session")
				.then().log().body().assertThat().statusCode(200).extract().response();

		JsonPath JiraID = reusableMethods.rawToJson(res);
		String SessionID = JiraID.get("session.value");
		return SessionID;

	}

	public static String getCommentID() throws IOException {

		// Adding a comment
		RestAssured.baseURI = "http://localhost:8080";
		Response res = given().header("Content-Type", "application/json")
				.header("cookie", "JSESSIONID=" + reusableMethods.getSessionID())
				.body("{\r\n"
						+ "    \"body\": \"This is a comment regarding the quality of the response for issue.\"\r\n"
						+ "}")
				.log().body().when().post("/rest/api/2/issue/"+getIssueID()+"/comment").then().log().body().
				// assertThat().statusCode(201).
				extract().response();

		JsonPath jsp = reusableMethods.rawToJson(res);
		String CommentID = jsp.get("id");
		return CommentID;

	}

	public static String getIssueID() throws IOException {

		RestAssured.baseURI = getDataProp().getProperty("HOSTJIRA");
		Response res = given().header("Content-Type", "application/json")
				.header("cookie", "JSESSIONID=" + reusableMethods.getSessionID()).body(payloads.bodyCreateIssue()).log()
				.body().when()
				.post(resources.resCreateIssue()).then().log().body().assertThat().statusCode(201)
				.extract().response();

		JsonPath jsp = reusableMethods.rawToJson(res);
		String IssueID = jsp.get("id");
		return IssueID;

	}
	

}
