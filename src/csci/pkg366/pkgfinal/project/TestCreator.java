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
        String temp;
        temp = scan.nextLine();
        input = Integer.parseInt(temp);
        
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
                    ps.executeUpdate();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
                break;
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
                    ps.executeUpdate();
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
                    ps.executeUpdate();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
                break;
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
                    ps.executeUpdate();
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
                    ps.executeUpdate();
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
                    ps.executeUpdate();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
                break;
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
                    ps.executeUpdate();
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
                    ps.executeUpdate();
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
                    ps.executeUpdate();
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
                    ps.executeUpdate();
                }
                catch (SQLException e) {
                    System.out.println("DB error during account creation.");
                }
                break;
        }
    }
    
    public void QuestionBuilder(int t_id){
        String questionText;
        String enterAnsNow;
        int points;
        String sql = "INSERT INTO Questions (question_text, points) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println("Enter the question:");
            questionText = scan.nextLine();
            System.out.println("How many points?:");
            String temp;
            temp = scan.nextLine();
            points = Integer.parseInt(temp);
            ps.setString(1, questionText);
            ps.setInt(2, points);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int newId = rs.getInt(1);
            System.out.println("Set answers now(Y/N)?:");
            enterAnsNow = scan.nextLine();
            enterAnsNow = enterAnsNow.toUpperCase();
            if(enterAnsNow.equals("Y")){
                AnswerBuilder(newId);
            }
            AddQuestion(t_id, newId);
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
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setInt(2, c_id);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int newId = rs.getInt(1);
            System.out.println("How many questions?:");
            String temp;
            temp = scan.nextLine();
            questionNum = Integer.parseInt(temp);
            for(int i = 0; i < questionNum; i++){
                QuestionBuilder(newId);
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
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("DB error during account creation.");
        }
    }
    
    public void editTest(int testId, String newTitle) {
        String sql = "UPDATE Tests SET title = ? WHERE test_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newTitle);
            ps.setInt(2, testId);
            ps.executeUpdate();

            System.out.println("Test updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating test.");
        }
    }
    
    public void editQuestion(int questionId, String newText, int newPoints) {
        String sql = "UPDATE Questions SET question_text = ?, points = ? WHERE question_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newText);
            ps.setInt(2, newPoints);
            ps.setInt(3, questionId);
            ps.executeUpdate();

            System.out.println("Question updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating question.");
        }
    }
    
    public void editAnswer(int answerId, String newText, boolean isCorrect) {
        String sql = "UPDATE Answers SET answer_text = ?, is_correct = ? WHERE answer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newText);
            ps.setBoolean(2, isCorrect);
            ps.setInt(3, answerId);
            ps.executeUpdate();

            System.out.println("Answer updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating answer.");
        }
    }
    
    public void deleteAnswer(int answerId) {
        String sql = "DELETE FROM Answers WHERE answer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, answerId);
            ps.executeUpdate();

            System.out.println("Answer deleted.");
        } catch (SQLException e) {
            System.out.println("Error deleting answer.");
        }
    }
    
    public void deleteQuestion(int questionId) {
        String deleteTestLinks = "DELETE FROM test_questions WHERE question_id = ?";
        String deleteAnswers   = "DELETE FROM Answers WHERE question_id = ?";
        String deleteQuestion  = "DELETE FROM Questions WHERE question_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement ps1 = conn.prepareStatement(deleteTestLinks);
            ps1.setInt(1, questionId);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(deleteAnswers);
            ps2.setInt(1, questionId);
            ps2.executeUpdate();

            PreparedStatement ps3 = conn.prepareStatement(deleteQuestion);
            ps3.setInt(1, questionId);
            ps3.executeUpdate();

            System.out.println("Question deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting question.");
        }
    }
    
    public void deleteTest(int testId) {
        String deleteLinks = "DELETE FROM test_questions WHERE test_id = ?";
        String deleteTest  = "DELETE FROM Tests WHERE test_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {

            PreparedStatement ps1 = conn.prepareStatement(deleteLinks);
            ps1.setInt(1, testId);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(deleteTest);
            ps2.setInt(1, testId);
            ps2.executeUpdate();

            System.out.println("Test deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error deleting test.");
        }
    }
}
