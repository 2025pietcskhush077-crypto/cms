import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class ViewRecordsFrame extends JFrame {
    private final JTable table;
    private final DefaultTableModel model;

    public ViewRecordsFrame() {
        setTitle("Course Management System - View All Course Records");
        setSize(950, 630);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 247, 250));

        // --- TOP HEADER PANEL WITH LOGO ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBounds(0, 0, 950, 110);
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setLayout(null);

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/poornim logo.jpeg"));
            Image img = logoIcon.getImage().getScaledInstance(350, 90, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(img));
            imageLabel.setBounds(300, 10, 350, 90);
            headerPanel.add(imageLabel);
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Poornima College of Engineering", SwingConstants.CENTER);
            errorLabel.setBounds(0, 40, 950, 30);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 18));
            headerPanel.add(errorLabel);
        }
        add(headerPanel);

        // Title Bar below header
        JPanel titleBar = new JPanel();
        titleBar.setBounds(0, 110, 950, 50);
        titleBar.setBackground(new Color(41, 128, 185));
        titleBar.setLayout(null);
        JLabel titleLabel = new JLabel("Course Records Management");
        titleLabel.setBounds(30, 10, 400, 30);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleBar.add(titleLabel);
        add(titleBar);

        // --- CENTER TABLE ---
        String[] columnNames = {"Course ID", "Course Title", "Fees", "Duration", "Teacher"};
        model = new DefaultTableModel(null, columnNames);
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 180, 875, 300);
        add(scrollPane);

        // Load data from database using DBConnection
        loadDataFromDatabase();

        // --- BOTTOM ACTION PANEL ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 500, 950, 75);
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 18));

        JButton viewButton = new JButton("View Details");
        viewButton.setFont(new Font("Arial", Font.BOLD, 12));
        viewButton.setBackground(new Color(52, 152, 219));
        viewButton.setForeground(Color.WHITE);
        viewButton.setFocusPainted(false);

        JButton editButton = new JButton("Edit Course");
        editButton.setFont(new Font("Arial", Font.BOLD, 12));
        editButton.setBackground(new Color(39, 174, 96));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);

        JButton deleteButton = new JButton("Delete Course");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12));
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.setFocusPainted(false);

        bottomPanel.add(viewButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(closeButton);
        add(bottomPanel);

        // --- BUTTON ACTIONS ---
        viewButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a course row to view!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                new ViewCourseDetailsFrame(
                        model.getValueAt(selectedRow, 0).toString(),
                        model.getValueAt(selectedRow, 1).toString(),
                        model.getValueAt(selectedRow, 2).toString(),
                        model.getValueAt(selectedRow, 3).toString(),
                        model.getValueAt(selectedRow, 4).toString()
                ).setVisible(true);
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a course row to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                new EditCourseFrame(
                        model, selectedRow,
                        model.getValueAt(selectedRow, 0).toString(),
                        model.getValueAt(selectedRow, 1).toString(),
                        model.getValueAt(selectedRow, 2).toString(),
                        model.getValueAt(selectedRow, 3).toString(),
                        model.getValueAt(selectedRow, 4).toString()
                ).setVisible(true);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a course row to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this course?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        String courseId = model.getValueAt(selectedRow, 0).toString();

                        Connection conn = DBConnection.getConnection();
                        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM courses WHERE course_id = ?");
                        pstmt.setString(1, courseId);
                        pstmt.executeUpdate();

                        model.removeRow(selectedRow);
                        JOptionPane.showMessageDialog(null, "Course Deleted Successfully!");

                        pstmt.close();
                        conn.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error deleting record: " + ex.getMessage());
                    }
                }
            }
        });

        closeButton.addActionListener(e -> dispose());
    }

    private void loadDataFromDatabase() {
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM courses");

            model.setRowCount(0);

            while (rs.next()) {
                String id = rs.getString("course_id");
                String name = rs.getString("course_name");
                String fees = rs.getString("fees");
                String duration = rs.getString("duration");
                String teacher = rs.getString("instructor");

                model.addRow(new Object[]{id, name, fees, duration, teacher});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Load Error: " + e.getMessage());
        }
    }
}