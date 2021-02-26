
/**
 * @author Mahsa Sadi
 * @since 2021 - 02 - 25
 * 
 * 
 * This project is about connecting to a REST API and getting a response. 
 * This connection can  be both synchronous and asynchronous.
 * Setting up connection in JAva 8 and Java 11 differs.
 * 
 */
public class Main {

	public static void main(String[] args) {

		// 1- Working with the sync version - Java 8:
		SyncRestAPIV1 gitAPI1 = new SyncRestAPIV1();
		gitAPI1.connect("https://api.github.com/users/m-h-s/repos");
		gitAPI1.setRequestType("GET");
		String response1 = gitAPI1.getResponse();
		gitAPI1.parseJSONResponse(response1);

		// 2- working with the async version - Java 11:
		AsyncRestAPI gitAPI2 = new AsyncRestAPI();
		String response2 = gitAPI2.connect("https://api.github.com/users/m-h-s/repos");
		gitAPI2.parseJSONResponse(response2);

		// 3- working with the sync version - Java 11:
		SyncRestAPIV2 gitAPI3 = new SyncRestAPIV2();
		String response3 = gitAPI2.connect("https://api.github.com/users/m-h-s/repos");
		gitAPI2.parseJSONResponse(response3);
	}

}
