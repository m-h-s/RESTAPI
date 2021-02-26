import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * @author Mahsa Sadi
 * @since 2021-02-25
 * 
 *        This class sets up a synchronous connection to the REST API provider.
 *        Performs a get request. Receives the response. (in Json format). parse
 *        the json response.
 * 
 * 
 *        This version is compatible with Java 8.
 * 
 * 
 *        #############Important: to work with APIS and rest APIs : java.net.*;
 *        to work with JSon Messages:org.json.*;
 * 
 */

public class SyncRestAPIV1 {

	HttpURLConnection con;

	public SyncRestAPIV1() {

	}

	public void connect(String address) {
		try {
			URL url = new URL(address);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
		}

		catch (Exception ex) {
			System.out.println("Could not open the file");
		}
	}

	public void setRequestType(String request) {
		try {
			con.setRequestMethod(request);
			// Time is provided in milli-second.
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
		}

		catch (Exception ex) {
			System.out.println("Could not set teh request type.");
		}
	}

	public int getConnectionStatus() {
		int stat = 10000;

		try {
			stat = con.getResponseCode();

		} catch (Exception ex) {
			System.out.println("Could not read the status.");
		}

		return stat;
	}

	public String readMessage(InputStream in) {

		String message = "";

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line;
			StringBuffer content = new StringBuffer();

			// StringBuffer content = new StringBuffer();
			while ((line = reader.readLine()) != null) {

				System.out.println(line);
				content.append(line);
			}

			System.out.println(content.toString());
			message = content.toString();
			reader.close();

		}

		catch (Exception ex) {
			System.out.println("Could not read the message.");
		}

		// The connection should be closed after reading the message.
		finally {
			con.disconnect();
		}

		return message;

	}

	public String getResponse() {

		// The response code should be 200. If it is greater than 200, this means that
		// an error has occurred.

		String response = "";

		if (this.getConnectionStatus() > 299) {
			response = readMessage(con.getErrorStream());
		}

		else {
			try {
				response = readMessage(con.getInputStream());
			}

			catch (Exception ex) {
				System.out.println("Could not get the message.");
			}
		}

		return response;

	}

	public void parseJSONResponse(String jsonString) {
		JSONArray gitRepos = new JSONArray(jsonString);

		for (int i = 0; i < gitRepos.length(); i++) {
			JSONObject repo = gitRepos.getJSONObject(i);
			long id = repo.getLong("id");
			String name = repo.getString("name");
			System.out.println(id + ":" + name);
		}
	}

}
