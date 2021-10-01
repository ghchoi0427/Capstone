package web.controller;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHandler {

    public static String getTemperature(String jsonString) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject root = (JSONObject) parser.parse(jsonString);
        JSONObject response = (JSONObject) root.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        for (Object object : item) {
            if (((JSONObject) object).get("category").toString().equals("T1H")) {
                return (((JSONObject) object).get("obsrValue").toString());
            }
        }
        return "-1";
    }

    public static String getHumidity(String jsonString) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject root = (JSONObject) parser.parse(jsonString);
        JSONObject response = (JSONObject) root.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        for (Object object : item) {
            if (((JSONObject) object).get("category").toString().equals("REH")) {
                return (((JSONObject) object).get("obsrValue").toString());
            }
        }
        return "-1";
    }



}
