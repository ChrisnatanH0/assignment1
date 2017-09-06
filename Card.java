public class Card {
    private String name, cleavage, abundance, ecoValue;
    private double hardness, gravity;

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

    public void getBasedOnInput(String input) {
        switch (input) {
            case "name":
                getName();
                break;
            case "hardness":
                getHardness();
                break;
            case "gravity":
                getGravity();
                break;
            case "cleavage":
                getCleavage();
                break;
            case "abundance":
                getAbundance();
                break;
            case "ecovalue":
                getEcoValue();
                break;
        }
    }

    @Override
    public String toString() {
        return String.format("Name: %s  |  Hardness: %.2f  |  Gravity: %.2f  |  Cleavage: %s  |  Abundance: %s  |  EcoValue: %s", name, hardness, gravity, cleavage, abundance, ecoValue);
    }
}
