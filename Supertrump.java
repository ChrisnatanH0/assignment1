public class Supertrump extends Card {
    private String name;
    private String trump;

    public Supertrump(String name, String trump) {
        this.name = name;
        this.trump = trump;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getTrump() { return trump; }

    @Override
    public String toString() {
        return name + ", " + trump;
    }
}
