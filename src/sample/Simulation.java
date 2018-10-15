package sample;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Simulation {
    public double hand1wins=0;
    public double hand2wins=0;
    public double ties=0;
    public ArrayList<Card> hand1;
    private ArrayList<Card> hand2;
    private ArrayList<Card> board;

    //Requires: hand1 is not empty
    //Modifies: this
    //Effects: Takes a hand and simulates 100,000 hands of poker with it, counts wins/ties/losses. Can also take a second hand or a board.
    Simulation(ArrayList<Card> hand1, ArrayList<Card> hand2, ArrayList <Card> board){
        this.hand1 = hand1;
        this.hand2 = hand2;
        this.board = board;
        for (int i = 0;i < 100000; i++){
            Deck deck = new Deck();
            deck.remove(this.hand1);
            deck.remove(this.hand2);
            deck.remove(this.board);
            if (this.hand2.size() == 0){
                hand2 = deck.hand();
            }
            if (this.board.size() == 0){
                board = deck.board();
            }
            int winner = Rules.findWinner(hand1,hand2,board);
            switch(winner){
                case 0:
                    ties +=1;
                    break;
                case 1:
                    hand1wins += 1;
                    break;
                case 2:
                    hand2wins += 1;
                    break;

            }

        }

    }

    //Requires: nothing
    //Modifies: this
    //Effects: Modifies toString method to give desired output
    public String toString(){
        if (hand2.size() == 0 && board.size() == 0){
            return hand1.get(0) + "|" + hand1.get(1) + " won against a random hand on a random board " + Math.round(hand1wins/1000) + "% and tied " + ties/1000 + "% of the time after 100,000 hands ";
        }
        else if (hand2.size() == 0){
            return hand1.get(0) + "|" + hand1.get(1) +  " won against a random hand on board: " + board.get(0) + "|" + board.get(1) + "|" + board.get(2) + "|" + board.get(3) + "|" + board.get(4) +  " " + Math.round(hand1wins/1000) + "% of the time and tied " + Math.round(ties/1000) + "% of the time after 100,000 hands.";
        }
        else if (board.size() == 0){
            return hand1.get(0) + "|" + hand1.get(1) + " won against " +   hand2.get(0) + "|" + hand2.get(1) +  " on a random board " + Math.round(hand1wins/1000) + "% of the time and tied " + Math.round(ties/1000) + "% of the time after 100,000 hands.";
        }
        return "";
    }

    //Requires: sims.txt exists
    //Modifies: sims.txt
    //Effects: Writes Simulation.toString() to sims.txt
    public void writeToFile() throws IOException{
        FileWriter fr = new FileWriter("sims.txt",true);
        BufferedWriter br = new BufferedWriter(fr);
        br.write(toString() + "\r");
        br.write(";\r");
        br.close();

    }

}
