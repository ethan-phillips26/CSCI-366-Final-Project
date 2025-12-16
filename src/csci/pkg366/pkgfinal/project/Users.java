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
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author cedar
 */
@Entity
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByFirstname", query = "SELECT u FROM Users u WHERE u.firstname = :firstname"),
    @NamedQuery(name = "Users.findByLastname", query = "SELECT u FROM Users u WHERE u.lastname = :lastname"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByUserType", query = "SELECT u FROM Users u WHERE u.userType = :userType")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "firstname")
    private String firstname;
    @Basic(optional = false)
    @Column(name = "lastname")
    private String lastname;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "user_type")
    private String userType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creatorId")
    private Collection<Tests> testsCollection;

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Users(Integer userId, String firstname, String lastname, String username, String password) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Collection<Tests> getTestsCollection() {
        return testsCollection;
    }

    public void setTestsCollection(Collection<Tests> testsCollection) {
        this.testsCollection = testsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "csci.pkg366.pkgfinal.project.Users[ userId=" + userId + " ]";
    }
    
    // SQLQueries
    public static long getUserCount() {
        try {
            String getCount = "SELECT COUNT(*) as c FROM users";
            Connection connection = DatabaseConnection.getConnection();
            
            PreparedStatement pstmt = connection.prepareStatement(getCount);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getLong("c");
            }
        
            } catch (SQLException sqle) {
                return -1;
            }
            return -1;
    }
    
    public static Users getUserById(int id) {
        try {
            //temp connection. We should make a connection class.
            
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "SELECT * FROM users WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            Users user = new Users();
            while (rs.next()){
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setUserId(rs.getInt("user_id"));
                user.setUserType(rs.getString("user_type"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
            return user;
        } catch (SQLException sqle) {
            return null;
        }
    }
    
    public static Users getUserByUsername(String _username) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setString(1, _username);
            ResultSet rs = pstmt.executeQuery();
            Users user = new Users();
            while (rs.next()){
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setUserId(rs.getInt("user_id"));
                user.setUserType(rs.getString("user_type"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
            return user;
        } catch (SQLException sqle) {
            return null;
        }
    }
    
     public static LinkedList<Users> getUsersByName(String firstn, String lastn) {
        LinkedList<Users> list = new LinkedList<>();
        try {
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "SELECT * FROM users WHERE firstname = ? AND lastname = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setString(1, firstn);
            pstmt.setString(2, lastn);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()){
                Users user = new Users();
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setUserId(rs.getInt("user_id"));
                user.setUserType(rs.getString("user_type"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                
                list.add(user);
            }
            return list;
        } catch (SQLException sqle) {
            return list;
        }
    }
     
    public static void updateFirstname(String firstn, int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "UPDATE users SET firstname = ? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setString(1, firstn);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error updating");
        }
    }
    
    public static void updateLastname(String lastn, int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "UPDATE users SET lastname = ? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setString(1, lastn);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error updating");
        }
    }
    
    public static void updateUserType(String userType, int id) {
        try {
            if (!(userType.equals("MANAGER") || userType.equals("STUDENT") || userType.equals("CREATOR"))) {
                System.out.println("invalid user type");
                return;
            }
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "UPDATE users SET userType = ? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setString(1, userType);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error updating");
        }
    }
    
    public static void updateUsername(String username, int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "UPDATE users SET username = ? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setString(1, username);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error updating");
        }
    }
    
    public static void updatePassword(String password, int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "UPDATE users SET password = ? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setString(1, password);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error updating");
        }
    }
    
    public static void updateId(int newId, int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "UPDATE users SET user_id = ? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setInt(1, newId);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error updating");
        }
    }
    
    public static void deleteUser(int id) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setInt(1, id);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error deleting");
        }
    }
    
    public static void createUser(String firstname, String lastname, String usertype, String username, String password) {
        try {
            if (!(usertype.equals("MANAGER") || usertype.equals("STUDENT") || usertype.equals("CREATOR"))) {
                System.out.println("Invalid user type");
                return;
            }
            
            Connection connection = DatabaseConnection.getConnection();
            String getUsers = "INSERT INTO users (firstname, lastname, user_type, username, password)"
                    + " VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(getUsers);
            pstmt.setString(1, firstname);
            pstmt.setString(2, lastname);
            pstmt.setString(3, usertype);
            pstmt.setString(4, username);
            pstmt.setString(5, password);
            pstmt.executeQuery();
        } catch (SQLException sqle) {
            System.out.println("error creating");
        }
    }
}
