package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import model.User;
import util.DatabaseConnection;

public class UserDAO {
	public User loginUser(String email, String password) {

	    String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

	    try (Connection connection = DatabaseConnection.getConnection();
	         PreparedStatement statement = connection.prepareStatement(sql)) {

	        System.out.println("Email received by DAO: " + email);
	        System.out.println("Password received by DAO: " + password);

	        statement.setString(1, email);
	        statement.setString(2, password);

	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {

	            System.out.println("USER FOUND!");

	            User user = new User(
	                resultSet.getInt("id"),
	                resultSet.getString("name"),
	                resultSet.getString("email"),
	                resultSet.getString("password"),
	                resultSet.getString("role")
	            );

	            return user;
	        }

	        System.out.println("USER NOT FOUND!");
	        return null;

	    } catch (SQLException e) {

	        System.out.println("DATABASE ERROR:");
	        e.printStackTrace();

	        return null;
	    }
	}
	public boolean registerUser(User user) {
		String sql = "INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)";
		try (Connection connection = DatabaseConnection.getConnection();
			     PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getRole());
			
			int rows = statement.executeUpdate();
			
			if (rows > 0) {
			    return true;
			} else {
			    return false;
			}
		}
		catch (SQLException e) {
		    System.out.println("DATABASE ERROR:");
		    e.printStackTrace();
		    return false;
		}
	}
}
