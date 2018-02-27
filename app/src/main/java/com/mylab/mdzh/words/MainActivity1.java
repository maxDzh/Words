package com.mylab.mdzh.words;

import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import static com.mylab.mdzh.words.R.id.button_know;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"--- Start1");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Request to server
        InputStream responceStream = null;
        String responce = "";
        HttpURLConnection MyConnection = null;
        InputStream MyStream = null;
        String LOG_TAG = MainActivity.class.getSimpleName();
        URL GetWord = null;



        private class TsunamiAsyncTask extends AsyncTask<URL, Void, Event> {

            @Override
            protected Event doInBackground(URL... urls) {
                // Create URL object
                URL url = createUrl(USGS_REQUEST_URL);

                // Perform HTTP request to the URL and receive a JSON response back
                String jsonResponse = "";
                try {
                    jsonResponse = makeHttpRequest(url);
                } catch (IOException e) {
                    // TODO Handle the IOException
                }

                // Extract relevant fields from the JSON response and create an {@link Event} object
                Event earthquake = extractFeatureFromJson(jsonResponse);

                // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
                return earthquake;
            }
        }





        try {
            GetWord = new URL("http://172.23.87.32:82/");
        }catch (MalformedURLException exception){
            Log.d(LOG_TAG, "--- Error with creating URL", exception);
        }

        try{
            MyConnection = (HttpURLConnection) GetWord.openConnection();
            MyConnection.setRequestMethod("GET");
            MyConnection.setReadTimeout(10000 /* milliseconds */);
            MyConnection.setConnectTimeout(15000 /* milliseconds */);
            MyConnection.connect();
            if (MyConnection.getResponseCode()==200){
                responceStream = MyConnection.getInputStream();
            }
            responce = readFromStream(responceStream);
            MyConnection.disconnect();
            Log.d(TAG,"--- responce reseived");
            responceStream.close();
        }catch (IOException e){
            Log.d(TAG," --- Something wrong");
            Log.d(TAG, " --- ",e);
        }finally{
            if(MyConnection != null){
                MyConnection.disconnect();
            }
            if(responceStream!=null){
                try {
                    responceStream.close();
                }catch (java.io.IOException e){

                }
            }
        }



        TextView know = (TextView) findViewById(R.id.button_know);
        know.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), "Now you know it!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void AddWord(View view){
        Intent i = new Intent(this,AddWord.class);
        startActivity(i);
    }

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
