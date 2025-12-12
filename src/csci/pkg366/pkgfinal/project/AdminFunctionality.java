/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csci.pkg366.pkgfinal.project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.util.LinkedList;
/**
 *
 * @author benjk
 */
public class AdminFunctionality {
    private static String jdbcURL = "jdbc:postgresql://localhost:5432/FinalProject";
    private static String username = "postgres";
    private static String password = "Biden&?43";
    
    
    public static void showAdminOptions() {
        Scanner scan = new Scanner(System.in);
        while(true) {
            System.out.print("\n\n\n\nWhat do you want to do?"
                    + "\n1) Create new user"
                    + "\n2) Create new test"
                    + "\n3) Manage existing user"
                    + "\n4) Manage existing test"
                    + "\n5) Logout"
                    + "\nEnter a number: ");
            String input = scan.next();
            switch (input) {
                case "1":
                    //add create user
                    break;
                case "2":
                    //add create test
                    break;
                case "3":
                    userFind();
                    break;
                case "4":
                    testFind();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Enter a valid number");
            }
        }
    }
    
    public static void testFind() {
        Scanner scan = new Scanner(System.in);
        
    }
    
    public static void testCreate() {
        
    }
    
    public static void questionCreate() {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n\n\n\nCreating new question");
        System.out.print("Enter question text: ");
        String text = scan.next();
        
        int points;
        while(true) {
            String input = scan.next();
            try {
                System.out.print("Enter question points: ");
                points = Integer.parseInt(input);
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("enter an integer");
            }
        }
        
        Questions.createQuestion(text, );
    }
    
    public static void answerCreate(int questionID) {
        Scanner scan = new Scanner(System.in);
        System.out.println("\n\n\n\nCreating new answer");
        System.out.print("Enter answer text: ");
        String text = scan.next();
        System.out.print("Is the answer correct? \n"
                + "Enter y for yes and anything else for no: ");
        String correct = scan.next();
        Boolean isCorrect = correct.equals("y");
        Answers.createAnswer(text, isCorrect, questionID);
    }
    
