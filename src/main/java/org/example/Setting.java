package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Setting {
    public static void main(String args[]){

        new Setting();
    }
    public Setting(){
        // JFrame 생성
        JFrame frame = new JFrame("설정");
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
        directBtn1.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        directBtns.add(directBtn1);

        JButton directBtn2 = new JButton("테마컬러");
        directBtn2.setBounds(0, 60, btnWidth, 30);
        directBtn2.setOpaque(false);
        directBtn2.setContentAreaFilled(false);
        directBtn2.setFocusPainted(false);
        directBtn2.setBorderPainted(false);
        directBtn2.setHorizontalAlignment(SwingConstants.RIGHT);
        directBtn2.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        directBtns.add(directBtn2);

        JButton directBtn3 = new JButton("레이아웃 수정");
        directBtn3.setBounds(0, 120, btnWidth, 30);
        directBtn3.setOpaque(false);
        directBtn3.setContentAreaFilled(false);
        directBtn3.setFocusPainted(false);
        directBtn3.setBorderPainted(false);
        directBtn3.setHorizontalAlignment(SwingConstants.RIGHT);
        directBtn3.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        directBtns.add(directBtn3);

        JButton directBtn4 = new JButton("나의 교실");
        directBtn4.setBounds(0, 180, btnWidth, 30);
        directBtn4.setOpaque(false);
        directBtn4.setContentAreaFilled(false);
        directBtn4.setFocusPainted(false);
        directBtn4.setBorderPainted(false);
        directBtn4.setHorizontalAlignment(SwingConstants.RIGHT);
        directBtn4.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        directBtns.add(directBtn4);

        JPanel rightPanel = new JPanel(new CardLayout());
        rightPanel.setBounds(235, 0, 1045, 832);

        JPanel basePanel = new JPanel();

        SettingClassInfo panel1 = new SettingClassInfo();
        SettingThemeColor panel2 = new SettingThemeColor();
        SettingEditLayout panel3 = new SettingEditLayout();
        SettingMyClass panel4 = new SettingMyClass();


        rightPanel.add(basePanel, "basePanel");
        rightPanel.add(panel1, "Panel 1");
        rightPanel.add(panel2, "Panel 2");
        rightPanel.add(panel3, "Panel 3");
        rightPanel.add(panel4, "Panel 4");

        directBtn1.addActionListener(e -> ((CardLayout) rightPanel.getLayout()).show(rightPanel, "Panel 1"));
        directBtn2.addActionListener(e -> ((CardLayout) rightPanel.getLayout()).show(rightPanel, "Panel 2"));
        directBtn3.addActionListener(e -> ((CardLayout) rightPanel.getLayout()).show(rightPanel, "Panel 3"));
        directBtn4.addActionListener(e -> ((CardLayout) rightPanel.getLayout()).show(rightPanel, "Panel 4"));

        frame.add(rightPanel);

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}