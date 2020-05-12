package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json)  {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject name = jsonObject.getJSONObject("name");
            String mainName = name.getString("mainName");
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                alsoKnownAs.add((String) alsoKnownAsArray.get(i));
            }
            String placeOfOrigin = jsonObject.getString("placeOfOrigin");
            String description = jsonObject.getString("description");
            String image = jsonObject.getString("image");
            JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
            List<String> ingredients = new ArrayList<>();
            for (int i = 0; i < ingredientsArray.length(); i++) {
                ingredients.add((String) ingredientsArray.get(i));
            }
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}