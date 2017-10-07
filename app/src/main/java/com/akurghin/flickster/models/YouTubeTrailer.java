package com.akurghin.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class YouTubeTrailer {
    private String name;
    private String size;
    private String source;
    private String type;

    public YouTubeTrailer(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString("name");
        this.size = jsonObject.getString("size");
        this.source = jsonObject.getString("source");
        this.type = jsonObject.getString("type");
    }

    public static List<YouTubeTrailer> fromJSONArray(JSONArray array) {
        List<YouTubeTrailer> result = new ArrayList<>();
        for (int i = 0; i < array.length(); ++i) {
            try {
                result.add(new YouTubeTrailer(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }
}
