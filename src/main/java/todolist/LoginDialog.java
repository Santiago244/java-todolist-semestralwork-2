package todolist;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Simple login window used before opening the main application.
 */
public class LoginDialog extends JDialog {
	private boolean authenticated;
	private final JTextField usernameField = new JTextField(18);
	private final JPasswordField passwordField = new JPasswordField(18);

    // creates teh login dialog with the main application window as its owner, so it is centered on it and blocks it until login is successful or cancelled.
	public LoginDialog(JFrame owner) {
		super(owner, "Login", true);
		buildUi();
	}

	private void buildUi() {
		JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0; gbc.weightx = 1;  gbc.weighty = 0.5;
		formPanel.add(new JLabel("USERNAME"), gbc);
        gbc.gridy=1;
		formPanel.add(usernameField, gbc);
        gbc.gridy = 2; gbc.weightx = 1; gbc.weighty = 0.5;
		formPanel.add(new JLabel("PASSWORD"), gbc);
        gbc.gridy= 4;
		formPanel.add(passwordField, gbc);

		JButton loginButton = new JButton("Login");
		JButton createUserButton = new JButton("Create User");
		JButton cancelButton = new JButton("Cancel");

		loginButton.addActionListener(event -> login());
		createUserButton.addActionListener(event -> createUser());
		cancelButton.addActionListener(event -> {
			this.authenticated = false;
			dispose();
		});

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonPanel.add(createUserButton);
		buttonPanel.add(loginButton);
		buttonPanel.add(cancelButton);

		setLayout(new BorderLayout());
		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
        setSize(getOwner().getSize());
	}

	private void login() {
		String username = usernameField.getText().trim();
		String password = new String(passwordField.getPassword());

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Type a username and password first.");
			return;
		}

		if (AuthManager.authenticate(username, password)) {
			this.authenticated = true;
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Invalid username or password.");
		}
	}

	private void createUser() {
		String username = usernameField.getText().trim();
		String password = new String(passwordField.getPassword());

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Type a username and password first.");
			return;
		}

		if (AuthManager.createUser(username, password)) {
			JOptionPane.showMessageDialog(this, "User created. You can log in now.");
		} else {
			JOptionPane.showMessageDialog(this, "That username already exists or could not be saved.");
		}
	}

	/**
	 * @return true when login succeeded
	 */
	public boolean isAuthenticated() {
		return authenticated;
	}
}
