package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ClassPresidentElectionVoting {
    private JFrame frame;
    private JPanel panelContainer;
    private List<CandidatePanel> panels;
    private JScrollPane scrollPane;

    private int voteCount;
    private String[] result;
    private int i;
    private JLabel title;
    private JLabel result11;
    private JLabel result22;
    private List<String> candidateNamesList1;
    private int position;

    public ClassPresidentElectionVoting(int voteCount, List<String> candidateNamesList) throws SQLException {
        this.candidateNamesList1 = candidateNamesList;
        position = 100;

        this.voteCount = voteCount;
        this.i = 1;
        result = new String[candidateNamesList.size()];
        Arrays.fill(result, "0");

        frame = new JFrame("회장 선거");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBackground(Color.WHITE);

        title = new JLabel(voteCount + "명 투표자 중 " + (i-1) + "번째 투표자 투표 진행중...");
        SettingClass.customFont(title, Font.BOLD, 20);
        title.setBounds(470, 80, 400, 60);
        title.setVisible(false);
        frame.add(title);

        JLabel result1 = new JLabel("투표 완료 학생");
        SettingClass.customFont(result1, Font.PLAIN, 20);
        result1.setBounds(550, 584, 400, 60);
        frame.add(result1);

        result11 = new JLabel((i-1) + " / " + voteCount);
        SettingClass.customFont(result11, Font.BOLD, 24);
        result11.setBounds(700, 584, 400, 60);
        result11.setVisible(false);
        frame.add(result11);

        JLabel result2 = new JLabel("투표 미완료 학생");
        SettingClass.customFont(result2, Font.PLAIN, 20);
        result2.setBounds(550, 624, 400, 60);
        frame.add(result2);

        result22 = new JLabel(0 + " / " + voteCount);
        SettingClass.customFont(result22, Font.BOLD, 24);
        result22.setBounds(700, 624, 400, 60);
        result22.setVisible(false);
        frame.add(result22);

        panels = new ArrayList<>();
        panelContainer = new JPanel();
        panelContainer.setBackground(Color.WHITE);
        panelContainer.setBorder(null);
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));
        panelContainer.setBorder(BorderFactory.createLineBorder(SettingClass.mainColor, 2));

        title.setVisible(true);
        result11.setVisible(true);
        result22.setVisible(true);

        for (int j = 1; j <= candidateNamesList.size(); j++) {
            CandidatePanel panel = new CandidatePanel(j);
            panels.add(panel);
            panelContainer.add(panel);
        }

        // Set up the JScrollPane
//        scrollPane = new JScrollPane(panelContainer);
//        scrollPane.setBounds(442, 165, 396, 390);
//        scrollPane.setBorder(BorderFactory.createLineBorder(SettingClass.mainColor, 2));
//        frame.add(scrollPane);

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
        private int panelIndex;

        public CandidatePanel(int number) {

            panelIndex = number - 1;
            setLayout(new BorderLayout());

            JButton saveBtn[] = new JButton[candidateNamesList1.size()];

//            saveBtn.setOpaque(true);
//            SettingClass.customFont(saveBtn, Font.BOLD, 25);
//            saveBtn.setBackground(Color.WHITE);
//            saveBtn.setForeground(Color.BLACK);
//            saveBtn.setBorder(BorderFactory.createLineBorder(SettingClass.mainColor, 2));
//            saveBtn.setHorizontalAlignment(SwingConstants.CENTER);
//            saveBtn.setVerticalAlignment(SwingConstants.CENTER);
//            saveBtn.setPreferredSize(new Dimension(70, 70));

            JLabel[] label = new JLabel[candidateNamesList1.size()];

            JPanel box = new JPanel();
            box.setLayout(new GridLayout(candidateNamesList1.size(), 1));
            box.setOpaque(true);
            box.setBounds(176, 125, 400, 450);
            box.setBorder(new LineBorder(SettingClass.mainColor, 3));

            for (int i = 0; i < candidateNamesList1.size(); i++) {
                final int index = i;
                JPanel panelbox = new JPanel();
                panelbox.setLayout(new GridLayout(1, 2));
                label[i] = new JLabel(candidateNamesList1.get(i));
                label[i].setBounds(120, position, 200, 100);
                SettingClass.customFont(label[i], Font.PLAIN, 36);
                label[i].setForeground(SettingClass.mainColor);
                position += 50;
                panelbox.add(label[i]);

                saveBtn[i].setOpaque(true);
                SettingClass.customFont(saveBtn[i], Font.BOLD, 25);
                saveBtn[i].setBackground(Color.WHITE);
                saveBtn[i].setForeground(Color.BLACK);
                saveBtn[i].setBorder(BorderFactory.createLineBorder(SettingClass.mainColor, 2));
                saveBtn[i].setHorizontalAlignment(SwingConstants.CENTER);
                saveBtn[i].setVerticalAlignment(SwingConstants.CENTER);
                saveBtn[i].setPreferredSize(new Dimension(70, 70));
                saveBtn[i].setText((i + 1) + "");
                panelbox.add(label[i], saveBtn[i]);
                box.add(panelbox);

//                saveBtn[i].addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        if (index <= voteCount) {
////                        String textFieldContent = textField.getText().trim();
//                            title.setVisible(true);
//                            result11.setVisible(true);
//                            result22.setVisible(true);
//
////                            title.setText(voteCount + "명 투표자 중 " + index + "번째 투표자 투표 진행중...");
////                            result11.setText(index + " / " + voteCount);
////                            result22.setText((voteCount - index) + " / " + voteCount);
////
////                            int currentValue = Integer.parseInt(result[panelIndex]);
////                            result[panelIndex] = Integer.toString(currentValue + 1);
////
////                            index++;
//                        }
//                        if (index > voteCount) {
//                            SwingUtilities.invokeLater(() -> {
//                                try {
//                                    new ClassPresidentElectionResult();
//                                } catch (SQLException ex) {
//                                    throw new RuntimeException(ex);
//                                }
//                            });
//                            frame.dispose();
//                        }
//                    }
//                });
////
////            add(saveBtn, BorderLayout.WEST);
//            }
//            frame.add(box);
            }
        }
    }
}
