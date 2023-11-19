package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassPresidentElectionVoting {
    private JFrame frame;
    private JPanel panelContainer;
    private List<CandidatePanel> panels;
    private JScrollPane scrollPane;

    // Add fields to store voteCount and candidateNames
    private int voteCount;
    private List<String> candidateNames;
    private int i;
    private JLabel title;
    private JLabel result11;
    private JLabel result22;
    private int[] result; // Array to store accumulated values

    public ClassPresidentElectionVoting(int voteCount, List<String> candidateNames) throws SQLException {
        // Initialize the fields
        this.voteCount = voteCount;
        this.candidateNames = candidateNames;
        this.result = new int[voteCount + 1]; // +1 to account for 0-based indexing

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
        result1.setBounds(465, 584, 400, 60);
        frame.add(result1);

        result11 = new JLabel("i + \" / \" + voteCount");
        SettingClass.customFont(result11, Font.PLAIN, 20);
        result11.setBounds(665, 584, 400, 60);
        result11.setVisible(false);
        frame.add(result11);

        JLabel result2 = new JLabel("투표 미완료 학생");
        SettingClass.customFont(result2, Font.PLAIN, 20);
        result2.setBounds(465, 624, 400, 60);
        frame.add(result2);

        result22 = new JLabel("(voteCount - i) + \" / \" + voteCount");
        SettingClass.customFont(result22, Font.PLAIN, 20);
        result22.setBounds(665, 624, 400, 60);
        result22.setVisible(false);
        frame.add(result22);

        panels = new ArrayList<>();
        panelContainer = new JPanel();
        panelContainer.setBackground(Color.WHITE);
        panelContainer.setBorder(null);
        panelContainer.setLayout(new BoxLayout(panelContainer, BoxLayout.Y_AXIS));

        // Create and add CandidatePanel instances based on voteCount
        for (i = 1; i <= voteCount; i++) {
            CandidatePanel panel = new CandidatePanel(i);
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

            title.setVisible(true);
            result11.setVisible(true);
            result22.setVisible(true);

            saveBtn.addActionListener(e -> {
                i=1;
                // Check if the current vote is within the valid range
                if (i <= voteCount) {
                    // Update the result array
                    String textFieldContent = textField.getText().trim();

                    // Check if the content is not empty before parsing
                    if (!textFieldContent.isEmpty()) {
                        try {
                            int vote = Integer.parseInt(textFieldContent);
                            System.out.println("Result for student " + (i - 1) + ": " + vote);

                            // Increment i and update the UI components
                            i++;
                            title.setText(voteCount + "명 투표자 중 " + i + "번 째 투표자 투표 진행중...");
                            result11.setText(i + " / " + voteCount);
                            result22.setText((voteCount - i) + " / " + voteCount);

                            // Check if the voting is completed
                            if (i > voteCount) {
                                // Perform any necessary actions when voting is completed
                                System.out.println("Voting completed for all students.");
                                // You can add additional logic here if needed
                            }
                        } catch (NumberFormatException ex) {
                            System.err.println("Invalid input. Please enter a valid integer.");
                        }
                    } else {
                        // Show an error message if the input is empty
                        System.err.println("Input cannot be empty. Please enter a valid integer.");
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
                new ClassPresidentElectionVoting(5, null); // Provide voteCount and candidateNames as needed
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
