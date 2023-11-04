package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                new Main();
            }
        });
        frame.add(homeBtn);

        JLabel resultFrame = new JLabel();
        resultFrame.setBounds(215, 140, 851, 390);
        resultFrame.setIcon(new ImageIcon("img/presenterResultFrame.png"));
        frame.getContentPane().add(resultFrame);

        JLabel resultLabel = new JLabel();
        resultLabel.setBounds(0, 134, 851, 123);
        resultLabel.setFont(new Font("Noto Sans", Font.PLAIN, 80)); // 폰트 및 글자 크기 설정
        resultLabel.setHorizontalAlignment(JLabel.CENTER); // 텍스트를 가운데에 정렬
        resultFrame.add(resultLabel);

        ImageIcon makePresenterBtnImg = new ImageIcon("img/makePresenterBtn.png");
        JButton makePresenterBtn = new JButton(makePresenterBtnImg);
        makePresenterBtn.setBounds(397, 606, 485, 92);
        makePresenterBtn.setIcon(new ImageIcon("img/makePresenterBtn.png"));

        // 버튼의 배경을 없애기
        makePresenterBtn.setOpaque(false);
        makePresenterBtn.setContentAreaFilled(false);
        makePresenterBtn.setBorderPainted(false);
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
