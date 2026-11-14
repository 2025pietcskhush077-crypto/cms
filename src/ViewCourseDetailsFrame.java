import javax.swing.*;
import java.awt.*;

public class ViewCourseDetailsFrame extends JFrame {
    public ViewCourseDetailsFrame(String id, String title, String fees, String duration, String teacher) {
        setTitle("Course Management System - View Course Details");
        setSize(480, 440);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 247, 250));

        JPanel card = new JPanel();
        card.setBounds(30, 20, 400, 365);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(210, 218, 226), 1));
        card.setLayout(null);

        JLabel heading = new JLabel("Course Details (Read-Only)");
        heading.setBounds(25, 15, 340, 25);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(new Color(41, 128, 185));
        card.add(heading);

        addLabelAndField(card, "Course ID:", id, 60);
        addLabelAndField(card, "Course Title:", title, 105);
        addLabelAndField(card, "Fees:", fees, 150);
        addLabelAndField(card, "Duration:", duration, 195);
        addLabelAndField(card, "Teacher:", teacher, 240);

        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(125, 300, 150, 35);
        closeBtn.setBackground(new Color(52, 73, 94));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> dispose());
        card.add(closeBtn);

        add(card);
    }

    private void addLabelAndField(JPanel panel, String labelText, String value, int y) {
        JLabel lbl = new JLabel(labelText);
        lbl.setBounds(25, y, 100, 25);
        lbl.setFont(new Font("Arial", Font.BOLD, 12));
        panel.add(lbl);

        JTextField field = new JTextField(value);
        field.setBounds(125, y, 240, 28);
        field.setEditable(false);
        panel.add(field);
    }
}