package assignment3;

public class Destroyer extends Ship {
    private int numMissiles;

    public Destroyer(int length, int speed, String name, String type, int numMissiles) {
        super(length, speed, name, type);
        setNumMissiles(numMissiles);
    }

    // getters
    public int getNumMissiles() {
        return this.numMissiles;
    }

    // setters
    public void setNumMissiles(int numMissiles) {
        this.numMissiles = numMissiles;
    }

    public void setNumMissiles(String numMissiles) {
        // try catch block to catch NumberFormatException
        try {
            this.numMissiles = Integer.parseInt(numMissiles);
        } catch (NumberFormatException e) {
            this.numMissiles = 2;
            System.out.println("Invalid number of missiles: " + numMissiles + ". Number of missiles must be an integer.");
            return;
        }
    }

    // overridee toString method
    @Override
    public String toString() {
        return super.toString() + String.format(" Number of missiles: %-5d", this.numMissiles);
    }
}
