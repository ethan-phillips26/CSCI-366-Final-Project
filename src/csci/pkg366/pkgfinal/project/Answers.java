/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package csci.pkg366.pkgfinal.project;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author cedar
 */
@Entity
@Table(name = "answers")
@NamedQueries({
    @NamedQuery(name = "Answers.findAll", query = "SELECT a FROM Answers a"),
    @NamedQuery(name = "Answers.findByAnswerId", query = "SELECT a FROM Answers a WHERE a.answerId = :answerId"),
    @NamedQuery(name = "Answers.findByAnswerText", query = "SELECT a FROM Answers a WHERE a.answerText = :answerText"),
    @NamedQuery(name = "Answers.findByIsCorrect", query = "SELECT a FROM Answers a WHERE a.isCorrect = :isCorrect")})
public class Answers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "answer_id")
    private Integer answerId;
    @Basic(optional = false)
    @Column(name = "answer_text")
    private String answerText;
    @Column(name = "is_correct")
    private Boolean isCorrect;
    @JoinColumn(name = "question_id", referencedColumnName = "question_id")
    @ManyToOne(optional = false)
    private Questions questionId;
    
    
    private static String jdbcURL = "jdbc:postgresql://localhost:5432/FinalProject";
    private static String username2 = "postgres";
    private static String password2 = "Biden&?43";

    public Answers() {
    }

    public Answers(Integer answerId) {
        this.answerId = answerId;
    }

    public Answers(Integer answerId, String answerText) {
        this.answerId = answerId;
        this.answerText = answerText;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Questions getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Questions questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerId != null ? answerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Answers)) {
            return false;
        }
        Answers other = (Answers) object;
        if ((this.answerId == null && other.answerId != null) || (this.answerId != null && !this.answerId.equals(other.answerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "csci.pkg366.pkgfinal.project.Answers[ answerId=" + answerId + " ]";
    }
    
    
    //SQL Queries
    public static void updateAnswerText(String text, int id) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username2, password2);
            String getUsers = "UPDATE answer SET answer_text = ? WHERE answer_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setString(1, text);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error updating");
        }
    }
    
    public static void updateIsCorrect(boolean isCorrect, int id) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username2, password2);
            String getUsers = "UPDATE answer SET is_correct = ? WHERE answer_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setBoolean(1, isCorrect);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error updating");
        }
    }
    
    public static void createAnswer(String text, boolean isCorrect, int questionID) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username2, password2);
            String getUsers = "INSERT INTO answers (answer_text, is_correct, question_id)"
                    + " VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setString(1, text);
            pstmt.setBoolean(2, isCorrect);
            pstmt.setInt(3, questionID);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error creating");
        }
    }
    
    public static void deleteAnswer(int id) {
        try {
            Connection connection = DriverManager.getConnection(jdbcURL, username2, password2);
            String getUsers = "DELETE FROM answers WHERE answer_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error deleting");
        }
    }
}
