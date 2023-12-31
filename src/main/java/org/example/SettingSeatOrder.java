package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.*;
import java.sql.*;
import java.util.ArrayList;

public class SettingSeatOrder extends JPanel {
    JComboBox<String> jComboBox;
    Border roundedBorder;
    int people;
    int division;
    ArrayList<Integer> seatOrder;
    int seatType;
    JLabel[] tables;
    JLabel[] InnerTable;
    JLabel[] InnerLabel;
    JPanel seat = new JPanel();
    int seatHeight;
    int tableWidth = 108;
    int tableHeight = 65;

    public SettingSeatOrder() throws SQLException {
        SettingClass.getMainColor();
        roundedBorder = BorderFactory.createLineBorder(SettingClass.mainColor, 30, true);
        setLayout(null);
        String[] classOption = getClassOption();

        jComboBox = new JComboBox<>(classOption);
        jComboBox.setBounds(28, 16, 200, 40);
        SettingClass.customFont(jComboBox, Font.BOLD, 20);
        jComboBox.setUI(new CustomComboBoxUI());
        jComboBox.setOpaque(false);
        add(jComboBox);

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

        getClassInfo(classOption[0]);

        add(seat);

        JLabel teachingDeskLabel = new JLabel();

        teachingDeskLabel.setBorder(roundedBorder);
        teachingDeskLabel.setBounds(372, 87, 295,78);

        JLabel InnerTeachingDest = new JLabel("교탁");
        SettingClass.customFont(InnerTeachingDest, Font.BOLD, 40);
        InnerTeachingDest.setBounds(10, 10, 275, 58);
        InnerTeachingDest.setForeground(Color.WHITE);
        InnerTeachingDest.setHorizontalAlignment(SwingConstants.CENTER);
        InnerTeachingDest.setVerticalAlignment(SwingConstants.CENTER);
        InnerTeachingDest.setOpaque(true);
        InnerTeachingDest.setBackground(SettingClass.mainColor);
        teachingDeskLabel.add(InnerTeachingDest);
        add(teachingDeskLabel);


        JButton printButton = new JButton();
        printButton.setLayout(null);
        printButton.setBounds(900, 630,90, 53);
        printButton.setBorder(roundedBorder);
        printButton.setOpaque(false);
        printButton.setContentAreaFilled(false);
        printButton.setFocusPainted(false);

        JLabel InnerPrintButton = new JLabel("인쇄");
        SettingClass.customFont(InnerPrintButton, Font.BOLD, 18);
        InnerPrintButton.setBounds(10, 10, 70, 33);
        InnerPrintButton.setHorizontalAlignment(SwingConstants.CENTER);
        InnerPrintButton.setVerticalAlignment(SwingConstants.CENTER);
        InnerPrintButton.setOpaque(true);
        InnerPrintButton.setBackground(SettingClass.mainColor);
        InnerPrintButton.setForeground(Color.WHITE);
        printButton.add(InnerPrintButton);

        add(printButton);

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPrintPreview(SettingSeatOrder.this, jComboBox, printButton, jComboBox.getSelectedItem().toString());
            }
        });
    }
    public String[] getClassOption() throws SQLException{
        ArrayList<String> classOptionsList = new ArrayList<>();
        String[] classOptions;
        if(SettingClass.getClassPeople() > 0){
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

            classOptions = classOptionsList.toArray(new String[classOptionsList.size()]);
        }else{
            classOptions = new String[1];
            classOptions[0] = " ";
        }

        return classOptions;
    }
    public void getClassInfo(String className) throws SQLException {
        people = 16;
        seatOrder = new ArrayList<>();
        Connection connection = Util.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM my_class WHERE class_name = ?");
        preparedStatement.setString(1, className);
        ResultSet result = preparedStatement.executeQuery();
        while(result.next()) {
            division = result.getInt("division");
            seatType = result.getInt("class_type");
        }
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
    public void mainTables() {
        removeAllComponents(seat);

        if(seatType == 1) type1MakeTables();
        else if(seatType == 2) type2MakeTables();
        else if(seatType == 3) type3MakeTables();

        seat.setBounds(0, 243, 1045, seatHeight);
    }
    public void type1MakeTables() {
        seatHeight = (int)(Math.ceil(people / (division * 2.0))) * tableHeight + ((int)(Math.ceil(people / (division * 2.0))) - 1) * 16;
        seat.setLayout(new GridLayout(1, division));

        tables = new JLabel[people];
        InnerLabel = new JLabel[people];
        InnerTable = new JLabel[people];

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
            height = height * tableHeight + (height - 1) + 16;

            int margin = (1045 / division - (tableWidth * 2 + 16)) / 2;

            JPanel divisions = new JPanel();
            divisions.setLayout(new GridLayout((int)(Math.ceil(repeat / 2.0)),2));
            divisions.setBounds(margin, 0, tableWidth * 2 + 16, height);

            JLabel l = new JLabel();
            l.setSize(tableWidth * 2 + 16, height);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setOpaque(true);

            for(int j = 0; j<(int)(Math.ceil(repeat / 2.0)); j++){ // 0 1 2
                int index = (i + 1) * 2 - 1;
                for(int k = index; k<=index + 1; k++){
                    cnt++;
                    if(cnt > repeat) break;
                    int tableIndex = (k + (j * (2 * division))) - 1;
                    String tableNumber = seatOrder.get(tableIndex).toString();
                    tables[tableIndex] = new JLabel();
                    tables[tableIndex].setOpaque(true);
                    tables[tableIndex].setLayout(null);

                    InnerTable[tableIndex] = new JLabel();
                    InnerTable[tableIndex].setBounds(3, 3, tableWidth - 6, tableHeight - 6);
                    InnerTable[tableIndex].setBorder(roundedBorder);
                    tables[tableIndex].add(InnerTable[tableIndex]);

                    JLabel InnerTextTable = new JLabel();
                    InnerTextTable.setLayout(null);
                    InnerTextTable.setBounds(3,3, tableWidth - 12, tableHeight - 12);
                    Border InnerRoundedBorder = BorderFactory.createLineBorder(Color.WHITE, 30, true);
                    InnerTextTable.setBorder(InnerRoundedBorder);

                    InnerLabel[tableIndex] = new JLabel(tableNumber);
                    InnerLabel[tableIndex].setBounds(5,5, tableWidth - 22, tableHeight - 22);
                    InnerLabel[tableIndex].setOpaque(true);
                    InnerLabel[tableIndex].setBackground(Color.WHITE);
                    InnerLabel[tableIndex].setHorizontalAlignment(SwingConstants.CENTER);
                    InnerLabel[tableIndex].setVerticalAlignment(SwingConstants.CENTER);
                    InnerLabel[tableIndex].setForeground(SettingClass.mainColor);
                    SettingClass.customFont(InnerLabel[tableIndex], Font.BOLD, 20);

                    InnerTextTable.add(InnerLabel[tableIndex]);

                    InnerTable[tableIndex].add(InnerTextTable);

                    divisions.add(tables[tableIndex]);
                }
            }
            l.add(divisions);
            seat.add(l);
        }
    }

    public void type2MakeTables() {
        seatHeight = (int)(Math.ceil(people / (division * 1.0))) * tableHeight + ((int)(Math.ceil(people / (division * 1.0))) - 1) * 5;
        seat.setLayout(new GridLayout(1, division));

        tables = new JLabel[people];
        InnerLabel = new JLabel[people];
        InnerTable = new JLabel[people];

        int[] divisionCnt = new int[division];
        int Remain = people % division;
        for(int i = 0; i<division; i++){
            int plus = 0;
            if(Remain - 1 >= 0){
                plus = 1;
                Remain -= 1;
            }
            int repeat = people / division + plus;
            divisionCnt[i] = repeat;
        }
        for(int i = 0; i<divisionCnt.length; i++){
            int cnt = 0;
            int repeat = divisionCnt[i];
            int height = repeat * tableHeight + (repeat - 1) * 5;
            int margin = (1045 / division - tableWidth) / 2;
            JPanel divisions = new JPanel();
            divisions.setLayout(new GridLayout(repeat,1));
            divisions.setBounds(margin, 0, tableWidth, height);

            JLabel l = new JLabel();
            l.setSize(tableWidth, height);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setOpaque(true);

            for(int j = 0; j<repeat; j++){
                cnt++;
                if(cnt > repeat) break;
                int tableIndex = (i + 1) + (j * division) - 1;
                String tableNumber = seatOrder.get(tableIndex).toString();
                tables[tableIndex] = new JLabel();
                tables[tableIndex].setOpaque(true);
                tables[tableIndex].setLayout(null);

                InnerTable[tableIndex] = new JLabel();
                InnerTable[tableIndex].setBounds(3, 3, tableWidth - 6, tableHeight - 6);
                InnerTable[tableIndex].setBorder(roundedBorder);
                tables[tableIndex].add(InnerTable[tableIndex]);

                JLabel InnerTextTable = new JLabel();
                InnerTextTable.setLayout(null);
                InnerTextTable.setBounds(3,3, tableWidth - 12, tableHeight - 12);
                Border InnerRoundedBorder = BorderFactory.createLineBorder(Color.WHITE, 30, true);
                InnerTextTable.setBorder(InnerRoundedBorder);

                InnerLabel[tableIndex] = new JLabel(tableNumber);
                InnerLabel[tableIndex].setBounds(5,5, tableWidth - 22, tableHeight - 22);
                InnerLabel[tableIndex].setOpaque(true);
                InnerLabel[tableIndex].setBackground(Color.WHITE);
                InnerLabel[tableIndex].setHorizontalAlignment(SwingConstants.CENTER);
                InnerLabel[tableIndex].setVerticalAlignment(SwingConstants.CENTER);
                InnerLabel[tableIndex].setForeground(SettingClass.mainColor);
                SettingClass.customFont(InnerLabel[tableIndex], Font.BOLD, 20);

                InnerTextTable.add(InnerLabel[tableIndex]);

                InnerTable[tableIndex].add(InnerTextTable);

                divisions.add(tables[tableIndex]);
            }
            l.add(divisions);
            seat.add(l);
        }
    }
    public void type3MakeTables() {
        int table_margin = 4;
        int division_margin = 40;

        int seatRows = (int)Math.ceil(Math.ceil(people / 4.0) / 2);
        seat.setLayout(new GridLayout(seatRows, 3));
        seatHeight = (tableHeight * 2 + table_margin) * 2 + ((seatRows - 1) * division_margin);

        int type3Division = (int)Math.ceil(people / 4.0);

        tables = new JLabel[people];
        InnerLabel = new JLabel[people];
        InnerTable = new JLabel[people];

        int[] divisionCnt = new int[type3Division]; //divisionCnt.length : 분단 수. divisionCnt[i] : 분단 별 인원
        int peopleCnt = 16;
        for(int i = 0; i<division; i++){
            if(peopleCnt - 4 >= 0) {
                divisionCnt[i] = 4;
                peopleCnt -= 4;
            }else{
                divisionCnt[i] = peopleCnt;
            }

        }
        int margin = 0;
        if(divisionCnt.length <= 4) margin = (1045 / 2 - (tableWidth * 2 + table_margin)) / 2;
        else margin = 60;
        for(int i = 0; i<divisionCnt.length; i++){
            int cnt = 0;
            int repeat = divisionCnt[i];
            int height = tableHeight * 2 + table_margin;
            JPanel divisions = new JPanel();
            divisions.setLayout(new GridLayout(2,2));
            divisions.setBounds(margin, 0, tableWidth * 2 + table_margin, height);

            JLabel l = new JLabel();
            l.setSize(tableWidth * 2 + table_margin, height);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setOpaque(true);

            for(int j = 0; j<repeat; j++){
                cnt++;
                if(cnt > repeat) break;
                int tableIndex = (i * 4) + j;
                String tableNumber = seatOrder.get(tableIndex).toString();
                tables[tableIndex] = new JLabel();
                tables[tableIndex].setOpaque(true);
                tables[tableIndex].setLayout(null);

                InnerTable[tableIndex] = new JLabel();
                InnerTable[tableIndex].setBounds(3, 3, tableWidth - 6, tableHeight - 6);
                InnerTable[tableIndex].setBorder(roundedBorder);
                tables[tableIndex].add(InnerTable[tableIndex]);

                JLabel InnerTextTable = new JLabel();
                InnerTextTable.setLayout(null);
                InnerTextTable.setBounds(3,3, tableWidth - 12, tableHeight - 12);
                Border InnerRoundedBorder = BorderFactory.createLineBorder(Color.WHITE, 30, true);
                InnerTextTable.setBorder(InnerRoundedBorder);

                InnerLabel[tableIndex] = new JLabel(tableNumber);
                InnerLabel[tableIndex].setBounds(5,5, tableWidth - 22, tableHeight - 22);
                InnerLabel[tableIndex].setOpaque(true);
                InnerLabel[tableIndex].setBackground(Color.WHITE);
                InnerLabel[tableIndex].setHorizontalAlignment(SwingConstants.CENTER);
                InnerLabel[tableIndex].setVerticalAlignment(SwingConstants.CENTER);
                InnerLabel[tableIndex].setForeground(SettingClass.mainColor);
                SettingClass.customFont(InnerLabel[tableIndex], Font.BOLD, 20);

                InnerTextTable.add(InnerLabel[tableIndex]);

                InnerTable[tableIndex].add(InnerTextTable);

                divisions.add(tables[tableIndex]);
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


    private static void showPrintPreview(JPanel panel, JComboBox<String> jComboBox, JButton printButton, String waterMark) {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pageFormat = job.defaultPage();

        // 용지 방향 설정 (가로 방향)
        pageFormat.setOrientation(PageFormat.LANDSCAPE);

        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2d = (Graphics2D) graphics;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

                // A4 용지 크기 계산
                double pageWidth = pageFormat.getImageableWidth();
                double pageHeight = pageFormat.getImageableHeight();

                // JPanel의 크기를 A4 용지 크기에 맞게 비율 조정
                double scaleX = pageWidth / panel.getWidth();
                double scaleY = pageHeight / panel.getHeight();
                g2d.scale(scaleX, scaleY);

                jComboBox.setVisible(false);
                printButton.setVisible(false);

                // 원하는 영역만 그리기
                panel.print(g2d);

                g2d.setColor(Color.BLACK);
                g2d.setFont(new Font("SansSerif", Font.BOLD, 25));
                String watermarkText = waterMark;
                int x = 50;
                int y = 50;
                g2d.drawString(watermarkText, x, y);

                jComboBox.setVisible(true);
                printButton.setVisible(true);

                return Printable.PAGE_EXISTS;
            }
        }, pageFormat);

        // 프린터 대화상자 띄우기
        if (job.printDialog()) {
            try {
                // 인쇄 실행
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }
}
