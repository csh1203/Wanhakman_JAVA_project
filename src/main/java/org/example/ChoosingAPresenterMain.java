package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class ChoosingAPresenterMain {
    public static void main(String args[]){
        ArrayList<Integer> ex = new ArrayList<>();
        ex.add(2);
        ex.add(14);
        new ChoosingAPresenterMain("16", "4", ex);
    }
    public ChoosingAPresenterMain(String allPerson, String presentPerson, ArrayList<Integer> exceptPerson) {
        Color setting = new Color(0x474747);
        // JFrame 생성
        JFrame frame = new JFrame("발표자 정하기");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼 동작 설정
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(null);

        JButton settingBtn = new JButton("설정");
        settingBtn.setBounds(1100, 31, 100, 33);
        settingBtn.setFont(new Font("Noto Sans", Font.BOLD, 24)); // 폰트 및 글자 크기 설정
        settingBtn.setForeground(setting); // 글자 색상 설정

        // 버튼의 배경을 없애기
        settingBtn.setOpaque(false);
        settingBtn.setContentAreaFilled(false);
        settingBtn.setBorderPainted(false);
        settingBtn.setFocusPainted(false);

        settingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ChoosingAPresenterSetting();
            }
        });
        frame.add(settingBtn);

        ImageIcon home = new ImageIcon("img/homeBtn.png");
        JButton homeBtn = new JButton(home);
        homeBtn.setBounds(1190, 24, 45, 45);

        // 버튼의 배경을 없애기
        homeBtn.setOpaque(false);
        homeBtn.setContentAreaFilled(false);
        homeBtn.setBorderPainted(false);
        homeBtn.setFocusPainted(false);
        homeBtn.addActionListener(new ActionListener() {
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
        frame.add(homeBtn);

        JPanel resultFrame = new JPanel();
        resultFrame.setBounds(215, 140, 851, 390);
        Border roundedBorder = BorderFactory.createLineBorder(SettingClass.mainColor, 30, true);
        resultFrame.setBorder(roundedBorder);
        resultFrame.setLayout(null);
        resultFrame.setOpaque(false);
        frame.getContentPane().add(resultFrame);

        JLabel resultLabel = new JLabel();
        Border InnerRoundedBorder = BorderFactory.createLineBorder(Color.WHITE, 30, true);
        resultLabel.setBorder(InnerRoundedBorder);
        resultLabel.setBounds(10, 10, 831, 370);
        resultLabel.setFont(new Font("Noto Sans", Font.PLAIN, 80)); // 폰트 및 글자 크기 설정
        resultLabel.setHorizontalAlignment(JLabel.CENTER); // 텍스트를 가운데에 정렬
        resultLabel.setVerticalAlignment(JLabel.CENTER);
        resultFrame.add(resultLabel);

        JButton makePresenterBtn = new JButton();
        makePresenterBtn.setBorder(roundedBorder);
        makePresenterBtn.setLayout(null);
        makePresenterBtn.setBounds(397, 606, 485, 92);

        JLabel InnerBtn = new JLabel("발표자 뽑기");
        InnerBtn.setBounds(10, 10, 465, 72);
        InnerBtn.setOpaque(true);
        InnerBtn.setBackground(SettingClass.mainColor);
        InnerBtn.setHorizontalAlignment(SwingConstants.CENTER);
        InnerBtn.setVerticalAlignment(SwingConstants.CENTER);
        InnerBtn.setFont(new Font("Noto Sans", Font.BOLD, 25)); // 폰트 및 글자 크기 설정
        InnerBtn.setForeground(Color.WHITE); // 글자 색상 설정
        makePresenterBtn.add(InnerBtn);

        // 버튼의 배경을 없애기
        makePresenterBtn.setOpaque(false);
        makePresenterBtn.setContentAreaFilled(false);
        makePresenterBtn.setFocusPainted(false);

        makePresenterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Integer> randomNumbers = generateRandomNumbers(1, Integer.parseInt(allPerson), Integer.parseInt(presentPerson), exceptPerson);
                resultLabel.setText(randomNumbers.toString().substring(1, randomNumbers.toString().length()-1));
            }
        });
        frame.add(makePresenterBtn);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private static List<Integer> generateRandomNumbers(int min, int max, int count, ArrayList<Integer> exceptPerson) {
        List<Integer> numbers = new ArrayList<>();
        Random rand = new Random();
        while (numbers.size() < count) {
            int randomNum = rand.nextInt(max - min + 1) + min;
            if (!exceptPerson.contains(randomNum) && !numbers.contains(randomNum)) {
                numbers.add(randomNum);
            }
        }
        return numbers;
    }
}
