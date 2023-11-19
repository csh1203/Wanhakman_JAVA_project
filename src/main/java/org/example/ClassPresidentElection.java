package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClassPresidentElection {
    private JFrame frame;
    private JPanel panelContainer;
    private List<CandidatePanel> panels;
    private JScrollPane scrollPane;
    private List<String> candidateNames; // Added field

    public ClassPresidentElection() throws SQLException {
        frame = new JFrame("회장 선거");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBackground(Color.WHITE);

        JLabel title = new JLabel("후보자 등록");
        SettingClass.customFont(title, Font.BOLD, 36);
        title.setBounds(550, 80, 400, 60);
        frame.add(title);

        panels = new ArrayList<>();
        panelContainer = new JPanel();
        panelContainer.setBackground(Color.WHITE);
        panelContainer.setBorder(null);
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(panelContainer);
        scrollPane.setBounds(442, 165, 396, 400);
        frame.add(scrollPane);

        JButton addBtn = new JButton("+");
        addBtn.setOpaque(true);
        addBtn.setBackground(SettingClass.mainColor);
        SettingClass.customFont(addBtn, Font.BOLD, 45);
        addBtn.setForeground(Color.WHITE);
        addBtn.setHorizontalAlignment(SwingConstants.CENTER);
        addBtn.setVerticalAlignment(SwingConstants.CENTER);
        addBtn.addActionListener(e -> addPanel());
        addBtn.setBounds(442, 624, 190, 59);
        frame.add(addBtn);

        JButton nextBtn = new JButton(">");
        nextBtn.setOpaque(true);
        nextBtn.setBackground(SettingClass.mainColor);
        SettingClass.customFont(nextBtn, Font.BOLD, 45);
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setHorizontalAlignment(SwingConstants.CENTER);
        nextBtn.setVerticalAlignment(SwingConstants.CENTER);
        nextBtn.addActionListener(e -> nextPage());
        nextBtn.setBounds(648, 624, 190, 59);
        frame.add(nextBtn);

        candidateNames = new ArrayList<>(); // Initialize the list

        getStudent();

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void nextPage() {
        if (areAllTextFieldsFilled()) {
            // Create an instance of ClassPresidentElectionVoting and pass the candidateNames
            try {
                new ClassPresidentElectionInput();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "후보자 이름을 적어주세요.");
        }
    }

    private boolean areAllTextFieldsFilled() {
        for (CandidatePanel panel : panels) {
            if (!panel.isTextFieldFilled()) {
                return false;
            }
        }
        return true;
    }

    private void addPanel() {
        if (panels.size() < 2) {
            CandidatePanel panel = new CandidatePanel(panels.size() + 1);
            panelContainer.add(panel);
            panels.add(panel);

            frame.revalidate();
            frame.repaint();

            // Scroll
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            verticalScrollBar.setValue(verticalScrollBar.getMaximum());
        }
    }

    private JPanel getBoxPanel() {
        return panelContainer;
    }

    private class CandidatePanel extends JPanel {
        private JTextField textField;

        public CandidatePanel(int number) {
            setLayout(new BorderLayout());

            JButton saveBtn = new JButton(Integer.toString(number) + ".");
            saveBtn.setOpaque(true);
            saveBtn.setBackground(SettingClass.mainColor);
            SettingClass.customFont(saveBtn, Font.BOLD, 25);
            saveBtn.setForeground(Color.WHITE);
            saveBtn.setHorizontalAlignment(SwingConstants.CENTER);
            saveBtn.setVerticalAlignment(SwingConstants.CENTER);
            saveBtn.setPreferredSize(new Dimension(70, 70));

            textField = new JTextField();
            textField.setPreferredSize(new Dimension(70, 70));

            add(saveBtn, BorderLayout.WEST);
            add(textField, BorderLayout.CENTER);
        }

        public boolean isTextFieldFilled() {
            return !textField.getText().trim().isEmpty();
        }
    }

    private void getStudent() throws SQLException {
        // Util class content reference
        Connection connection = Util.getConnection();

        // Statement creation and query execution
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM student");

        while (result.next()) {
            candidateNames.add(result.getString("student_name"));
        }

        // Resource release
        result.close();
        statement.close();
        connection.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ClassPresidentElection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