    public static void editAnswer(Answers answer) {
        if (answer == null) return;
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("\n\n\n\nEditing answer with id: " + answer.getAnswerId() 
                    + "\n1) Text: " + answer.getAnswerText()
                    + "\n2) isCorrect: " + answer.getIsCorrect()
                    + "\n3) delete user"
                    + "\n4) cancel"
                    + "\nEnter number: ");
            String option = scan.next();
            switch (option) {
                case "1":
                    System.out.print("Enter new answer text: ");
                    String text = scan.next();
                    Answers.updateAnswerText(text, answer.getAnswerId());
                    break;
                case "2":
                    System.out.print("Is the answer correct? \n"
                            + "Enter y for yes and anything else for no: ");
                    String correct = scan.next();
                    Boolean isCorrect = correct.equals("y");
                    Answers.updateIsCorrect(isCorrect, answer.getAnswerId());
                    break;
                case "3":
                    Answers.deleteAnswer(answer.getAnswerId());
                    return;
                case "4":
                    return;
            }
        }
    }
    
    public static void viewAnswerList(LinkedList<Answers> list) {
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\n\n\n\nSelect Answer: ");
        Answers[] part = new Answers[5];
        for(int i = 0; i < 5 && !list.isEmpty(); i++){
            Answers answer = list.peek();
            System.out.println((i + 1) + ") " 
                + "Answer ID: " + answer.getAnswerId() 
                + ", Answer Text: " + answer.getAnswerText()
                + ", Is Correct: " + answer.getIsCorrect());
            part[i] = list.pop();
        }
        System.out.println("6) cancel");
        System.out.print("Enter number: ");
        String input = scan.next();
            
        switch (input) {
            case "1":
                if (part[0] != null)
                    editAnswer(part[0]);
                return;
            case "2":
                if (part[1] != null)
                    editAnswer(part[1]);
                return;
            case "3":
                if (part[2] != null)
                    editAnswer(part[2]);
                return;
            case "4":
                if (part[3] != null)
                    editAnswer(part[3]);
                return;
            case "5":
                if (part[4] != null)
                    editAnswer(part[4]);
        }
    }
    
    public static void createUser() {
        System.out.println("\n\n\n\nCreate new user: ");
        Scanner scan = new Scanner(System.in);
        String firstname;
        String lastname;
        String usertype;
        String username;
        String password;
        System.out.print("Enter new user's firstname: ");
        firstname = scan.next();
        System.out.print("Enter new user's lastname: ");
        lastname = scan.next();
        System.out.print("Enter new user's usertype: ");
        usertype = scan.next();
        System.out.print("Enter new user's username: ");
        username = scan.next();
        System.out.print("Enter new user's password: ");
        password = scan.next();
        Users.createUser(firstname, lastname, usertype, username, password);
    }
    
    
    
    public static void userFind() {
        Scanner scan = new Scanner(System.in);
       
        //Allow admin to lookup user
        
        //choose user lookup method
        boolean selected = false;
        while (!selected) {
            System.out.print(
                    "\n\n\n\nEnter User lookup method\n"
                    + "1) user_id\n"
                    + "2) name\n"
                    + "3) username\n"
                    + "4) cancel\n"
                    + "Enter number here: ");
            String input = scan.next();
            Users user;
            switch (input){
                case "1":
                    //look up by id
                    System.out.print("Lookup user by user_id: ");
                    int user_id;
                    try {
                        user_id = Integer.parseInt(scan.next());
                    } catch (NumberFormatException nfe) {
                        System.out.println("Error: User ID must be integer");
                        break;
                    }
                    user = Users.getUserById(user_id);
                    editUser(user);
                    selected = true;
                    break;
                    
                case "2":
                    //lookup by name
                    System.out.print("Enter user lastname: ");
                    String lastname = scan.next();
                    System.out.print("Enter user firstname: ");
                    String firstname = scan.next();
                    LinkedList<Users> list = Users.getUsersByName(firstname, lastname);
                    viewUserList(list);
                    break;
                    
                case "3":
                    
                    //lookup by username
                    System.out.print("Lookup user by username: ");
                    String userName = scan.next();
                    user = Users.getUserByUsername(userName);
                    editUser(user);
                    selected = true;
                    break;
                case "4":
                    return;
                    
                default:
                    System.out.println("Error enter 1, 2, 3, or 4");
            }
        }
    }
    
    public static void viewUserList(LinkedList<Users> list) {
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("\n\n\n\nSelect User: ");
            Users[] part = new Users[5];
            for(int i = 0; i < 5 && !list.isEmpty(); i++){
                Users user = list.peek();
                System.out.println((i + 1) + ") " 
                    + "user_id: " + user.getUserId()
                    + ", firstname: " + user.getFirstname()
                    + ", lastname: " + user.getLastname()
                    + ", user_type: " + user.getUserType()
                    + ", username: " + user.getUsername());
                    
                part[i] = list.pop();
            }
            if (!list.isEmpty()) {
                System.out.println("6) More results");
            }
            System.out.println("7) cancel");
            System.out.print("Enter number: ");
            String input = scan.next();
            
            switch (input) {
                case "1":
                    if (part[0] != null)
                        editUser(part[0]);
                    return;
                case "2":
                    if (part[1] != null)
                        editUser(part[1]);
                    return;
                case "3":
                    if (part[2] != null)
                        editUser(part[2]);
                    return;
                case "4":
                    if (part[3] != null)
                        editUser(part[3]);
                    return;
                case "5":
                    if (part[4] != null)
                        editUser(part[4]);
                    return;
                case "6":
                    if (list.isEmpty()) return;
                    break;
                default:
                    return;
            }
            
        }
    }
    
    public static void editUser(Users user) {
        if (user == null) return;
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("\n\n\n\nEditing user with id: " + user.getUserId() 
                    + "\n1) firstname: " + user.getFirstname()
                    + "\n2) lastname: " + user.getLastname()
                    + "\n3) user_type: " + user.getUserType()
                    + "\n4) username: " + user.getUsername()
                    + "\n5) password: " + user.getPassword()
                    + "\n6) user_id: " + user.getUserId()
                    + "\n7) delete user"
                    + "\n8) cancel"
                    + "\nEnter number: ");
            String option = scan.next();
            switch (option) {
                case "1":
                    System.out.print("Enter new firstname: ");
                    String firstname = scan.next();
                    Users.updateFirstname(firstname, user.getUserId());
                    break;
                case "2":
                    System.out.print("Enter new lastname: ");
                    String lastname = scan.next();
                    Users.updateLastname(lastname, user.getUserId());
                    break;
                case "3":
                    System.out.print("Enter new user_type: ");
                    String user_type = scan.next();
                    Users.updateUserType(user_type, user.getUserId());
                    break;
                case "4":
                    System.out.print("Enter new username: ");
                    String username = scan.next();
                    Users.updateUsername(username, user.getUserId());
                    break;
                case "5":
                    System.out.print("Enter new password: ");
                    String password = scan.next();
                    Users.updatePassword(password, user.getUserId());
                    break;
                case "6":
                    System.out.print("Enter new user_id: ");
                    String user_id = scan.next();
                    int id;
                    try {
                        id = Integer.parseInt(user_id);
                        Users.updateId(id, user.getUserId());
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid ID");
                    }
                    break;
                case "7":
                    Users.deleteUser(user.getUserId());
                    return;
                case "8":
                    return;
                
            }
            
        }
        
    }
}
