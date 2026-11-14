import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddCourseFrame extends JFrame {
    private final JTextField idField;
    private final JTextField titleField;
    private final JTextField feesField;
    private final JTextField durationField;
    private final JTextField teacherField;

    public AddCourseFrame() {
        setTitle("Course Management System - Add Course");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 247, 250));

        // --- HEADER PANEL WITH LOGO ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 600, 110);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(null);

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/poornim logo.jpeg"));
            Image img = logoIcon.getImage().getScaledInstance(350, 90, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setBounds(125, 10, 350, 90);
            headerPanel.add(imageLabel);
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Poornima College of Engineering", SwingConstants.CENTER);
            errorLabel.setBounds(0, 40, 600, 30);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
            headerPanel.add(errorLabel);
        }
        add(headerPanel);

        // Form Container Panel
        JPanel cardPanel = new JPanel();
        cardPanel.setBounds(70, 130, 440, 400);
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 218, 226), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        cardPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Add New Course");
        titleLabel.setBounds(30, 15, 340, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(41, 128, 185));
        cardPanel.add(titleLabel);

        JLabel idLabel = new JLabel("Course ID:");
        idLabel.setBounds(30, 70, 100, 25);
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(130, 70, 260, 30);
        cardPanel.add(idField);

        JLabel nameLabel = new JLabel("Course Title:");
        nameLabel.setBounds(30, 115, 100, 25);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(nameLabel);

        titleField = new JTextField();
        titleField.setBounds(130, 115, 260, 30);
        cardPanel.add(titleField);

        JLabel feesLabel = new JLabel("Fees:");
        feesLabel.setBounds(30, 160, 100, 25);
        feesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(feesLabel);

        feesField = new JTextField();
        feesField.setBounds(130, 160, 260, 30);
        cardPanel.add(feesField);

        JLabel durationLabel = new JLabel("Duration:");
        durationLabel.setBounds(30, 205, 100, 25);
        durationLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(durationLabel);

        durationField = new JTextField();
        durationField.setBounds(130, 205, 260, 30);
        cardPanel.add(durationField);

        JLabel teacherLabel = new JLabel("Teacher:");
        teacherLabel.setBounds(30, 250, 100, 25);
        teacherLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(teacherLabel);

        teacherField = new JTextField();
        teacherField.setBounds(130, 250, 260, 30);
        cardPanel.add(teacherField);

        JButton saveButton = new JButton("Save Course");
        saveButton.setBounds(130, 305, 260, 38);
        saveButton.setBackground(new Color(41, 128, 185));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Arial", Font.BOLD, 13));
        saveButton.setFocusPainted(false);
        cardPanel.add(saveButton);

        add(cardPanel);

        // --- SAVE BUTTON ACTION LISTENER USING DBConnection ---
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                String title = titleField.getText().trim();
                String feesStr = feesField.getText().trim();
                String duration = durationField.getText().trim();
                String teacher = teacherField.getText().trim();

                if (id.isEmpty() || title.isEmpty() || feesStr.isEmpty() || duration.isEmpty() || teacher.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        double fees = Double.parseDouble(feesStr);

                        // Call shared DBConnection class method
                        Connection con = DBConnection.getConnection();
                        PreparedStatement ps = con.prepareStatement(
                                "INSERT INTO courses (course_id, course_name, duration, fees, instructor) VALUES (?, ?, ?, ?, ?)"
                        );
                        ps.setString(1, id);
                        ps.setString(2, title);
                        ps.setString(3, duration);
                        ps.setDouble(4, fees);
                        ps.setString(5, teacher);

                        int rows = ps.executeUpdate();
                        if (rows > 0) {
                            JOptionPane.showMessageDialog(null, "Course Added Successfully!");
                            dispose();
                        }
                        ps.close();
                        con.close();

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid numeric value for Fees!", "Format Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}