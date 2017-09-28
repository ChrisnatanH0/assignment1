import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class JPopUp extends JFrame implements ActionListener {
    JLabel label = new JLabel();
    JTextField textField = new JTextField(1);
    JButton button = new JButton("Enter");
    Font font = new Font("Arial", Font.PLAIN, 24);
    int playerAmount;
    String trumpSelect;
    String instruction;

    public JPopUp(String instruction) {
        super("SuperTrumps");
        this.instruction = instruction;
        setLayout(new GridLayout(3, 1));
        setSize(700, 500);
        //setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label.setText(instruction);
        label.setFont(font);
        textField.setFont(font);
        button.setFont(font);
        add(label);
        add(textField);
        add(button);
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Objects.equals(instruction, "How many players? (3-5 players, 1 player will be the dealer)")) {
            playerAmount = Integer.parseInt(textField.getText()) - 1;
            if (playerAmount < 2 || playerAmount > 4) {
                label.setText("Please input a valid number of players");
            } else {
                setVisible(false);
            }
        } else if (Objects.equals(instruction, "Select which value to use (Hardness, Gravity, Cleavage, Abundance, EcoValue): ")) {

        }
    }
}
