package jiraAPI;

public class resources {
	
	public static String resCreateIssue() {
		
		String resource = "/rest/api/2/issue";
		return resource;
	}
	
	public static String resCreateComment(String issueID) {
		
		String resource = "/rest/api/2/issue/"+issueID+"/comment";
		return resource;
	}

}
