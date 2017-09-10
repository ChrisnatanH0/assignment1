import java.util.Arrays;
import java.util.List;

public class Card {
    private String name, cleavage, abundance, ecoValue;
    private double hardness, gravity;
    private List<String> cleavageList = Arrays.asList("none", "poor/none", "1 poor", "2 poor", "1 good", "1 good/1 poor", "2 good", "3 good", "1 perfect", "1 perfect/1 good", "1 perfect/2 good", "2 perfect/1 good", "3 perfect", "4 perfect", "6 perfect");
    private List<String> abundanceList = Arrays.asList("ultratrace", "trace", "low", "moderate", "high", "very high");
    private List<String> ecoValueList = Arrays.asList("trivial", "low", "moderate", "high", "very high", "I'm rich!");


    public Card(String name, double hardness, double gravity, String cleavage, String abundance, String ecoValue) {
        this.name = name;
        this.hardness = hardness;
        this.gravity = gravity;
        this.cleavage = cleavage;
        this.abundance = abundance;
        this.ecoValue = ecoValue;
    }

    public Card() {
        this("Nothing", 0, 0, "poor/none", "low", "low");
    }

    public String getName() { return name; }
    public double getHardness() { return hardness; }
    public double getGravity() { return gravity; }
    public String getCleavage() { return cleavage; }
    public String getAbundance() { return abundance; }
    public String getEcoValue() { return ecoValue; }
    public String getTrump() { return "hardness"; }

    public int getCleavageNum() { return cleavageList.indexOf(cleavage); }
    public int getAbundanceNum() { return abundanceList.indexOf(abundance); }
    public int getEcoValueNum() { return ecoValueList.indexOf(ecoValue); }

    public double getValueBasedOnInput(String input) {
        switch (input) {
            case "hardness":
                return hardness;
            case "gravity":
                return gravity;
            case "cleavage":
                return cleavageList.indexOf(cleavage);
            case "abundance":
                return abundanceList.indexOf(abundance);
            case "ecovalue":
                return ecoValueList.indexOf(ecoValue);
        }
        return 0;
    }

    public String getStringBasedOnInput (String input) {
        switch (input) {
            case "name":
                return name;
            case "hardness":
                return String.valueOf(hardness);
            case "gravity":
                return String.valueOf(gravity);
            case "cleavage":
                return cleavage;
            case "abundance":
                return abundance;
            case "ecovalue":
                return ecoValue;
        }
        return input;
    }

    @Override
    public String toString() {
        return String.format("Name: %s  |  Hardness: %.2f  |  Gravity: %.2f  |  Cleavage: %s  |  Abundance: %s  |  EcoValue: %s", name, hardness, gravity, cleavage, abundance, ecoValue);
    }
}
