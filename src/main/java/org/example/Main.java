package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;


public class Main extends Frame {
    private Date currentDate;
    JLabel[] dateLabel;
    JLabel[] weekLabel;

    JTextArea scheduleArea[];

    public static void main(String[] args) throws SQLException{
        new Main();
    }

    public Main() throws SQLException {
        SettingClass.getMainColor();
        currentDate = getStartOfThisWeek(); // 이번주 월요일 날짜를 사용
        // JFrame 생성
        JFrame frame = new JFrame("완벽한 학급 만들기");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼 동작 설정
        frame.setBackground(Color.WHITE);

        JLabel titleImg = new JLabel();
        titleImg.setBounds(512, 85, 256, 133);
        titleImg.setIcon(new ImageIcon("img/title.png"));
        frame.getContentPane().add(titleImg);

        JLabel userProfile = new JLabel();
        userProfile.setBounds(1150, 35, 70, 70);
        userProfile.setIcon(new ImageIcon("img/userProfile.png"));
        frame.getContentPane().add(userProfile);

        userProfile.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                try {
                    new Setting();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        Calendar calendar = Calendar.getInstance();

        //학급 일정표 JPanel 생성
        JPanel schedulePanel = new JPanel();
        schedulePanel.setBounds(75, 259, 1120, 220);
        schedulePanel.setLayout(null);
        JPanel weekPanel = new JPanel(new GridLayout(1, 7));
        weekPanel.setBounds(0, 0, 1120, 36);

        String weekDay[] = {"월", "화", "수", "목", "금", "토", "일"};
        weekLabel = new JLabel[7];
        for(int i = 0; i<7; i++){
            weekLabel[i] = new JLabel(weekDay[i]);
            Border border = BorderFactory.createLineBorder(SettingClass.mainColor, 3);
            weekLabel[i].setBorder(border);
            weekLabel[i].setFont(new Font("Noto Sans", Font.PLAIN, 18)); // 폰트 및 글자 크기 설정
            weekLabel[i].setHorizontalAlignment(JLabel.CENTER);
            weekLabel[i].setVerticalAlignment(JLabel.CENTER);
            weekPanel.add(weekLabel[i]);
        }
        schedulePanel.add(weekPanel);

        JPanel datePanel = new JPanel(new GridLayout(1, 7));
        datePanel.setBounds(0, 33, 1120, 187);
        JButton dateButton[] = new JButton[7];
        scheduleArea = new JTextArea[7];
        dateLabel = new JLabel[7];

        for(int i = 0; i<7; i++){
            dateButton[i] = new JButton();
            Border border = BorderFactory.createLineBorder(SettingClass.mainColor, 3);
            dateButton[i].setBorder(border);
            dateButton[i].setLayout(null);
            dateButton[i].setContentAreaFilled(false);
            dateButton[i].setFocusPainted(false);
            dateButton[i].setBackground(Color.white); // JButton의 배경색을 초록색으로 설정

            dateLabel[i] = new JLabel();
            dateLabel[i].setBounds(5, 5, 150, 25);
            dateLabel[i].setFont(new Font("Noto Sans", Font.PLAIN, 16)); // 폰트 및 글자 크기 설정
            dateLabel[i].setForeground(SettingClass.mainColor); // 글자 색상 설정
            dateLabel[i].setOpaque(true);
            dateLabel[i].setBackground(Color.WHITE);
            dateButton[i].add(dateLabel[i]);

            scheduleArea[i] = new JTextArea();
            scheduleArea[i].setBounds(5, 30, 150, 150);
            scheduleArea[i].setOpaque(true);
            scheduleArea[i].setPreferredSize(new Dimension(150, 150));
            scheduleArea[i].setLayout(null);
            scheduleArea[i].setLineWrap(true);
            scheduleArea[i].setWrapStyleWord(true);
            scheduleArea[i].setEditable(false);
            dateButton[i].add(scheduleArea[i]);

            datePanel.add(dateButton[i]);
            calendar.add(Calendar.DAY_OF_MONTH, 1); // 다음 날짜로 이동

            final int index = i;

            scheduleArea[i].addMouseListener (new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        editSchedule(dateLabel[index].getText(), scheduleArea[index].getText(), scheduleArea[index]);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            dateButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        editSchedule(dateLabel[index].getText(), scheduleArea[index].getText(), scheduleArea[index]);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }
        updateCalendar(currentDate);

        schedulePanel.add(datePanel);
        frame.add(schedulePanel);

        JButton prevWeek = new JButton();
        prevWeek.setIcon(new ImageIcon("img/schedulerPrevWeek.png"));
        prevWeek.setBounds(16, 344, 50,50);
        prevWeek.setBorderPainted(false);
        prevWeek.setContentAreaFilled(false);
        prevWeek.setFocusPainted(false);
        prevWeek.setOpaque(false);
        frame.add(prevWeek);

        JButton nextWeek = new JButton();
        nextWeek.setIcon(new ImageIcon("img/schedulerNextWeek.png"));
        nextWeek.setBounds(1210, 344, 50,50);
        nextWeek.setBorderPainted(false);
        nextWeek.setContentAreaFilled(false);
        nextWeek.setFocusPainted(false);
        nextWeek.setOpaque(false);
        frame.add(nextWeek);

        prevWeek.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e)  {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.WEEK_OF_YEAR, -1); // 이전 주로 이동
                currentDate = calendar.getTime();
                try {
                    updateCalendar(currentDate);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        nextWeek.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.WEEK_OF_YEAR, 1); // 이전 주로 이동
                currentDate = calendar.getTime();
                try {
                    updateCalendar(currentDate);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

//      버튼 JPanel 생성
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(257, 530, 765, 210); // JPanel의 크기를 설정

        RoundedButton button1 = new RoundedButton("");
        button1.setBounds(0, 0, 375, 100);
        button1.setLayout(new BorderLayout());

        ImageIcon InnerTextImg1 = new ImageIcon("img/mainPickASeatBtn.png");
        JLabel label1 = new JLabel(InnerTextImg1);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        label1.setVerticalAlignment(SwingConstants.CENTER);

        button1.add(label1);
        panel.add(button1);

        RoundedButton button2 = new RoundedButton("");
        button2.setBounds(390, 0, 375, 100);
        button2.setLayout(new BorderLayout());

        ImageIcon InnerTextImg2 = new ImageIcon("img/mainPickClassPresidentBtn.png");
        JLabel label2 = new JLabel(InnerTextImg2);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setVerticalAlignment(SwingConstants.CENTER);

        button2.add(label2);
        panel.add(button2);

        RoundedButton button3 = new RoundedButton("");
        button3.setBounds(0, 110, 375, 100);
        button3.setLayout(new BorderLayout());

        ImageIcon InnerTextImg3 = new ImageIcon("img/mainClassRoleBtn.png");
        JLabel label3 = new JLabel(InnerTextImg3);
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        label3.setVerticalAlignment(SwingConstants.CENTER);

        button3.add(label3);
        panel.add(button3);

        RoundedButton button4 = new RoundedButton("");
        button4.setBounds(390, 110, 375, 100);
        button4.setLayout(new BorderLayout());

        ImageIcon InnerTextImg4 = new ImageIcon("img/mainPickPresenterBtn.png");
        JLabel label4 = new JLabel(InnerTextImg4);
        label4.setHorizontalAlignment(SwingConstants.CENTER);
        label4.setVerticalAlignment(SwingConstants.CENTER);

        button4.add(label4);
        panel.add(button4);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                try {
                    new PickASeatMain(0);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ClassPresidentElection();
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new DetermineRole();
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ChoosingAPresenterSetting();
            }
        });

        frame.add(panel);

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);

    }
    private void
    updateCalendar(Date date) throws SQLException{

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");

        Calendar today = Calendar.getInstance();
        String getToday = dateFormat.format(today.getTime());
        // 이번 주 월요일부터 일요일까지의 날짜를 표시
        for (int i = 0; i < 7; i++) {
            String getDate = dateFormat.format(calendar.getTime());
            String text = getCurrectText(getDate);
            scheduleArea[i].setText(text);
            dateLabel[i].setText(getDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1); // 다음 날짜로 이동
            if(getDate.equals(getToday)){
                weekLabel[i].setForeground(Color.white); // 글자 색상 설정
                weekLabel[i].setOpaque(true);
                weekLabel[i].setBackground(SettingClass.mainColor);
            }else{
                weekLabel[i].setForeground(SettingClass.mainColor); // 글자 색상 설정
                weekLabel[i].setOpaque(true);
                weekLabel[i].setBackground(Color.WHITE);
            }
        }
    }

    public String getCurrectText(String date) throws SQLException{
        Connection connection = Util.getConnection();

        Statement statement1 = connection.createStatement();

        String text = "";
        String sql1 = "select EXISTS (SELECT date, content from scheduler WHERE date Like \""+date+"\" limit 1) as success";

        ResultSet result2 = statement1.executeQuery(sql1);
        result2.next();

        if(result2.getString(1).equals("1")){
            PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM scheduler WHERE date = ?");
            preparedStatement1.setString(1, date);
            ResultSet result = preparedStatement1.executeQuery();
            result.next();
            text = result.getString(2);
            preparedStatement1.close();
        }
        result2.close();
        statement1.close();
        connection.close();

        return text;
    }
    private Date getStartOfThisWeek() {
        Calendar calendar = Calendar.getInstance();

        if(calendar.get(Calendar.DAY_OF_WEEK) == 1){
            calendar.add(Calendar.DAY_OF_WEEK, -6);
        }else {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 이번 주 월요일
        }

        return calendar.getTime();
    }
    public void editSchedule(String date, String content, JTextArea contentArea) throws SQLException{

        JFrame editFrame = new JFrame("일정 편집");
        editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editFrame.setSize(325, 400);
        editFrame.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(5, 5, 292, 290);
        panel.setLayout(null);

        Border border = BorderFactory.createLineBorder(Color.black, 2);

        JLabel dateLabel = new JLabel(date);
        dateLabel.setBounds(5,5, 280, 40);
        dateLabel.setBorder(border);
        dateLabel.setOpaque(true);
        dateLabel.setBackground(Color.WHITE);
        dateLabel.setHorizontalAlignment(JLabel.CENTER);
        dateLabel.setVerticalAlignment(JLabel.CENTER);
        dateLabel.setFont(new Font("Noto Sans", Font.PLAIN, 16)); // 폰트 및 글자 크기 설정
        panel.add(dateLabel);

        JTextArea contentLabel = new JTextArea(content);
        contentLabel.setText(content);
        contentLabel.setLayout(null);
        contentLabel.setBounds(5,43, 280, 242);
        contentLabel.setBorder(border);
        contentLabel.setLineWrap(true);
        contentLabel.setWrapStyleWord(true);
        contentLabel.setFont(new Font("Noto Sans", Font.PLAIN, 16)); // 폰트 및 글자 크기 설정
        panel.add(contentLabel);
        editFrame.add(panel);

        Border borderBtn = BorderFactory.createLineBorder(SettingClass.mainColor, 1);

        JButton check = new JButton("확인");
        check.setBounds(10, 295, 140, 40);
        check.setFont(new Font("Noto Sans", Font.PLAIN, 16)); // 폰트 및 글자 크기 설정
        check.setForeground(Color.WHITE);
        check.setBackground(SettingClass.mainColor);
        check.setBorder(borderBtn);
        check.setFocusPainted(false);
        editFrame.add(check);

        JButton cancel = new JButton("취소");
        cancel.setBounds(150, 295, 140, 40);
        cancel.setFont(new Font("Noto Sans", Font.PLAIN, 16)); // 폰트 및 글자 크기 설정
        cancel.setForeground(SettingClass.mainColor);
        cancel.setBackground(Color.WHITE);
        cancel.setBorder(borderBtn);
        cancel.setFocusPainted(false);
        editFrame.add(cancel);

        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setSchedule(date, contentLabel.getText(), contentArea);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                editFrame.dispose();
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editFrame.dispose();
            }
        });

        editFrame.setLocationRelativeTo(null);
        editFrame.setVisible(true);
    }
    static public void setSchedule(String date, String content, JTextArea contentArea) throws SQLException{
        Connection connection = Util.getConnection();

        Statement statement1 = connection.createStatement();
        String sql1 = "select EXISTS (SELECT date, content from scheduler WHERE date Like \""+date+"\" limit 1) as success";

        ResultSet result2 = statement1.executeQuery(sql1);
        result2.next();
        if(result2.getString(1).equals("1")){ // 이미 존재 한다.
            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE scheduler SET content = ? WHERE date = ?");
            preparedStatement2.setString(1, content);
            preparedStatement2.setString(2, date);
            preparedStatement2.executeUpdate();

            contentArea.setText(content);
            preparedStatement2.close();
        }else{// 존재하지 않는다.
            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO scheduler (date, content) VALUES(\""+ date+"\", \""+content+"\")");
            preparedStatement2.executeUpdate();
            contentArea.setText(content);
            preparedStatement2.close();
        }
        result2.close();
        statement1.close();

        connection.close();
    }
}
class RoundedButton extends JButton {
    private int arcWidth = 20;
    private int arcHeight = 20;

    SettingClass setting = new SettingClass();
    Color mainColor = setting.mainColor;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBackground(SettingClass.mainColor); // JButton의 배경색을 초록색으로 설정
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(getBackground());
            setForeground(SettingClass.mainColor);
        } else {
            g.setColor(getBackground());
            setForeground(SettingClass.mainColor);
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcHeight));
    }

}