/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package csci.pkg366.pkgfinal.project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.PreparedStatement;

public class TestForDatabase {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String jdbcURL = "jdbc:postgresql://localhost:5432/InClass";
        String username = "postgres";
        String password = "Biden&?43";
        
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, username, password);
            
            //insnert sales department
            Statement stmt = connection.createStatement();
            String insertDepartment = 
                    "INSERT INTO department (department_name) values ('Sales')";
            //int count = stmt.executeUpdate(insertDepartment);
            //System.out.println(count + " department been inserted.");
            
            
            //Get all departments
            /*
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter department number: ");
            String depNum = scan.nextLine();
            
            String getDepartment = "SELECT * FROM department WHERE department_num = " + depNum;
            ResultSet rs = stmt.executeQuery(getDepartment);
             
            while (rs.next()) {
            System.out.println("dept num: " + rs.getInt("department_num"));
            System.out.println("dept name: " + rs.getString("department_name") + "\n");
            
            }
            */
            Scanner scan = new Scanner(System.in);
            System.out.print("Enter dept_num: ");
            String dept_num = scan.nextLine();
            
            String getDepartmentByNum = 
                    "SELECT * FROM department " +
                    "WHERE department_num = ?";
            
            PreparedStatement pstmt = connection.prepareStatement(getDepartmentByNum);
            pstmt.setInt(1, Integer.parseInt(dept_num));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
            System.out.println("dept num: " + rs.getInt("department_num"));
            System.out.println("dept name: " + rs.getString("department_name") + "\n");
            }
        } catch (ClassNotFoundException e){
            System.out.println("error");
        } catch (SQLException e){
            System.out.println("SQL Exception");
        }
    }
    
}