package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingClass {
    static public Color mainColor = new Color(0x47815E);

    public static void getMainColor() throws SQLException {
        Connection connection = Util.getConnection();

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT color FROM main_color WHERE id = 1");

        result.next();
        SettingClass.mainColor = Color.decode(result.getString("color"));

        // 리소스 해제
        result.close();
        statement.close();
        connection.close();
    }
    public static void customFont(JComponent component, int fontStyle, int fontSize) {
        String fontPath = "NotoSansKR-Medium.ttf"; // 다운로드한 파일명에 맞게 수정
        Font notoSansFont;
        // 폰트 로딩
        try {
            notoSansFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(fontStyle, fontSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(notoSansFont);

            component.setFont(notoSansFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // 폰트 로딩에 실패한 경우 예외 처리
        }
    }
    public static void LightCustomFont(JComponent component, int fontStyle, int fontSize) {
        String fontPath = "NotoSansKR-Light.ttf"; // 다운로드한 파일명에 맞게 수정
        Font notoSansFont;
        // 폰트 로딩
        try {
            notoSansFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontPath)).deriveFont(fontStyle, fontSize);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(notoSansFont);

            component.setFont(notoSansFont);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // 폰트 로딩에 실패한 경우 예외 처리
        }
    }
}
