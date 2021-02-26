import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author Mahsa Sadi
 * @since 2021-02-25
 * 
 * 
 *        This class is compatible with Java 11 and later .
 * 
 *        This class sets up a async connection to a Rest API. Sends a get
 *        request. Receives the response. Parses the response.
 * 
 * 
 *        ########important: parsing JSon using org.json.*.
 *
 */

public class AsyncRestAPI {

	public AsyncRestAPI() {

	}

	// This Async version is difficult to work with. Incomplete.
	public String connect(String address) {

		String responseString = "";

		HttpClient client = HttpClient.newHttpClient();
		URI uri = URI.create(address);
		HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(uri).build();
		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());
		try {
			responseString = response.thenApply(HttpResponse<String>::body).get();
			System.out.println(responseString);
		} catch (Exception ex) {
			System.out.println("Could not connect and receive the resposne");

		}

		return responseString;
	}

	public static void parseJSONResponse(String jsonString) {
		JSONArray gitRepos = new JSONArray(jsonString);

		for (int i = 0; i < gitRepos.length(); i++) {
			JSONObject repo = gitRepos.getJSONObject(i);
			long id = repo.getLong("id");
			String name = repo.getString("name");
			System.out.println(id + ":" + name);
		}
	}

}
