package todolist;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles the simplest possible username/password checks for the application.
 */
public class AuthManager {

	private AuthManager() {
		// Utility class.
	}

	/**
	 * Creates a new user in the SQLite database.
	 *
	 * @param username username chosen by the user
	 * @param password raw password typed by the user
	 * @return true when the user was saved
	 */
	public static boolean createUser(String username, String password) {
		String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
		try (PreparedStatement statement = DatabaseManager.getConnection().prepareStatement(sql)) {
			statement.setString(1, username.trim());
			statement.setString(2, hashPassword(password));
			statement.executeUpdate();
            User user = User.createUser(username, password);
            User.users.add(user);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	/**
	 * Checks if a username and password match a stored account.
	 *
	 * @param username username typed in the login form
	 * @param password password typed in the login form
	 * @return true when the credentials are valid
	 */
	public static boolean authenticate(String username, String password) {
		String sql = "SELECT password FROM users WHERE username = ?";
		try (PreparedStatement statement = DatabaseManager.getConnection().prepareStatement(sql)) {
			statement.setString(1, username.trim());
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					String storedPassword = resultSet.getString("password");
					return storedPassword.equals(hashPassword(password));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

    public static ArrayList<User> getAllUsers(){
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement statement = DatabaseManager.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                User user = User.createUser(resultSet.getString("username"), resultSet.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

	/**
	 * Converts a password into a SHA-256 hash before storing it in the database.
	 *
	 * @param password raw password
	 * @return hashed password as hexadecimal text
	 */
	public static String hashPassword(String password) {
    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hash) {
            result.append(String.format("%02x", b));
        }

        return result.toString();

    } catch (Exception e) {
        throw new RuntimeException(e);
        }
    }
}
