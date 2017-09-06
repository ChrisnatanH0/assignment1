public class Supertrump extends Card {
    private String name;
    private String effect;

    public Supertrump(String name, String effect) {
        this.name = name;
        this.effect = effect;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        return name + ", " + effect + " expert.";
    }
}
