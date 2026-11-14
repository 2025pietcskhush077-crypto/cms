import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EditCourseFrame extends JFrame {
    private final JTextField idField;
    private final JTextField titleField;
    private final JTextField feesField;
    private final JTextField durationField;
    private final JTextField teacherField;

    public EditCourseFrame(DefaultTableModel model, int selectedRow, String id, String title, String fees, String duration, String teacher) {
        setTitle("Course Management System - Edit Course");

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 247, 250));

        // --- HEADER PANEL ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 600, 110);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(null);

        JLabel headerLabel = new JLabel("Edit Course Details", SwingConstants.CENTER);
        headerLabel.setBounds(0, 40, 600, 30);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(new Color(41, 128, 185));
        headerPanel.add(headerLabel);
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

        JLabel titleLabel = new JLabel("Update Course");
        titleLabel.setBounds(30, 15, 340, 30);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(39, 174, 96));
        cardPanel.add(titleLabel);

        JLabel idLabel = new JLabel("Course ID:");
        idLabel.setBounds(30, 70, 100, 25);
        idLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(idLabel);

        idField = new JTextField(id);
        idField.setBounds(130, 70, 260, 30);
        idField.setEditable(false); // Course ID should not be editable primary key
        idField.setBackground(new Color(230, 230, 230));
        cardPanel.add(idField);

        JLabel nameLabel = new JLabel("Course Title:");
        nameLabel.setBounds(30, 115, 100, 25);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(nameLabel);

        titleField = new JTextField(title);
        titleField.setBounds(130, 115, 260, 30);
        cardPanel.add(titleField);

        JLabel feesLabel = new JLabel("Fees:");
        feesLabel.setBounds(30, 160, 100, 25);
        feesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(feesLabel);

        feesField = new JTextField(fees);
        feesField.setBounds(130, 160, 260, 30);
        cardPanel.add(feesField);

        JLabel durationLabel = new JLabel("Duration:");
        durationLabel.setBounds(30, 205, 100, 25);
        durationLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(durationLabel);

        durationField = new JTextField(duration);
        durationField.setBounds(130, 205, 260, 30);
        cardPanel.add(durationField);

        JLabel teacherLabel = new JLabel("Teacher:");
        teacherLabel.setBounds(30, 250, 100, 25);
        teacherLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cardPanel.add(teacherLabel);

        teacherField = new JTextField(teacher);
        teacherField.setBounds(130, 250, 260, 30);
        cardPanel.add(teacherField);

        JButton updateButton = new JButton("Update Course");
        updateButton.setBounds(130, 305, 260, 38);
        updateButton.setBackground(new Color(39, 174, 96));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFont(new Font("Arial", Font.BOLD, 13));
        updateButton.setFocusPainted(false);
        cardPanel.add(updateButton);

        add(cardPanel);

        // --- UPDATE BUTTON ACTION LISTENER ---
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatedTitle = titleField.getText().trim();
                String updatedFeesStr = feesField.getText().trim();
                String updatedDuration = durationField.getText().trim();
                String updatedTeacher = teacherField.getText().trim();

                if (updatedTitle.isEmpty() || updatedFeesStr.isEmpty() || updatedDuration.isEmpty() || updatedTeacher.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        double updatedFees = Double.parseDouble(updatedFeesStr);

                        Connection con = DBConnection.getConnection();
                        PreparedStatement ps = con.prepareStatement(
                                "UPDATE courses SET course_name = ?, duration = ?, fees = ?, instructor = ? WHERE course_id = ?"
                        );
                        ps.setString(1, updatedTitle);
                        ps.setString(2, updatedDuration);
                        ps.setDouble(3, updatedFees);
                        ps.setString(4, updatedTeacher);
                        ps.setString(5, id);

                        int rows = ps.executeUpdate();
                        if (rows > 0) {
                            model.setValueAt(updatedTitle, selectedRow, 1);
                            model.setValueAt(updatedFeesStr, selectedRow, 2);
                            model.setValueAt(updatedDuration, selectedRow, 3);
                            model.setValueAt(updatedTeacher, selectedRow, 4);

                            JOptionPane.showMessageDialog(null, "Course Updated Successfully!");
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Course Not Found in Database!");
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