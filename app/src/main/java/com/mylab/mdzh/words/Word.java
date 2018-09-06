package com.mylab.mdzh.words;

/**
 * Created by maxdz on 6/20/2018.
 */

public class Word {

    String TheWord;
    String Translation;
    String Category;

    Word(String word, String translation, String category){
        TheWord = word;
        Translation = translation;
        Category = category;
    }

    public String GetWord(){
        return TheWord;
    }

    public String GetTranslation(){
        return Translation;
    }

    public String GetCategory(){
        return Category;
    }

}
