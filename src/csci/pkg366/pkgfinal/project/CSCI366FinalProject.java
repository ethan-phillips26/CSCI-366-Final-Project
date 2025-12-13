/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package csci.pkg366.pkgfinal.project;

import java.util.Scanner;

/**
 *
 * @author ethan
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
                    currentUser = Login.loginPrompt();
                    if (currentUser != null) {
                        loggedInMenu(scan, currentUser);
                        currentUser = null;
                    }
                    break;

                case "2":
                    clearConsole();
                    Users newUser = ManageAccount.createAccount();
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
        while (true) {
            System.out.println("\n==== Logged In ====");
            ManageAccount.displayUserInfo(user);

            System.out.println("1) Update account");
            System.out.println("2) Delete account");
            System.out.println("3) Logout");
            System.out.print("Choose: ");

            String choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    clearConsole();
                    ManageAccount.updateAccount(user);
                    break;

                case "2":
                    clearConsole();
                    boolean deleted = ManageAccount.deleteAccount(user);
                    if (deleted) return;
                    break;

                case "3":
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    
    public static void clearConsole() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
}
}
