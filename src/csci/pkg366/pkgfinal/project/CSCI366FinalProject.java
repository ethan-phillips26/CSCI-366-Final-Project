
package csci.pkg366.pkgfinal.project;

import java.util.Scanner;

/**
 *
 * @author Ethan and Hariom
 */
public class CSCI366FinalProject {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Users currentUser = null;

        while (true) {
            System.out.println("\n==== Online Testing System ====");
            System.out.println("1) Login");
            System.out.println("2) Create Account");
            System.out.println("3) Exit");
            System.out.print("Choose: ");

            String choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    clearConsole();
                    currentUser = Login.loginPrompt(scan);
                    if (currentUser != null) {
                        loggedInMenu(scan, currentUser);
                        currentUser = null;
                    }
                    break;

                case "2":
                    clearConsole();
                    Users newUser = ManageAccount.createAccount(scan);
                    if (newUser != null) {
                        currentUser = newUser;
                        loggedInMenu(scan, currentUser);
                        currentUser = null;
                    }
                    break;

                case "3":
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void loggedInMenu(Scanner scan, Users user) {
        final boolean isStudent = user != null && user.getUserType() != null
                && user.getUserType().equalsIgnoreCase("STUDENT");
        final Student student = isStudent ? new Student(user, scan) : null;
        
        final boolean isManager = user != null && user.getUserType() != null
                && user.getUserType().equalsIgnoreCase("MANAGER");

        while (true) {
            clearConsole();
            System.out.println("\n==== Logged In ====");
            ManageAccount.displayUserInfo(user);

            System.out.println("1) Update account");
            System.out.println("2) Delete account");
            System.out.println("3) Logout");
            if (isStudent) {
                System.out.println("4) View available tests");
                System.out.println("5) Take a test");
                System.out.println("6) View past tests");
                System.out.println("7) View results (pick a test)");
            }
            if (isManager) {
                System.out.println("8) Edit Current Users");
                System.out.println("9) Create new User");
            }
            System.out.print("Choose: ");

            String choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    clearConsole();
                    ManageAccount.updateAccount(scan, user);
                    pause(scan);
                    break;

                case "2":
                    clearConsole();
                    boolean deleted = ManageAccount.deleteAccount(scan, user);
                    if (deleted) return;
                    pause(scan);
                    break;

                case "3":
                    return;

                case "4":
                    if (!isStudent) {
                        System.out.println("Invalid option.");
                        break;
                    }
                    clearConsole();
                    System.out.println(student.ViewAvailableTests());
                    pause(scan);
                    break;

                case "5":
                    if (!isStudent) {
                        System.out.println("Invalid option.");
                        break;
                    }
                    clearConsole();
                    student.TakeTest();
                    pause(scan);
                    break;

                case "6":
                    if (!isStudent) {
                        System.out.println("Invalid option.");
                        break;
                    }
                    clearConsole();
                    System.out.println(student.ViewPastTests());
                    pause(scan);
                    break;

                case "7":
                    if (!isStudent) {
                        System.out.println("Invalid option.");
                        break;
                    }
                    clearConsole();
                    System.out.print("Enter test_id to view results (or blank to cancel): ");
                    String rawTestId = scan.nextLine().trim();
                    if (rawTestId.isEmpty()) break;
                    try {
                        int testId = Integer.parseInt(rawTestId);
                        Tests t = new Tests(testId);
                        System.out.println(student.ViewResults(t));
                        pause(scan);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid test_id.");
                        pause(scan);
                    }
                    break;
                case "8":
                    if (!isManager) {
                        System.out.println("Invalid Option");
                        break;
                    }
                    ManagerFunctionality.userFind(scan);
                    break;
                case "9":
                    if (!isManager) {
                        System.out.println("Invalid option.");
                        break;
                    }
                    ManagerFunctionality.createUser(scan);
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    public static void clearConsole() {
        // ANSI clear screen (works in VS Code terminal / Windows Terminal)
        try {
            System.out.print("\u001b[H\u001b[2J");
            System.out.flush();
        } catch (Exception ignored) {
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
        }
    }

    private static void pause(Scanner scan) {
        System.out.print("\nPress Enter to continue...");
        scan.nextLine();
    }
}
