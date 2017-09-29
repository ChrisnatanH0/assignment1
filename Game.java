import javax.swing.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Game {
    public static void main(String[] args) throws InputMismatchException{
        Scanner playerInput = new Scanner(System.in);
        ArrayList<Card> cards = new ArrayList<>();
        CardImgs imgs = new CardImgs();
        ArrayList<Card> p1Cards = new ArrayList<>();
        ArrayList<ImageIcon> p1Imgs = new ArrayList<>();
        ArrayList<Card> p2Cards = new ArrayList<>();
        ArrayList<ImageIcon> p2Imgs = new ArrayList<>();
        ArrayList<Card> p3Cards = new ArrayList<>();
        ArrayList<ImageIcon> p3Imgs = new ArrayList<>();
        ArrayList<Card> p4Cards = new ArrayList<>();
        ArrayList<ImageIcon> p4Imgs = new ArrayList<>();
        ArrayList<Card> currentCardSet = new ArrayList<>();
        ArrayList<ImageIcon> currentCardImgs = new ArrayList<>();
        List<String> superTrumps = Arrays.asList("miner", "petrologist", "gemmologist", "mineralogist", "geophysicist", "geologist");
        List<String> trumps = Arrays.asList("ecovalue", "abundance", "hardness", "cleavage", "gravity", "any");
        ArrayList<String> playerWins = new ArrayList<>();
        ArrayList<String> playerPass = new ArrayList<>();
        boolean firstTurn = true;
        boolean flag = true;
        int numOfPlayers = 4, playerTurn = 1, id = 0;
        double currentHighest = 0, valueSelect;
        String trumpSelect = "hardness";
        String currentHighestString = "";

        ReadCSV(cards);

        //Adds the SuperTrump cards to the list of cards
        for (int i = 0; i < 6; i++) {
            Card st = new Supertrump(superTrumps.get(i), trumps.get(i));
            cards.add(st);
        }

        ShuffleCards(cards, imgs.getCards(), p1Cards, p1Imgs);
        ShuffleCards(cards, imgs.getCards(), p2Cards, p2Imgs);
        if (numOfPlayers >= 3) ShuffleCards(cards, imgs.getCards(), p3Cards, p3Imgs);
        if (numOfPlayers == 4) ShuffleCards(cards, imgs.getCards(), p4Cards, p4Imgs);
        JGame jGame = new JGame(cards, imgs.cards, p1Cards, p2Cards, p3Cards, p4Cards, p1Imgs, p2Imgs, p3Imgs, p4Imgs);
        jGame.setVisible(true);
    }

    //Select a trump
    public static String SelectTrump(Scanner playerInput, List<String> trumps) {
        String trumpSelect;
        System.out.print("Select which value to use (Hardness, Gravity, Cleavage, Abundance, EcoValue): ");
        trumpSelect = playerInput.next().toLowerCase();
        while (!(trumps.contains(trumpSelect))) {
            System.out.println("ERROR! Invalid input!");
            System.out.print("Select which trump to use (Hardness, Gravity, Cleavage, Abundance, EcoValue): ");
            trumpSelect = playerInput.next().toLowerCase();
        }
        return trumpSelect;
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
    public static void ShuffleCards(ArrayList<Card> cards, ArrayList<ImageIcon> cardImgs, ArrayList<Card> playerCards, ArrayList<ImageIcon> playerImgs) {
        Random randNum = new Random();
        int arrayNum;
        for (int i = 0; i < 5; i++) {
            arrayNum = randNum.nextInt(cards.size());
            playerCards.add(cards.get(arrayNum));
            playerImgs.add(cardImgs.get(arrayNum));
        }
    }

    //Prints a list of the player's cards
    public static void DisplayCards(ArrayList<Card> playerCards) {
        for (int i = 0; i < playerCards.size(); i++) {
            System.out.println(i + " -> " + playerCards.get(i));
        }
        System.out.println(999 + " -> pass your turn");
    }

    //Draws a card for a player
    public static void DrawCard (ArrayList<Card> cards, ArrayList<ImageIcon> cardImgs, ArrayList<Card> playerCards, ArrayList<ImageIcon> playerImgs) {
        Random randnum = new Random();
        int arraynum = randnum.nextInt(cards.size());
        playerCards.add(cards.get(arraynum));
        playerImgs.add(cardImgs.get(arraynum));
    }
}
