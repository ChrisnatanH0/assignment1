import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;

public class JGame extends JFrame implements ActionListener {
    //New variables
    JPanel panel1, panel2, panel3, mp;
    JLabel label = new JLabel();
    JLabel topCard = new JLabel();
    JButton cardButton = new JButton();
    JButton prev = new JButton("< Prev. Card");
    JButton next = new JButton("Next Card >");
    JButton pass = new JButton("Pass Turn");
    Font font = new Font("Arial", Font.BOLD, 36);
    ImageIcon topIcon;
    ImageIcon buttonIcon;
    ArrayList<Card> p1Cards, p2Cards, p3Cards, p4Cards;
    ArrayList<ImageIcon> p1Imgs, p2Imgs, p3Imgs, p4Imgs;

    //Old variables from Game
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<ImageIcon> imgs = new ArrayList<>();
    ArrayList<Card> currentCardSet = new ArrayList<>();
    ArrayList<ImageIcon> currentImgSet = new ArrayList<>();
    ArrayList<String> playerWins = new ArrayList<>();
    ArrayList<String> playerPass = new ArrayList<>();
    List<String> superTrumps = Arrays.asList("miner", "petrologist", "gemmologist", "mineralogist", "geophysicist", "geologist");
    List<String> trumps = Arrays.asList("ecovalue", "abundance", "hardness", "cleavage", "gravity", "any");
    boolean firstTurn = true;
    int numOfPlayers = 4, playerTurn = 1, id = 0;
    double currentHighest = 0, valueSelect;
    String trumpSelect = "hardness";
    boolean flag = false;

