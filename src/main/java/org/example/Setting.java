package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Setting {
    static JFrame frame;

    public Setting() throws SQLException {
        // JFrame 생성
        frame = new JFrame("설정");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼 동작 설정
        frame.setBackground(Color.WHITE);

        Border leftBorder = BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK); // 왼쪽 테두리 생성
        JPanel directPanel = new JPanel();
        directPanel.setBounds(0, 0, 235, 832);
        directPanel.setLayout(null);
        directPanel.setBorder(leftBorder);
        frame.getContentPane().add(directPanel);

        JButton backBtn = new JButton();
        backBtn.setBounds(22, 20, 50, 50);
        backBtn.setIcon(new ImageIcon("img/back_btn.png"));
        backBtn.setOpaque(false);
        backBtn.setContentAreaFilled(false);
        backBtn.setFocusPainted(false);
        backBtn.setBorderPainted(false);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new Main();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        directPanel.add(backBtn);

        int btnWidth = 200;
        JPanel directBtns = new JPanel();
        directBtns.setBounds(30, 137, btnWidth, 210);
        directBtns.setLayout(null);
        directPanel.add(directBtns);

        JButton directBtn1 = new JButton("학급 정보");
        directBtn1.setBounds(0, 0, btnWidth, 30);
        directBtn1.setOpaque(false);
        directBtn1.setContentAreaFilled(false);
        directBtn1.setFocusPainted(false);
        directBtn1.setBorderPainted(false);
        directBtn1.setHorizontalAlignment(SwingConstants.RIGHT);
        SettingClass.customFont(directBtn1, Font.PLAIN, 25);
        directBtns.add(directBtn1);

        JButton directBtn2 = new JButton("테마컬러");
        directBtn2.setBounds(0, 60, btnWidth, 30);
        directBtn2.setOpaque(false);
        directBtn2.setContentAreaFilled(false);
        directBtn2.setFocusPainted(false);
        directBtn2.setBorderPainted(false);
        directBtn2.setHorizontalAlignment(SwingConstants.RIGHT);
        SettingClass.customFont(directBtn2, Font.PLAIN, 25);
        directBtns.add(directBtn2);

        JButton directBtn3 = new JButton("나의 교실");
        directBtn3.setBounds(0, 120, btnWidth, 30);
        directBtn3.setOpaque(false);
        directBtn3.setContentAreaFilled(false);
        directBtn3.setFocusPainted(false);
        directBtn3.setBorderPainted(false);
        directBtn3.setHorizontalAlignment(SwingConstants.RIGHT);
        SettingClass.customFont(directBtn3, Font.PLAIN, 25);
        directBtns.add(directBtn3);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(235, 0, 1045, 832);

        directBtn1.addActionListener(e -> {
            rightPanel.removeAll();
            rightPanel.revalidate();
            rightPanel.repaint();

            SettingClassInfo panel1 = null;
            try {
                panel1 = new SettingClassInfo();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            rightPanel.add(panel1);
        });
        directBtn2.addActionListener(e -> {
            rightPanel.removeAll();
            rightPanel.revalidate();
            rightPanel.repaint();

            SettingThemeColor panel2 = null;
            try {
                panel2 = new SettingThemeColor();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            rightPanel.add(panel2);
        });
        directBtn3.addActionListener(e -> {
            rightPanel.removeAll();
            rightPanel.revalidate();
            rightPanel.repaint();
            SettingMyClass panel3 = null;
            try {
                panel3 = new SettingMyClass();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            rightPanel.add(panel3);
        });

        frame.add(rightPanel);

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}