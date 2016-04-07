package com.moodbanner.dev.any.JSON;

import com.moodbanner.dev.any.backgrounds.ValueBackground;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParserBackgrounds {

    public List<ValueBackground> parse(JSONObject jsonObject) {
        List<ValueBackground> postList = new ArrayList<>();
        ValueBackground valueBackground;
        try {
            JSONArray postsArray = jsonObject.getJSONArray("posts");

            for (int i = 0; i < postsArray.length(); i++) {
                JSONObject posts = postsArray.getJSONObject(i);
                JSONObject post = posts.getJSONObject("post");

                valueBackground = new ValueBackground();

                String name = post.getString("name");
                String imageURL = post.getString("imageURL");

                valueBackground.setName(name);
                valueBackground.setImageURL(imageURL);

                postList.add(valueBackground);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postList;
    }

}
