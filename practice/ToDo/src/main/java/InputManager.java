import java.util.Scanner;

public class InputManager {
    private final Scanner menuInput;

    public InputManager() {
        menuInput = new Scanner(System.in);
    }

    public int takeZeroIndexInput(int rangeHigh) {
        System.out.println("Select task/tag");
        int userInput = menuInput.nextInt();
        while (userInput < 1 || userInput > rangeHigh) {
            System.out.println("Please choose a number between: " + 1 + " and " + rangeHigh);
            userInput = menuInput.nextInt();
        }
        menuInput.nextLine();
        return userInput - 1;
    }

    public int takeMenuInput(int rangeLow, int rangeHigh) {
        int userInput = menuInput.nextInt();
        menuInput.nextLine();
        while (userInput < rangeLow || userInput > rangeHigh) {
            System.out.println("Please choose a number between: " + rangeLow + "and " + rangeHigh);
            userInput = menuInput.nextInt();
            menuInput.nextLine();
        }
        return userInput;
    }

    public String takeStringInput() {
        return menuInput.nextLine();
    }

    public void closeScanner() {
        this.menuInput.close();
    }
}
