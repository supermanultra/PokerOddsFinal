package sample;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class saveSim {
    private static FileReader fr;
    private static BufferedReader br;
    private ArrayList<String> sims = new ArrayList<String>();

    //Requires: valid filename
    //Modifies: this
    //Effects: Fills sims ArrayList with all Simulation results in given txt file
    public ArrayList<String> createAllSims() throws IOException {
        fr = new FileReader("sims.txt");
        br = new BufferedReader(fr);
        String line;
        String simString = "";
        while ((line = br.readLine()) != null) {
            if (!line.equals( ";")){
                sims.add(line);
            }


        }
        return sims;
    }
}

