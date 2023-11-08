package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.nio.channels.FileLock;
import java.sql.*;
import java.util.*;
import java.util.List;

public class PickASeatMain {
    JComboBox<String> jComboBox;
    int people;
    int division;
    String firstSelect;
    JFrame frame;
    JLabel[] tables;
    CustomToggleButton[] customToggleButtons;
    JLabel[] InnerLabel;
    ArrayList<Integer> fixedNumbers;
    Border roundedBorder = BorderFactory.createLineBorder(SettingClass.mainColor, 30, true);
    JPanel seat = new JPanel();
    ArrayList<Integer> seatOrder;
    public static void main(String args[]) throws SQLException {

        new PickASeatMain(0);
    }
    PickASeatMain(int index) throws SQLException {
        String[] classOption = getClassOption();

        Color setting = new Color(0x474747);

        // JFrame 생성
        frame = new JFrame("자리 뽑기");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼 동작 설정
        frame.setLayout(null);


        jComboBox = new JComboBox<>(classOption);
        jComboBox.setBounds(47, 36, 200, 40);
        jComboBox.setFont(new Font("Noto Sans", Font.BOLD, 24));
        jComboBox.setUI(new CustomComboBoxUI());
        jComboBox.setSelectedItem(classOption[index]);
        jComboBox.setOpaque(false);
        frame.add(jComboBox);

        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = jComboBox.getSelectedItem().toString();
                try {
                    getClassInfo(selectedItem);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        getClassInfo(classOption[index]);

        int seatHeight = (int)(Math.ceil(people / (division * 2.0)));
        seatHeight = seatHeight * 75 + (seatHeight - 1) * 16;
//        seat = new JPanel();
        seat.setLayout(new GridLayout(1, division));
        seat.setBounds(0, 259, 1280, seatHeight);
        frame.add(seat);

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
                try {
                    new PickASeatSetting(jComboBox.getSelectedItem().toString());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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

        JLabel teachingDeskLabel = new JLabel();

        teachingDeskLabel.setBorder(roundedBorder);
        teachingDeskLabel.setBounds(463, 104, 355,95);

        JLabel InnerTeachingDest = new JLabel("교탁");
        InnerTeachingDest.setFont(new Font("Noto Sans", Font.BOLD, 40)); // 폰트 및 글자 크기 설정
        InnerTeachingDest.setBounds(10, 10, 335, 75);
        InnerTeachingDest.setForeground(Color.WHITE);
        InnerTeachingDest.setHorizontalAlignment(SwingConstants.CENTER);
        InnerTeachingDest.setVerticalAlignment(SwingConstants.CENTER);
        InnerTeachingDest.setOpaque(true);
        InnerTeachingDest.setBackground(SettingClass.mainColor);
        teachingDeskLabel.add(InnerTeachingDest);

        frame.add(teachingDeskLabel);

        // 고정할 숫자의 위치를 ArrayList로 지정
        fixedNumbers = new ArrayList<>();

        JButton seatChangeBtn = new JButton();
        Border RoundedBorder = BorderFactory.createLineBorder(SettingClass.mainColor, 15, true);
        seatChangeBtn.setBorder(RoundedBorder);
        seatChangeBtn.setLayout(null);
        seatChangeBtn.setBounds(942, 710, 193, 53);
        seatChangeBtn.setOpaque(false);
        seatChangeBtn.setContentAreaFilled(false);
        seatChangeBtn.setFocusPainted(false);

        JLabel InnerSeatChangeBtn = new JLabel("자리 바꾸기");
        InnerSeatChangeBtn.setBounds(10, 10, 173, 33);
        InnerSeatChangeBtn.setOpaque(true);
        InnerSeatChangeBtn.setBackground(SettingClass.mainColor);
        InnerSeatChangeBtn.setFont(new Font("Noto Sans", Font.BOLD, 25));
        InnerSeatChangeBtn.setForeground(Color.WHITE);
        InnerSeatChangeBtn.setHorizontalAlignment(SwingConstants.CENTER);
        InnerSeatChangeBtn.setVerticalAlignment(SwingConstants.CENTER);
        seatChangeBtn.add(InnerSeatChangeBtn);

        frame.add(seatChangeBtn);


        seatChangeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] numbers = new int[people]; // 원하는 숫자 범위
                for(int i = 0; i<people; i++){
//                    System.out.println(Integer.parseInt(InnerLabel[i].getText()));
                    numbers[i] = Integer.parseInt(InnerLabel[i].getText());
                }

                // 숫자를 섞는 함수 호출
                List<Integer> shuffledNumbers = shuffleNumbers(numbers, fixedNumbers);

                // 섞인 숫자 출력
                for (int i = 0; i<shuffledNumbers.size(); i++) {
                    InnerLabel[i].setText(shuffledNumbers.get(i).toString());
                    customToggleButtons[i].setText(shuffledNumbers.get(i).toString());
                }
            }
        });

        JButton seatSaveBtn = new JButton();
        seatSaveBtn.setBounds(1145, 710,93, 53);
        seatSaveBtn.setLayout(null);
        seatSaveBtn.setBorder(RoundedBorder);
        seatSaveBtn.setOpaque(false);
        seatSaveBtn.setContentAreaFilled(false);
        seatSaveBtn.setFocusPainted(false);

        JLabel InnerSeatSaveBtn = new JLabel("저장");
        InnerSeatSaveBtn.setBounds(10, 10, 73, 33);
        InnerSeatSaveBtn.setOpaque(true);
        InnerSeatSaveBtn.setBackground(SettingClass.mainColor);
        InnerSeatSaveBtn.setFont(new Font("Noto Sans", Font.BOLD, 25));
        InnerSeatSaveBtn.setForeground(Color.WHITE);
        InnerSeatSaveBtn.setHorizontalAlignment(SwingConstants.CENTER);
        InnerSeatSaveBtn.setVerticalAlignment(SwingConstants.CENTER);
        seatSaveBtn.add(InnerSeatSaveBtn);

        seatSaveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] numbers = new int[people]; // 원하는 숫자 범위
                for(int i = 0; i<people; i++){
                    numbers[i] = Integer.parseInt(InnerLabel[i].getText());
                }
                try {
                    setTable(numbers, jComboBox.getSelectedItem().toString());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.add(seatSaveBtn);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setTable(int[] numbers, String className) throws SQLException {
        Connection connection = Util.getConnection();

        for(int i = 0; i<numbers.length; i++){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE seat SET seat_order = ? WHERE class_name = ? AND student_id = ?");
            preparedStatement.setInt(1, numbers[i]);
            preparedStatement.setString(2, className);
            preparedStatement.setInt(3, i+1);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }

        connection.close();

        JOptionPane.showMessageDialog(frame,"저장되었습니다.");
    }
    public void mainTables() {
        removeAllComponents(seat);

        int seatHeight = (int)(Math.ceil(people / (division * 2.0)));
        seatHeight = seatHeight * 75 + (seatHeight - 1) * 16;
//        seat = new JPanel();
        seat.setLayout(new GridLayout(1, division));
        seat.setBounds(0, 259, 1280, seatHeight);

        tables = new JLabel[people];
        customToggleButtons = new CustomToggleButton[people];
        InnerLabel = new JLabel[people];

        int[] divisionCnt = new int[division];
        int Remain = people % (division * 2);
        for(int i = 0; i<division; i++){
            int plus = 0;
            if(Remain - 2 >= 0) {
                plus = 2;
                Remain -= 2;
            }else if(Remain - 1 >= 0){
                plus = 1;
                Remain -= 1;
            }
            int repeat = (people / (division * 2)) * 2 + plus;
            divisionCnt[i] = repeat;
        }
        for(int i = 0; i<divisionCnt.length; i++){
            int cnt = 0;
            int repeat = divisionCnt[i];
            int height = (int)(Math.ceil(repeat / 2.0));
            height = height * 75 + (height - 1) + 16;

            int margin = (1200 / division - 259) / 2;

            JPanel divisions = new JPanel();
            divisions.setLayout(new GridLayout((int)(Math.ceil(repeat / 2.0)),2));
            divisions.setBounds(margin, 0, 259, height);

            JLabel l = new JLabel();
            l.setSize(259, height);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setOpaque(true);

            for(int j = 0; j<(int)(Math.ceil(repeat / 2.0)); j++){ // 0 1 2
                int index = (i + 1) * 2 - 1;
                for(int k = index; k<=index + 1; k++){
                    cnt++;
                    if(cnt > repeat) break;
                    int tableIndex = (k + (j * (2 * division))) - 1;
                    String tableNumber = seatOrder.get(tableIndex).toString();
                    tables[Integer.parseInt(tableNumber) - 1] = new JLabel();
                    tables[Integer.parseInt(tableNumber) - 1].setOpaque(true);
                    tables[Integer.parseInt(tableNumber) - 1].setLayout(null);

                    JLabel InnerTable = new JLabel();
                    InnerTable.setBounds(3, 3, 120, 69);
                    InnerTable.setBorder(roundedBorder);
                    tables[Integer.parseInt(tableNumber) - 1].add(InnerTable);

                    JLabel InnerTextTable = new JLabel();
                    InnerTextTable.setLayout(null);
                    InnerTextTable.setBounds(3,3, 114, 63);
                    Border InnerRoundedBorder = BorderFactory.createLineBorder(Color.WHITE, 30, true);
                    InnerTextTable.setBorder(InnerRoundedBorder);

                    InnerLabel[Integer.parseInt(tableNumber) - 1] = new JLabel(tableNumber);
                    InnerLabel[Integer.parseInt(tableNumber) - 1].setBounds(5,5, 104, 53);
                    InnerLabel[Integer.parseInt(tableNumber) - 1].setOpaque(true);
                    InnerLabel[Integer.parseInt(tableNumber) - 1].setBackground(Color.WHITE);
                    InnerLabel[Integer.parseInt(tableNumber) - 1].setHorizontalAlignment(SwingConstants.CENTER);
                    InnerLabel[Integer.parseInt(tableNumber) - 1].setVerticalAlignment(SwingConstants.CENTER);
                    InnerLabel[Integer.parseInt(tableNumber) - 1].setForeground(SettingClass.mainColor);
                    InnerLabel[Integer.parseInt(tableNumber) - 1].setFont(new Font("Noto Sans", Font.BOLD, 20)); // 폰트 및 글자 크기 설정

                    InnerTextTable.add(InnerLabel[Integer.parseInt(tableNumber) - 1]);

                    InnerTable.add(InnerTextTable);

                    customToggleButtons[Integer.parseInt(tableNumber) - 1] = new CustomToggleButton("",  tableNumber, fixedNumbers);
                    customToggleButtons[Integer.parseInt(tableNumber) - 1].setBounds(2, 2, 20, 20);
                    customToggleButtons[Integer.parseInt(tableNumber) - 1].setOpaque(false);
                    customToggleButtons[Integer.parseInt(tableNumber) - 1].setContentAreaFilled(false);
                    customToggleButtons[Integer.parseInt(tableNumber) - 1].setBorderPainted(false);
                    customToggleButtons[Integer.parseInt(tableNumber) - 1].setFocusPainted(false);
                    customToggleButtons[Integer.parseInt(tableNumber) - 1].setForeground(new Color(0, 0, 0, 0));

                    InnerLabel[Integer.parseInt(tableNumber) - 1].add(customToggleButtons[Integer.parseInt(tableNumber) - 1]);
                    divisions.add(tables[Integer.parseInt(tableNumber) - 1]);
                }
            }
            l.add(divisions);
            seat.add(l);
        }

    }
    public static void removeAllComponents(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
    public static List<Integer> shuffleNumbers(int[] numberRange, List<Integer> excludeNumbers) {
        List<Integer> availableNumbers = new ArrayList<>();

        // 제외할 번호가 있는 index
        ArrayList<Integer> exceptIndex = new ArrayList<>();
        for(int i = 0; i<excludeNumbers.size(); i++){
            for(int j = 0; j<numberRange.length; j++){
                if(excludeNumbers.get(i) == numberRange[j]){
                    exceptIndex.add(j);
                }
            }
        }

        for(int i = 0; i<exceptIndex.size() - 1; i++){
            for(int j = i + 1; j<exceptIndex.size(); j++){
                if(exceptIndex.get(i) > exceptIndex.get(j)){
                    int temp = exceptIndex.get(i);
                    exceptIndex.set(i, exceptIndex.get(j));
                    exceptIndex.set(j, temp);

                    int temp2 = excludeNumbers.get(i);
                    excludeNumbers.set(i, excludeNumbers.get(j));
                    excludeNumbers.set(j, temp2);
                }
            }
        }

        for (int number : numberRange) {
            if (!excludeNumbers.contains(number)) {
                availableNumbers.add(number);
            }
        }

        // 숫자 섞기
        Collections.shuffle(availableNumbers, new Random());

        // 제외할 번호를 다시 추가
        List<Integer> shuffledNumbers = new ArrayList<>();


        if(excludeNumbers.size() == 0){
            for(int i = 0; i<numberRange.length; i++){
                shuffledNumbers.add(availableNumbers.get(i));
            }
        }else{
            int Eindex = 0;
            int index = 0;

            for(int i = 0; i<numberRange.length; i++){
                if(i == exceptIndex.get(Eindex)){
                    shuffledNumbers.add(excludeNumbers.get(Eindex));
                    if(Eindex < exceptIndex.size() - 1) Eindex++;
                }else{
                    shuffledNumbers.add(availableNumbers.get(index));
                    if(index < availableNumbers.size() - 1) index++;
                }

            }
        }

        return shuffledNumbers;
    }
    public void getClassInfo(String className) throws SQLException {
        people = 16;
        seatOrder = new ArrayList<>();
        Connection connection = Util.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT division FROM my_class WHERE class_name = ?");
        preparedStatement.setString(1, className);
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()) {
            division = result.getInt("division");
        }


//        seatOrder
        PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT seat_order FROM seat WHERE class_name = ?");
        preparedStatement2.setString(1, className);
        ResultSet result2 = preparedStatement2.executeQuery();
        while(result2.next()) {
            seatOrder.add(result2.getInt("seat_order"));
        }

        preparedStatement.close();
        preparedStatement2.close();

        connection.close();

        mainTables();
    }
    public String[] getClassOption() throws SQLException{
        ArrayList<String> classOptionsList = new ArrayList<>();
        Connection connection = Util.getConnection();
        // Statement 객체 생성 및 질의문 실행
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM my_class");

        // 결과 조회
        while(result.next()) {
            classOptionsList.add(result.getString("class_name"));
        }
        // 리소스 해제
        result.close();
        statement.close();
        connection.close();

        String[] classOptions = classOptionsList.toArray(new String[classOptionsList.size()]);

        return classOptions;
    }

}
class CustomToggleButton extends JButton {
    private boolean filled = false;

