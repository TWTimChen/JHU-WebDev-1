package assignment3;

public class P3 extends Aircraft {
    private int numEngines;

    // constructor
    public P3(int length, int speed, String name, String type, int altitude, int numEngines) {
        super(length, speed, name, type, altitude);
        setNumEngines(numEngines);
    }

    // getters
    public int getNumEngines() {
        return this.numEngines;
    }

    // setters
    public void setNumEngines(int numEngines) {
        if (checkPositive(numEngines)) {
            this.numEngines = numEngines;
        }
    }

    // override toString method
    @Override
    public String toString() {
        return super.toString() + String.format(" Number of engines: %-5d", this.numEngines);
    }
}
