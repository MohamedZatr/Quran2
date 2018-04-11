package com.example.mohamedramadan.quran;

/**
 * Created by Mohamed Ramadan on 15/02/2018.
 */

public class MyUrls {

    private static  String authors = "http://api.islamhouse.com/v1/CNIguyG5QtlZ3UEk/quran/get-category/364794/ar/json/";
    private static  String suras ="http://api.islamhouse.com/v1/soF6Fs60IjX4C2EA/quran/get-recitation/";
    private static  String id ;

    /**
     *
     * @return
     */
    public static String getSuras() {
        return suras+id+"/ar/json/";
    }

    /**
     *
     * @return
     */
    public static String getAuthors() {
        return authors;
    }


    /**
     *
     * @param id
     */
    public static void setId(String id) {
        MyUrls.id = id;
    }



}