    public JGame(ArrayList<Card> cards, ArrayList<ImageIcon> imgs, ArrayList<Card> p1Cards, ArrayList<Card> p2Cards, ArrayList<Card> p3Cards, ArrayList<Card> p4Cards,
                 ArrayList<ImageIcon> p1Imgs, ArrayList<ImageIcon> p2Imgs, ArrayList<ImageIcon> p3Imgs, ArrayList<ImageIcon> p4Imgs) {
        super("SuperTrumps");
        setSize(2500, 3000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.cards = cards;
        this.imgs = imgs;
        this.p1Cards = p1Cards;
        this.p1Imgs = p1Imgs;
        this.p2Cards = p2Cards;
        this.p2Imgs = p2Imgs;
        this.p3Cards = p3Cards;
        this.p3Imgs = p3Imgs;
        this.p4Cards = p4Cards;
        this.p4Imgs = p4Imgs;

        mp = new JPanel(new GridLayout(3, 1));
        setContentPane(mp);
        panel1 = new JPanel(new GridLayout(1, 2));
        mp.add(panel1);
        panel2 = new JPanel(new GridLayout(1, 3));
        mp.add(panel2);
        panel3 = new JPanel (new FlowLayout());
        mp.add(panel3);

        label.setFont(font);
        label.setText("Player " + playerTurn + "'s turn!");
        panel1.add(label);
        topIcon = new ImageIcon("Slide66.jpg");
        topCard.setIcon(ResizedIcon(topIcon, 500, 700));
        panel1.add(topCard);

        switch (playerTurn) {
            case 1:
                currentCardSet = p1Cards;
                currentImgSet = p1Imgs;
                break;
            case 2:
                currentCardSet = p2Cards;
                currentImgSet = p2Imgs;
                break;
            case 3:
                currentCardSet = p3Cards;
                currentImgSet = p3Imgs;
                break;
            case 4:
                currentCardSet = p4Cards;
                currentImgSet = p4Imgs;
                break;
        }

        buttonIcon = new ImageIcon(String.valueOf(currentImgSet.get(id)));
        cardButton.setIcon(ResizedIcon(buttonIcon, 500, 700));
        prev.setFont(font);
        next.setFont(font);
        panel2.add(prev);
        panel2.add(cardButton);
        panel2.add(next);
        cardButton.addActionListener(this);
        prev.addActionListener(this);
        next.addActionListener(this);

        pass.setFont(font);
        panel3.add(pass);
        pass.addActionListener(this);

        numOfPlayers = Integer.parseInt(JOptionPane.showInputDialog(mp, "How many players? (2 - 4 players)", 4));
        while (numOfPlayers < 2 || numOfPlayers > 4 || numOfPlayers == 0) {
            numOfPlayers = Integer.parseInt(JOptionPane.showInputDialog(mp, "Please input correctly! (2 - 4 players)", 4));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Previous card
        if (e.getSource() == prev) {
            id -= 1;
            if (id < 0) {
                id = currentImgSet.size() - 1;
            }
            buttonIcon = new ImageIcon(String.valueOf(currentImgSet.get(id)));
            cardButton.setIcon(ResizedIcon(buttonIcon, 500, 700));
        }

        //Next card
        else if (e.getSource() == next) {
            id += 1;
            if (id > currentImgSet.size() - 1) {
                id = 0;
            }
            buttonIcon = new ImageIcon(String.valueOf(currentImgSet.get(id)));
            cardButton.setIcon(ResizedIcon(buttonIcon, 500, 700));
        }

        //Choose card
        else if (e.getSource() == cardButton) {
            //If it is a SuperTrump card
            if (superTrumps.contains(currentCardSet.get(id).getName())) {
                if (firstTurn) {
                    label.setText("Player " + playerTurn + "'s turn! You cannot use a SuperTrump card right now!");
                }
                else {
                    if (Objects.equals(currentCardSet.get(id).getName(), "geologist")) {
                        trumpSelect = JOptionPane.showInputDialog(mp, "Select which value to use (Hardness, Gravity, Cleavage, Abundance, EcoValue)", "hardness").toLowerCase();
                        while (!(trumps.contains(trumpSelect))) {
                            trumpSelect = JOptionPane.showInputDialog(mp, "Please input correctly! (Hardness, Gravity, Cleavage, Abundance, EcoValue)", "hardness").toLowerCase();
                        }
                    }
                    else {
                        trumpSelect = currentCardSet.get(id).getTrump();
                    }
                    currentHighest = 0;
                    topIcon = new ImageIcon("Slide66.jpg");
                    topCard.setIcon(ResizedIcon(topIcon, 500, 700));
                    flag = true;
                }
            }

            //If it is a regular card
            else {
                if (firstTurn) {
                    trumpSelect = JOptionPane.showInputDialog(mp, "Select which value to use (Hardness, Gravity, Cleavage, Abundance, EcoValue)", "hardness").toLowerCase();
                    while (!(trumps.contains(trumpSelect))) {
                        trumpSelect = JOptionPane.showInputDialog(mp, "Please input correctly! (Hardness, Gravity, Cleavage, Abundance, EcoValue)", "hardness").toLowerCase();
                    }
                    currentHighest = currentCardSet.get(id).getValueBasedOnInput(trumpSelect);
                    topIcon = new ImageIcon(String.valueOf(currentImgSet.get(id)));
                    topCard.setIcon(ResizedIcon(topIcon, 500, 700));
                    firstTurn = false;
                    flag = true;
                }
                else {
                    valueSelect = currentCardSet.get(id).getValueBasedOnInput(trumpSelect);
                    if (valueSelect < currentHighest) {
                        label.setText("Player " + playerTurn + "'s turn! The value is not high enough!");
                    }
                    else {
                        currentHighest = valueSelect;
                        topIcon = new ImageIcon(String.valueOf(currentImgSet.get(id)));
                        topCard.setIcon(ResizedIcon(topIcon, 500, 700));
                        flag = true;
                    }
                }
            }

            if (flag) {
                PostProcessing();
                flag = false;
            }
        }

        //Pass turn
        else if (e.getSource() == pass) {
            playerPass.add("player"+playerTurn);
            Game.DrawCard(cards, imgs, currentCardSet, currentImgSet);
            PostProcessing();
        }
    }

    public void PostProcessing() {
        currentCardSet.remove(id);
        currentImgSet.remove(id);

        if (currentCardSet.size() == 0) {
            System.out.println("Player " + playerTurn + " has no more cards!");
            playerWins.add("player"+playerTurn);
        }

        switch (playerTurn) {
            case 1:
                p1Cards = currentCardSet;
                p1Imgs = currentImgSet;
                break;
            case 2:
                p2Cards = currentCardSet;
                p2Imgs = currentImgSet;
                break;
            case 3:
                p3Cards = currentCardSet;
                p3Imgs = currentImgSet;
                break;
            case 4:
                p4Cards = currentCardSet;
                p4Imgs = currentImgSet;
                break;
        }

        playerTurn++;
        do {
            if (playerPass.contains("player" + playerTurn)) {
                playerTurn++;
            } else if (playerWins.contains("player" + playerTurn)) {
                playerTurn++;
            } else if (playerTurn > numOfPlayers) {
                playerTurn = 1;
            } else {
                break;
            }
        } while (true);

        switch (playerTurn) {
            case 1:
                currentCardSet = p1Cards;
                currentImgSet = p1Imgs;
                break;
            case 2:
                currentCardSet = p2Cards;
                currentImgSet = p2Imgs;
                break;
            case 3:
                currentCardSet = p3Cards;
                currentImgSet = p3Imgs;
                break;
            case 4:
                currentCardSet = p4Cards;
                currentImgSet = p4Imgs;
                break;
        }

        if (playerPass.size() == numOfPlayers - 1) {
            playerPass.clear();
        }

        if (playerWins.size() == numOfPlayers - 1) {
           JOptionPane.showMessageDialog(mp, "GAME OVER! Thanks for Playing!");
        }

        label.setText("Player " + playerTurn + "'s turn!");
        buttonIcon = new ImageIcon(String.valueOf(currentImgSet.get(id)));
        cardButton.setIcon(ResizedIcon(buttonIcon, 500, 700));
    }

    private static Icon ResizedIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(resizedWidth, resizedHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
