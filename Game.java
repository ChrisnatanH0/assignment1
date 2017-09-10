import java.text.ParseException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) throws InputMismatchException{
        Scanner playerInput = new Scanner(System.in);
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Card> player1Cards = new ArrayList<>();
        ArrayList<Card> player2Cards = new ArrayList<>();
        ArrayList<Card> player3Cards = new ArrayList<>();
        ArrayList<Card> player4Cards = new ArrayList<>();
        String[] superTrumpList = {"miner", "petrologist", "gemmologist", "mineralogist", "geophysicist", "geologist"};
        String[] trumpList = {"ecovalue", "abundance", "hardness", "cleavage", "gravity", "any"};
        ArrayList<Integer> playerWins = new ArrayList<>();
        ArrayList<Integer> playerPass = new ArrayList<>();
        boolean firstTurn = true;
        int numOfPlayers;

        ReadCSV(cards);

        //Adds the SuperTrump cards to the list of cards
        for (int i = 0; i < 6; i++) {
            Card st = new Supertrump(superTrumpList[i], trumpList[i]);
            cards.add(st);
        }

        System.out.println("Welcome to SuperTrumps!\nThis is a 3 - 5 player game. One player will be the dealer.\nPlease enter the number of players.");
        System.out.print(">>>");
        numOfPlayers = playerInput.nextInt() - 1;
        while (numOfPlayers < 2 || numOfPlayers > 4) {
            System.out.println("ERROR! Please input correctly!");
            System.out.print(">>>");
            numOfPlayers = playerInput.nextInt() - 1;
        }

        ShuffleCards(cards, player1Cards);
        ShuffleCards(cards, player2Cards);
        if (numOfPlayers >= 3) ShuffleCards(cards, player3Cards);
        if (numOfPlayers == 4) ShuffleCards(cards,player4Cards);
    }

    //Reads the card.csv file
    public static void ReadCSV(ArrayList<Card> cards) {
        BufferedReader br = null;
        String csvFile = "card.csv";
        String line = "";
        String csvSplitBy = ",";
        int count = 0;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                if (count > 0) {
                    double hardness = Double.parseDouble(data[1]);
                    double gravity = Double.parseDouble(data[2]);
                    Card mineral = new Card(data[0], hardness, gravity, data[3], data[4], data[5]);
                    cards.add(mineral);
                }
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Shuffle cards to a player
    public static void ShuffleCards(ArrayList<Card> cards, ArrayList<Card> playerCards) {
        Random randNum = new Random();
        int arrayNum;
        for (int i = 0; i < 5; i++) {
            arrayNum = randNum.nextInt(cards.size());
            playerCards.add(cards.get(arrayNum));
        }
    }
}
