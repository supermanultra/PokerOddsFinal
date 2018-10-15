package sample;

import java.util.*;

public class Deck {
    public ArrayList<Card> deck = new ArrayList<Card>();
    private String[] suits = {"h","c","s","d"};
    private int[] values = {2,3,4,5,6,7,8,9,10,11,12,13,14};
    private String[] suitNames = {"Hearts","Clubs","Spades","Diamonds"};
    private String[] valNames = {"Deuce","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
    private ArrayList<String> names = new ArrayList<String>();
    private int count = 0;



    //Requires: nothing
    //Modifies: this
    //Effects: Upon being called, generates full deck of cards as ArrayList
    Deck(){
        for (String v: suitNames){
            for (String n: valNames){
                names.add(n + " of " + v);
            }
        }
        for (String s : suits){
            for (int v : values){
                deck.add(new Card(s,v,names.get(count),count));
                count += 1;
            }
        }

    }

    //Requires: this.deck has 5 or more cards inside
    //Modifies: this
    //Effects: Generates Arraylist of random 5 cards to be used as a board, removes cards from this.deck
    public ArrayList<Card> board(){
        ArrayList<Card> board = new ArrayList<Card>();
        for (int i = 0; i < 5; i++){
            Random rand = new Random();
            int num = rand.nextInt(deck.size());
            Card card = deck.get(num);
            board.add(card);
            deck.remove(card);
        }
        return board;
    }

    //Requires: this.deck has 2 or more cards inside
    //Modifies: this
    //Effects: Generates Arraylist of random 2 cards to be used as a hand, removes cards from this.deck
    public ArrayList<Card> hand(){
        ArrayList<Card> hand = new ArrayList<Card>();
        for (int i = 0; i < 2; i++) {
            Random rand = new Random();
            int num = rand.nextInt(deck.size());
            Card card = deck.get(num);
            hand.add(card);
            deck.remove(card);
        }
        return hand;
    }

    //Requires: Arraylist cards contains Card objects that were also built with Deck()
    //Modifies: this
    //Effects: Removes given cards from this.deck
    public void remove(ArrayList<Card> cards){
        ArrayList<Card> remove = new ArrayList<Card>();
        for (Card c : cards){
            for (Card x : deck){
                if (x.suit.equals(c.suit) && x.value == c.value){
                    remove.add(x);
                }
            }
        }
        deck.removeAll(remove);
    }
}
