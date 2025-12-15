/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csci.pkg366.pkgfinal.project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Collection;
import java.util.ArrayList;
import java.sql.Statement;

/**
 *
 * @author cedar
 */
public class TestCreator {
    Scanner scan = new Scanner(System.in);
    static String jdbcURL = "jdbc:postgresql://localhost:5432/SimpleCompany";
    static String username = "postgres";
    static String password = "postgres123";
    
    public void AnswerBuilder(int q_id){
        String correct = "";
        int input;
        String answer1 = "";
        String answer2 = "";
        String answer3 = "";
        String answer4 = "";
        boolean corr1 = false;
        boolean corr2 = false;
        boolean corr3 = false;
        boolean corr4 = false;
        String sql = "INSERT INTO Answers(answer_text, is_correct, question_id) VALUES (?, ?, ?)";
        System.out.print("How many options(1-4)?:");
        input = scan.nextInt();
        
        switch(input){
            case 1:
                System.out.println("Answer 1:");
                answer1 = scan.nextLine();
                System.out.println("Is it correct(Y/N)?:");
                correct = scan.nextLine();
                correct = correct.toUpperCase();
                switch(correct){
                    case("Y"):
                        corr1 = true;
                    case("N"):
                        corr1 = false;
                }
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, answer1);
                    ps.setBoolean(2, corr1);
                    ps.setInt(3, q_id);
                    ps.executeQuery();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
            case 2:
                System.out.println("Answer 1:");
                answer1 = scan.nextLine();
                System.out.println("Is it correct(Y/N)?:");
                correct = scan.nextLine();
                correct = correct.toUpperCase();
                switch(correct){
                    case("Y"):
                        corr1 = true;
                    case("N"):
                        corr1 = false;
                }
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, answer1);
                    ps.setBoolean(2, corr1);
                    ps.setInt(3, q_id);
                    ps.executeQuery();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
                System.out.println("Answer 2:");
                answer2 = scan.nextLine();
                System.out.println("Is it correct(Y/N)?:");
                correct = scan.nextLine();
                correct = correct.toUpperCase();
                switch(correct){
                    case("Y"):
                        corr2 = true;
                    case("N"):
                        corr2 = false;
                }
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, answer2);
                    ps.setBoolean(2, corr2);
                    ps.setInt(3, q_id);
                    ps.executeQuery();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
            case 3:
                System.out.println("Answer 1:");
                answer1 = scan.nextLine();
                System.out.println("Is it correct(Y/N)?:");
                correct = scan.nextLine();
                correct = correct.toUpperCase();
                switch(correct){
                    case("Y"):
                        corr1 = true;
                    case("N"):
                        corr1 = false;
                }
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, answer1);
                    ps.setBoolean(2, corr1);
                    ps.setInt(3, q_id);
                    ps.executeQuery();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
                System.out.println("Answer 2:");
                answer2 = scan.nextLine();
                System.out.println("Is it correct(Y/N)?:");
                correct = scan.nextLine();
                correct = correct.toUpperCase();
                switch(correct){
                    case("Y"):
                        corr2 = true;
                    case("N"):
                        corr2 = false;
                }
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, answer2);
                    ps.setBoolean(2, corr2);
                    ps.setInt(3, q_id);
                    ps.executeQuery();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
                System.out.println("Answer 3:");
                answer3 = scan.nextLine();
                System.out.println("Is it correct(Y/N)?:");
                correct = scan.nextLine();
                correct = correct.toUpperCase();
                switch(correct){
                    case("Y"):
                        corr3 = true;
                    case("N"):
                        corr3 = false;
                }
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, answer3);
                    ps.setBoolean(2, corr3);
                    ps.setInt(3, q_id);
                    ps.executeQuery();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
            case 4:
                System.out.println("Answer 1:");
                answer1 = scan.nextLine();
                System.out.println("Is it correct(Y/N)?:");
                correct = scan.nextLine();
                correct = correct.toUpperCase();
                switch(correct){
                    case("Y"):
                        corr1 = true;
                    case("N"):
                        corr1 = false;
                }
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, answer1);
                    ps.setBoolean(2, corr1);
                    ps.setInt(3, q_id);
                    ps.executeQuery();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
                System.out.println("Answer 2:");
                answer2 = scan.nextLine();
                System.out.println("Is it correct(Y/N)?:");
                correct = scan.nextLine();
                correct = correct.toUpperCase();
                switch(correct){
                    case("Y"):
                        corr2 = true;
                    case("N"):
                        corr2 = false;
                }
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, answer2);
                    ps.setBoolean(2, corr2);
                    ps.setInt(3, q_id);
                    ps.executeQuery();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
                System.out.println("Answer 3:");
                answer3 = scan.nextLine();
                System.out.println("Is it correct(Y/N)?:");
                correct = scan.nextLine();
                correct = correct.toUpperCase();
                switch(correct){
                    case("Y"):
                        corr3 = true;
                    case("N"):
                        corr3 = false;
                }
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, answer3);
                    ps.setBoolean(2, corr3);
                    ps.setInt(3, q_id);
                    ps.executeQuery();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
                System.out.println("Answer 4:");
                answer4 = scan.nextLine();
                System.out.println("Is it correct(Y/N)?:");
                correct = scan.nextLine();
                correct = correct.toUpperCase();
                switch(correct){
                    case("Y"):
                        corr4 = true;
                    case("N"):
                        corr4 = false;
                }
                try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, answer4);
                    ps.setBoolean(2, corr4);
                    ps.setInt(3, q_id);
                    ps.executeQuery();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
        }
    }
    
    public void QuestionBuilder(int t_id){
        String questionText;
        String enterAnsNow;
        int points;
        String sql = "INSERT INTO Questions (question_text, points) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println("Enter the question:");
            questionText = scan.nextLine();
            System.out.println("How many points?:");
            points = scan.nextInt();
            ps.setString(1, questionText);
            ps.setInt(2, points);
            ResultSet rs = ps.executeQuery();
            System.out.println("Set answers now(Y/N)?:");
            enterAnsNow = scan.nextLine();
            enterAnsNow = enterAnsNow.toUpperCase();
            if(enterAnsNow.equals("Y")){
                AnswerBuilder(rs.getInt("question_id"));
            }
            AddQuestion(t_id, rs.getInt("question_id"));
        }
        catch (SQLException e) {
            System.out.println("DB error during account creation.");
        }
    }
    
    public void TestBuilder(int c_id){
        String title;
        int questionNum;
        String sql = "INSERT INTO Tests(title, creator_id) VALUES (?, ?)";
        System.out.println("Enter test title:");
        title = scan.nextLine();
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setInt(2, c_id);
            ResultSet rs = ps.executeQuery();
            System.out.println("How many questions?:");
            questionNum = scan.nextInt();
            for(int i = 0; i < questionNum; i++){
                QuestionBuilder(rs.getInt("test_id"));
            }
        }
        catch (SQLException e) {
            System.out.println("DB error during account creation.");
        }
        
    }
    
    public void DisplayCreatedTests(int id){
        try{
            Connection connection = DatabaseConnection.getConnection();
            String getTests = "SELECT * FROM Tests WHERE creator_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getTests);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                System.out.println("Test Title: " + rs.getString("title"));
                System.out.println("--------------------");
            }
            System.out.println("Done");
        }
        catch(SQLException e){
            System.out.println("Cannot connect");
        }
    }
    
    public void AddQuestion(int t_id, int q_id){
        String sql = "INSERT INTO test_questions(test_id, question_id) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, t_id);
            ps.setInt(2, q_id);
            ps.executeQuery();
        }
        catch (SQLException e) {
            System.out.println("DB error during account creation.");
        }
    }
}
