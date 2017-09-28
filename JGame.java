import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class JGame extends JFrame implements ActionListener {
    JPanel panel1, panel2, panel3, mp;
    JLabel label = new JLabel();
    JLabel topCard = new JLabel();
    JButton cardButton = new JButton();
    JButton prev = new JButton("< Prev. Card");
    JButton next = new JButton("Next Card >");
    JButton pass = new JButton("Pass Turn");
    Font font = new Font("Arial", Font.BOLD, 36);
    //JPopUp popup = new JPopUp("Select which value to use (Hardness, Gravity, Cleavage, Abundance, EcoValue): ");

    CardImgs imgs = new CardImgs();
    ArrayList<Card> currentCardSet = new ArrayList<>();
    ArrayList<ImageIcon> currentImg = new ArrayList<>();
    ArrayList<String> playerWins = new ArrayList<>();
    ArrayList<String> playerPass = new ArrayList<>();
    boolean firstTurn = true;
    int numOfPlayers = 4, playerTurn = 1, id = 0;
    double currentHighest = 0, valueSelect;
    String trumpSelect = "hardness";
    String currentHighestString = "";

    public JGame(ArrayList<Card> allCards, ArrayList<Card> p1Cards, ArrayList<Card> p2Cards, ArrayList<Card> p3Cards, ArrayList<Card> p4Cards,
                 ArrayList<ImageIcon> p1Imgs, ArrayList<ImageIcon> p2Imgs, ArrayList<ImageIcon> p3Imgs, ArrayList<ImageIcon> p4Imgs) {
        super("SuperTrumps");
        setSize(2000, 3000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        ImageIcon topIcon = new ImageIcon("Slide66.jpg");
        topCard.setIcon(resizedIcon(topIcon, 500, 700));
        panel1.add(topCard);

        switch (playerTurn) {
            case 1:
                currentCardSet = p1Cards;
                currentImg = p1Imgs;
                break;
            case 2:
                currentCardSet = p2Cards;
                currentImg = p2Imgs;
                break;
            case 3:
                currentCardSet = p3Cards;
                currentImg = p3Imgs;
                break;
            case 4:
                currentCardSet = p4Cards;
                currentImg = p4Imgs;
                break;
    }

        ImageIcon buttonIcon = new ImageIcon(String.valueOf(currentImg.get(id)));
        cardButton.setIcon(resizedIcon(buttonIcon, 500, 700));
        prev.setFont(font);
        next.setFont(font);
        panel2.add(prev);
        panel2.add(cardButton);
        panel2.add(next);

        pass.setFont(font);
        panel3.add(pass);

        numOfPlayers = Integer.parseInt(JOptionPane.showInputDialog(mp, "How many players? (2 - 4 players)", 4));
        while (numOfPlayers < 2 || numOfPlayers > 4 || numOfPlayers == 0) {
            numOfPlayers = Integer.parseInt(JOptionPane.showInputDialog(mp, "Please input correctly! (2 - 4 players)", 4));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private static Icon resizedIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(resizedWidth, resizedHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
