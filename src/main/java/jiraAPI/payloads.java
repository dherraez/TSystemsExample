package jiraAPI;

public class payloads {
	
	//Get the body for the add Issue request
	public static String bodyCreateIssue() {
		
		String body = 	"{\r\n" + 
				"    \"fields\": {\r\n" + 
				"       \"project\":\r\n" + 
				"       {\r\n" + 
				"          \"key\": \"PROJ\"\r\n" + 
				"       },\r\n" + 
				"       \"summary\": \"Issue created for Selenium purposes\",\r\n" + 
				"       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\r\n" + 
				"       \"issuetype\": {\r\n" + 
				"          \"name\": \"Bug\"\r\n" + 
				"       }\r\n" + 
				"   }\r\n" + 
				"}";
		
		return body;
		
	}
	
	public static String bodyCreateComment() {
		
		String body = "{\r\n" + 
				"    \"body\": \"This is a comment regarding the quality of the response for issue 10031.\"\r\n" + 
				"}";
		return body;
	}
	
	public static String bodyUpdateComment() {
		String body = "{\r\n" + 
				"    \"body\": \"This is an updated comment\"\r\n" + 
				"}";
		return body;
	}
	

}
