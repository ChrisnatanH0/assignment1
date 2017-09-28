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

        //JPopUp popup = new JPopUp("How many players? (3-5 players, 1 player will be the dealer)");


        ReadCSV(cards);

        //Adds the SuperTrump cards to the list of cards
        for (int i = 0; i < 6; i++) {
            Card st = new Supertrump(superTrumps.get(i), trumps.get(i));
            cards.add(st);
        }

        /*System.out.println("Welcome to SuperTrumps!\nThis is a 3 - 5 player game. One player will be the dealer.\nPlease enter the number of players.");
        System.out.print(">>>"); */

        /*popup.setVisible(true);
        if (!popup.isVisible()) {
            numOfPlayers = popup.playerAmount;
            ShuffleCards(cards, imgs, p1Cards, p1Imgs);
            ShuffleCards(cards, imgs, p2Cards, p2Imgs);
            if (numOfPlayers >= 3) ShuffleCards(cards, imgs, p3Cards, p3Imgs);
            if (numOfPlayers == 4) ShuffleCards(cards, imgs, p4Cards, p4Imgs);
            jGame.setVisible(true);
        }*/

        /*while (numOfPlayers < 2 || numOfPlayers > 4) {
            System.out.println("ERROR! Please input correctly!");
            System.out.print(">>>");
            numOfPlayers = playerInput.nextInt() - 1;
        }*/

        ShuffleCards(cards, imgs.getCards(), p1Cards, p1Imgs);
        ShuffleCards(cards, imgs.getCards(), p2Cards, p2Imgs);
        if (numOfPlayers >= 3) ShuffleCards(cards, imgs.getCards(), p3Cards, p3Imgs);
        if (numOfPlayers == 4) ShuffleCards(cards, imgs.getCards(), p4Cards, p4Imgs);
        JGame jGame = new JGame(cards, imgs.cards, p1Cards, p2Cards, p3Cards, p4Cards, p1Imgs, p2Imgs, p3Imgs, p4Imgs);
        jGame.setVisible(true);

        //The game runs
        /*while (flag) {
            System.out.println("Player " + playerTurn + "'s turn!");
            //Display list of cards
            switch (playerTurn) {
                case 1:
                    DisplayCards(p1Cards);
                    currentCardSet = p1Cards;
                    break;
                case 2:
                    DisplayCards(p2Cards);
                    currentCardSet = p2Cards;
                    break;
                case 3:
                    DisplayCards(p3Cards);
                    currentCardSet = p3Cards;
                    break;
                case 4:
                    DisplayCards(p4Cards);
                    currentCardSet = p4Cards;
                    break;
            }

            System.out.print("Select your card: ");
            id = playerInput.nextInt();
            //Error checking
            do {
                if (id < 0 || id >= currentCardSet.size()) {
                    //If the player chooses to pass
                    if (id == 999) {
                        playerPass.add("player"+playerTurn);
                        DrawCard(cards, currentCardSet);
                        break;
                    } else {
                        System.out.println("ERROR! Invalid input!");
                        System.out.print("Select your card: ");
                        id = playerInput.nextInt();
                    }
                }
                //Players cannot use their SuperTrump cards in the first turn
                else if (firstTurn && superTrumps.contains(currentCardSet.get(id).getName())){
                    System.out.println("You cannot use a SuperTrump card right now!");
                    System.out.print("Select your card: ");
                    id = playerInput.nextInt();
                } else {
                    break;
                }
            } while (true);

            //If this is the first turn
            if (firstTurn && !(playerPass.contains("player"+playerTurn))) {
                trumpSelect = SelectTrump(playerInput, trumps);
                currentHighest = currentCardSet.get(id).getValueBasedOnInput(trumpSelect);
                currentHighestString = "The current highest " + trumpSelect + ": " + currentCardSet.get(id).getStringBasedOnInput(trumpSelect);
                firstTurn = false;
            }

            //If this isn't the first turn
            else if (!firstTurn && !(playerPass.contains("player"+playerTurn))){
                //If it is a regular card
                if (!(superTrumps.contains(currentCardSet.get(id).getName()))) {
                    valueSelect = currentCardSet.get(id).getValueBasedOnInput(trumpSelect);
                    while (valueSelect <= currentHighest) {
                        System.out.println("The value is not high enough!");
                        System.out.println(currentHighestString);
                        DisplayCards(currentCardSet);
                        System.out.print("Select your card: ");
                        id = playerInput.nextInt();
                        valueSelect = currentCardSet.get(id).getValueBasedOnInput(trumpSelect);
                    }
                    currentHighest = valueSelect;
                    currentHighestString = "The current highest " + trumpSelect + ": " + currentCardSet.get(id).getStringBasedOnInput(trumpSelect);
                }

                //If it is a SuperTrump card
                else {
                    //Check if it is the Geologist card
                    if (!Objects.equals(currentCardSet.get(id).getName(), "geologist")) {
                        trumpSelect = currentCardSet.get(id).getTrump();
                    } else {
                        trumpSelect = SelectTrump(playerInput, trumps);
                    }
                    System.out.println("Trump has been changed to: " + trumpSelect);
                    playerPass.clear();
                    currentHighest = 0;
                    currentHighestString = "The current highest " + trumpSelect + ": " + 0;
                }
            }

            System.out.println(currentHighestString);

            //Remove a card
            if (id != 999) { currentCardSet.remove(id); }

            //Check if the player loses all of the cards
            if (currentCardSet.size() == 0) {
                System.out.println("Player " + playerTurn + " has no more cards!");
                playerWins.add("player"+playerTurn);
            }

            //Updates the card set of each player
            switch (playerTurn) {
                case 1:
                    p1Cards = currentCardSet;
                    break;
                case 2:
                    p2Cards = currentCardSet;
                    break;
                case 3:
                    p3Cards = currentCardSet;
                    break;
                case 4:
                    p4Cards = currentCardSet;
                    break;
            }

            //Change the current player's turn
            playerTurn++;
            do {
                if (playerPass.contains("player"+playerTurn)) { playerTurn++; }
                else if (playerWins.contains("player"+playerTurn)) { playerTurn++; }
                else if (playerTurn > numOfPlayers) { playerTurn = 1; }
                else { break; }
            } while(true);

            //Check if all but one player chose to pass
            if (playerPass.size() == numOfPlayers - 1) {
                playerPass.clear();
            }

            //Check if all but one player wins
            if (playerWins.size() == numOfPlayers - 1) {
                flag = false;
            }
        }

        //Prints out the list of winners
        System.out.println("GAME OVER!");
        System.out.println("Winning Players:");
        for (String each:playerWins) {
            System.out.println(each);
        }
        System.out.println("Thank you for playing!");*/
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
