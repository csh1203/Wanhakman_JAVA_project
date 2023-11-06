package org.example;

import javax.swing.*;
import java.awt.*;

public class SettingMyClass extends JPanel {
    public SettingMyClass() {
        JLabel title = new JLabel("나의 교실");
        title.setBounds(113, 119, 119, 36);
        title.setFont(new Font("Noto Sans", Font.PLAIN, 16)); // 폰트 및 글자 크기 설정
    }
}
