package Tools;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;


////////////////////////////////////////////////////////////////////////////////////////////////////////
public class JsonReader {

	private static String readAll(Reader rd) throws IOException {
		
		StringBuilder sb = new StringBuilder();
	    int cp;
	    
	    while ((cp = rd.read()) != -1) {
	    	sb.append((char) cp);
	    }
	    
	    return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) {
		
		try {
			
			InputStream is = new URL(url).openStream();
		
			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				JSONObject json = new JSONObject(jsonText);
				return json;
			} 
			finally {
				is.close();
			}
			
		}
		catch (UnknownHostException | NoRouteToHostException e) {
			
			e.printStackTrace();
			ErrorAlert.errorAlert("No internet connection!");
			return new JSONObject();	
			
		}
		catch (JSONException | IOException e) {
			
			e.printStackTrace();
			ErrorAlert.errorAlert("Unable to retrieve information!");
			return new JSONObject();
			
		}
		
	}
	
	public static void main(String[] args) throws IOException, JSONException {
		//String url = "";
		//System.out.println(readJsonFromUrl(url).toString());
	}
  
}