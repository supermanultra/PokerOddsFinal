package sample;

import jdk.internal.org.objectweb.asm.tree.InnerClassNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Rules {

    //Requires: hand and board not empty
    //Modifies: nothing
    //Effects: Transforms Card objects into Arraylist of values to be used in other methods
    public static ArrayList<Integer> vals(ArrayList<Card> hand, ArrayList<Card> board) {
        ArrayList<Integer> vals = new ArrayList<Integer>();

        for (Card v : hand) {
            vals.add(v.value);
        }

        for (Card v : board) {
            vals.add(v.value);
        }


        return vals;
    }


    //Requires: nothing
    //Modifies: nothing
    //Effects: Finds best card in array, or best cards added together
    public static double bestCards(ArrayList<Integer> values, int num){

        double best = Collections.max(values);

        if (num >= 2){

            double finalNum = best / 100;
            double[] multis = new double[]{10000,1000000,100000000};
            for (int i = 1; i < num; i++){
                finalNum += (values.get(i) / multis[i-1]);
            }
            return finalNum;

        }
        return (best / 100);
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: Checks for one or two+ pairs, returns highest value of pair + 1 or 2
    public static ArrayList<Integer> isPair(ArrayList<Integer> vals) {
        ArrayList<Integer> num = new ArrayList<Integer>();
        for (int i = 0; i < vals.size()-1; i++) {
            if (num.contains(vals.get(i)) && vals.subList(i + 1, vals.size()).contains(vals.get(i))){
                num.remove(vals.get(i));

            }
            else if (vals.subList(i + 1, vals.size()).contains(vals.get(i))) {
                num.add(vals.get(i));

            }

        }
        return num;
    }



    //Requires: nothing
    //Modifies: nothing
    //Effects: returns 3+card that occurs thrice if vals contains three of the same value
    public static double threeOfAKind(ArrayList<Integer> vals) {
        double num = 0.0;
        ArrayList<Double> threes = new ArrayList<Double>();
        int count = 0;
        for (int i = 0; i < vals.size()-1;i++){
            if (count == 2){
                threes.add(num);
            }
            else if (count > 2){
                return 0;
            }
            count = 0;
            num = (double)vals.get(i);
            for (int b = i+1; b < vals.size();b++){
                if (vals.get(b) == num){
                    count += 1;
                }
            }
        }
        Collections.sort(threes,Collections.<Double>reverseOrder());
        if (threes.size() == 1){
            return (threes.get(0) / 100 + 3);
        }
        else if (threes.size() == 2){
            return (threes.get(1) / 100);
        }
        return 0;
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: returns 7+card that occurs four times if vals contains quads
    public static double fourOfAKind(ArrayList<Integer> vals) {
        double num = 0.0;
        int count = 0;
        for (int i = 0; i < vals.size()-3;i++){
            if (count == 3){
                return num / 100.0 + 7;
            }

            count = 0;
            num = (double)vals.get(i);
            for (int b = i+1; b < vals.size();b++){
                if (vals.get(b) == num){
                    count += 1;
                }
            }
        }
        return 0;
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: Returns 7+highest card in three of a kind + highest card in pair if vals contain full house
    public static double fullHouse(ArrayList<Integer> vals){
        double three = threeOfAKind(vals);
        double pair = 0;
        ArrayList<Integer> newVals = new ArrayList<Integer>();

        for (int i : vals){
            newVals.add(i);
        }

        if (three > 0 && three < 3){
            pair = three / 100;
            Integer num = ((int)(three * 100));
            newVals.remove(num);
            newVals.remove(num);
            three = threeOfAKind(newVals);
            return (three + pair + 3);
        }


        if (isPair(newVals).size() != 0) {
             pair = bestCards(isPair(newVals), 1);
        }
        if (three != 0){
            if (pair != 0){
                return (three + ((pair / 100) + 3 ));
            }
        }
        return 0;
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: Returns 5+highest card in flush if hand+board contain a flush
    public static double isFlush(ArrayList<Card> hand, ArrayList<Card> board){
        ArrayList<Card> total = new ArrayList<Card>(hand);
        total.addAll(board);
        ArrayList<Card> hCards = new ArrayList<Card>();
        ArrayList<Card> sCards = new ArrayList<Card>();
        ArrayList<Card> dCards = new ArrayList<Card>();
        ArrayList<Card> cCards = new ArrayList<Card>();
        ArrayList<Integer> vals = new ArrayList<Integer>();
        int hearts = 0;
        int clubs = 0;
        int spades = 0;
        int diamonds = 0;
        for (Card c : total){
            switch(c.suit.charAt(0)){
                case 'h':
                    hearts += 1;
                    hCards.add(c);
                    break;
                case 'c':
                    clubs += 1;
                    cCards.add(c);
                    break;
                case 'd':
                    diamonds += 1;
                    dCards.add(c);
                    break;
                case 's':
                    spades += 1;
                    sCards.add(c);
                    break;

            }
        }
        if (diamonds >= 5){
            for (Card c : dCards){
                vals.add(c.value);
            }
            return (bestCards(vals,1)) + 5;


        }
        else if (hearts >= 5){
            for (Card c : hCards){
                vals.add(c.value);
            }
            return (bestCards(vals,1)) + 5;


        }
        else if (spades >= 5){
            for (Card c : sCards){
                vals.add(c.value);
            }
            return (bestCards(vals,1)) + 5;


        }
        else if (clubs >= 5){
            for (Card c : cCards){
                vals.add(c.value);
            }
            return (bestCards(vals,1)) + 5;


        }
        return 0;
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: Returns 4+highest card in straight if vals contains a straight
    public static double isStraight(ArrayList<Integer> vals){
        Collections.sort(vals, Collections.<Integer>reverseOrder());
        Set<Integer> temp = new HashSet<Integer>();
        temp.addAll(vals);
        ArrayList<Integer> newValues = new ArrayList<Integer>(temp);
        Collections.sort(newValues, Collections.reverseOrder());
        int straight = 1;
        ArrayList<Integer> cards = new ArrayList<Integer>();
        ArrayList<Integer> tempCards = new ArrayList<Integer>();
        if (newValues.contains(14)){
            newValues.add(1);
        }
        for (int i : newValues){
            if (straight == 5){
                cards.addAll(tempCards);
                return (bestCards(cards, 1)) + 4;
            }
            else if (newValues.contains(i-1)){
                straight += 1;
                tempCards.add(i);
            }
            else {
                straight = 1;
                tempCards.clear();
            }
        }
        return 0;

    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: returns Arraylist of cards that add up to a flush, for use in isStraightFlush
    public static ArrayList<Integer> flushCards(ArrayList<Card> hand, ArrayList<Card> board){
        ArrayList<Card> total = new ArrayList<Card>(hand);
        total.addAll(board);
        ArrayList<Card> hCards = new ArrayList<Card>();
        ArrayList<Card> sCards = new ArrayList<Card>();
        ArrayList<Card> dCards = new ArrayList<Card>();
        ArrayList<Card> cCards = new ArrayList<Card>();
        ArrayList<Integer> vals = new ArrayList<Integer>();
        int hearts = 0;
        int clubs = 0;
        int spades = 0;
        int diamonds = 0;
        for (Card c : total){
            switch(c.suit.charAt(0)){
                case 'h':
                    hearts += 1;
                    hCards.add(c);
                    break;
                case 'c':
                    clubs += 1;
                    cCards.add(c);
                    break;
                case 'd':
                    diamonds += 1;
                    dCards.add(c);
                    break;
                case 's':
                    spades += 1;
                    sCards.add(c);
                    break;

            }
        }
        if (diamonds >= 5){
            for (Card c : dCards){
                vals.add(c.value);
            }
            return vals;


        }
        else if (hearts >= 5){
            for (Card c : hCards){
                vals.add(c.value);
            }
            return vals;


        }
        else if (spades >= 5){
            for (Card c : sCards){
                vals.add(c.value);
            }
            return vals;


        }
        else if (clubs >= 5){
            for (Card c : cCards){
                vals.add(c.value);
            }
            return vals;


        }
        return vals;
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: returns 8+ highest card in straight if hand+board contain a straight
    public static double isStraightFlush(ArrayList<Card> hand, ArrayList<Card> board){
        ArrayList<Integer> vals = flushCards(hand,board);
       if (vals.size() != 0){
           if (isStraight(vals) != 0){
               return (isStraight(vals) + 4);
           }
       }
       return 0;

    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: Returns 9 if hand+board contain a royal flush, else 0
    public static double isRoyal(ArrayList<Card> hand, ArrayList<Card> board){
        ArrayList<Integer> vals = flushCards(hand,board);
        ArrayList<Integer> royals = new ArrayList<Integer>();
        royals.add(14);
        royals.add(13);
        royals.add(12);
        royals.add(11);
        royals.add(10);
        if (vals.size() != 0){
            if (vals.containsAll(royals)){
                return 9;
            }
        }
        return 0;
    }

    //Requires: vals not empty
    //Modifies: nothing
    //Effects: Returns sorted ArrayList of values from highest to lowest
    public static ArrayList<Integer> highCard(ArrayList<Integer> vals){
        Collections.sort(vals,Collections.<Integer>reverseOrder());
        return vals;
    }

    //Requires: nothing
    //Modifies: nothing
    //Effects: Checks score of each hand, returns 1 if hand1 wins and 2 if hand2 wins. Runs tiebreaker if initial scores are equal.
    public static int findWinner(ArrayList<Card> hand1, ArrayList<Card> hand2, ArrayList<Card> board){
        double score1 = scoreHand(hand1,board);
        double score2 = scoreHand(hand2,board);
        double diff = score1 - score2;
        double delta = 0.001;
        double negDelta = -0.001;
        ArrayList<Integer> vals = vals(hand1,board);

        if ((diff <= delta && diff >=negDelta) && isStraight(vals) == 0 && isFlush(hand1,board) == 0 && fullHouse(vals) == 0){
            ArrayList<Integer> vals1 = highCard(vals(hand1,board));
            ArrayList<Integer> vals2 = highCard(vals(hand2,board));
            for (int i = 0; i < vals1.size(); i++){
                if ( vals1.get(i) > vals2.get(i)){
                    return 1;
                }
                else if (vals2.get(i) > vals1.get(i)){
                    return 2;
                }
            }

        }
        else if (score1 > score2){
            return 1;
        }
        else if (score2 > score1){
            return 2;
        }
        return 0;
    }

    //Requires: hand and board not empty
    //Modifies: nothing
    //Effects: Takes hand and board, returns score of hand according to what type of hand it is
    public static double scoreHand(ArrayList<Card> hand, ArrayList<Card> board){
        ArrayList<Integer> vals = vals(hand,board);
        if (isRoyal(hand,board) != 0){
            return 9;
        }
        else if (isStraightFlush(hand, board) != 0){
            return isStraightFlush(hand,board);
        }
        else if (fourOfAKind(vals) != 0){
            return fourOfAKind(vals) ;
        }
        else if (fullHouse(vals) != 0){
            return fullHouse(vals);
        }
        else if (isFlush(hand,board) != 0){
            return isFlush(hand,board) ;
        }
        else if (isStraight(vals) != 0){
            return isStraight(vals);
        }
        else if (threeOfAKind(vals) != 0){
            return threeOfAKind(vals);
        }
        else if (isPair(vals).size() > 1){
            return (bestCards(isPair(vals),2) + 2);
        }
        else if (isPair(vals).size() == 1){
            return (bestCards(isPair(vals),1) + 1) ;
        }
        else {
            return bestCards(vals, 1);
        }
    }

}