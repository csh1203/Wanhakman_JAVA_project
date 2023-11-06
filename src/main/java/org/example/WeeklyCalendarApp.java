package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class WeeklyCalendarApp {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private JPanel calendarPanel;
    private Date currentDate;

    public WeeklyCalendarApp() {
        currentDate = getStartOfThisWeek(); // 이번주 월요일 날짜를 사용

        JFrame frame = new JFrame("Weekly Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());

        calendarPanel = new JPanel();
        updateCalendar(currentDate);
        mainPanel.add(calendarPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton prevWeekButton = new JButton("이전 주");
        prevWeekButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.WEEK_OF_YEAR, -1); // 이전 주로 이동
                currentDate = calendar.getTime();
                updateCalendar(currentDate);
            }
        });
        JButton nextWeekButton = new JButton("다음 주");
        nextWeekButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.WEEK_OF_YEAR, 1); // 다음 주로 이동
                currentDate = calendar.getTime();
                updateCalendar(currentDate);
            }
        });

        buttonPanel.add(prevWeekButton);
        buttonPanel.add(nextWeekButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void updateCalendar(Date date) {
        calendarPanel.removeAll();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 이번 주 월요일부터 일요일까지의 날짜를 표시
        for (int i = 0; i < 7; i++) {
            JLabel label = new JLabel(dateFormat.format(calendar.getTime()));
            calendarPanel.add(label);
            calendar.add(Calendar.DAY_OF_YEAR, 1); // 다음 날짜로 이동
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private Date getStartOfThisWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 이번 주 월요일
        return calendar.getTime();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WeeklyCalendarApp();
            }
        });
    }
}