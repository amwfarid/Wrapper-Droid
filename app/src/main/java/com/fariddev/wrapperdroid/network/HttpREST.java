package com.fariddev.wrapperdroid.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpREST {

    private HttpURLConnection httpURLConnection;

    public String[] sendGet(String requestURL) throws IOException {
        URL url = new URL(requestURL);

        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true); // true if we want to read server's response
        httpURLConnection.setDoOutput(false); // false indicates this is a GET request

        String[] response = readRespone();

        httpURLConnection.disconnect();

        return response;
    }

    public String[] sendPost(String requestURL,Map<String, String> params) throws IOException {

        URL url = new URL(requestURL);

        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setDoInput(true); // true indicates the server returns response

        StringBuffer requestParams = new StringBuffer();

        if (params != null && params.size() > 0) {

            httpURLConnection.setDoOutput(true); // true indicates POST request

            // creates the params string, encode them using URLEncoder
            Iterator<String> paramIterator = params.keySet().iterator();
            while (paramIterator.hasNext()) {
                String key = paramIterator.next();
                String value = params.get(key);
                requestParams.append(URLEncoder.encode(key, "UTF-8"));
                requestParams.append("=").append(
                        URLEncoder.encode(value, "UTF-8"));
                requestParams.append("&");
            }

            // sends POST data
            OutputStreamWriter writer = new OutputStreamWriter(
                    httpURLConnection.getOutputStream());
            writer.write(requestParams.toString());
            writer.flush();
        }

        String[] response = readRespone();

        httpURLConnection.disconnect();

        return response;
    }

    public String[] readRespone() throws IOException {
        InputStream inputStream = null;
        if (httpURLConnection != null) {
            inputStream = httpURLConnection.getInputStream();
        } else {
            throw new IOException("Connection is not established.");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        List<String> response = new ArrayList<String>();

        String line = "";
        while ((line = reader.readLine()) != null) {
            response.add(line);
        }
        reader.close();

        return (String[]) response.toArray(new String[0]);
    }
}