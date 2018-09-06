package com.mylab.mdzh.words;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;

public class AddWord extends AppCompatActivity {

    AsynkActions TheActions = new AsynkActions(MainActivity.Server_URL);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        //--- Add button listener -----------------------------------------------------------------
        TextView add = (TextView) findViewById(R.id.button_add);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText word = (EditText) findViewById(R.id.word);
                EditText translation = (EditText) findViewById(R.id.translation);
                EditText category = (EditText) findViewById(R.id.category);

                Word newWord = new Word(word.getText().toString(), translation.getText().toString(), category.getText().toString());

                String[] NewWord = new String[3];
                NewWord[0] = word.getText().toString();
                NewWord[1] = translation.getText().toString();
                NewWord[2] = category.getText().toString();


                //TheActions.add(NewWord);
                TheActions.add(newWord);



                Intent i = new Intent(AddWord.this,MainActivity.class);
                startActivity(i);

                Toast.makeText(view.getContext(), "Word has been added", Toast.LENGTH_SHORT).show();


            }
        });
    }





}
