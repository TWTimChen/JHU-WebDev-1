# Hike Booking System

The Hike Booking System is a simple Java application with a GUI that allows users to book hikes, select a date, and choose the duration of the hike. It also validates and calculates the total cost based on the user's selection.

## Features

1. Hike Selection: Users can select the type of hike they wish to book.
2. Booking Date Input: Users can choose the desired booking date through separate fields for day, month, and year.
3. Duration Dropdown: The duration of the hike can be selected from a dropdown. The options in the dropdown adjust dynamically based on the type of hike selected.
4. Total Cost Calculation: On submitting the form, the total cost for the hike is calculated and displayed to the user. If there's any validation error, the user is informed.

## Dependencies

* The application relies on the BhcUtils.jar library, which provides classes like BookingDay, HikeType, and Rates essential for the functioning of this application.

## Running the Application

Ensure you have Java installed and the required JAR files are in the appropriate directories.

1. Navigate to the directory containing HikeBookingUI.jar.
2. Run the application using the command:

```bash
java -jar HikeBookingUI.jar
```

## Compiling and Packaging

### Compiling the Application

```bash
# On macOS/Linux:
javac -cp .:BhcUtils/BhcUtils.jar assignment4/HikeBookingUI.java

# On Windows:
javac -cp .;BhcUtils/BhcUtils.jar assignment4/HikeBookingUI.java
```

### Packaging into a JAR

```bash
jar cvfm assignment4/HikeBookingUI.jar assignment4/manifest.txt assignment4/HikeBookingUI.class
```

After these steps, you'll have HikeBookingUI.jar in the assignment4 directory, ready to run.
