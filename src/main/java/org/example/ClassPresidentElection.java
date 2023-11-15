package org.example;

import javax.swing.*;
import java.awt.*;

public class ClassPresidentElection {
    private JFrame frame;

    public ClassPresidentElection() {

        frame = new JFrame("회장 선거");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBackground(Color.WHITE);

        JLabel title = new RoundedLabel("후보자 등록");
        SettingClass.customFont(title, Font.BOLD, 36);
        title.setForeground(Color.BLACK);
        title.setBounds(550, 80, 400, 60);
        frame.add(title);

        JLabel panel1 = new RoundedLabel("1.");
        panel1.setOpaque(true);
        panel1.setBackground(Color.BLUE);
        panel1.setBounds(100, 100, 100, 100);
        SettingClass.customFont(panel1, Font.PLAIN, 30);
        frame.add(panel1);

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private class RoundedLabel extends JLabel {
        public RoundedLabel(String text) {
            super(text);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClassPresidentElection();
            }
        });
    }
}
