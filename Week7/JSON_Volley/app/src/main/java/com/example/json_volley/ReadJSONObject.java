package com.example.json_volley;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ReadJSONObject extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... strings) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(strings[0]);
            InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //phân tích JSON
        try {
            JSONObject object = new JSONObject(s);
            String name = object.getString("name");
            String desc = object.getString("desc");
            String pic = object.getString("pic");
            String kq = name + "\n" + desc + "\n" + pic;
//            Toast.makeText(MainActivity.this,kq,Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
//        //phân tích JSON
//        try {
//            JSONObject object = new JSONObject(s);
//            JSONObject objectLang = object.getJSONObject("language");
//            JSONObject objectVN = objectLang.getJSONObject("vn");
//            String ten = objectVN.getString("ten");
////            Toast.makeText(MainActivity.this,ten,Toast.LENGTH_SHORT).show();
//        } catch (JSONException ex) {
//            throw new RuntimeException(ex);
//        }
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
}