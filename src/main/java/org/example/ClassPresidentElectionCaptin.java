package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ClassPresidentElectionCaptin {
    private JFrame frame;
    private String captin;

    public ClassPresidentElectionCaptin() throws SQLException {
        frame = new JFrame("회장 선거");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(SettingClass.mainColor);
        mainPanel.setBounds(280, 191, 720, 450);
        mainPanel.setLayout(null);

        JLabel title = new JLabel( "'" + captin + "'" + "학생");
        SettingClass.customFont(title, Font.BOLD, 36);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setBackground(null);
        title.setBounds(480, 280, 309, 113);
        frame.add(title);

        JLabel subtitle = new JLabel("당선을 축하합니다!");
        SettingClass.customFont(subtitle, Font.BOLD, 36);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        subtitle.setVerticalAlignment(SwingConstants.CENTER);
        subtitle.setForeground(Color.WHITE);
        subtitle.setBackground(null);
        subtitle.setBounds(480, 350, 309, 113);
        frame.add(subtitle);

        JButton addBtn = new JButton("돌아가기");
        addBtn.setOpaque(true);
        addBtn.setBackground(Color.WHITE);
        SettingClass.customFont(addBtn, Font.BOLD, 25);
        addBtn.setHorizontalAlignment(SwingConstants.CENTER);
        addBtn.setVerticalAlignment(SwingConstants.CENTER);
        addBtn.addActionListener(e -> {
            try {
                new Main();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        addBtn.setBounds(537, 521, 205, 53);
        frame.add(addBtn);

        frame.getContentPane().add(mainPanel);

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}