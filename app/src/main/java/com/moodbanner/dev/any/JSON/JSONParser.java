package com.moodbanner.dev.any.JSON;

import com.moodbanner.dev.any.backgrounds.PostValueBackground;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    public List<PostValueBackground> parse(JSONObject jsonObject) {
        List<PostValueBackground> postList = new ArrayList<>();
        PostValueBackground postValueBackground;
        try {
            JSONArray postsArray = jsonObject.getJSONArray("posts");

            for (int i = 0; i < postsArray.length(); i++) {
                JSONObject posts = postsArray.getJSONObject(i);
                JSONObject post = posts.getJSONObject("post");

                postValueBackground = new PostValueBackground();

                int id = post.getInt("id");
                String name = post.getString("name");
                String imageURL = post.getString("imageURL");

                postValueBackground.setId(id);
                postValueBackground.setName(name);
                postValueBackground.setImageURL(imageURL);

                postList.add(postValueBackground);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postList;
    }

}
