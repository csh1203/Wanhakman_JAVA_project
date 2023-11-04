package org.example;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        JLabel backgroundImg = new JLabel();
        backgroundImg.setBounds(344, 155, 591, 482);
        backgroundImg.setIcon(new ImageIcon("img/choosingAPresenter_backImg.png"));
        frame.getContentPane().add(backgroundImg);

        JLabel allPerson = new JLabel("학생 수");
        allPerson.setBounds(88, 90, 80, 34);
        allPerson.setFont(new Font("Noto Sans", Font.PLAIN, 25));
        backgroundImg.add(allPerson);

        JTextField inputPerson = new JTextField(3);
        setNumberOnlyFilter(inputPerson); // 텍스트 필드에 숫자만 입력되도록 필터 설정
        inputPerson.setBounds(88, 142, 119, 29);
        inputPerson.setFont(new Font("Noto Sans", Font.PLAIN, 21)); // 폰트 및 글자 크기 설정
        backgroundImg.add(inputPerson);

        JLabel myung = new JLabel("명");
        myung.setBounds(219, 142, 25, 29);
        myung.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        myung.setForeground(Color.black); // 글자 색상 설정
        backgroundImg.add(myung);

        JLabel line = new JLabel();
        line.setBounds(88, 209, 400, 1);
        line.setBackground(Color.GRAY);
        line.setOpaque(true);
        backgroundImg.add(line);

        JLabel allDivision = new JLabel("분단 수");
        allDivision.setBounds(88, 241, 100, 34);
        allDivision.setFont(new Font("Noto Sans", Font.PLAIN, 25));
        backgroundImg.add(allDivision);

        JTextField inputDivision = new JTextField(3);
        setNumberOnlyFilter(inputDivision); // 텍스트 필드에 숫자만 입력되도록 필터 설정
        inputDivision.setBounds(88, 290, 119, 29);
        inputDivision.setFont(new Font("Noto Sans", Font.PLAIN, 21)); // 폰트 및 글자 크기 설정
        backgroundImg.add(inputDivision);

        JLabel division = new JLabel("분단");
        division.setBounds(219, 290, 50, 29);
        division.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        division.setForeground(Color.black); // 글자 색상 설정
        backgroundImg.add(division);

        ImageIcon checkBtnImg = new ImageIcon("img/choosingPresenterCheck.png");
        JButton checkBtn = new JButton(checkBtnImg);

        // 버튼의 배경을 없애기
        checkBtn.setOpaque(false);
        checkBtn.setContentAreaFilled(false);
        checkBtn.setBorderPainted(false);

        checkBtn.setBounds(219, 384, 152, 46);
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
                    new PickASeatMain(Integer.parseInt(people), Integer.parseInt(division));
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
