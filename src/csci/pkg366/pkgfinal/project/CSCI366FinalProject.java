
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
            System.out.println("\n
                               == Online Testing System ====");
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
    final String type = (user.getUserType() == null) ? "" : user.getUserType().trim().toUpperCase();

    while (true) {
        clearConsole();
        System.out.println("\n==== Logged In ====");
        ManageAccount.displayUserInfo(user);

        switch (type) {
            case "STUDENT":
                if (studentMenu(scan, user)) return;
                break;

            case "CREATOR":
                if (creatorMenu(scan, user)) return;
                break;

            case "MANAGER":
                if (managerMenu(scan, user)) return;
                break;

            default:
                System.out.println("Unknown user type: " + user.getUserType());
                pause(scan);
                return;
        }
    }
}

private static boolean studentMenu(Scanner scan, Users user) {
    Student student = new Student(user, scan);

    System.out.println("0) Logout");
    System.out.println("1) Update account");
    System.out.println("2) Delete account");
    System.out.println("3) View available tests");
    System.out.println("4) Take a test");
    System.out.println("5) View past tests");
    System.out.println("6) View results (pick a test)");
    System.out.print("Choose: ");

    String choice = scan.nextLine().trim();

    switch (choice) {
        case "0":
            return true;

        case "1":
            clearConsole();

            ManageAccount.updateAccount(scan, user);
            pause(scan);
            return false;

        case "2":
            clearConsole();
            boolean deleted = ManageAccount.deleteAccount(scan, user);
            pause(scan);
            return deleted;

        case "3":
            clearConsole();
            System.out.println(student.ViewAvailableTests());
            pause(scan);
            return false;

        case "4":
            clearConsole();
            student.TakeTest();
            pause(scan);
            return false;

        case "5":
            clearConsole();
            System.out.println(student.ViewPastTests());
            pause(scan);
            return false;

        case "6":
            clearConsole();
            System.out.print("Enter test_id to view results (or blank to cancel): ");
            String rawTestId = scan.nextLine().trim();
            if (rawTestId.isEmpty()) return false;

            try {
                int testId = Integer.parseInt(rawTestId);
                Tests t = new Tests(testId);
                System.out.println(student.ViewResults(t));
            } catch (NumberFormatException e) {
                System.out.println("Invalid test_id.");

            }
            pause(scan);
            return false;

        default:
            System.out.println("Invalid option.");
            pause(scan);
            return false;
    }
}

private static boolean creatorMenu(Scanner scan, Users user) {
    TestCreator testCreator = new TestCreator();
    System.out.println("0) Logout");
    System.out.println("1) Update account");
    System.out.println("2) Delete account");
    System.out.println("3) View created tests");
    System.out.println("4) Create a test");
    System.out.println("5) Add answers to a question");
    System.out.println("6) Add questions to a test");
    System.out.println("7) Edit a test, question, or answer");
    System.out.println("8) Delete a test, question, or answer");
    System.out.print("Choose: ");

    String choice = scan.nextLine().trim();

    switch (choice) {
        case "0":
            return true;

        case "1":
            clearConsole();
            ManageAccount.updateAccount(scan, user);
            pause(scan);
            return false;

        case "2":
            clearConsole();
            boolean deleted = ManageAccount.deleteAccount(scan, user);
            pause(scan);
            return deleted;

        case "3": {
            clearConsole();
            int userId = promptInt(scan, "Enter user ID: ");
            testCreator.DisplayCreatedTests(userId);
            pause(scan);
            return false;
        }

        case "4": {
            clearConsole();
            int userId = promptInt(scan, "Enter user ID: ");
            testCreator.TestBuilder(userId);
            pause(scan);
            return false;
        }

        case "5": {
            clearConsole();
            int questionId = promptInt(scan, "Enter question ID: ");
            testCreator.AnswerBuilder(questionId);
            pause(scan);
            return false;
        }

        case "6": {
            clearConsole();
            int testId = promptInt(scan, "Enter test ID: ");
            testCreator.QuestionBuilder(testId);
            pause(scan);
            return false;
        }

        case "7": {
            clearConsole();
            System.out.println("Edit: 1) Test  2) Question  3) Answer");
            String edit = scan.nextLine().trim();

            switch (edit) {
                case "1": {
                    int testId = promptInt(scan, "Enter the test ID: ");
                    System.out.print("Enter the new name: ");
                    String name = scan.nextLine();
                    testCreator.editTest(testId, name);
                    break;
                }
                case "2": {
                    int questionId = promptInt(scan, "Enter the question ID: ");
                    System.out.print("Enter the new question: ");
                    String questionText = scan.nextLine();
                    int points = promptInt(scan, "Enter the new points: ");
                    testCreator.editQuestion(questionId, questionText, points);
                    break;
                }
                case "3": {
                    int answerId = promptInt(scan, "Enter the answer ID: ");
                    System.out.print("Enter the new answer: ");
                    String answerText = scan.nextLine();
                    System.out.print("Is it correct? (Y/N): ");
                    String correctString = scan.nextLine().trim().toUpperCase();
                    boolean correct = correctString.equals("Y");
                    testCreator.editAnswer(answerId, answerText, correct);
                    break;
                }
                default:
                    System.out.println("Invalid option.");
            }

            pause(scan);
            return false;
        }

        case "8": {
            clearConsole();
            System.out.println("Delete: 1) Test  2) Question  3) Answer");
            String del = scan.nextLine().trim();

            switch (del) {
                case "1": {
                    int testId = promptInt(scan, "Enter the test ID: ");
                    testCreator.deleteTest(testId);
                    break;
                }
                case "2": {
                    int questionId = promptInt(scan, "Enter the question ID: ");
                    testCreator.deleteQuestion(questionId);
                    break;
                }
                case "3": {
                    int answerId = promptInt(scan, "Enter the answer ID: ");
                    testCreator.deleteAnswer(answerId);
                    break;
                }
                
                default:
                    System.out.println("Invalid option.");
            }

            pause(scan);
            return false;
        }

        default:
            System.out.println("Invalid option.");
            pause(scan);
            return false;
    }
}

private static boolean managerMenu(Scanner scan, Users user) {
    System.out.println("0) Logout");
    System.out.println("1) Update account");
    System.out.println("2) Delete account");
    System.out.println("3) Edit current users");
    System.out.println("4) Create new user");
    System.out.println("5) View leaderboard");
    System.out.print("Choose: ");

    String choice = scan.nextLine().trim();

    switch (choice) {

        case "0":
            return true;

        case "1":
            clearConsole();
            ManageAccount.updateAccount(scan, user);
            pause(scan);
            return false;

        case "2":
            clearConsole();
            boolean deleted = ManageAccount.deleteAccount(scan, user);
            pause(scan);
            return deleted;


        case "3":
            clearConsole();
            ManagerFunctionality.userFind(scan);
            pause(scan);
            return false;

        case "4":
            clearConsole();
            ManagerFunctionality.createUser(scan);
            pause(scan);
            return false;
        
        case "5":
                    if (!isManager) {
                        System.out.println("Invalid option.");
                        break;
                    }
                    clearConsole();
                    ManagerFunctionality.viewLeaderboard();
                    pause(scan);
                    break;

        default:
            System.out.println("Invalid option.");
            pause(scan);
            return false;
    }
}

private static int promptInt(Scanner scan, String prompt) {
    while (true) {
        System.out.print(prompt);
        String raw = scan.nextLine().trim();
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
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
