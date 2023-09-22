package assignment3;

public class Submarine extends Ship {
    private int numTorpedoes;

    public Submarine(int length, int speed, String name, String type, int numTorpedoes) {
        super(length, speed, name, type);
        setNumTorpedoes(numTorpedoes);
    }

    // getters
    public int getNumTorpedoes() {
        return this.numTorpedoes;
    }

    // setters
    public void setNumTorpedoes(int numTorpedoes) {
        if (checkPositive(numTorpedoes)) {
            this.numTorpedoes = numTorpedoes;
        }
    }

    public void setNumTorpedoes(String numTorpedoes) {
        // try catch block to catch NumberFormatException
        try {
            int numTorpedoesTmp = Integer.parseInt(numTorpedoes);
            if (checkPositive(numTorpedoesTmp)) {
                this.numTorpedoes = numTorpedoesTmp;
            }
        } catch (NumberFormatException e) {
            this.numTorpedoes = 2;
            System.out.println("Invalid number of torpedoes: " + numTorpedoes + ". Number of torpedoes must be an integer.");
            return;
        }
    }

    // overridee toString method
    @Override
    public String toString() {
        return super.toString() + String.format(" Number of torpedoes: %-5d", this.numTorpedoes);
    }
}
