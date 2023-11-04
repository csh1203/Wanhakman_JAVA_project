package org.example;

import javax.swing.*;
import java.awt.*;

public class ClassPresidentElection {
    public ClassPresidentElection() {
        // JFrame 생성
        JFrame frame = new JFrame("회장 선거");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼 동작 설정
        frame.setBackground(Color.WHITE);

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
