import javax.swing.*;
import java.util.ArrayList;

public class CardImgs {
    ArrayList<ImageIcon> cards = new ArrayList<>();

    public CardImgs() {
        for (int i = 1; i <= 60; i++) {
            if (i < 10) {
                cards.add(new ImageIcon("Slide0"+i+".jpg"));
            } else {
                cards.add(new ImageIcon("Slide"+i+".jpg"));
            }
        }
    }

    public ArrayList<ImageIcon> getCards() {
        return cards;
    }
}
