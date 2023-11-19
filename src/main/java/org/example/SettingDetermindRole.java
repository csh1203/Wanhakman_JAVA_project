package org.example;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.*;
import java.util.ArrayList;

public class SettingDetermindRole extends JPanel {
    private static boolean isPrintButtonVisible;
    Border roundedBorder;
    JPanel role = new JPanel();
    private DefaultTableModel model;
    private JTable table;

    public SettingDetermindRole() throws SQLException {
        SettingClass.getMainColor();
        roundedBorder = BorderFactory.createLineBorder(SettingClass.mainColor, 30, true);
        setLayout(null);

        JLabel studentLabel = new JLabel();
        studentLabel.setBounds(372, 87, 295,78);

        model = new DefaultTableModel();
        model.addColumn("역할");
        model.addColumn("하는 일");
        model.addColumn("담당자");

        table = new JTable(model);

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
        printBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPrintPreview(SettingDetermindRole.this, printBtn, "인쇄 미리보기");
            }
        });

        add(printBtn);

        getRole();

        add(role);
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
    private void showTable(ArrayList<String> role_name, ArrayList<String> role_explain, ArrayList<String> student_name) {
        model.setRowCount(0);

        for(int i=0; i<role_name.size(); i++) {
            model.addRow(new String[]{role_name.get(i), role_explain.get(i), student_name.get(i)});
        }

        table.setRowHeight(30);

        // Calculate the preferred height based on the number of rows
        int preferredHeight = model.getRowCount() * table.getRowHeight();

        // Set the preferred size of the scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(110, 50, 800, 550);

        // 스크롤 안 보이게
//        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        // table column 조정
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(500);
        columnModel.getColumn(2).setPreferredWidth(150);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        SettingClass.customFont(table, Font.PLAIN, 14);
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.setRowHeight(0, 30);

        // table 전체 크기
        scrollPane.setPreferredSize(new Dimension(800, preferredHeight));

        // 역할, 하는일, 담당자 폰트 적용
        JTableHeader header = table.getTableHeader();
        SettingClass.customFont(header, Font.BOLD, 20);
        header.setBackground(Color.decode("#47815E"));
        header.setForeground(Color.WHITE);

        add(scrollPane);
    }

    private static void showPrintPreview(JPanel panel, JButton printButton, String waterMark) {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pageFormat = job.defaultPage();

        // Set paper orientation (Portrait)
        pageFormat.setOrientation(PageFormat.PORTRAIT);

        if (isPrintButtonVisible) {
            printButton.setVisible(false);
        }

        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // A4 paper size calculation
                double pageWidth = pageFormat.getImageableWidth();
                double pageHeight = pageFormat.getImageableHeight();

                // Scale the JPanel to fit the A4 paper size
                double scaleX = pageWidth / panel.getWidth();
                double scaleY = pageHeight / panel.getHeight();
                g2d.scale(scaleX, scaleY);

                // Draw only the desired area
                panel.print(g2d);

                printButton.setVisible(false);

                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 25));
                String watermarkText = waterMark;
                int x = 50;
                int y = 50;
                g2d.drawString(watermarkText, x, y);

                return Printable.PAGE_EXISTS;
            }
        }, pageFormat);

        // Show the print dialog
        if (job.printDialog()) {
            try {
                // Execute the print job
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            } finally {
                // Set the boolean variable to control the visibility of the print button
                isPrintButtonVisible = false;
                printButton.setVisible(true);
            }
        }
    }
}