    public CustomToggleButton(String text, String innerText, ArrayList<Integer> fixedNumber) {
        super(text);
        setPreferredSize(new Dimension(50, 50));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(true);
        setText(innerText);

        addActionListener(new ActionListener() {
            boolean isColored = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                filled = !filled;
                repaint();
                if(isColored){
                    isColored = false;
                }
                if(filled){

                }
                String clikedButton = ((JButton) e.getSource()).getText();
                if (fixedNumber.contains(Integer.parseInt(clikedButton))) {
                    fixedNumber.remove(fixedNumber.indexOf(Integer.parseInt(clikedButton)));
                }else{
                    fixedNumber.add(Integer.parseInt(clikedButton));
                }

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    filled = false;
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int diameter = Math.min(getWidth(), getHeight()) - 6;
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        g.setColor(SettingClass.mainColor);
        g.drawOval(x, y, diameter, diameter);

        if (filled) {
            g.setColor(SettingClass.mainColor);
            g.fillOval(x, y, diameter, diameter);
        }
    }
}
class CustomComboBoxUI extends MetalComboBoxUI {
    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        comboBox.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, SettingClass.mainColor)); // Set the border color to blue
        comboBox.setFocusable(false);
    }

    @Override
    protected JButton createArrowButton() {
        JButton button = super.createArrowButton();
        button.setBackground(SettingClass.mainColor); // Set the button background color to blue
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        // Do nothing to remove the background
    }
}
