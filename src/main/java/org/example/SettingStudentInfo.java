package org.example;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.ArrayList;

public class SettingStudentInfo extends JPanel {
    Border roundedBorder;
    JPanel student = new JPanel();
    private DefaultTableModel model;
    private JTable table;

    public SettingStudentInfo() throws SQLException {
        SettingClass.getMainColor();
        roundedBorder = BorderFactory.createLineBorder(SettingClass.mainColor, 30, true);
        setLayout(null);

        JLabel studentLabel = new JLabel();
        studentLabel.setBounds(372, 87, 295,78);

        model = new DefaultTableModel();
        model.addColumn("번호");
        model.addColumn("이름");

        table = new JTable(model);

        // 추가
        JButton addBtn = new JButton();
        addBtn.setLayout(null);
        addBtn.setBounds(600, 630,90, 53);
        addBtn.setBorder(roundedBorder);
        addBtn.setOpaque(false);
        addBtn.setContentAreaFilled(false);
        addBtn.setFocusPainted(false);

        JLabel addLabel = new JLabel("추가");
        SettingClass.customFont(addLabel, Font.BOLD, 18);
        addLabel.setBounds(10, 10, 70, 33);
        addLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addLabel.setVerticalAlignment(SwingConstants.CENTER);
        addLabel.setOpaque(true);
        addLabel.setBackground(SettingClass.mainColor);
        addLabel.setForeground(Color.WHITE);
        addLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addTableRow();
            }
        });
        addBtn.add(addLabel);
        add(addBtn);

        // 삭제
        JButton delBtn = new JButton();
        delBtn.setLayout(null);
        delBtn.setBounds(700, 630,90, 53);
        delBtn.setBorder(roundedBorder);
        delBtn.setOpaque(false);
        delBtn.setContentAreaFilled(false);
        delBtn.setFocusPainted(false);

        JLabel delLabel = new JLabel("삭제");
        SettingClass.customFont(delLabel, Font.BOLD, 18);
        delLabel.setBounds(10, 10, 70, 33);
        delLabel.setHorizontalAlignment(SwingConstants.CENTER);
        delLabel.setVerticalAlignment(SwingConstants.CENTER);
        delLabel.setOpaque(true);
        delLabel.setBackground(SettingClass.mainColor);
        delLabel.setForeground(Color.WHITE);
        delLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    DelTableRow();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        delBtn.add(delLabel);
        add(delBtn);

        // 저장
        JButton saveBtn = new JButton();
        saveBtn.setLayout(null);
        saveBtn.setBounds(800, 630,90, 53);
        saveBtn.setBorder(roundedBorder);
        saveBtn.setOpaque(false);
        saveBtn.setContentAreaFilled(false);
        saveBtn.setFocusPainted(false);

        JLabel saveLabel = new JLabel("저장");
        SettingClass.customFont(saveLabel, Font.BOLD, 18);
        saveLabel.setBounds(10, 10, 70, 33);
        saveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        saveLabel.setVerticalAlignment(SwingConstants.CENTER);
        saveLabel.setOpaque(true);
        saveLabel.setBackground(SettingClass.mainColor);
        saveLabel.setForeground(Color.WHITE);
        saveLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setRole();
            }
        });
        saveBtn.add(saveLabel);
        add(saveBtn);

        // 인쇄
        JButton printBtn = new JButton();
        printBtn.setLayout(null);
        printBtn.setBounds(900, 630,90, 53);
        printBtn.setBorder(roundedBorder);
        printBtn.setOpaque(false);
        printBtn.setContentAreaFilled(false);
        printBtn.setFocusPainted(false);

        JLabel InnerPrintButton = new JLabel("인쇄");
        SettingClass.customFont(InnerPrintButton, Font.BOLD, 18);
        InnerPrintButton.setBounds(10, 10, 70, 33);
        InnerPrintButton.setHorizontalAlignment(SwingConstants.CENTER);
        InnerPrintButton.setVerticalAlignment(SwingConstants.CENTER);
        InnerPrintButton.setOpaque(true);
        InnerPrintButton.setBackground(SettingClass.mainColor);
        InnerPrintButton.setForeground(Color.WHITE);
        printBtn.add(InnerPrintButton);
        add(printBtn);

        getStudent();

        add(student);
    }

    private void getStudent() throws SQLException {
        ArrayList<String> student_num = new ArrayList<>();
        ArrayList<String> student_name = new ArrayList<>();

        // Util 클래스 내용 참조
        Connection connection = Util.getConnection();

        // Statement 객체 생성 및 질의문 실행
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM student"); // 결과 조회

        while (result.next()) {
            student_num.add(result.getString("student_num"));
            student_name.add(result.getString("student_name"));
        }

        // 리소스 해제
        result.close();
        statement.close();
        connection.close();

        // Call the appropriate showTable method with arguments
        showTable(student_num, student_name);
    }

    private void addTableRow() {
        model.addRow(new Object[]{"", "", ""});
        int newRow = model.getRowCount() - 1;
        Rectangle rect = table.getCellRect(newRow, 0, true);
        table.setRowHeight(newRow, 30);
        table.scrollRectToVisible(rect);
    }

    private void DelTableRow() throws SQLException {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int choice = JOptionPane.showConfirmDialog(student, "선택한 행을 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                deleteRowFromDatabase(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(student, "행을 선택한 후 삭제해주세요.", "알림", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteRowFromDatabase(int rowIndex) throws SQLException {
        String student_num = (String) model.getValueAt(rowIndex, 0);
        String student_name = (String) model.getValueAt(rowIndex, 1);

        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM student WHERE student_num = ? AND student_name = ?");

        preparedStatement.setString(1, student_num);
        preparedStatement.setString(2, student_name);

        int rowsAffected = preparedStatement.executeUpdate();
        preparedStatement.close();

        connection.close();

        // 선택한 행 삭제
        model.removeRow(rowIndex);
    }

    // 저장을 누르면 바뀐 값을 DB에 저장
    public void setRole() {
        try {
            Connection connection = Util.getConnection();
            String sql = "INSERT INTO student (student_num, student_name) VALUES (?, ?)";
            String checkIfExistsSql = "SELECT COUNT(*) FROM student WHERE student_num = ? AND student_name = ?";

            try (PreparedStatement insertStatement = connection.prepareStatement(sql);
                 PreparedStatement checkIfExistsStatement = connection.prepareStatement(checkIfExistsSql)) {

                for (int i = 0; i < model.getRowCount(); i++) {
                    String student_num = (String) model.getValueAt(i, 0);
                    String student_name = (String) model.getValueAt(i, 1);

                    // 데이터가 이미 존재하는지
                    checkIfExistsStatement.setString(1, student_num);
                    checkIfExistsStatement.setString(2, student_name);
                    ResultSet resultSet = checkIfExistsStatement.executeQuery();
                    resultSet.next();
                    int rowCount = resultSet.getInt(1);

                    // 내가 추가 버튼을 눌러 입력한 데이터만 들어가게
                    if (rowCount == 0) {
                        insertStatement.setString(1, student_num);
                        insertStatement.setString(2, student_name);
                        insertStatement.executeUpdate();
                    }
                }

                JOptionPane.showMessageDialog(student, "데이터가 성공적으로 저장되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(student, "데이터 저장 중 오류가 발생했습니다.", "에러", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showTable(ArrayList<String> student_num, ArrayList<String> student_name) {
        model.setRowCount(0);

        for(int i=0; i<student_num.size(); i++) {
            model.addRow(new String[]{student_num.get(i), student_name.get(i)});
        }

        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(300, 75, 400, 486);

        // table column 조정
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(400);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        SettingClass.customFont(table, Font.PLAIN, 14);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.setRowHeight(0, 30);

        // table 전체 크기
        scrollPane.setPreferredSize(new Dimension(400, 286));

        // 역할, 하는일, 담당자 폰트 적용
        JTableHeader header = table.getTableHeader();
        SettingClass.customFont(header, Font.BOLD, 20);
        header.setBackground(Color.decode("#47815E"));
        header.setForeground(Color.WHITE);

        add(scrollPane);
    }
}
