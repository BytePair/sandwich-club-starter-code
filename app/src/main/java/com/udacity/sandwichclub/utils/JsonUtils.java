package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        JSONObject jsonObject = new JSONObject(json);

        String mainName = jsonObject.optString(KEY_MAIN_NAME);
        List<String> alsoKnownAs = jsonArrayToList(jsonObject.optJSONArray(KEY_ALSO_KNOW_AS));
        String placeOfOrigin = jsonObject.optString(KEY_PLACE_OF_ORIGIN);
        String description = jsonObject.optString(KEY_DESCRIPTION);
        String image = jsonObject.optString(KEY_IMAGE);
        List<String> ingredients = jsonArrayToList(jsonObject.optJSONArray(KEY_INGREDIENTS));

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null) {
            return null;
        }
        List<String> listToReturn = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            listToReturn.add((String) jsonArray.get(i));
        }
        return listToReturn;
    }
}
