package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Controller {
    public ListView<Card> listDeck = new ListView<Card>();
    public ListView<Card> listBoard = new ListView<Card>();
    public ListView<Card> listHand = new ListView<Card>();
    public ListView<Card> listHand2 = new ListView<Card>();
    public ListView<String> listSimHistory = new ListView<String>();
    public Button btnAddToBoard;
    public Button btnAddToHand;
    public Button btnResetBoard;
    public Button btnResetHand;
    public Button btnAddToHand2;
    public Button btnResetSecondHand;
    public Button btnRunSim;


    private Deck deck = new Deck();

    //Requires: nothing
    //Modifies: this
    //Effects: Initializes listDeck and pulls past simulations from sims.txt
    @FXML
    private void initialize() throws IOException {
        saveSim saved = new saveSim();
        for (Card s: deck.deck){
            listDeck.getItems().add(s);
        }
        listDeck.getSelectionModel().select(0);
        for (String s :saved.createAllSims()){
            listSimHistory.getItems().add(s);
        }
    }


    //Requires: Card is selected
    //Modifies: this
    //Effects: Adds selected card to listBoard
    public void addToBoard(ActionEvent actionEvent) {
        Card temp = listDeck.getSelectionModel().getSelectedItem();
        listDeck.getItems().remove(temp);
        listBoard.getItems().add(temp);
        if (listBoard.getItems().size() == 5){
            btnAddToBoard.setDisable(true);
        }
        checkValid();
    }

    //Requires: Card is selected
    //Modifies: this
    //Effects: Adds selected card to listHand
    public void addToHand(ActionEvent actionEvent) {
        Card temp = listDeck.getSelectionModel().getSelectedItem();
        listDeck.getItems().remove(temp);
        listHand.getItems().add(temp);
        if (listHand.getItems().size() == 2){
            btnAddToHand.setDisable(true);
        }
        checkValid();
    }

    //Requires: nothing
    //Modifies: this
    //Effects: Clears listBoard, adds content back to listDeck and sorts listDeck
    public void resetBoard(ActionEvent actionEvent) {
        correctIndex(listBoard);
        listBoard.getItems().clear();
        btnAddToBoard.setDisable(false);
        re();
        checkValid();
    }

    //Requires: nothing
    //Modifies: this
    //Effects: Clears listHand, adds content back to listDeck and sorts listDeck
    public void resetHand(ActionEvent actionEvent) {
        correctIndex(listHand);
        listHand.getItems().clear();
        btnAddToHand.setDisable(false);
        re();
        checkValid();
    }

    //Requires: nothing
    //Modifies: this
    //Effects: Clears listHand2, adds content back to listDeck and sorts listDeck
    public void resetSecondHand(ActionEvent actionEvent) {
        correctIndex(listHand2);
        re();
        listHand2.getItems().clear();
        btnAddToHand2.setDisable(false);
        checkValid();
    }

    //Requires: Card selected
    //Modifies: this
    //Effects: Adds selected card to listHand2
    public void addToHand2(ActionEvent actionEvent) {
        Card temp = listDeck.getSelectionModel().getSelectedItem();
        listDeck.getItems().remove(temp);
        listHand2.getItems().add(temp);
        if (listHand2.getItems().size() == 2){
            btnAddToHand2.setDisable(true);
        }
        checkValid();
    }

    //Requires: hand1/hand2/board have valid input
    //Modifies: this, sims.txt
    //Effects: Generates Simulation based on user input, adds result to listSimHistory and writes to sims.txt
    public void runSim(ActionEvent actionEvent) throws IOException {
        List<Card> t1 = listHand.getItems();
        List<Card> t2 = listHand2.getItems();
        List<Card> t3 = listBoard.getItems();
        ArrayList<Card> hand1 = new ArrayList<Card>(t1);
        ArrayList<Card> hand2 = new ArrayList<Card>(t2);
        ArrayList<Card> board = new ArrayList<Card>(t3);
        Simulation sim = new Simulation(hand1,hand2,board);
        listSimHistory.getItems().add(sim.toString());
        btnRunSim.setDisable(true);
        resetBoard(actionEvent);
        resetHand(actionEvent);
        resetSecondHand(actionEvent);
        sim.writeToFile();

    }

    //Requires: nothing
    //Modifies: this
    //Effects: Checks that input is valid before allowing user to access btnRunSim
    public void checkValid(){
        if (listHand.getItems().size() == 2){
            if (listHand2.getItems().size() == 2 && listBoard.getItems().size() == 0){
                btnRunSim.setDisable(false);
            }
            else if (listHand2.getItems().size() == 0 && listBoard.getItems().size() == 0){
                btnRunSim.setDisable(false);
            }
            else if (listHand2.getItems().size() == 0 && listBoard.getItems().size() == 5){
                btnRunSim.setDisable(false);
            }
            else {btnRunSim.setDisable(true);}
        }
        else {btnRunSim.setDisable(true);}
    }

    //Requires: nothing
    //Modifies: this
    //Effects: Adds cards from given list back to listDeck
    public void correctIndex(ListView<Card> list){
        for (Card c : list.getItems()){
            if (c.position <= listDeck.getItems().size()){
                listDeck.getItems().add(c.position,c);
            }
            else {listDeck.getItems().add(c);}

        }
    }
    //Requires: nothing
    //Modifies: this
    //Effects: Sorts listDeck to original order
    public void re(){
        listDeck.getItems().sort(new CardComparator());
    }
}
