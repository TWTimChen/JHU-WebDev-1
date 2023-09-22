package assignment3;

public abstract class Ship implements Contact {
    private int length;
    private int speed;
    private String Name;
    private String type;

    // contructor
    public Ship(int length, int speed, String name, String type) {
        setLength(length);
        setSpeed(speed);
        setName(name);
        setType(type);
    }

    // getters
    public int getLength() {
        return this.length;
    }

    public int getSpeed() {
        return this.speed;
    }

    public String getName() {
        return this.Name;
    }

    public String getType() {
        return this.type;
    }

    // setters
    // check if the number is positive
    protected boolean checkPositive(int num) {
        if (num < 0) {
            System.out.println("Invalid number: " + num + ". Number must be positive.");
            return false;
        }
        return true;
    }

    public void setLength(int length) {
        if (checkPositive(length)) {
            this.length = length;
        }
    }

    public void setSpeed(int speed) {
        if (checkPositive(speed)) {
            this.speed = speed;
        }
    }

    public void setSpeed(String speed) {
        // try catch block to catch NumberFormatException
        try {
            int speedTmp = Integer.parseInt(speed);
            if (checkPositive(speedTmp)) {
                this.speed = speedTmp;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid speed: " + speed + ". Speed must be an integer.");
            return;
        }
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    // overide toString method
    @Override
    public String toString() {
        return String.format("Name: %-15s Type: %-10s Length: %-5d Speed: %-5d", this.Name, this.type, this.length, this.speed);
    }
}
