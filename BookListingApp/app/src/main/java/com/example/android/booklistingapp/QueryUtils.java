package com.example.android.booklistingapp;

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
 * Created by PerlaIvet on 18/12/2017.
 */

public class QueryUtils {
    public static final String LOG_TAG = Book.class.getName();
    public static final int MILIS_SLEEP = 1500;
    public static final int READ_TIMEOUT = 10000;
    public static final int CONNECT_TIMEOUT = 15000;
    public static final int SUCCESSFUL = 200;
    private static final String KEY_TITLE = "title";
    private static final String KEY_SUBTITLE = "subtitle";
    private static final String KEY_AUTHORS = "authors";
    private static final String KEY_INFOLINK = "infoLink";
    private static final String KEY_SALEINFO = "saleInfo";
    private static final String KEY_SALEABILITY = "saleability";
    private static final String KEY_VOLUMEINFO = "volumeInfo";
    private static final String KEY_ITEMS = "items";

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
            Log.e(LOG_TAG, "Problem retrieving the book.", e);
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


    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Book> extractBooks(String bookJSON) {
        String authors = "";
        String url = "";
        String saleability = "";
        String subtitle = "";
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding books to
        List<Book> books = new ArrayList<>();

        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // Making a request to url and getting response
            JSONObject jsonObj = new JSONObject(bookJSON);
            //If there are items founded
            if (jsonObj.has(KEY_ITEMS)) {
                // Getting JSON Array node
                JSONArray books_array = jsonObj.getJSONArray(KEY_ITEMS);
                // Loop through each feature in the array
                for (int i = 0; i < books_array.length(); i++) {
                    authors = "";
                    // Get book JSONObject at position i
                    JSONObject b = books_array.getJSONObject(i);
                    // Get volumeInfo
                    JSONObject info = b.getJSONObject(KEY_VOLUMEINFO);
                    // Extract title
                    String title = info.getString(KEY_TITLE);
                    //If there is a subtitle
                    if (info.has(KEY_SUBTITLE)) {
                        // Extract subtitle
                        subtitle = info.getString(KEY_SUBTITLE);
                    }
                    //If the volume information has authors...
                    if (info.has(KEY_AUTHORS)) {
                        // Extract authors
                        // Getting JSON Array node
                        JSONArray authors_array = info.getJSONArray(KEY_AUTHORS);

                        for (int x = 0; x < authors_array.length(); x++) {
                            if (authors_array.length() > 1) {
                                authors += authors_array.getString(x) + ", ";
                            } else
                                authors = authors_array.getString(x);
                        }
                    }
                    //If there is a url with the information of the book
                    if (info.has(KEY_INFOLINK)) {
                        // Extract infoLink
                        url = info.getString(KEY_INFOLINK);
                    }
                    //If there is some sale information
                    if (b.has(KEY_SALEINFO)) {
                        JSONObject saleInfo = b.getJSONObject(KEY_SALEINFO);
                        saleability = saleInfo.getString(KEY_SALEABILITY);
                    }
                    // adding book to books list
                    books.add(new Book(title, subtitle, authors, url, saleability));
                }
            } else {
                return null;
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }

        // Return the list of books
        return books;
    }

    /**
     * Query the Google API dataset and return a list of {@link Book} objects.
     */
    public static List<Book> fetchBookData(String requestUrl) {
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

        // Extract relevant fields from the JSON response and create a list of {@link Book}s
        List<Book> books = extractBooks(jsonResponse);
        // Return the list of {@link Book}s
        return books;
    }

}
