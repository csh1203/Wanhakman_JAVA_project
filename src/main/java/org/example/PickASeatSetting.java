package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class PickASeatSetting {
    public static void main(String args[]){
        new PickASeatSetting();
    }
    PickASeatSetting(){
        Color backgroud = new Color(0xA1A1A1);

        // JFrame 생성
        JFrame frame = new JFrame("자리 뽑기");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼 동작 설정
        frame.setBackground(Color.WHITE);
        frame.getContentPane().setBackground(backgroud);
        frame.setLayout(null);

        JPanel backtheme = new JPanel();
        backtheme.setLayout(null);
        backtheme.setBounds(344, 155, 591, 482);
        Border roundedBorder = BorderFactory.createLineBorder(SettingClass.mainColor, 30, true);
        backtheme.setBorder(roundedBorder);
        backtheme.setOpaque(false);
        frame.getContentPane().add(backtheme);

        JPanel backwhite = new JPanel();
        backwhite.setLayout(null);
        backwhite.setBounds(10, 10, 571, 462);
        Border InnerRoundedBorder = BorderFactory.createLineBorder(Color.WHITE, 30, true);
        backwhite.setBorder(InnerRoundedBorder);
        backwhite.setOpaque(false);
        backtheme.add(backwhite);

        JPanel backgroundImg = new JPanel();
        backgroundImg.setLayout(null);
        backgroundImg.setBounds(10, 10, 551, 442);
        backgroundImg.setOpaque(true);
        backgroundImg.setBackground(Color.WHITE);
        backwhite.add(backgroundImg);

        JLabel allPerson = new JLabel("학생 수");
        allPerson.setBounds(68, 70, 80, 34);
        allPerson.setFont(new Font("Noto Sans", Font.PLAIN, 25));
        backgroundImg.add(allPerson);

        JTextField inputPerson = new JTextField(3);
        setNumberOnlyFilter(inputPerson); // 텍스트 필드에 숫자만 입력되도록 필터 설정
        inputPerson.setBounds(68, 122, 119, 29);
        inputPerson.setFont(new Font("Noto Sans", Font.PLAIN, 21)); // 폰트 및 글자 크기 설정
        backgroundImg.add(inputPerson);

        JLabel myung = new JLabel("명");
        myung.setBounds(199, 122, 25, 29);
        myung.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        myung.setForeground(Color.black); // 글자 색상 설정
        backgroundImg.add(myung);

        JLabel line = new JLabel();
        line.setBounds(68, 189, 400, 1);
        line.setBackground(Color.GRAY);
        line.setOpaque(true);
        backgroundImg.add(line);

        JLabel allDivision = new JLabel("분단 수");
        allDivision.setBounds(68, 221, 100, 34);
        allDivision.setFont(new Font("Noto Sans", Font.PLAIN, 25));
        backgroundImg.add(allDivision);

        JTextField inputDivision = new JTextField(3);
        setNumberOnlyFilter(inputDivision); // 텍스트 필드에 숫자만 입력되도록 필터 설정
        inputDivision.setBounds(68, 270, 119, 29);
        inputDivision.setFont(new Font("Noto Sans", Font.PLAIN, 21)); // 폰트 및 글자 크기 설정
        backgroundImg.add(inputDivision);

        JLabel division = new JLabel("분단");
        division.setBounds(199, 270, 50, 29);
        division.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        division.setForeground(Color.black); // 글자 색상 설정
        backgroundImg.add(division);

        JButton checkBtn = new JButton();

        // 버튼의 배경을 없애기
        checkBtn.setContentAreaFilled(false);
        Border buttonroundedBorder = BorderFactory.createLineBorder(SettingClass.mainColor, 20, true);
        checkBtn.setBorder(buttonroundedBorder);
        checkBtn.setLayout(null);
        checkBtn.setBounds(199, 364, 152, 46);

        JLabel InnerBtn = new JLabel("확인");
        InnerBtn.setLayout(null);
        InnerBtn.setHorizontalAlignment(SwingConstants.CENTER);
        InnerBtn.setVerticalAlignment(SwingConstants.CENTER);
        InnerBtn.setBounds(10, 10, 132, 26);
        InnerBtn.setOpaque(true);
        InnerBtn.setBackground(SettingClass.mainColor);
        InnerBtn.setFont(new Font("Noto Sans", Font.BOLD, 25)); // 폰트 및 글자 크기 설정
        InnerBtn.setForeground(Color.WHITE);
        checkBtn.add(InnerBtn);

        backgroundImg.add(checkBtn);

        checkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String people = inputPerson.getText();
                String division = inputDivision.getText();

                if(people == ""){
                    JOptionPane.showMessageDialog(frame, "학생 수를 입력해주세요!");
                }else if(division == ""){
                    JOptionPane.showMessageDialog(frame, "분단 수를 입력해주세요!");
                }else{
                    frame.dispose();
//                    new PickASeatMain(Integer.parseInt(people), Integer.parseInt(division));
                    try {
                        new PickASeatMain();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    private static void setNumberOnlyFilter(JTextField textField) {
        PlainDocument doc = (PlainDocument) textField.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (isNumber(string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isNumber(text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean isNumber(String text) {
                return text != null && text.matches("\\d*");
            }
        });
    }
}
