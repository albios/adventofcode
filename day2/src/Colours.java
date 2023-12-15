public enum Colours {
    GREEN(13, "green"),
    RED(12, "red"),
    BLUE(14, "blue");

    private int maxPossible;
    private String name;

    Colours(int maxPossible, String name) {
        this.maxPossible = maxPossible;
        this.name = name;
    }

    public int getMaxPossible() {
        return maxPossible;
    }

    public String getName() {
        return name;
    }

    static Colours parseColourByNumber (String s) {
        for (Colours value : Colours.values()) {
            if (s.contains(value.getName()))
                return value;
        }
        throw new IllegalArgumentException("There is no colour name in string " + s);
    }
}
