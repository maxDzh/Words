package com.mylab.mdzh.words;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by maxdz on 5/23/2018.
 */

public class AsynkActions {
    private String ServerIp;
    String DELETE = "/delete/";
    String ADD = "/add";

    private String method;

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();


    public AsynkActions(String ip){
        this.ServerIp = ip;
    }

    public boolean delete(String id){
        //this.method = "GET";

        String[] data = new String[3];
        data[0] = "GET";
        data[1] = ServerIp+DELETE+id;

        Log.e(LOG_TAG, data[1]);

        MyAsyncTask ObjAsynkTask = new MyAsyncTask();
        ObjAsynkTask.execute(data);

        return true;
    }

    public boolean add(Word word){

        String[] data = new String[3];
        data[0] = "POST";
        data[1] = ServerIp+ADD;
        data[2] = "word="+word.GetWord()+"&translation="+word.GetTranslation()+"&category="+word.GetCategory();

        Log.e(LOG_TAG, data[1]);

        MyAsyncTask ObjAsynkTask = new MyAsyncTask();
        ObjAsynkTask.execute(data);

        return true;
    }

    private class MyAsyncTask extends AsyncTask<String , Void, String> {

        @Override
        protected String doInBackground(String... reqData) {
            // Create URL object
            URL url = createUrl(reqData[1]);


            // Perform HTTP request to the URL and receive a JSON response back
            String WordResponse = "";
            try {
                WordResponse = makeHttpRequest(url, reqData[0],reqData[2]);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            return WordResponse;
        }

        @Override
        protected void onPostExecute(String WordResponse) {
            if (WordResponse == null) {
                return;
            }

            //updateWordData(WordResponse);
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url, String method, String postData) throws IOException {
            String Response = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            Log.e(LOG_TAG, "Trying make a request: " + method + "" + url);

            //Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 8888));

             try {
            // urlConnection = (HttpURLConnection) url.openConnection(proxy);
             urlConnection = (HttpURLConnection) url.openConnection();
             urlConnection.setRequestMethod(method);

             if (method == "POST"){
                 urlConnection.setDoInput(true);
                 urlConnection.setFixedLengthStreamingMode(postData.length());
                 urlConnection.setRequestProperty("Content-Type","text/plain");

                 OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

                 Log.e(LOG_TAG, "POST body: "+ postData);
                 writer.write(postData);
                 writer.flush();
                 writer.close();
                 out.close();
             }

             urlConnection.setReadTimeout(10000 );
             urlConnection.setConnectTimeout(15000 );
             urlConnection.connect();
             inputStream = urlConnection.getInputStream();
             Response = readFromStream(inputStream);
             } catch (IOException e) {
                Log.e(LOG_TAG, "Error with connection", e);
             } finally {
                 if (urlConnection != null) {
                    urlConnection.disconnect();
                 }
                 if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                 }
             }

            Log.e(LOG_TAG, "Received response is: " + Response);
            return Response;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
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
    }
}
