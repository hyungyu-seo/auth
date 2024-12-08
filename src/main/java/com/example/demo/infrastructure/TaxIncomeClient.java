package com.example.demo.infrastructure;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class TaxIncomeClient {

    public JSONObject apiCallData(String userId, String regNo) {
        return callScrapingApi(userId, regNo);
    }

    public JSONObject callScrapingApi(String userId, String regNo){
        JSONObject result = new JSONObject();
        try{
            String apiUrl = "https://codetest-v4.3o3.co.kr/scrap";
            URL url = new URL(apiUrl);

            JSONObject body = new JSONObject();
            body.put("name", userId);
            body.put("regNo", regNo);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-API-KEY", "G9fSpitmtAeKzYJC7X0SwA==");
            connection.setRequestProperty("Content-Type", " application/json");
            connection.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
            bw.write(body.toString());
            bw.flush();
            bw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            result = new JSONObject(br.readLine());

            readResponseCode(connection);
            connection.disconnect();

        } catch (IOException e){
            System.out.println(e.getLocalizedMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    private void readResponseCode(HttpURLConnection connection){

        try{
            int responseCode = connection.getResponseCode();
            if(responseCode == 400){
                System.out.println("400 : command error");
            } else if (responseCode == 500){
                System.out.println("500 : Server error");
            } else {
                System.out.println("response code : " + responseCode);
            }
        } catch (IOException e){
            System.out.println("IOException : read response code error");
        }
    }
}
