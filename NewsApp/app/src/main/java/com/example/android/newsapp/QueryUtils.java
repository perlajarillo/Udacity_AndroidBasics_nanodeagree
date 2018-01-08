package com.example.android.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PerlaIvet on 20/12/2017.
 */

public class QueryUtils {
    public static final String LOG_TAG = New.class.getName();
    public static final int MILIS_SLEEP = 1500;
    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECT_TIMEOUT = 15000;
    public static final int SUCCESSFUL = 200;
    private static final String KEY_TITLE = "webTitle";
    private static final String KEY_DATE = "webPublicationDate";
    private static final String KEY_SECTIONNAME = "sectionName";
    private static final String KEY_WEBURL = "webUrl";
    private static final String KEY_RESULTS = "results";
    private static final String KEY_RESPONSE = "response";

    private QueryUtils() {
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(READ_TIMEOUT /* milliseconds */);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == SUCCESSFUL) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the new.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static List<New> extractNews(String newJSON) {
        String title = "";
        String date = "";
        String section = "";
        String web = "";
        String author = "";
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding news to
        List<New> news = new ArrayList<>();

        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Making a request to url and getting response
            JSONObject jsonObj = new JSONObject(newJSON);
            //If there are items founded
            if (jsonObj.has(KEY_RESPONSE)) {
                JSONObject response = jsonObj.getJSONObject(KEY_RESPONSE);
                if (response.has(KEY_RESULTS)) {
                    // Getting JSON Array node
                    JSONArray news_array = response.getJSONArray(KEY_RESULTS);
                    // Loop through each feature in the array
                    for (int i = 0; i < news_array.length(); i++) {
                        // Get new JSONObject at position i
                        JSONObject n = news_array.getJSONObject(i);
                        // Extract title
                        title = n.getString(KEY_TITLE);
                        String[] arrayTitle = title.split("\\|");
                        title = arrayTitle[0];
                        if (arrayTitle.length > 1) {
                            author = arrayTitle[1];
                        }
                        //If there is a date
                        if (n.has(KEY_DATE)) {
                            // Extract subtitle
                            date = n.getString(KEY_DATE);
                        }
                        //If the volume information has section name...
                        if (n.has(KEY_SECTIONNAME)) {
                            // Extract section name
                            section = n.getString(KEY_SECTIONNAME);

                        }
                        //If there is a url with the information of the new
                        if (n.has(KEY_WEBURL)) {
                            // Extract web url
                            web = n.getString(KEY_WEBURL);
                        }
                        // adding new to news list
                        //String sectionName, String webPublicationDate, String webTitle, String webUrl, String author
                        news.add(new New(section, date, title, web, author));
                    }
                } else {
                    return null;
                }
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the new JSON results", e);
        }
        // Return the list of news
        return news;
    }

    public static List<New> fetchNewData(String requestUrl) {
        try {
            Thread.sleep(MILIS_SLEEP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link New}s
        List<New> news = extractNews(jsonResponse);
        // Return the list of {@link New}s
        return news;
    }


}
