package sample;

import java.util.Comparator;

class CardComparator implements Comparator<Card>
{

    @Override
    //Requires: Used on Card objects
    //Modifies: nothing
    //Effects: Comparator to be used on Card objects to sort them
    public int compare(Card o1, Card o2)
    {
        if (o1.position == o2.position)
        {
            return 0;
        }
        else if (o1.position > o2.position)
        {
                return 1;
        }
        else
            return -1;
    }

}