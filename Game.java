import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Game {
    public static void main(String[] args) throws InputMismatchException{
        Scanner playerInput = new Scanner(System.in);
        ArrayList<Card> cards = new ArrayList<>();
        ArrayList<Card> player1Cards = new ArrayList<>();
        ArrayList<Card> player2Cards = new ArrayList<>();
        ArrayList<Card> player3Cards = new ArrayList<>();
        ArrayList<Card> player4Cards = new ArrayList<>();
        ArrayList<Card> currentCardSet = new ArrayList<>();
        List<String> superTrumps = Arrays.asList("miner", "petrologist", "gemmologist", "mineralogist", "geophysicist", "geologist");
        List<String> trumps = Arrays.asList("ecovalue", "abundance", "hardness", "cleavage", "gravity", "any");
        ArrayList<Integer> playerWins = new ArrayList<>();
        ArrayList<Integer> playerPass = new ArrayList<>();
        boolean firstTurn = true;
        boolean flag = true;
        int numOfPlayers, playerTurn = 1, cardSelect = 0;
        double currentHighest = 0, valueSelect;
        String trumpSelect = "hardness";
        String currentHighestString = "";

        ReadCSV(cards);

        //Adds the SuperTrump cards to the list of cards
        for (int i = 0; i < 6; i++) {
            Card st = new Supertrump(superTrumps.get(i), trumps.get(i));
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

        //The game runs
        while (flag) {
            //Display list of cards
            switch (playerTurn) {
                case 1:
                    DisplayCards(player1Cards);
                    currentCardSet = player1Cards;
                    break;
                case 2:
                    DisplayCards(player2Cards);
                    currentCardSet = player2Cards;
                    break;
                case 3:
                    DisplayCards(player3Cards);
                    currentCardSet = player3Cards;
                    break;
                case 4:
                    DisplayCards(player4Cards);
                    currentCardSet = player4Cards;
                    break;
            }

            System.out.print("Select your card: ");
            cardSelect = playerInput.nextInt();
            //Error checking
            do {
                if (cardSelect < 0 || cardSelect >= currentCardSet.size()) {
                    //If the player chooses to pass
                    if (cardSelect == 999) {
                        playerPass.add(playerTurn);
                        break;
                    } else {
                        System.out.println("ERROR! Invalid input!");
                        System.out.print("Select your card: ");
                        cardSelect = playerInput.nextInt();
                    }
                }
                //Players cannot use their SuperTrump cards in the first turn
                else if (firstTurn && superTrumps.contains(currentCardSet.get(cardSelect).getName())){
                    System.out.println("You cannot use a SuperTrump card right now!");
                    System.out.print("Select your card: ");
                    cardSelect = playerInput.nextInt();
                } else {
                    break;
                }
            } while (true);

            //If this is the first turn
            if (firstTurn) {
                trumpSelect = SelectTrump(playerInput, trumps);
                currentHighest = currentCardSet.get(cardSelect).getValueBasedOnInput(trumpSelect);
                currentHighestString = "The current highest " + trumpSelect + ": " + currentCardSet.get(cardSelect).getStringBasedOnInput(trumpSelect);
                System.out.println(currentHighestString);
                firstTurn = false;
            }

            //If this isn't the first turn
            else {
                //If it is a regular card
                if (!(superTrumps.contains(currentCardSet.get(cardSelect).getName()))) {
                    valueSelect = currentCardSet.get(cardSelect).getValueBasedOnInput(trumpSelect);
                    while (valueSelect <= currentHighest) {
                        System.out.println("The value is not high enough!");
                        System.out.println(currentHighestString);
                        DisplayCards(currentCardSet);
                    }
                    currentHighest = valueSelect;
                    currentHighestString = "The current highest " + trumpSelect + ": " + currentCardSet.get(cardSelect).getStringBasedOnInput(trumpSelect);
                    System.out.println(currentHighestString);

                }

                //If it is a SuperTrump card
                else {
                    //Check if it is the Geologist card
                    if (!Objects.equals(currentCardSet.get(cardSelect).getName(), "geologist")) {
                        trumpSelect = currentCardSet.get(cardSelect).getTrump();
                    } else {
                        trumpSelect = SelectTrump(playerInput, trumps);
                    }
                    System.out.println("Trump has been changed to: " + trumpSelect);
                    playerPass.clear();
                    currentHighestString = "The current highest " + trumpSelect + ": " + 0;
                    System.out.println(currentHighestString);
                }
            }

            //Check if the player loses all of the cards
            if (currentCardSet.size() == 0) {
                System.out.println("Player " + playerTurn + " has no more cards!");
                playerWins.add(playerTurn);
            }

            //Change the current player's turn
            do {
                playerTurn++;
                if (playerPass.contains(playerTurn)) playerTurn++;
                else if (playerWins.contains(playerTurn)) playerTurn++;
                else if (playerTurn > 4) playerTurn = 1;
                else break;
            } while(true);

            //Check if all but one player chose to pass
            if (playerPass.size() == numOfPlayers - 1) {
                playerPass.clear();
                firstTurn = true;
            }

            //Check if all but one player wins
            if (playerWins.size() == numOfPlayers - 1) {
                flag = false;
            }
        }

        //Prints out the list of winners
        System.out.println("GAME OVER!");
        System.out.println("Winning Players:");
        for (int i = 0; i < playerWins.size(); i++) {
            System.out.println(i+1 + ". Player " + playerWins.get(i));
        }
        System.out.println("Thank you for playing!");
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
    public static void ShuffleCards(ArrayList<Card> cards, ArrayList<Card> playerCards) {
        Random randNum = new Random();
        int arrayNum;
        for (int i = 0; i < 5; i++) {
            arrayNum = randNum.nextInt(cards.size());
            playerCards.add(cards.get(arrayNum));
        }
    }

    //Prints a list of the player's cards
    public static void DisplayCards(ArrayList<Card> playerCards) {
        for (int i = 0; i < playerCards.size(); i++) {
            System.out.println(i + " -> " + playerCards.get(i));
        }
        System.out.println(999 + " -> pass your turn");
    }
}
