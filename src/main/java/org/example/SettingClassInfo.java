package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.sql.SQLException;

public class SettingClassInfo extends JPanel {
    public SettingClassInfo() throws SQLException {
        setBounds(0,0, 1045, 832);

        Border underBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK);
        setLayout(null);

        JPanel directPanel = new JPanel();
        directPanel.setBounds(0, 0, 1045, 73);
        directPanel.setBorder(underBorder);
        directPanel.setLayout(null);
        add(directPanel);

        JButton stuInfo = new JButton("학생 정보");
        stuInfo.setBounds(47, 24, 150, 30);
        SettingClass.customFont(stuInfo, Font.PLAIN, 22);
        stuInfo.setBorderPainted(false);
        stuInfo.setContentAreaFilled(false);
        stuInfo.setFocusPainted(false);
        stuInfo.setOpaque(false);
        directPanel.add(stuInfo);

        JButton seatOrder = new JButton("자리 배치");
        seatOrder.setBounds(235, 24, 150, 30);
        SettingClass.customFont(seatOrder, Font.PLAIN, 22);
        seatOrder.setBorderPainted(false);
        seatOrder.setContentAreaFilled(false);
        seatOrder.setFocusPainted(false);
        seatOrder.setOpaque(false);
        directPanel.add(seatOrder);

        JButton roleDetermind = new JButton("1인 1역");
        roleDetermind.setBounds(423, 24, 150, 30);
        SettingClass.customFont(roleDetermind, Font.PLAIN, 22);
        roleDetermind.setBorderPainted(false);
        roleDetermind.setContentAreaFilled(false);
        roleDetermind.setFocusPainted(false);
        roleDetermind.setOpaque(false);
        directPanel.add(roleDetermind);

        JPanel bottomPanel = new JPanel(new CardLayout());
        bottomPanel.setBounds(0, 73, 1045, 759);
        add(bottomPanel);

        JPanel basePanel = new JPanel();

        SettingStudentInfo panel1 = new SettingStudentInfo();
        SettingSeatOrder panel2 = new SettingSeatOrder();
        SettingDetermindRole panel3 = new SettingDetermindRole();

        bottomPanel.add(basePanel, "basePanel");
        bottomPanel.add(panel1, "Panel 1");
        bottomPanel.add(panel2, "Panel 2");
        bottomPanel.add(panel3, "Panel 3");

        stuInfo.addActionListener(e -> ((CardLayout) bottomPanel.getLayout()).show(bottomPanel, "Panel 1"));
        seatOrder.addActionListener(e -> ((CardLayout) bottomPanel.getLayout()).show(bottomPanel, "Panel 2"));
        roleDetermind.addActionListener(e -> ((CardLayout) bottomPanel.getLayout()).show(bottomPanel, "Panel 3"));
    }
}
