package com.test.utils;

import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Baseclass {

    public static void writeJSON(String key, String value) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put(key,value);
        FileWriter file = new FileWriter("src/test/resources/Data/output.json");
        file.write(String.valueOf(obj));
        file.close();

    }
}
