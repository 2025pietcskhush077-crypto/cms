import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class LoginFrame extends JFrame {

    private JTextField userTextField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Course Management System - Login");

        // Window ko poori screen (Maximized) par kholne ke liye
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 247, 250));

        // 1. TOP HEADER PANEL WITH WIDE POORNIMA LOGO
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1920, 130);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(null);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(218, 225, 233)));

        try {
            ImageIcon logoIcon = null;
            URL imgUrl = getClass().getResource("/poornim logo.jpeg");
            if (imgUrl != null) {
                logoIcon = new ImageIcon(imgUrl);
            } else {
                logoIcon = new ImageIcon("poornim logo.jpeg"); // Project root fallback
            }

            Image img = logoIcon.getImage().getScaledInstance(520, 100, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(img));

            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            int logoWidth = 520;
            int centeredX = (screenWidth - logoWidth) / 2;

            logoLabel.setBounds(centeredX, 15, logoWidth, 100);
            headerPanel.add(logoLabel);
        } catch (Exception e) {
            JLabel fallbackTitle = new JLabel("Poornima Institute of Engineering & Technology", SwingConstants.CENTER);
            fallbackTitle.setBounds(0, 40, 1920, 40);
            fallbackTitle.setFont(new Font("Arial", Font.BOLD, 24));
            fallbackTitle.setForeground(new Color(41, 128, 185));
            headerPanel.add(fallbackTitle);
        }

        add(headerPanel);

        // 2. LOGIN PORTAL TITLE
        JLabel portalTitle = new JLabel("Course Login Portal", JLabel.CENTER);
        portalTitle.setFont(new Font("Arial", Font.BOLD, 22));
        portalTitle.setForeground(new Color(40, 40, 40));
        portalTitle.setBounds(0, 160, 1920, 30);
        add(portalTitle);

        // 3. CENTER LOGIN FORM CARD PANEL
        JPanel formCard = new JPanel();
        formCard.setBounds(710, 215, 500, 320);
        formCard.setBackground(Color.WHITE);
        formCard.setLayout(null);
        formCard.setBorder(BorderFactory.createLineBorder(new Color(210, 218, 226), 1));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        userLabel.setBounds(50, 45, 100, 30);
        formCard.add(userLabel);

        userTextField = new JTextField();
        userTextField.setBounds(160, 45, 280, 35);
        userTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        formCard.add(userTextField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        passLabel.setBounds(50, 105, 100, 30);
        formCard.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 105, 280, 35);
        formCard.add(passwordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        roleLabel.setBounds(50, 165, 100, 30);
        formCard.add(roleLabel);

        String[] roles = {"Admin", "User"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(160, 165, 280, 35);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        formCard.add(roleComboBox);

        loginButton = new JButton("Login");
        loginButton.setBounds(160, 230, 280, 42);
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setFocusPainted(false);
        formCard.add(loginButton);

        add(formCard);

        JLabel forgotPass = new JLabel("Forgot Password?", JLabel.CENTER);
        forgotPass.setFont(new Font("Arial", Font.PLAIN, 13));
        forgotPass.setForeground(new Color(41, 128, 185));
        forgotPass.setBounds(0, 550, 1920, 25);
        add(forgotPass);

        // 4. DARK FOOTER PANEL WITH YOUR NAME & EMAIL (Safe Y Position)
        JPanel footerPanel = new JPanel();
        footerPanel.setBounds(0, 620, 1920, 50);
        footerPanel.setBackground(new Color(45, 45, 45));
        footerPanel.setLayout(null);

        JLabel footerText = new JLabel("By Khush Sharma  |  2025pietcskhush077@poornima.org", JLabel.CENTER);
        footerText.setFont(new Font("Arial", Font.BOLD, 13));
        footerText.setForeground(new Color(241, 196, 15)); // Professional yellow highlight color
        footerText.setBounds(0, 12, 1920, 25);
        footerPanel.add(footerText);

        add(footerPanel);

        // --- BUTTON ACTION LISTENER ---
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String role = (String) roleComboBox.getSelectedItem();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Hardcoded login check
                if (username.equals("admin") && password.equals("12345")) {
                    JOptionPane.showMessageDialog(null, "Login Successful as " + role + "!");
                    new DashboardFrame(role).setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password!", "Access Denied", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}