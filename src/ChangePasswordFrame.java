import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChangePasswordFrame extends JFrame {
    private final JTextField userTextField;
    private final JPasswordField oldPasswordField;
    private final JPasswordField newPasswordField;
    private final JPasswordField confirmPasswordField;

    public ChangePasswordFrame() {
        setTitle("Course Management System - Change Password");
        setSize(500, 530);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 247, 250));

        // --- HEADER PANEL WITH LOGO ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 500, 110);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(null);

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/poornim logo.jpeg"));
            Image img = logoIcon.getImage().getScaledInstance(300, 90, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setBounds(100, 10, 300, 90);
            headerPanel.add(imageLabel);
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Poornima Institute of Engineering", SwingConstants.CENTER);
            errorLabel.setBounds(0, 40, 500, 30);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
            headerPanel.add(errorLabel);
        }
        add(headerPanel);

        JPanel cardPanel = new JPanel();
        cardPanel.setBounds(50, 130, 385, 345);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 218, 226), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        cardPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Change Password");
        titleLabel.setBounds(25, 15, 320, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(41, 128, 185));
        cardPanel.add(titleLabel);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(25, 60, 120, 25);
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(userLabel);

        userTextField = new JTextField();
        userTextField.setBounds(140, 60, 210, 30);
        cardPanel.add(userTextField);

        JLabel oldPassLabel = new JLabel("Old Password:");
        oldPassLabel.setBounds(25, 105, 120, 25);
        oldPassLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(oldPassLabel);

        oldPasswordField = new JPasswordField();
        oldPasswordField.setBounds(140, 105, 210, 30);
        cardPanel.add(oldPasswordField);

        JLabel newPassLabel = new JLabel("New Password:");
        newPassLabel.setBounds(25, 150, 120, 25);
        newPassLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(newPassLabel);

        newPasswordField = new JPasswordField();
        newPasswordField.setBounds(140, 150, 210, 30);
        cardPanel.add(newPasswordField);

        JLabel confirmPassLabel = new JLabel("Confirm Pass:");
        confirmPassLabel.setBounds(25, 195, 120, 25);
        confirmPassLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(confirmPassLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(140, 195, 210, 30);
        cardPanel.add(confirmPasswordField);

        JButton updateButton = new JButton("Update Password");
        updateButton.setBounds(140, 250, 210, 38);
        updateButton.setBackground(new Color(41, 128, 185));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 13));
        updateButton.setFocusPainted(false);
        cardPanel.add(updateButton);

        add(cardPanel);

        // --- DATABASE INTEGRATED ACTION LISTENER ---
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText().trim();
                String oldPass = new String(oldPasswordField.getPassword()).trim();
                String newPass = new String(newPasswordField.getPassword()).trim();
                String confirmPass = new String(confirmPasswordField.getPassword()).trim();

                if (username.isEmpty() || oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!newPass.equals(confirmPass)) {
                    JOptionPane.showMessageDialog(null, "New passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        // Centralized DBConnection matching your project pattern
                        Connection con = DBConnection.getConnection();

                        // 1. Pehle check karein ki username aur old password sahi hain ya nahi
                        PreparedStatement checkPs = con.prepareStatement(
                                "SELECT * FROM users WHERE username = ? AND password = ?"
                        );
                        checkPs.setString(1, username);
                        checkPs.setString(2, oldPass);
                        ResultSet rs = checkPs.executeQuery();

                        if (rs.next()) {
                            // 2. Agar match ho gaya, toh naya password update kar dein
                            PreparedStatement updatePs = con.prepareStatement(
                                    "UPDATE users SET password = ? WHERE username = ?"
                            );
                            updatePs.setString(1, newPass);
                            updatePs.setString(2, username);
                            updatePs.executeUpdate();

                            JOptionPane.showMessageDialog(null, "Password Updated Successfully!");
                            updatePs.close();
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid Username or Old Password!", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                        rs.close();
                        checkPs.close();
                        con.close();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}