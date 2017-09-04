public class Mineral {
    protected String name, cleavage, abundance, ecoValue;
    protected double hardness, gravity;

    public Mineral(String name, double hardness, double gravity, String cleavage, String abundance, String ecoValue) {
        this.name = name;
        this.hardness = hardness;
        this.gravity = gravity;
        this.cleavage = cleavage;
        this.abundance = abundance;
        this.ecoValue = ecoValue;
    }

    public Mineral() {
        this("Nothing", 0, 0, "poor/none", "low", "low");
    }

    public String getName() { return name; }
    public double getHardness() { return hardness; }
    public double getGravity() { return gravity; }
    public String getCleavage() { return cleavage; }
    public String getAbundance() { return abundance; }
    public String getEcoValue() { return ecoValue; }

    @Override
    public String toString() {
        return String.format("Name: %s  |  Hardness: %f  |  Gravity: %f  |  Cleavage: %s  |  Abundance: %s  |  EcoValue: %s", name, hardness, gravity, cleavage, abundance, ecoValue);
    }
}
