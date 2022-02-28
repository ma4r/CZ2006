package Control;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Entity.Location;
import Tools.ErrorAlert;
import Tools.JsonReader;
import Tools.Key;

///////////////////////////////
public class RouteManager {

    private static Location dummy = new Location("Error in getting Lat long", 0, 0);
    
    public static Location getLatLongPositions(String address)
    {

        String api = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address.replaceAll("\\s+","+") + "&components=country:SG&key=" + Key.getKey();
        
        try {
        	JSONObject result = JsonReader.readJsonFromUrl(api);
            if (result.length() == 0) {
                return dummy;
            }
            JSONArray rslt = (JSONArray) result.get("results");
            JSONObject rslt2 =  (JSONObject) rslt.get(0);
            String formatted_address = rslt2.getString("formatted_address");
            JSONObject geo = (JSONObject)rslt2.get("geometry");
            JSONObject loc =(JSONObject) geo.get("location");
            double longitude = loc.getDouble("lng");
            double latitude = loc.getDouble("lat");
            return new Location(formatted_address, latitude, longitude);
        }
        catch (JSONException e) {
            e.printStackTrace();
            ErrorAlert.errorAlert("Unable to retrieve location information!\nAddress input:" + address);
            return dummy;
        }
        
    }

}
