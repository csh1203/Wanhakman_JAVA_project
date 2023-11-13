package org.example;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.sql.SQLException;

public class DetermineRole {
    private JFrame frame;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton addButton;

    public DetermineRole() {
        frame = new JFrame("1인 1역 정하기");
        frame.setBounds(100, 100, 1280, 832);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);

        JLabel title = new RoundedLabel("우리반 1인 1역");
        title.setFont(new Font("Noto Sans", Font.BOLD, 36));
        title.setForeground(Color.BLACK);
        title.setBounds(500, 80, 400, 60);
        frame.add(title);

        JLabel homeBtn = new JLabel();
        homeBtn.setBounds(1150, 35, 70, 70);
        homeBtn.setIcon(new ImageIcon("C:\\java_Wanhakman\\Wanhakman_JAVA_project\\img\\homeBtn.png"));
        frame.getContentPane().add(homeBtn);

        homeBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                try {
                    new Main();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JLabel button1 = new RoundedLabel("인쇄");
        button1.setFont(new Font("Noto Sans", Font.BOLD, 14));
        button1.setBounds(900, 697, 80, 40);
        button1.setHorizontalAlignment(SwingConstants.CENTER);
        button1.setBackground(Color.decode("#47815E"));
        button1.setOpaque(true);
        button1.setForeground(Color.WHITE);
        frame.add(button1);

        JLabel button2 = new RoundedLabel("저장");
        button2.setFont(new Font("Noto Sans", Font.BOLD, 14));
        button2.setBounds(1000, 697, 80, 40);
        button2.setHorizontalAlignment(SwingConstants.CENTER);
        button2.setBackground(Color.decode("#47815E"));
        button2.setOpaque(true);
        button2.setForeground(Color.WHITE);
        frame.add(button2);

        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ("추가".equals(button2.getText())) {
                    tableModel.addRow(new String[]{"", "", ""});
                }
            }
        });

        JLabel button3 = new RoundedLabel("편집");
        button3.setFont(new Font("Noto Sans", Font.BOLD, 14));
        button3.setBounds(1100, 697, 80, 40);
        button3.setHorizontalAlignment(SwingConstants.CENTER);
        button3.setBackground(Color.decode("#47815E"));
        button3.setOpaque(true);
        button3.setForeground(Color.WHITE);
        frame.add(button3);

        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (button3.getText().equals("편집")) {
                    button1.setVisible(false);
                    button2.setText("추가");
                    button3.setText("확인");
                } else {
                    button1.setVisible(true);
                    button2.setText("저장");
                    button3.setText("편집");
                }
            }
        });

        tableModel = new DefaultTableModel(new String[]{"역할", "하는 일", "이름"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public Object getValueAt(int row, int column) {
                return super.getValueAt(row, column);
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                super.setValueAt(aValue, row, column);
                fireTableCellUpdated(row, column);
            }
        };
        tableModel.setRowCount(13);

        table = new JTable(tableModel) {
            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    @Override
                    public TableCellRenderer getDefaultRenderer() {
                        return new DefaultTableCellRenderer() {
                            @Override
                            public Component getTableCellRendererComponent(JTable table, Object value,
                                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                                component.setBackground(Color.decode("#47815E"));
                                component.setFont(new Font("Noto Sans", Font.BOLD, 20));
                                component.setForeground(Color.WHITE);
                                setHorizontalAlignment(SwingConstants.CENTER);
                                return component;
                            }
                        };
                    }
                };
            }
            public TableCellRenderer getCellRenderer(int row, int column) {
                return new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                                                                   boolean isSelected, boolean hasFocus, int row, int column) {
                        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        setHorizontalAlignment(SwingConstants.CENTER);
                        setFont(new Font("Noto Sans", Font.PLAIN, 14));
                        return component;
                    }
                };
            }
        };
        table.setRowHeight(25);

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(180, 180, 900, 360);
        frame.add(tableScrollPane);

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private class RoundedLabel extends JLabel {
        public RoundedLabel(String text) {
            super(text);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
            super.paintComponent(g2d);
            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DetermineRole();
            }
        });
    }
}
