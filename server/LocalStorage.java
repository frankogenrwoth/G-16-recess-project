package server;

import org.json.*;
import java.io.*;

public class LocalStorage {
    String filePath;

    public LocalStorage(String filePath) throws IOException {
        this.filePath = filePath;
        File file = new File(this.filePath);
        if (!file.exists()) {
            FileWriter fhandle = new FileWriter(this.filePath);
            fhandle.write(new JSONArray().toString());
            fhandle.flush();
        }
    }

    public void add(JSONObject newEntry) {
        JSONArray jsonArray = this.read();
        jsonArray.put(newEntry);
        try (FileWriter file = new FileWriter(this.filePath)) {
            file.write(jsonArray.toString(4));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray read() {
        JSONArray jsonArray = new JSONArray();

        try (FileReader reader = new FileReader(this.filePath)) {
            StringBuilder jsonData = new StringBuilder();
            int i;
            while ((i = reader.read()) != -1) {
                jsonData.append((char) i);
            }
            if (jsonData.length() > 0) {
                jsonArray = new JSONArray(jsonData.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public JSONObject readEntryByName(String name) {
        JSONArray jsonArray = this.read();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("name").equals(name)) {
                return jsonObject;
            }
        }
        return null;
    }

    public void deleteEntryByName(String name) {
        JSONArray jsonArray = this.read();
        JSONArray updatedArray = new JSONArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!jsonObject.getString("name").equals(name)) {
                updatedArray.put(jsonObject);
            }
        }

        try (FileWriter file = new FileWriter(this.filePath)) {
            file.write(updatedArray.toString(4));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void delete() {
        File file = new File(this.filePath);
        file.delete();
    }
}
