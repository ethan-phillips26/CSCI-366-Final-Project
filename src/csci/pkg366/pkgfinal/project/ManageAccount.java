package csci.pkg366.pkgfinal.project;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author ethan
 */

public class ManageAccount {

    private static final Scanner scan = new Scanner(System.in);

    public static Users createAccount() {
        System.out.println("\n==== Create Account ====");

        System.out.print("First name: ");
        String firstname = scan.nextLine().trim();

        System.out.print("Last name: ");
        String lastname = scan.nextLine().trim();

        String username;
        while (true) {
            System.out.print("Username: ");
            username = scan.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
                continue;
            }
            if (usernameExists(username)) {
                System.out.println("That username is already taken. Try another.");
                continue;
            }
            break;
        }

        System.out.print("Password: ");
        String password = scan.nextLine();

        String sql =
            "INSERT INTO users (firstname, lastname, username, password, user_type) " +
            "VALUES (?, ?, ?, ?, 'STUDENT') " +
            "RETURNING user_id, firstname, lastname, username, user_type;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, firstname);
            ps.setString(2, lastname);
            ps.setString(3, username);
            ps.setString(4, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Failed to create account");
                    return null;
                }

                Users u = new Users();
                u.setUserId(rs.getInt("user_id"));
                u.setFirstname(rs.getString("firstname"));
                u.setLastname(rs.getString("lastname"));
                u.setUsername(rs.getString("username"));
                u.setUserType(rs.getString("user_type"));
                u.setPassword(password);
                
                System.out.println("Account created successfully! Welcome, " + u.getFirstname() + "!");
                return u;
            }

        } catch (SQLException e) {
            System.out.println("DB error during account creation.");
            return null;
        }
    }

 
    public static void updateAccount(Users user) {
        if (user == null) {
            System.out.println("No user logged in");
            return;
        }

        System.out.println("\n==== Update Account ====");
        displayUserInfo(user);

        String firstname = user.getFirstname();
        String lastname  = user.getLastname();
        String username  = user.getUsername();
        String password  = user.getPassword();

        while (true) {
            System.out.println("\nWhat would you like to update?");
            System.out.println("1) First name");
            System.out.println("2) Last name");
            System.out.println("3) Username");
            System.out.println("4) Password");
            System.out.println("5) Save changes and exit");
            System.out.print("Choose: ");

            String choice = scan.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("New first name (blank = keep '" + firstname + "'): ");
                    String fn = scan.nextLine().trim();
                    if (!fn.isEmpty()) firstname = fn;
                    break;

                case "2":
                    System.out.print("New last name (blank = keep '" + lastname + "'): ");
                    String ln = scan.nextLine().trim();
                    if (!ln.isEmpty()) lastname = ln;
                    break;

                case "3":
                    while (true) {
                        System.out.print("New username (blank = keep '" + username + "'): ");
                        String un = scan.nextLine().trim();
                        if (un.isEmpty()) break;

                        if (!un.equalsIgnoreCase(user.getUsername()) && usernameExists(un)) {
                            System.out.println("That username is already taken. Try another.");
                            continue;
                        }
                        username = un;
                        break;
                    }
                    break;

                case "4":
                    System.out.print("New password (blank = keep current): ");
                    String pw = scan.nextLine();
                    if (!pw.trim().isEmpty()) password = pw;
                    break;

                case "5":
                    saveAccountUpdates(user, firstname, lastname, username, password);
                    return;

                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private static void saveAccountUpdates(Users user,
                                      String firstname,
                                      String lastname,
                                      String username,
                                      String password) {

    String sql =
        "UPDATE users SET firstname = ?, lastname = ?, username = ?, password = ? " +
        "WHERE user_id = ?;";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, firstname);
        ps.setString(2, lastname);
        ps.setString(3, username);
        ps.setString(4, password);
        ps.setInt(5, user.getUserId());

        int updated = ps.executeUpdate();
        if (updated == 1) {
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setUsername(username);
            user.setPassword(password);

            System.out.println("Account updated successfully");
        } else {
            System.out.println("Failed to update account");
        }

    } catch (SQLException e) {
        System.out.println("DB error during account update");
    }
}


    public static boolean deleteAccount(Users user) {
        if (user == null) {
            System.out.println("No user logged in.");
            return false;
        }

        System.out.println("\n==== Delete Account ====");
        displayUserInfo(user);

        System.out.print("Are you sure you want to permanently delete your account? (yes/no): ");
        String confirm = scan.nextLine().trim().toLowerCase();

        if (!confirm.equals("yes")) {
            System.out.println("Account deletion cancelled.");
            return false;
        }

        String sql = "DELETE FROM users WHERE user_id = ?;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, user.getUserId());
            int deleted = ps.executeUpdate();

            if (deleted == 1) {
                System.out.println("Account deleted. All related data has been removed.");
                return true;
            } else {
                System.out.println("No account deleted");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("DB error during account deletion.");
            return false;
        }
    }

    public static void displayUserInfo(Users user) {
        if (user == null) {
            System.out.println("(No user)");
            return;
        }
        System.out.println("\n--- User Info ---");
        System.out.println("ID:        " + user.getUserId());
        System.out.println("First:     " + user.getFirstname());
        System.out.println("Last:      " + user.getLastname());
        System.out.println("Username:  " + user.getUsername());
        System.out.println("User Type: " + user.getUserType());
        System.out.println("--------------");
    }

    private static boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1;";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("DB error checking username");
            return true;
        }
    }

}
