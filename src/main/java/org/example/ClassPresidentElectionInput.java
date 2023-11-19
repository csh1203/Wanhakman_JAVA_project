package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassPresidentElectionInput {
    private JFrame frame;
    private List<CandidatePanel> panels;
    private JTextField countTextField;

    public ClassPresidentElectionInput() throws SQLException {
        frame = new JFrame("회장 선거");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setBackground(Color.WHITE);

        JPanel box = new JPanel();
        box.setOpaque(true);
        box.setBounds(176, 125, 400, 450);
        box.setBorder(new LineBorder(SettingClass.mainColor, 3));

        JLabel titlebox = new JLabel("후보자");
        titlebox.setHorizontalAlignment(SwingConstants.CENTER);
        titlebox.setVerticalAlignment(SwingConstants.CENTER);
        SettingClass.customFont(titlebox, Font.PLAIN, 36);
        titlebox.setForeground(SettingClass.mainColor);
        titlebox.setBounds(176, 150, 400, 60);
        frame.add(titlebox);

        JLabel underlinebox = new JLabel();
        underlinebox.setBounds(226, 235, 300, 2);
        underlinebox.setOpaque(true);
        underlinebox.setBackground(SettingClass.mainColor);
        box.add(underlinebox);
        frame.add(underlinebox);
        frame.add(box);

        JLabel title = new JLabel("투표할 인원");
        SettingClass.customFont(title, Font.PLAIN, 28);
        title.setBounds(611, 150, 400, 60);
        frame.add(title);

        countTextField = new JTextField();
        countTextField.setBounds(781, 150, 120, 60);
        frame.add(countTextField);

        JLabel subtitle = new JLabel("명");
        SettingClass.customFont(subtitle, Font.PLAIN, 28);
        subtitle.setBounds(911, 150, 400, 60);
        frame.add(subtitle);

        JLabel underline = new JLabel();
        underline.setBounds(611, 235, 400, 2);
        underline.setOpaque(true);
        underline.setBackground(Color.BLACK);
        frame.add(underline);

        String contentText = "차례대로 한 명씩 나와 키보드를 통해 해당 후보자의 기호를 입력해주세요! 후보자 기호 이외의 다른 번호를 입력할 경우, 자동 기권이 됩니다.";
        JTextArea contents = new JTextArea(contentText);
        SettingClass.customFont(contents, Font.PLAIN, 20);
        contents.setBounds(611, 285, 400, 300);
        contents.setEditable(false);
        contents.setLineWrap(true);
        contents.setWrapStyleWord(true);
        contents.setOpaque(true);
        frame.add(contents);

        JButton nextBtn = new JButton("투표하기");
        nextBtn.setOpaque(true);
        nextBtn.setBackground(SettingClass.mainColor);
        SettingClass.customFont(nextBtn, Font.BOLD, 36);
        nextBtn.setForeground(Color.WHITE);
        nextBtn.setHorizontalAlignment(SwingConstants.CENTER);
        nextBtn.setVerticalAlignment(SwingConstants.CENTER);
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextPage();
            }
        });
        nextBtn.setBounds(476, 624, 380, 59);
        frame.add(nextBtn);

        panels = new ArrayList<>();
        for (int i = 1; i <= MAX_CANDIDATES; i++) {
            CandidatePanel panel = new CandidatePanel(this, i);
            panels.add(panel);
        }

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void nextPage() {
        // Check if countTextField is filled
        int voteCount = getVoteCount();
        if (voteCount <= 0) {
            JOptionPane.showMessageDialog(frame, "투표할 인원을 입력해주세요.");
        } else {
            // Pass the required information to ClassPresidentElectionVoting
            try {
                new ClassPresidentElectionVoting(voteCount, getCandidateNames());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int getVoteCount() {
        try {
            return Integer.parseInt(countTextField.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private List<String> getCandidateNames() {
        List<String> candidateNames = new ArrayList<>();
        for (CandidatePanel panel : panels) {
            candidateNames.add(panel.getCandidateName());
        }
        return candidateNames;
    }

    private boolean areAllTextFieldsFilled() {
        for (CandidatePanel panel : panels) {
            if (!panel.isTextFieldFilled()) {
                return false;
            }
        }
        return true;
    }

    private class CandidatePanel extends JPanel {
        private JTextField textField;

        public CandidatePanel(ClassPresidentElectionInput parent, int number) {
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

            add(saveBtn, BorderLayout.WEST);
            add(textField, BorderLayout.CENTER);
        }

        public String getCandidateName() {
            return textField.getText().trim();
        }

        public boolean isTextFieldFilled() {
            return !textField.getText().trim().isEmpty();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new ClassPresidentElectionInput();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static final int MAX_CANDIDATES = 5;
}
