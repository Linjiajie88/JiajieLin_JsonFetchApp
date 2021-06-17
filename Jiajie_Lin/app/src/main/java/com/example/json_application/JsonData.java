package com.example.json_application;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class JsonData extends AsyncTask<Void,Void,Void> {
    String data = "";
    String dataParsed = "";
    String singleParsed = "";




    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.data.setText(this.dataParsed);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //connect to the URL
            URL url = new URL(" https://fetch-hiring.s3.amazonaws.com/hiring.json");
            HttpURLConnection httpcnt = (HttpURLConnection) url.openConnection();

            InputStream inputStream = httpcnt.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            //store the data from the URL to a datalist
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray jsonArray = new JSONArray(data);
            //group the data by Listid by using hashmap
            HashMap<String, List<String>> mymap = new HashMap<>();
            for(int i = 0; i< jsonArray.length();i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                //filter null and blank name
                if (!jsonObject.get("name").equals("") && !jsonObject.get("name").equals(null)) {

                    String listid = "ListId:" + jsonObject.get("listId");

                    String Singleinfor ="ID:"+jsonObject.get("id")+" "+
                                        "Name:" + jsonObject.get("name") + " ";
                    ;
                    if (mymap.containsKey(listid)) {
                        List<String> infor = new ArrayList<String>();
                        infor = mymap.get(listid);
                        infor.add(Singleinfor);
                        //sort value in the list
                        Collections.sort(infor);
                        mymap.put(listid, infor);
                    } else {
                        List<String> infor = new ArrayList<String>();
                        infor.add(Singleinfor);
                        //sort value in the list
                        Collections.sort(infor);
                        mymap.put(listid, infor);
                    }
                }
            }


            //sort key(ListId) by the treemap
            TreeMap<String, List<String>> sorted = new TreeMap<>();
            sorted.putAll(mymap);
            // put the treemap into a "easy-to-read list"
            for(String mykey : sorted.keySet()){
                String keyString = mykey + ": \n";
                dataParsed = dataParsed + keyString;
                for(int a = 0; a<sorted.get(mykey).size(); a++){
                    dataParsed = dataParsed + sorted.get(mykey).get(a)+"\n";
                }
                dataParsed = dataParsed + "\n";
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
