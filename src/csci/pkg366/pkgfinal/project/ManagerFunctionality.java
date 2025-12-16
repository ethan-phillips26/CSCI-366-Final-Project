/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csci.pkg366.pkgfinal.project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;
/**
 *
 * @author benjk
 */
public class ManagerFunctionality {
  
    public static void createUser(Scanner scan) {
        System.out.println("\n\n\n\nCreate new user: ");
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
    
    
    
    public static void userFind(Scanner scan) {
       
        //Allow admin to lookup user
        
        //choose user lookup method
        while (true) {
            long count = Users.getUserCount();
            System.out.print(
                    "\n\n\n\nUser Count: " + count + "\n"
                    + "Enter User lookup method\n"
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
                    editUser(user, scan);
                    break;
                    
                case "2":
                    //lookup by name
                    System.out.print("Enter user lastname: ");
                    String lastname = scan.next();
                    System.out.print("Enter user firstname: ");
                    String firstname = scan.next();
                    LinkedList<Users> list = Users.getUsersByName(firstname, lastname);
                    viewUserList(list, scan);
                    break;
                    
                case "3":
                    
                    //lookup by username
                    System.out.print("Lookup user by username: ");
                    String userName = scan.next();
                    user = Users.getUserByUsername(userName);
                    editUser(user, scan);
                    break;
                case "4":
                    return;
                    
                default:
                    System.out.println("Error enter 1, 2, 3, or 4");
            }
        }
    }
    
    public static void viewUserList(LinkedList<Users> list, Scanner scan) {
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
                        editUser(part[0], scan);
                    return;
                case "2":
                    if (part[1] != null)
                        editUser(part[1], scan);
                    return;
                case "3":
                    if (part[2] != null)
                        editUser(part[2], scan);
                    return;
                case "4":
                    if (part[3] != null)
                        editUser(part[3], scan);
                    return;
                case "5":
                    if (part[4] != null)
                        editUser(part[4], scan);
                    return;
                case "6":
                    if (list.isEmpty()) return;
                    break;
                default:
                    return;
            }
            
        }
    }
    
    public static void editUser(Users user, Scanner scan) {
        if (user == null || user.getUserId() == null) return;
        while (true) {
            System.out.print("\n\n\n\nEditing user with id: " + user.getUserId() 
                    + "\n1) firstname: " + user.getFirstname()
                    + "\n2) lastname: " + user.getLastname()
                    + "\n3) user_type: " + user.getUserType()
                    + "\n4) username: " + user.getUsername()
                    + "\n5) password: " + user.getPassword()
                    + "\n6) delete user"
                    + "\n7) cancel"
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
                    Users.deleteUser(user.getUserId());
                    return;
                case "7":
                    return;
                
            }
            
        }
        
    }

    public static void viewLeaderboard() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String sql = "SELECT test_title, top_user, high_score FROM leaderboard_view";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n\nLeaderboard:");
            System.out.printf("%-30s | %-20s | %s\n", "Test Title", "Top User", "High Score");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()) {
                String title = rs.getString("test_title");
                String topUser = rs.getString("top_user");
                int highScore = rs.getInt("high_score");
                System.out.printf("%-30s | %-20s | %d\n", title, topUser, highScore);
            }
        } catch (SQLException sqle) {
            System.out.println("Error retrieving leaderboard: " + sqle.getMessage());
        }
    }
}
