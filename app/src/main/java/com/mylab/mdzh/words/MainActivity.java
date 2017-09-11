package com.mylab.mdzh.words;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import static com.mylab.mdzh.words.R.id.button_know;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
}
