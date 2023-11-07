package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class SettingMyClass extends JPanel {
    JButton[] deleteClass;
    JPanel myClassPanel;
    JPanel deleteBtnPanel;
    JLabel[] myClassLabel;
    boolean isVisible = false;
    JButton editClass;
    JButton addClassBtn;

    int classNameWidth = 200;

    int deleteBtnXPosition = 113 + classNameWidth;
    public static void main(String args[]) throws SQLException {
        new Setting();
    }
    public SettingMyClass() throws SQLException {
        setLayout(null);

        JLabel title = new JLabel("나의 교실");
        title.setBounds(113, 110, 150, 36);
        title.setFont(new Font("Noto Sans", Font.BOLD, 30)); // 폰트 및 글자 크기 설정
        add(title);

        ArrayList<String> myClass = getMyClass();

        myClassPanel = new JPanel();
        myClassPanel.setLayout(null);
        myClassPanel.setBounds(113, 200, classNameWidth, 500);
        add(myClassPanel);

        deleteBtnPanel = new JPanel();
        deleteBtnPanel.setLayout(null);
        deleteBtnPanel.setBounds(deleteBtnXPosition, 200, 80, 500);
        add(deleteBtnPanel);

        myClassLabel = new JLabel[myClass.size()];
        deleteClass = new JButton[myClass.size()];
        int yPosition = 0;
        Border roundedBorder = BorderFactory.createLineBorder(Color.BLACK, 20, true);

        for(int i = 0; i<myClass.size(); i++){
            myClassLabel[i] = new JLabel(myClass.get(i));
            myClassLabel[i].setBounds(0, yPosition, classNameWidth, 35);
            myClassLabel[i].setFont(new Font("Noto Sans", Font.BOLD, 25)); // 폰트 및 글자 크기 설정
            myClassPanel.add(myClassLabel[i]);

            deleteClass[i] = new JButton();
            deleteClass[i].setBounds(0, yPosition, 70, 35);
            deleteClass[i].setLayout(null);
            deleteClass[i].setOpaque(false);
            deleteClass[i].setBorderPainted(true);
            deleteClass[i].setFocusPainted(false);
            deleteClass[i].setBorder(roundedBorder);
            deleteClass[i].setContentAreaFilled(false);

            JLabel InnerDeleteClass = new JLabel("삭제");
            InnerDeleteClass.setBounds(10, 10, 50, 15);
            InnerDeleteClass.setOpaque(true);
            InnerDeleteClass.setBackground(Color.BLACK);
            InnerDeleteClass.setFont(new Font("Noto Sans", Font.PLAIN, 16));
            InnerDeleteClass.setForeground(Color.WHITE);
            InnerDeleteClass.setHorizontalAlignment(SwingConstants.CENTER);
            InnerDeleteClass.setVerticalAlignment(SwingConstants.CENTER);
            deleteClass[i].add(InnerDeleteClass);
            deleteClass[i].setVisible(false);

            deleteBtnPanel.add(deleteClass[i]);
            yPosition += 40;

            final int index = i;
            deleteClass[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        deleteCurrectClass(myClass.get(index));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        editClass = new JButton("교실 편집");
        editClass.setBounds(855, 30, 150, 40);
        editClass.setFont(new Font("Noto Sans", Font.BOLD, 20));
        editClass.setOpaque(false);
        editClass.setBorderPainted(false);
        editClass.setFocusPainted(false);
        editClass.setContentAreaFilled(false);
        add(editClass);

        editClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editToggleButton(editClass, deleteClass);
            }
        });

        addClassBtn = new JButton("교실 추가하기");
        addClassBtn.setBounds(800, 710, 200, 40);
        addClassBtn.setFont(new Font("Noto Sans", Font.BOLD, 20));
        addClassBtn.setOpaque(false);
        addClassBtn.setContentAreaFilled(false);
        addClassBtn.setBorderPainted(false);
        addClassBtn.setFocusPainted(false);
        addClassBtn.setVisible(false);
        add(addClassBtn);

        addClassBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddFrame();
            }
        });
    }

    public void showAddFrame(){
        JFrame addFrame = new JFrame("교실 추가하기");
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setSize(490, 340);
        addFrame.setLayout(null);

        JLabel title = new JLabel("교실 추가하기");
        title.setFont(new Font("Noto Sans", Font.BOLD, 25));
        title.setBounds(40, 30, 200, 40);
        addFrame.add(title);

        JLabel subTitle = new JLabel("교실 이름");
        subTitle.setBounds(40, 90, 100, 40);
        subTitle.setFont(new Font("Noto Sans", Font.BOLD, 20));
        addFrame.add(subTitle);

        JTextField inputClassName = new JTextField();
        inputClassName.setBounds(40, 130, 268, 40);
        inputClassName.setFont(new Font("Noto Sans", Font.BOLD, 18));
        addFrame.add(inputClassName);

        Border roundedBorder = BorderFactory.createLineBorder(Color.BLACK, 20, true);

        JButton addBtn = new JButton();
        addBtn.setLayout(null);
        addBtn.setBounds(277, 225, 78, 45);
        addBtn.setOpaque(false);
        addBtn.setContentAreaFilled(false);
        addBtn.setBorder(roundedBorder);

        JLabel InnerAddBtn = new JLabel("추가");
        InnerAddBtn.setBounds(10, 10, 58, 25);
        InnerAddBtn.setFont(new Font("Noto Sans", Font.BOLD, 18));
        InnerAddBtn.setOpaque(true);
        InnerAddBtn.setBackground(Color.BLACK);
        InnerAddBtn.setForeground(Color.WHITE);
        InnerAddBtn.setHorizontalAlignment(SwingConstants.CENTER);
        InnerAddBtn.setVerticalAlignment(SwingConstants.CENTER);
        addBtn.add(InnerAddBtn);
        addFrame.add(addBtn);

        JButton deleteBtn = new JButton("취소");
        deleteBtn.setBounds(370, 225, 78, 45);
        deleteBtn.setFont(new Font("Noto Sans", Font.BOLD, 18));
        deleteBtn.setOpaque(false);
        deleteBtn.setBorderPainted(false);
        deleteBtn.setContentAreaFilled(false);
        deleteBtn.setFocusPainted(false);
        addFrame.add(deleteBtn);

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String class_name = inputClassName.getText();
                try {
                    addCurrentClass(class_name);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                addFrame.dispose();
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addFrame.dispose();
            }
        });

        addFrame.setLocationRelativeTo(null);
        addFrame.setVisible(true);
    }
    public void editToggleButton(JButton editClass, JButton[] deleteClass){
        isVisible = !isVisible;
        System.out.println(isVisible);
        for(int i = 0; i< deleteClass.length; i++){
            deleteClass[i].setVisible(isVisible);
        }
        editClass.setText(isVisible ? "확인" : "교실 편집");
        addClassBtn.setVisible(isVisible);
    }
    public ArrayList<String> getMyClass() throws SQLException {
        ArrayList<String> myClass = new ArrayList<>();
        // Util 클래스 내용 참조
        Connection connection = Util.getConnection();
        // Statement 객체 생성 및 질의문 실행
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM my_class");

        // 결과 조회
        while(result.next()) {
            myClass.add(result.getString("class_name"));
        }

        // 리소스 해제
        result.close();
        statement.close();
        connection.close();
        return myClass;
    }
    public void addCurrentClass(String class_name) throws SQLException{

        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO my_class (class_name, class_type) VALUES (?, 1)");
        preparedStatement.setString(1, class_name);
        // 다음의 질의문이 실행됨
        // 수정, 삭제 관련 작업은 Statement와 마찬가지로 executeUpdate 메서드 실행
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println(rowsAffected);
        preparedStatement.close();

        connection.close();

        showMyClass();
    }
    public void deleteCurrectClass(String class_name) throws SQLException{
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM my_class WHERE class_name = ?");
        preparedStatement.setString(1, class_name);
        // 다음의 질의문이 실행됨
        // 수정, 삭제 관련 작업은 Statement와 마찬가지로 executeUpdate 메서드 실행
        int rowsAffected = preparedStatement.executeUpdate();
        System.out.println(rowsAffected);
        preparedStatement.close();

        connection.close();

        showMyClass();
    }
    public void showMyClass() throws SQLException {
        ArrayList<String> myClass = getMyClass();

        Component[] myClassComponents = myClassPanel.getComponents();
        Component[] deleteBtnComponents =  deleteBtnPanel.getComponents();

        for(int i = 0; i<myClassComponents.length; i++){
            myClassPanel.remove(myClassComponents[i]);
            deleteBtnPanel.remove(deleteBtnComponents[i]);
        }

        deleteBtnPanel.revalidate();
        deleteBtnPanel.repaint();

        myClassPanel.revalidate();
        myClassPanel.repaint();

        myClassLabel = new JLabel[myClass.size()];
        deleteClass = new JButton[myClass.size()];
        int yPosition = 0;
        Border roundedBorder = BorderFactory.createLineBorder(Color.BLACK, 20, true);

        for(int i = 0; i<myClass.size(); i++){
            myClassLabel[i] = new JLabel(myClass.get(i));
            myClassLabel[i].setBounds(0, yPosition, classNameWidth, 35);
            myClassLabel[i].setFont(new Font("Noto Sans", Font.BOLD, 25)); // 폰트 및 글자 크기 설정
            myClassPanel.add(myClassLabel[i]);

            deleteClass[i] = new JButton();
            deleteClass[i].setBounds(0, yPosition, 70, 35);
            deleteClass[i].setLayout(null);
            deleteClass[i].setOpaque(false);
            deleteClass[i].setBorderPainted(true);
            deleteClass[i].setFocusPainted(false);
            deleteClass[i].setBorder(roundedBorder);
            deleteClass[i].setContentAreaFilled(false);

            JLabel InnerDeleteClass = new JLabel("삭제");
            InnerDeleteClass.setBounds(10, 10, 50, 15);
            InnerDeleteClass.setOpaque(true);
            InnerDeleteClass.setBackground(Color.BLACK);
            InnerDeleteClass.setFont(new Font("Noto Sans", Font.PLAIN, 16));
            InnerDeleteClass.setForeground(Color.WHITE);
            InnerDeleteClass.setHorizontalAlignment(SwingConstants.CENTER);
            InnerDeleteClass.setVerticalAlignment(SwingConstants.CENTER);
            deleteClass[i].add(InnerDeleteClass);
            deleteClass[i].setVisible(true);

            deleteBtnPanel.add(deleteClass[i]);
            yPosition += 40;

            final int index = i;
            deleteClass[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        deleteCurrectClass(myClass.get(index));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

//        editClass.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                editToggleButton(editClass, deleteClass);
//            }
//        });
    }

}
