package sample;

import java.util.ArrayList;

public class Card {

    public String suit;
    public int value;
    public String name;
    public int position;

    //Requires: nothing
    //Modifies: this
    //Effects: Generates Card object with provided parameters
    public Card(String suit, int value, String name,int position) {
        this.suit = suit;
        this.value = value;
        this.name = name;
        this.position = position;
    }


    //Requires: nothing
    //Modifies: this
    //Effects: Changes .toString method to return this.name
    public String toString() {
        return this.name;
    }

}