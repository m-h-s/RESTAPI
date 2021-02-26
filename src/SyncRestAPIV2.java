import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.json.JSONArray;
import org.json.JSONObject;

public class SyncRestAPIV2 {

	public SyncRestAPIV2() {

	}

	// This Async version is difficult to work with. Incomplete.
	public String connect(String address) {

		String responseString = "";

		HttpClient client = HttpClient.newHttpClient();
		URI uri = URI.create(address);
		HttpRequest request = HttpRequest.newBuilder().GET().header("accept", "application/json").uri(uri).build();
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			responseString = response.body();
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
