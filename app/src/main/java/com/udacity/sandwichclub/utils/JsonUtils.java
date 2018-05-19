package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;
import java.util.Arrays;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        int startPosition;
        int endPosition;

        // find main name
        startPosition = json.indexOf("{\"name\":{\"mainName\":\"") + "{\"name\":{\"mainName\":\"".length();
        endPosition = json.indexOf("\",\"alsoKnownAs\":[");
        String mainName = json.substring(startPosition, endPosition);

        // find alternative names
        startPosition = json.indexOf("\",\"alsoKnownAs\":[") + "\",\"alsoKnownAs\":[".length();
        endPosition = json.indexOf("]},\"placeOfOrigin\":\"");
        ArrayList<String> alsoKnownAs = new ArrayList<>(Arrays.asList(json.substring(startPosition, endPosition).replace("\"", "").split(",")));

        // find place of origin
        startPosition = json.indexOf("]},\"placeOfOrigin\":\"") + "]},\"placeOfOrigin\":\"".length();
        endPosition = json.indexOf("\",\"description\":\"");
        String placeOfOrigin = json.substring(startPosition, endPosition);

        // find description
        startPosition = json.indexOf("\",\"description\":\"") + "\",\"description\":\"".length();
        endPosition = json.indexOf("\",\"image\":\"");
        String description = json.substring(startPosition, endPosition).replace("\\", "");

        // find image
        startPosition = json.indexOf("\",\"image\":\"") + "\",\"image\":\"".length();
        endPosition = json.indexOf("\",\"ingredients\":[\"");
        String image = json.substring(startPosition, endPosition);

        // find ingredients
        startPosition = json.indexOf("\",\"ingredients\":[\"") + "\",\"ingredients\":[\"".length();
        endPosition = json.indexOf("]}", startPosition);
        ArrayList<String> ingredients = new ArrayList<>(Arrays.asList(json.substring(startPosition, endPosition).replace("\"", "").split(",")));

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
