package com.mylab.mdzh.words;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import static com.mylab.mdzh.words.R.id.button_know;

public class MainActivity extends AppCompatActivity {

    public int randInt = 0;
    public String currentID = "";

    public static final String Server_URL = "http://172.23.87.32:83";
    private static final String GET_WORD_URL = "http://172.23.87.32:83/variants";

    /** Tag for the log messages */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    public RadioButton rb1 = null;
    public RadioButton rb2 = null;
    public RadioButton rb3 = null;
    public RadioButton rb4 = null;
    public RadioButton rb5 = null;

    protected void OnLoadFinished(){

    }

    AsynkActions TheActions = new AsynkActions(Server_URL);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitVars();

        //--- Know button listener -----------------------------------------------------------------
        TextView know = (TextView) findViewById(R.id.button_know);
        know.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(view.getContext(), "Now you know it!", Toast.LENGTH_SHORT).show();
            }
        });

        //--- Know button listener -----------------------------------------------------------------
        TextView delete = (TextView) findViewById(R.id.button_delete);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                TheActions.delete(currentID);
                Toast.makeText(view.getContext(), "Word has been deleted", Toast.LENGTH_SHORT).show();
            }
        });

        // Kick off an {@link AsyncTask} to perform the network request
        MyAsyncTask task = new MyAsyncTask();
        task.execute();
    }



    public void AddWord(View view){
        Intent i = new Intent(this,AddWord.class);
        startActivity(i);
    }

    public void UpdateWord(View view){
        /*
        rb1.setTextColor(Color.parseColor("#fff"));
        rb2.setTextColor(Color.parseColor("#fff"));
        rb3.setTextColor(Color.parseColor("#fff"));
        rb4.setTextColor(Color.parseColor("#fff"));
        rb5.setTextColor(Color.parseColor("#fff"));*/

        MyAsyncTask UpdateTask = new MyAsyncTask();
        UpdateTask.execute();
    }

    public void InitVars(){
        rb1 = (RadioButton) findViewById(R.id.radioV1);
        rb2 = (RadioButton) findViewById(R.id.radioV2);
        rb3 = (RadioButton) findViewById(R.id.radioV3);
        rb4 = (RadioButton) findViewById(R.id.radioV4);
        rb5 = (RadioButton) findViewById(R.id.radioV5);
    }

    private void updateWordData(String  WordData){
        Random random = new Random();
        randInt = random.nextInt(5);
        Log.e(LOG_TAG, "The random value = "+String.valueOf(randInt));

        rb1.setChecked(false);
        rb2.setChecked(false);
        rb3.setChecked(false);
        rb4.setChecked(false);
        rb5.setChecked(false);
        //rb1.setTextColor(Color.parseColor("#ffffff"));
        //rb2.setTextColor(Color.parseColor("#ffffff"));
        //rb3.setTextColor(Color.parseColor("#ffffff"));
        //rb4.setTextColor(Color.parseColor("#ffffff"));
        //rb5.setTextColor(Color.parseColor("#ffffff"));
        rb1.setVisibility(View.VISIBLE);
        rb2.setVisibility(View.VISIBLE);
        rb3.setVisibility(View.VISIBLE);
        rb4.setVisibility(View.VISIBLE);
        rb5.setVisibility(View.VISIBLE);
        rb1.setTextSize(15);
        rb2.setTextSize(15);
        rb3.setTextSize(15);
        rb4.setTextSize(15);
        rb5.setTextSize(15);

        //--- Button 1 listener ---
        rb1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(randInt==0){
                    //rb1.setTextColor(Color.parseColor("#1E1816"));
                    rb1.setTextSize(20);
                }else {
                    //rb1.setTextColor(Color.parseColor("#CF2A06"));
                    rb1.setVisibility(View.INVISIBLE);
                }

            }
        });

        //--- Button 2 listener ---
        rb2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(randInt==1){
                    //rb2.setTextColor(Color.parseColor("#1E1816"));
                    rb2.setTextSize(25);
                }else {
                    //rb2.setTextColor(Color.parseColor("#CF2A06"));
                    rb2.setVisibility(View.INVISIBLE);
                }

            }
        });

        //--- Button 3 listener ---
        rb3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(randInt==2){
                    //rb3.setTextColor(Color.parseColor("#1E1816"));
                    rb3.setTextSize(25);
                }else {
                    //rb3.setTextColor(Color.parseColor("#CF2A06"));
                    rb3.setVisibility(View.INVISIBLE);
                }

            }
        });

        //--- Button 4 listener ---
        rb4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(randInt==3){
                    //rb4.setTextColor(Color.parseColor("#1E1816"));
                    rb4.setTextSize(25);
                }else {
                    //rb4.setTextColor(Color.parseColor("#CF2A06"));
                    rb4.setVisibility(View.INVISIBLE);
                }

            }
        });

        //--- Button 5 listener ---
        rb5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //RadioButton rb = (RadioButton) findViewById(R.id.radioV5);
                if(randInt==4){
                    //rb5.setTextColor(Color.parseColor("#1E1816"));
                    rb5.setTextSize(25);
                }else {
                    //rb5.setTextColor(Color.parseColor("#CF2A06"));
                    rb5.setVisibility(View.INVISIBLE);
                }

            }
        });

        String[] ArrayData = WordData.split(",");
        TextView TheWord = (TextView) findViewById(R.id.MainWord);

        RadioButton[] ButtonArray = new RadioButton[5];
        ButtonArray[0] = (RadioButton) findViewById(R.id.radioV1);
        ButtonArray[1] = (RadioButton) findViewById(R.id.radioV2);
        ButtonArray[2] = (RadioButton) findViewById(R.id.radioV3);
        ButtonArray[3] = (RadioButton) findViewById(R.id.radioV4);
        ButtonArray[4] = rb5;

        this.currentID = ArrayData[0];
        Log.e(LOG_TAG, "Current id is: " + currentID);

        TheWord.setText(ArrayData[1]);
        ButtonArray[randInt].setText(ArrayData[2]);

        int j = 3;
        for (int i=0; i<5;i++)
        {
            if(i!=randInt){
                ButtonArray[i].setText(ArrayData[j]);
                j++;
            }
        }
    }



    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the first earthquake in the response.
     */
    private class MyAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(GET_WORD_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String WordResponse = "";
            try {
                WordResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }

            return WordResponse;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link MyAsyncTask}).
         */
        @Override
        protected void onPostExecute(String WordResponse) {
            if (WordResponse == null) {
                return;
            }

            updateWordData(WordResponse);
        }

        /**
         * Returns new URL object from the given string URL.
         */
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
        private String makeHttpRequest(URL url) throws IOException {
            String Response = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
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
            Log.e(LOG_TAG, "Received response is: "+ Response);
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
