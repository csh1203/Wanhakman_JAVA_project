package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChoosingAPresenterSetting {
    public static void main(String args[]){
        new ChoosingAPresenterSetting();
    }
    public ChoosingAPresenterSetting() {
        Color backgroud = new Color(0xA1A1A1);
        Dimension dim = new Dimension(1280, 832);

        // JFrame 생성
        JFrame frame = new JFrame("발표자 정하기");
        frame.setBounds(100, 100, 1280, 832);
        frame.setPreferredSize(dim);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼 동작 설정
        frame.getContentPane().setBackground(backgroud);
        frame.setLayout(null);

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

        JLabel person = new JLabel("학생 수");
        person.setBounds(68, 70, 80, 34);
        person.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        person.setForeground(Color.black); // 글자 색상 설정
        backgroundImg.add(person);

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

        JLabel presenterCnt = new JLabel("발표 인원");
        presenterCnt.setBounds(276, 70, 105, 33);
        presenterCnt.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        backgroundImg.add(presenterCnt);

        JTextField inputPresenterCnt = new JTextField(3);
        setNumberOnlyFilter(inputPresenterCnt); // 텍스트 필드에 숫자만 입력되도록 필터 설정
        inputPresenterCnt.setBounds(276, 122, 119, 29);
        inputPresenterCnt.setFont(new Font("Noto Sans", Font.PLAIN, 21)); // 폰트 및 글자 크기 설정
        backgroundImg.add(inputPresenterCnt);

        JLabel myung2 = new JLabel("명");
        myung2.setBounds(407, 122, 25, 29);
        myung2.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        myung2.setForeground(Color.black); // 글자 색상 설정
        backgroundImg.add(myung2);

        JLabel line = new JLabel();
        line.setBounds(68, 189, 400, 1);
        line.setBackground(Color.GRAY);
        line.setOpaque(true);
        backgroundImg.add(line);

        JLabel exceptPerson = new JLabel("제외할 번호");
        exceptPerson.setBounds(68, 221, 135, 31);
        exceptPerson.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        exceptPerson.setForeground(backgroud);
        backgroundImg.add(exceptPerson);

        JCheckBox exceptCheck = new JCheckBox();
        exceptCheck.setBounds(199, 228, 20, 20);
        exceptCheck.setUI(new CustomCheckboxUI());
        backgroundImg.add(exceptCheck);

        JTextField inputExcept = new JTextField(3);
        inputExcept.setBounds(68, 270, 119, 29);
        inputExcept.setBorder(new LineBorder(backgroud));
        inputExcept.setEnabled(false);
        PlainDocument doc = (PlainDocument) inputExcept.getDocument();
        doc.setDocumentFilter(new NumberWithCommaDocumentFilter());
//        setNumberOnlyFilter(inputExcept); // 텍스트 필드에 숫자만 입력되도록 필터 설정
        inputExcept.setFont(new Font("Noto Sans", Font.PLAIN, 21)); // 폰트 및 글자 크기 설정
        backgroundImg.add(inputExcept);

        JLabel bun = new JLabel("번");
        bun.setBounds(199, 270, 25, 29);
        bun.setFont(new Font("Noto Sans", Font.PLAIN, 25)); // 폰트 및 글자 크기 설정
        bun.setForeground(backgroud); // 글자 색상 설정
        backgroundImg.add(bun);

        exceptCheck.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    exceptPerson.setForeground(Color.BLACK); // 체크 박스가 선택되었을 때 라벨의 글씨 색을 파란색으로 변경
                    inputExcept.setBorder(new LineBorder(Color.BLACK));
                    bun.setForeground(Color.BLACK);
                    inputExcept.setEnabled(true);
                } else {
                    exceptPerson.setForeground(backgroud); // 체크 박스가 선택 해제되었을 때 라벨의 글씨 색을 검은색으로 변경
                    inputExcept.setBorder(new LineBorder(backgroud));
                    bun.setForeground(backgroud);
                    inputExcept.setEnabled(false);
                    inputExcept.setText("");
                }

            }
        });

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
                String Allperson = inputPerson.getText();
                String PresenterPerson = inputPresenterCnt.getText();
                String ExceptPerson = inputExcept.getText();

                ArrayList<Integer> ExpectPersonArr = new ArrayList<>();
                if(ExceptPerson.length() == 0){
                    ExpectPersonArr.add(-1);
                }else{
                    String ch = "";
                    for(int i = 0; i<ExceptPerson.length(); i++){
                        if(ExceptPerson.charAt(i) == ','){
                            ExpectPersonArr.add(Integer.parseInt(ch));
                            ch = "";
                        }else{
                            ch += ExceptPerson.charAt(i);
                        }
                    }
                    ExpectPersonArr.add(Integer.parseInt(ch));
                }

                if (Allperson.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "학생 수를 입력해주세요.");
                }else if(PresenterPerson.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "발표 인원을 입력해주세요.");
                }else if(Integer.parseInt(Allperson) < Integer.parseInt(PresenterPerson)){
                    JOptionPane.showMessageDialog(frame, "발표 인원을 다시 입력해주세요.");
                }else if(Integer.parseInt(Allperson) < Integer.parseInt(PresenterPerson) + ExpectPersonArr.size()){
                    JOptionPane.showMessageDialog(frame, "학생 수를 다시 입력해주세요.");
                }else{
                    frame.dispose();
                    new ChoosingAPresenterMain(Allperson, PresenterPerson, ExpectPersonArr);
                }
            }
        });

        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setLayout(null);
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
class CustomCheckboxUI extends BasicCheckBoxUI {
    Color backgroud = new Color(0xA1A1A1);
    public static ComponentUI createUI(JComponent c) {
        return new CustomCheckboxUI();
    }

    @Override
    public synchronized void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        Graphics2D g2 = (Graphics2D) g;

        // 배경을 투명으로 설정하여 배경색을 없앱니다.
        b.setOpaque(false);

        // 테두리 색을 선택 상태에 따라 조절
        if (model.isSelected()) {
            g2.setColor(Color.black); // 체크 박스가 선택되었을 때 테두리 색을 파란색으로 설정
        } else {
            g2.setColor(backgroud); // 체크 박스가 선택되지 않았을 때 테두리 색을 검은색으로 설정
        }

        // 체크 박스 테두리를 그립니다.
        g2.drawRect(0, 0, b.getWidth() - 1, b.getHeight() - 1);

        // 체크 박스 내부에 체크가 되어있을 경우 체크 표시를 그립니다.
        if (model.isSelected()) {
            // 커스텀 아이콘을 그립니다.
            Icon checkIcon = new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    g.setColor(Color.BLACK); // 체크 색상을 검은색으로 설정
                    g.drawLine(x, y + getIconHeight() / 2, x + getIconWidth() / 3, y + getIconHeight());
                    g.drawLine(x + getIconWidth() / 3, y + getIconHeight(), x + getIconWidth(), y);
                }

                @Override
                public int getIconWidth() {
                    return 15; // 아이콘의 가로 크기 설정
                }

                @Override
                public int getIconHeight() {
                    return 15; // 아이콘의 세로 크기 설정
                }
            };

            // 커스텀 아이콘을 그립니다.
            checkIcon.paintIcon(c, g, 3, 3);
        }
    }
}

class NumberWithCommaDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);

        if (isValidInput(sb.toString())) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);

        if (isValidInput(sb.toString())) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isValidInput(String input) {
        return input.matches("^[0-9,]*$");
    }
}


