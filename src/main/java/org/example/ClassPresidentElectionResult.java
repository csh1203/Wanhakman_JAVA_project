package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ClassPresidentElectionResult {
    private JFrame frame;

    public ClassPresidentElectionResult() throws SQLException {
        frame = new JFrame("회장 선거");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(SettingClass.mainColor);
        mainPanel.setBounds(40, 25, 1180, 732);

        frame.getContentPane().add(mainPanel);

        JButton addBtn = new JButton("+");
        addBtn.setOpaque(true);
        addBtn.setBackground(Color.WHITE);
        SettingClass.customFont(addBtn, Font.BOLD, 45);
        addBtn.setForeground(SettingClass.mainColor);
        addBtn.setHorizontalAlignment(SwingConstants.CENTER);
        addBtn.setVerticalAlignment(SwingConstants.CENTER);
        addBtn.addActionListener(e -> {
            try {
                new ClassPresidentElectionCaptin();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        addBtn.setBounds(442, 624, 380, 59);
        frame.add(addBtn);

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ClassPresidentElectionCaptin();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}