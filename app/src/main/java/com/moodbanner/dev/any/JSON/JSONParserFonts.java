package com.moodbanner.dev.any.JSON;

import com.moodbanner.dev.any.Fonts.ValueFont;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class JSONParserFonts {

    public List<ValueFont> parse(JSONObject jsonObject) {
        List<ValueFont> ListFonts = new ArrayList<>();
        ValueFont valueFont;
        try {
            JSONArray postsArray = jsonObject.getJSONArray("posts");

            for (int i = 0; i < postsArray.length(); i++) {
                JSONObject posts = postsArray.getJSONObject(i);
                JSONObject post = posts.getJSONObject("post");

                valueFont = new ValueFont();

                int id = post.getInt("id");
                String name = post.getString("name");
                String fontthumb = post.getString("fontthumb");
                String fontfile = post.getString("fontfile");

                valueFont.setId(id);
                valueFont.setName(name);
                valueFont.setFontThumb(fontthumb);
                valueFont.setFontFile(fontfile);

                ListFonts.add(valueFont);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ListFonts;
    }

}