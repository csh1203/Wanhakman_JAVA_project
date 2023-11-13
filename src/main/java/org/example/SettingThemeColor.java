package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;

public class SettingThemeColor extends JPanel {

    Color mainColor;
    JLabel InnerLabel = new JLabel();
    JButton setColorButton = new JButton();
    public SettingThemeColor() throws SQLException {
        setBounds(0,0, 1045, 832);
        getMainColor();

        setLayout(null);

        JLabel currectThemeColorTitle = new JLabel("현재 테마 컬러");
        currectThemeColorTitle.setBounds(124, 111, 200, 41);
        SettingClass.customFont(currectThemeColorTitle, Font.BOLD, 25);
        add(currectThemeColorTitle);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set the stroke (line thickness) and draw the rounded rectangle
                Stroke oldStroke = g2d.getStroke();
                g2d.setStroke(new BasicStroke(1f)); // 1px thickness
                g2d.setColor(Color.BLACK);

                int width = 261;  // Adjusted size
                int height = 105;  // Adjusted size
                int arc = 20; // 20px rounded corners

                g2d.draw(new RoundRectangle2D.Double(0, 0, width, height, arc, arc));

                g2d.setStroke(oldStroke);
            }
        };
//        panel.setBackground(Color.pink);
        panel.setBounds(124, 189, 262, 106);
        panel.setLayout(null);
        add(panel);

        Border roundedBorder = BorderFactory.createLineBorder(mainColor, 20, true);

        setColorButton.setLayout(null);
        setColorButton.setBounds(10, 10, 241, 85);
        setColorButton.setOpaque(false);
        setColorButton.setFocusPainted(false);
        setColorButton.setContentAreaFilled(false);
        setColorButton.setBorder(roundedBorder);
        panel.add(setColorButton);

        InnerLabel.setBounds(10, 10, 221, 65);
        SettingClass.customFont(InnerLabel, Font.PLAIN, 20);
        InnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        InnerLabel.setVerticalAlignment(SwingConstants.CENTER);
        InnerLabel.setOpaque(true);
        InnerLabel.setBackground(mainColor);
        setColorButton.add(InnerLabel);

        setColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color selectedColor = JColorChooser.showDialog(SettingThemeColor.this,"Choose a Color", Color.BLACK);

                if (selectedColor != null) {
                    // Get RGB values
                    int red = selectedColor.getRed();
                    int green = selectedColor.getGreen();
                    int blue = selectedColor.getBlue();

                    // Convert RGB to hex
                    String hex = String.format("#%02X%02X%02X", red, green, blue);

                    mainColor = selectedColor;

                    setTextColorBasedOnBackground(selectedColor);
                    try {
                        setMainColor(hex);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
    }
    private void setTextColorBasedOnBackground(Color color) {
        int brightness = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());

        Color textColor = (brightness > 128) ? Color.BLACK : Color.WHITE;
        InnerLabel.setForeground(textColor);
    }
    private void getMainColor() throws SQLException {
        Connection connection = Util.getConnection();

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT color FROM main_color WHERE id = 1");

        result.next();
        mainColor = Color.decode(result.getString("color"));
        Border roundedBorder = BorderFactory.createLineBorder(mainColor, 20, true);
        setColorButton.setBorder(roundedBorder);
        InnerLabel.setBackground(mainColor);

        InnerLabel.setText(result.getString("color"));

        SettingClass.mainColor = mainColor;
        // 리소스 해제
        result.close();
        statement.close();
        connection.close();

        SettingClass.getMainColor();
    }

    private void setMainColor(String color) throws SQLException {
        Connection connection = Util.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE main_color SET color = ? WHERE id = 1");
        preparedStatement.setString(1, color);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();

        getMainColor();


    }
}
