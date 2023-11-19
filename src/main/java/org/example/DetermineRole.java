package org.example;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.sql.*;
import java.util.ArrayList;

public class DetermineRole {
    private JFrame frame;
    private DefaultTableModel model;
    private JTable table;

    public DetermineRole() throws SQLException {
        frame = new JFrame("1인 1역 정하기");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Title
        JLabel title = new JLabel("우리반 1인 1역");
        SettingClass.customFont(title, Font.BOLD, 36);
        title.setForeground(Color.BLACK);
        title.setBounds(525, 100, 300, 50);
        frame.add(title);

        // 홈버튼
        ImageIcon home = new ImageIcon("img/homeBtn.png");
        JButton homeBtn = new JButton(home);
        homeBtn.setBounds(1190, 24, 45, 45);
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

        model = new DefaultTableModel();
        model.addColumn("역할");
        model.addColumn("하는 일");
        model.addColumn("담당자");

        table = new JTable(model);

        // 추가
        RoundedButton addBtn = new RoundedButton("");
        addBtn.setBounds(930, 710, 93, 53);
        addBtn.setLayout(new BorderLayout());

        JLabel addLabel = new JLabel("추가");
        addLabel.setOpaque(true);
        addLabel.setBackground(SettingClass.mainColor);
        SettingClass.customFont(addLabel, Font.BOLD, 25);
        addLabel.setForeground(Color.WHITE);
        addLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addLabel.setVerticalAlignment(SwingConstants.CENTER);
        addBtn.add(addLabel);

        addLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addTableRow();
            }
        });
        frame.add(addBtn);

        // 삭제
        RoundedButton delBtn = new RoundedButton("");
        delBtn.setBounds(1030, 710, 93, 53);
        delBtn.setLayout(new BorderLayout());

        JLabel delLabel = new JLabel("삭제");
        delLabel.setOpaque(true);
        delLabel.setBackground(SettingClass.mainColor);
        SettingClass.customFont(delLabel, Font.BOLD, 25);
        delLabel.setForeground(Color.WHITE);
        delLabel.setHorizontalAlignment(SwingConstants.CENTER);
        delLabel.setVerticalAlignment(SwingConstants.CENTER);
        delBtn.add(delLabel);

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
        frame.add(delBtn);

        // 저장
        RoundedButton saveBtn = new RoundedButton("");
        saveBtn.setBounds(1130, 710, 93, 53);
        saveBtn.setLayout(new BorderLayout());

        JLabel saveLabel = new JLabel("저장");
        saveLabel.setOpaque(true);
        saveLabel.setBackground(SettingClass.mainColor);
        SettingClass.customFont(saveLabel, Font.BOLD, 25);
        saveLabel.setForeground(Color.WHITE);
        saveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        saveLabel.setVerticalAlignment(SwingConstants.CENTER);
        saveBtn.add(saveLabel);

        saveLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setRole();
            }
        });
        frame.add(saveBtn);

        getRole();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // 1인 1역 불러오기
    public void getRole() throws SQLException {
        ArrayList<String> role_name = new ArrayList<>();
        ArrayList<String> role_explain = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();

        // Util 클래스 내용 참조
        Connection connection = Util.getConnection();

        // Statement 객체 생성 및 질의문 실행
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM role"); // 결과 조회

        while (result.next()) {
            role_name.add(result.getString("role_name"));
            role_explain.add(result.getString("role_explain"));
            name.add(result.getString("name"));
        }

        // 리소스 해제
        result.close();
        statement.close();
        connection.close();

        // Call the appropriate showTable method with arguments
        showTable(role_name, role_explain, name);
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
            int choice = JOptionPane.showConfirmDialog(frame, "선택한 행을 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                deleteRowFromDatabase(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "행을 선택한 후 삭제해주세요.", "알림", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deleteRowFromDatabase(int rowIndex) throws SQLException {
        String role_name = (String) model.getValueAt(rowIndex, 0);
        String role_explain = (String) model.getValueAt(rowIndex, 1);
        String name = (String) model.getValueAt(rowIndex, 2);

        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM role WHERE role_name = ? AND role_explain = ? AND name = ?");

        preparedStatement.setString(1, role_name);
        preparedStatement.setString(2, role_explain);
        preparedStatement.setString(3, name);

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
            String sql = "INSERT INTO role (role_name, role_explain, name) VALUES (?, ?, ?)";
            String checkIfExistsSql = "SELECT COUNT(*) FROM role WHERE role_name = ? AND role_explain = ? AND name = ?";

            try (PreparedStatement insertStatement = connection.prepareStatement(sql);
                 PreparedStatement checkIfExistsStatement = connection.prepareStatement(checkIfExistsSql)) {

                for (int i = 0; i < model.getRowCount(); i++) {
                    String role_name = (String) model.getValueAt(i, 0);
                    String role_explain = (String) model.getValueAt(i, 1);
                    String name = (String) model.getValueAt(i, 2);

                    // 데이터가 이미 존재하는지
                    checkIfExistsStatement.setString(1, role_name);
                    checkIfExistsStatement.setString(2, role_explain);
                    checkIfExistsStatement.setString(3, name);
                    ResultSet resultSet = checkIfExistsStatement.executeQuery();
                    resultSet.next();
                    int rowCount = resultSet.getInt(1);

                    // 내가 추가 버튼을 눌러 입력한 데이터만 들어가게
                    if (rowCount == 0) {
                        insertStatement.setString(1, role_name);
                        insertStatement.setString(2, role_explain);
                        insertStatement.setString(3, name);
                        insertStatement.executeUpdate();
                    }
                }

                JOptionPane.showMessageDialog(frame, "데이터가 성공적으로 저장되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "데이터 저장 중 오류가 발생했습니다.", "에러", JOptionPane.ERROR_MESSAGE);
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

    // table
    private void showTable(ArrayList<String> role_name, ArrayList<String> role_explain, ArrayList<String> student_name) {
        model.setRowCount(0);

        for(int i=0; i<role_name.size(); i++) {
            model.addRow(new String[]{role_name.get(i), role_explain.get(i), student_name.get(i)});
        }

        table.setRowHeight(30);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(140, 200, 1000, 400);

        // table column 조정
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200);
        columnModel.getColumn(1).setPreferredWidth(800);
        columnModel.getColumn(2).setPreferredWidth(200);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        SettingClass.customFont(table, Font.PLAIN, 14);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.setRowHeight(0, 30);

        // table 전체 크기
        scrollPane.setPreferredSize(new Dimension(1000, 400));

        // 역할, 하는일, 담당자 폰트 적용
        JTableHeader header = table.getTableHeader();
        SettingClass.customFont(header, Font.BOLD, 20);
        header.setBackground(Color.decode("#47815E"));
        header.setForeground(Color.WHITE);

        frame.add(scrollPane);
    }

    public static void removeAllComponents(JPanel panel) {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new DetermineRole();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
