package assignment3;
import java.util.ArrayList;
import java.util.List;


public class Test {
    public static void printDashLine() {
        System.out.println("--------------------------------------------------");
    }
    public static void main(String[] args) {
        // Create 2 Destroyers
        Destroyer destroyer1 = new Destroyer(100, 100, "Destroyer 1", "Destroyer", 10);
        Destroyer destroyer2 = new Destroyer(200, 200, "Destroyer 2", "Destroyer", 20);

        // Create 2 Submarines
        Submarine submarine1 = new Submarine(100, 100, "Submarine 1", "Submarine", 10);
        Submarine submarine2 = new Submarine(200, 200, "Submarine 2", "Submarine", 20);

        // Try setting "Foo" to number of torpedos for submarine2
        System.out.println("Setting number of torpedoes for submarine2 to \"Foo\"");
        printDashLine();
        submarine2.setNumTorpedoes("Foo");
        printDashLine();
        System.out.println();

        // Create 2 P3s
        P3 p31 = new P3(100, 100, "P3 1", "P3", 10, 2);
        P3 p32 = new P3(200, 200, "P3 2", "P3", 20, 4);

        // Try setting -100 to number of torpedos for p32
        System.out.println("Setting number of torpedoes for p32 to -100");
        printDashLine();
        P3 p33 = new P3(200, -200, "P3 2", "P3", 20, 4);
        printDashLine();
        System.out.println();

        // Make a collection of Destroyers
        List<Destroyer> destroyers = new ArrayList<>();
        destroyers.add(destroyer1);
        destroyers.add(destroyer2);

        // Make a collection of Submarines
        List<Submarine> submarines = new ArrayList<>();
        submarines.add(submarine1);
        submarines.add(submarine2);

        // Make a collection that holds all P3s
        List<P3> p3s = new ArrayList<>();
        p3s.add(p31);
        p3s.add(p32);
        p3s.add(p33);

        // Make a collection that holds all Ships
        List<Ship> ships = new ArrayList<>();
        ships.add(destroyer1);
        ships.add(destroyer2);
        ships.add(submarine1);
        ships.add(submarine2);

        // Make a collection that holds all Contacts
        List<Contact> contacts = new ArrayList<>();
        contacts.add(destroyer1);
        contacts.add(destroyer2);
        contacts.add(submarine1);
        contacts.add(submarine2);
        contacts.add(p31);
        contacts.add(p32);
        contacts.add(p33);

        // Print out all Contacts
        System.out.println("All Contacts:");
        printDashLine();
        for (Contact contact : contacts) {
            System.out.println(contact);
        }
        printDashLine();
        System.out.println();
    }
}
