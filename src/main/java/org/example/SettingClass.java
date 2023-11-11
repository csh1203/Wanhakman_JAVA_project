package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
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
}
