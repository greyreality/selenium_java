package ru.st.selenium;

import java.nio.charset.Charset;
import java.util.Random;
/**
 * Created by zelenb on 11/8/2015.
 */


public class Movie {
    private String title;
    private String year;
    private String secondName;

    public void InfoMovie(){
        System.out.println("title="+"title"+";year="+"year");
    }
    public String GetTitle(){
        return title;
    }
    public String GetYear(){
        return year;
    }
    public String SecondName(){
        return secondName;
    }
    public Movie() {
        try {
            title = "MOVIE_" + RandomStringGenerator.generateRandomString(10, RandomStringGenerator.Mode.ALPHA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            year = RandomStringGenerator.generateRandomString(1,RandomStringGenerator.Mode.YEAR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            secondName = "SECOND_NAME_" + RandomStringGenerator.generateRandomString(5, RandomStringGenerator.Mode.ALPHA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Integer.toString(1900 + (int)Math.round(Math.random()*(2015 - 1900)));
    }
}
