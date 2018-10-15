import org.junit.Before;
import org.junit.Test;
import sample.Card;
import sample.Rules;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Tests {

    ArrayList<Card> hand = new ArrayList<Card>();
    ArrayList<Card> board = new ArrayList<Card>();
    Card twoClubs = new Card("c",2,"",0);
    Card twoDia = new Card("d",2,"",0);
    Card fourDia = new Card("d",4,"",0);
    Card tenDia = new Card("d",10,"",0);
    Card aceHearts = new Card("h",14,"",0);
    Card fourClubs = new Card("c",4,"",0);
    Card twoHearts = new Card("h",2,"",0);
    Card twoSpades = new Card("s",2,"",0);
    Card aceSpades = new Card("s",14,"",0);
    Card fourSpades = new Card("s",4,"",0);
    Card threeDia = new Card("d",3,"",0);
    Card sixDia = new Card("d",6,"",0);
    Card sevenDia = new Card("d",7,"",0);
    Card fiveClubs = new Card("c",5,"",0);
    Card fiveDia = new Card("d",5,"",0);
    Card kingHearts = new Card("h",13,"",0);
    Card queenHearts = new Card("h",12,"",0);
    Card jackHearts = new Card("h",11,"",0);
    Card tenHearts = new Card("h",10,"",0);
    Card aceClubs = new Card("c",14,"",0);
    ArrayList<Integer> values = new ArrayList<Integer>();

    @Before
    public void setup(){
        hand.add(twoClubs);
        hand.add(twoDia);
        board.add(fourDia);
        board.add(tenDia);
        board.add(aceHearts);
        values.add(14);
        values.add(2);
        values.add(6);
    }


    @Test
    public void testBestCards(){
        assertEquals(.14,Rules.bestCards(values,1),0);
        assertEquals(.02, Rules.bestCards(Rules.isPair(Rules.vals(hand,board)),1),0);

    }

    @Test
    public void testIsPair(){
        //Makes sure a pair is found and reported when there are two cards with value 2
        assertEquals(.02, Rules.bestCards(Rules.isPair(Rules.vals(hand,board)),1),0);
        //Makes sure no number reported if there is no pair
        hand.remove(twoClubs);
        assertEquals(0, Rules.isPair(Rules.vals(hand,board)).size());

    }

    @Test
    public void testIsTwoPair(){
        //Checks two pairs are found and reported when there are two pairs of cards with the same value
        hand.add(fourClubs);
        assertEquals(.04, Rules.bestCards(Rules.isPair(Rules.vals(hand,board)),1),0);
    }



    @Test
    public void testThreeOfAKind(){
        //Makes sure three of a kind are found and reported when there are three cards of the same value
        hand.add(twoHearts);
        assertEquals(3.02, Rules.threeOfAKind((Rules.vals(hand,board))),0);
        //Checks no number is reported if there are four of a kind
        hand.add(twoSpades);
        assertEquals(0,Rules.threeOfAKind(Rules.vals(hand,board)),0);
        //Checks no number reported if there are two pairs or one pair
        hand.remove(twoHearts);
        hand.remove(twoSpades);
        assertEquals(0,Rules.threeOfAKind(Rules.vals(hand,board)),0);
        hand.add(fourClubs);
        assertEquals(0,Rules.threeOfAKind(Rules.vals(hand,board)),0);

    }

    @Test
    public void testFourOfAKind(){
        //Checks four of a kind are found and reported when there are four cards of the same value
        hand.add(twoHearts);
        hand.add(twoSpades);
        assertEquals(7.02, Rules.fourOfAKind(Rules.vals(hand,board)),0);
        //Checks no number is reported if there are three of a kind
        hand.remove(twoHearts);
        assertEquals(0, Rules.fourOfAKind(Rules.vals(hand,board)),0);
        //Checks no number is reported if there are two pairs or one pair
        hand.add(fourClubs);
        assertEquals(0, Rules.fourOfAKind(Rules.vals(hand,board)),0);
        hand.remove(twoSpades);
        hand.remove(fourClubs);
        assertEquals(0, Rules.fourOfAKind(Rules.vals(hand,board)),0);
    }

    @Test
    public void testFullHouse(){
        //Checks full house is found and reported when there are three of a kind and a pair in a hand
        hand.add(twoHearts);
        hand.add(fourClubs);
        assertEquals(6.0204, Rules.fullHouse(Rules.vals(hand,board)),0);
        //Checks a full house with an equal three of a kind but better pair scores higher
        hand.add(aceSpades);
        assertEquals(6.0214, Rules.fullHouse(Rules.vals(hand,board)),0);
        //Checks a full house with better three of a kind but equal pair scores higher
        hand.add(fourSpades);
        hand.remove(twoHearts);
        assertEquals(6.0414, Rules.fullHouse(Rules.vals(hand,board)),0.00001);
        //Tests if there's two three of a kinds if you get a full house
        hand.add(aceClubs);
        hand.remove(twoDia);
        assertEquals(6.1404,Rules.fullHouse(Rules.vals(hand,board)),0.00001);
    }

    @Test
    public void testFlush(){
        //Checks flush is found and reported when there are five of the same suit
        hand.add(threeDia);
        hand.add(sixDia);
        assertEquals(5.1,Rules.isFlush(hand,board),0);
        //Checks flush is found when there are more than five of the same suit
        hand.add(sevenDia);
        assertEquals(5.1,Rules.isFlush(hand,board),0);
        //Checks no flush is found when there are fewer than five of the same suit
        hand.remove(threeDia);
        hand.remove(sevenDia);
        assertEquals(0,Rules.isFlush(hand,board),0);
    }

    @Test
    public void testStraight(){
        //Checks straight is found and reported when there are five cards in a row
        hand.add(threeDia);
        hand.add(fiveClubs);
       // assertEquals(4.05, Rules.isStraight(Rules.vals(hand,board)),0);
        //Checks straight is found and reported when there are different five cards in a row
        hand.add(sixDia);
       // assertEquals(4.06, Rules.isStraight(Rules.vals(hand,board)),0);
        //Checks straight is not found when there are fewer than five cards in a row
        hand.remove(sixDia);
        hand.remove(fiveClubs);
        //assertEquals(0,Rules.isStraight(Rules.vals(hand,board)),0);
        //Checks on only five cards
        board.clear();
        hand.clear();
        hand.add(fiveDia);
        hand.add(sixDia);
        board.add(twoDia);
        board.add(threeDia);
        board.add(fourDia);
        assertEquals(4.06, Rules.isStraight(Rules.vals(hand,board)),0);
    }

    @Test
    public void testStraightFlush(){
        //Checks straight flush is found and reported when there are five cards of the same suit in a row
        hand.add(threeDia);
        hand.add(fiveDia);
        hand.add(sixDia);
        assertEquals(8.06, Rules.isStraightFlush(hand,board),0.00001);
        //Checks straight flush is not found when there is no flush but a straight
        board.remove(tenDia);
        board.remove(fourDia);
        hand.add(fourSpades);
        assertEquals(0,Rules.isStraightFlush(hand,board),0);
        assertTrue(Rules.isStraight(Rules.vals(hand,board)) > 0);
        //Checks straight flush is not found when there is flush but no straight
        board.add(tenDia);
        assertEquals(0,Rules.isStraightFlush(hand,board),0);
        assertTrue(Rules.isFlush(hand,board) > 0);
        //Checks if board is straight flush
        board.clear();
        hand.clear();
        hand.add(aceClubs);
        hand.add(kingHearts);
        board.add(twoDia);
        board.add(threeDia);
        board.add(fourDia);
        board.add(fiveDia);
        board.add(sixDia);
        assertEquals(8.06, Rules.isStraightFlush(hand,board),0.001);

    }

    @Test
    public void testRoyalFlush(){
        //Checks royal flush is found when present
        hand.add(kingHearts);
        hand.add(queenHearts);
        hand.add(jackHearts);
        hand.add(tenHearts);
        assertEquals(9, Rules.isRoyal(hand,board),0);
        //Checks royal flush is not found if all royals are not present
        hand.remove(tenHearts);
        assertEquals(0, Rules.isRoyal(hand,board),0);

    }

    @Test
    public void testScore(){
        //Checks score of high card hand is correct
        hand.remove(twoDia);
        hand.add(sixDia);
        //assertEquals(0.14,Rules.scoreHand(hand,board),0);
        //Checks score of single pair hand is correct
        hand.add(twoDia);
        //assertEquals(1.02,Rules.scoreHand(hand,board),0);
        //Checks score of two pair hand is correct
        hand.add(fourSpades);
        //assertEquals(2.0402, Rules.scoreHand(hand,board),0);
        //Checks score of three of a kind is correct
        hand.add(twoSpades);
        hand.remove(fourSpades);
        assertEquals(3.02, Rules.scoreHand(hand,board),0);
        //Checks score of straight is correct
        board.remove(tenDia);
        hand.add(fiveClubs);
        hand.add(threeDia);
        assertEquals(4.06, Rules.scoreHand(hand,board),0);
        //Checks score of flush is correct
        hand.add(tenDia);
        assertEquals(5.1, Rules.scoreHand(hand,board),0);
        //Checks score of full house is correct
        hand.add(fourClubs);
        assertEquals(6.0204, Rules.scoreHand(hand,board),0);
        //Checks score of four of a kind is correct
        hand.add(twoHearts);
        assertEquals(7.02,  Rules.scoreHand(hand,board),0);
        //Checks score of straight flush is correct
        hand.add(fiveDia);
        assertEquals(8.06, Rules.scoreHand(hand,board),0.00001);
        //Checks score of royal flush is correct
        hand.remove(twoDia);
        hand.remove(tenDia);
        hand.add(kingHearts);
        hand.add(queenHearts);
        hand.add(jackHearts);
        hand.add(tenHearts);
        assertEquals(9,Rules.scoreHand(hand,board),0);

    }

    @Test
    public void testWinner(){
        ArrayList<Card> hand1 = new ArrayList<Card>();
        ArrayList<Card> hand2 = new ArrayList<Card>();
        ArrayList<Card> board1 = new ArrayList<Card>();
        hand1.add(twoHearts);
        hand1.add(aceHearts);
        hand2.add(twoSpades);
        hand2.add(aceSpades);
        board1.add(tenDia);
        board1.add(threeDia);
        board1.add(fourClubs);
        board1.add(sevenDia);
        board1.add(kingHearts);
        //Checks tie is found when hands have matching value
        assertEquals(0,Rules.findWinner(hand1,hand2,board1));
        //Checks hand1 wins when it has a higher high card value
        hand1.remove(twoHearts);
        hand1.add(jackHearts);
        assertEquals(1,Rules.findWinner(hand1,hand2,board1));
        //Checks tie is found when hands have matching pairs and high card
        hand1.remove(jackHearts);
        hand2.remove(twoSpades);
        hand1.add(fourSpades);
        hand2.add(fourDia);
        assertEquals(0,Rules.findWinner(hand1,hand2,board1));
        //Checks hand1 wins when it has a higher pair
        hand1.remove(fourSpades);
        hand1.add(tenHearts);
        assertEquals(1,Rules.findWinner(hand1,hand2,board1));
        //Checks hand2 wins when it has better two pair
        board1.remove(threeDia);
        board1.add(aceClubs);
        hand2.remove(fourDia);
        hand1.remove(tenHearts);
        hand1.add(fourDia);
        hand2.add(tenHearts);
        assertEquals(2,Rules.findWinner(hand1,hand2,board1));
        //Checks hand1 wins when it has three of a kind
        board1.remove(aceClubs);
        board1.add(fourSpades);
        assertEquals(1, Rules.findWinner(hand1,hand2,board1));

    }
}
