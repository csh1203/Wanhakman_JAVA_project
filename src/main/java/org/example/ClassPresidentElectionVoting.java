package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassPresidentElectionVoting {
    private JFrame frame;
    private JPanel panelContainer;
    private List<CandidatePanel> panels;
    private JScrollPane scrollPane;

    private int voteCount;
    private List<String> candidateNames;
    private int i;
    private JLabel title;
    private JLabel result11;
    private JLabel result22;

    public ClassPresidentElectionVoting(int voteCount, List<String> candidateNames) throws SQLException {
        this.voteCount = voteCount;
        this.i = 1;

        frame = new JFrame("회장 선거");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBackground(Color.WHITE);

        title = new JLabel(voteCount + "명 투표자 중 " + i + "번 째 투표자 투표 진행중...");
        SettingClass.customFont(title, Font.BOLD, 20);
        title.setBounds(470, 80, 400, 60);
        title.setVisible(false);
        frame.add(title);

        JLabel result1 = new JLabel("투표 완료 학생");
        SettingClass.customFont(result1, Font.PLAIN, 20);
        result1.setBounds(550, 584, 400, 60);
        frame.add(result1);

        result11 = new JLabel(i + " / " + voteCount);
        SettingClass.customFont(result11, Font.BOLD, 24);
        result11.setBounds(700, 584, 400, 60);
        result11.setVisible(false);
        frame.add(result11);

        JLabel result2 = new JLabel("투표 미완료 학생");
        SettingClass.customFont(result2, Font.PLAIN, 20);
        result2.setBounds(550, 624, 400, 60);
        frame.add(result2);

        result22 = new JLabel((voteCount-i) + " / " + voteCount);
        SettingClass.customFont(result22, Font.BOLD, 24);
        result22.setBounds(700, 624, 400, 60);
        result22.setVisible(false);
        frame.add(result22);

        panels = new ArrayList<>();
        panelContainer = new JPanel();
        panelContainer.setBackground(Color.WHITE);
        panelContainer.setBorder(null);
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        title.setVisible(true);
        result11.setVisible(true);
        result22.setVisible(true);

        for (int j = 1; j <= candidateNames.size(); j++) {
            CandidatePanel panel = new CandidatePanel(j);
            panels.add(panel);
            panelContainer.add(panel);
        }

        // Set up the JScrollPane
        scrollPane = new JScrollPane(panelContainer);
        scrollPane.setBounds(442, 165, 396, 400);
        frame.add(scrollPane);

        JLabel text = new JLabel("신중하게 투표해주세요!");
        SettingClass.customFont(text, Font.PLAIN, 16);
        text.setBounds(580, 674, 400, 60);
        frame.add(text);

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private class CandidatePanel extends JPanel {
        private JTextField textField;

        public CandidatePanel(int number) {
            setLayout(new BorderLayout());

            JButton saveBtn = new JButton(Integer.toString(number) + ".");

            saveBtn.setOpaque(true);
            saveBtn.setBackground(SettingClass.mainColor);
            SettingClass.customFont(saveBtn, Font.BOLD, 25);
            saveBtn.setForeground(Color.WHITE);
            saveBtn.setHorizontalAlignment(SwingConstants.CENTER);
            saveBtn.setVerticalAlignment(SwingConstants.CENTER);
            saveBtn.setPreferredSize(new Dimension(70, 70));

            textField = new JTextField();
            textField.setPreferredSize(new Dimension(70, 70));

            saveBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (i <= voteCount) {
                        String textFieldContent = textField.getText().trim();
                        title.setVisible(true);
                        result11.setVisible(true);
                        result22.setVisible(true);

                        title.setText(voteCount + "명 투표자 중 " + i + "번째 투표자 투표 진행중...");
                        result11.setText(i + " / " + voteCount);
                        result22.setText((voteCount - i) + " / " + voteCount);
                        i++;
                    }
                }
            });


            add(saveBtn, BorderLayout.WEST);
            add(textField, BorderLayout.CENTER);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ClassPresidentElectionVoting(5, null);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
