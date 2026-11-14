import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardFrame extends JFrame {

    public DashboardFrame(String role) {
        setTitle("Course Management System - Poornima Institute of Engineering & Technology Dashboard (" + role + ")");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen maximized mode
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 247, 250));

        // 1. HEADER PANEL WITH WIDE LOGO IMAGE
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 1920, 130);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(null);

        try {
            ImageIcon logoIcon = new ImageIcon("poornim logo.jpeg");
            Image img = logoIcon.getImage().getScaledInstance(500, 105, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));

            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            int logoWidth = 500;
            int centeredX = (screenWidth - logoWidth) / 2;

            imageLabel.setBounds(centeredX, 12, logoWidth, 105);
            headerPanel.add(imageLabel);
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Poornima Institute of Engineering & Technology", SwingConstants.CENTER);
            errorLabel.setBounds(0, 40, 1920, 40);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 22));
            errorLabel.setForeground(new Color(41, 128, 185));
            headerPanel.add(errorLabel);
        }

        // Logged in user role label on top right
        JLabel userRoleLabel = new JLabel("Logged in as: " + role);
        userRoleLabel.setBounds(1180, 50, 200, 30);
        userRoleLabel.setForeground(new Color(41, 128, 185));
        userRoleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(userRoleLabel);

        add(headerPanel);

        // 2. DARK FOOTER PANEL WITH YOUR NAME & EMAIL
        JPanel footerPanel = new JPanel();
        footerPanel.setBounds(0, 960, 1920, 60);
        footerPanel.setBackground(new Color(45, 45, 45));
        footerPanel.setLayout(null);

        JLabel footerText = new JLabel("By Khush Sharma  |  2025pietcskhush077@poornima.org", JLabel.CENTER);
        footerText.setFont(new Font("Arial", Font.BOLD, 13));
        footerText.setForeground(new Color(241, 196, 15)); // Professional yellow accent color
        footerText.setBounds(0, 15, 1920, 30);
        footerPanel.add(footerText);

        add(footerPanel);

        // 3. LEFT SIDEBAR (Menu Buttons)
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBounds(0, 130, 250, 830);
        sidebarPanel.setBackground(new Color(236, 240, 241));
        sidebarPanel.setLayout(null);

        JButton addCourseBtn = new JButton("Add Course");
        addCourseBtn.setBounds(25, 40, 200, 40);
        addCourseBtn.setFont(new Font("Arial", Font.BOLD, 13));
        sidebarPanel.add(addCourseBtn);

        JButton viewRecordsBtn = new JButton("View Records");
        viewRecordsBtn.setBounds(25, 110, 200, 40);
        viewRecordsBtn.setFont(new Font("Arial", Font.BOLD, 13));
        sidebarPanel.add(viewRecordsBtn);

        JButton changePassBtn = new JButton("Change Password");
        changePassBtn.setBounds(25, 180, 200, 40);
        changePassBtn.setFont(new Font("Arial", Font.BOLD, 13));
        sidebarPanel.add(changePassBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(25, 680, 200, 40);
        logoutBtn.setBackground(new Color(231, 76, 60));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 13));
        logoutBtn.setFocusPainted(false);
        sidebarPanel.add(logoutBtn);

        add(sidebarPanel);

        // 4. MAIN CONTENT AREA ("About This Project")
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBounds(290, 160, 1020, 750);
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setBorder(BorderFactory.createLineBorder(new Color(218, 225, 233)));
        mainContentPanel.setLayout(null);

        JLabel aboutTitle = new JLabel("About This Project");
        aboutTitle.setBounds(40, 35, 400, 30);
        aboutTitle.setFont(new Font("Arial", Font.BOLD, 20));
        aboutTitle.setForeground(new Color(41, 128, 185));
        mainContentPanel.add(aboutTitle);

        JTextArea aboutText = new JTextArea(
                "Welcome to the Course Management System (CMS).\n\n" +
                        "This application is developed as part of the Minor Project NSP at Poornima Institute of Engineering & Technology.\n" +
                        "Technology Stack: Java, Java Swing, JDBC, MySQL, and IntelliJ IDEA.\n\n" +
                        "Core Features:\n" +
                        "• Secure Login Authentication (Admin/User roles)\n" +
                        "• Comprehensive Course Management (Add, View, Update, Delete records)\n" +
                        "• Built-In Form Validation and Database Integration\n\n" +
                        "Instructions:\n" +
                        "Use the navigation buttons on the left sidebar to manage course records and system operations seamlessly."
        );
        aboutText.setBounds(40, 95, 940, 580);
        aboutText.setFont(new Font("Arial", Font.PLAIN, 15));
        aboutText.setEditable(false);
        aboutText.setOpaque(false);
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);
        mainContentPanel.add(aboutText);

        add(mainContentPanel);

        // --- BUTTON ACTIONS ---
        addCourseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddCourseFrame().setVisible(true);
            }
        });

        viewRecordsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewRecordsFrame().setVisible(true);
            }
        });

        changePassBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePasswordFrame().setVisible(true);
            }
        });

        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DashboardFrame("Admin").setVisible(true);
            }
        });
    }
}