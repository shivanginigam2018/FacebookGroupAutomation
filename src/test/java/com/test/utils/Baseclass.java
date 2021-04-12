package com.test.utils;

import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Baseclass {

    public static void writeJSON(String key, String value1, String value2) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("ProfileID", key);
        obj.put("Name", value1);
        obj.put("Email", value2);
        FileWriter file = new FileWriter("src/test/resources/Data/output.json");
        file.write(String.valueOf(obj));
        file.close();

    }
}
