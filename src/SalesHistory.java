import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SalesHistory extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private DefaultTableModel detailTableModel;
    private JTable detailTable;

    public SalesHistory() {
        setTitle("Sales History");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Create and configure the side panel
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.DARK_GRAY);
        sidePanel.setLayout(new GridLayout(10, 1, 0, 10));
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));
        String[] menu = {"HOME", "Products", "Customers", "Current Sales", "History"};

        for (String item : menu) {
            JButton button = new JButton(item);
            button.setForeground(Color.LIGHT_GRAY);
            button.setBackground(Color.DARK_GRAY);
            button.setFocusPainted(false);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBorderPainted(false);
            sidePanel.add(button);

            // Add action listeners for each button
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (item) {
                        case "Products":
                            new product(); // Assuming you have a class named 'product'
                            dispose();
                            break;
                        case "Customers":
                            new Customers();
                            dispose();
                            break;
                        case "HOME":
                            new HOME();
                            break;
                        case "Current Sales":
                            new currentsales();
                            dispose();
                            break;
                        case "History":
                            new SalesHistory();
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "Clicked: " + item);
                            break;
                    }
                }
            });
        }

        // Logout button
        JButton logoutButton = new JButton("Sign out");
        logoutButton.setForeground(Color.ORANGE);
        logoutButton.setBackground(Color.DARK_GRAY);
        logoutButton.setFocusPainted(false);
        logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logoutButton.setBorderPainted(false);
        sidePanel.add(logoutButton);

        // Register the logout action listener here, outside of the addButton action listener
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to logout? You will have to login again.",
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (response == JOptionPane.YES_OPTION) {
                    dispose();
                    new LOGIN();
                }
            }
        });

        // Create and configure the main panel
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.BLACK);

        // Header panel
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.BLACK);

        // Panel for date and refresh
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        datePanel.setBackground(Color.BLACK);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font("Serif", Font.BOLD, 20));
        dateLabel.setForeground(Color.LIGHT_GRAY);
        datePanel.add(dateLabel);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        JTextField dateField = new JTextField(formatter.format(new Date()));
        dateField.setPreferredSize(new Dimension(120, 25));

        dateField.setFont(new Font("Serif", Font.PLAIN, 15));
        dateField.setBackground(Color.BLACK);
        dateField.setForeground(Color.WHITE);
        dateField.setEditable(false);
        dateField.setHorizontalAlignment(JTextField.CENTER);
        datePanel.add(dateField);

        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setFont(new Font("Serif", Font.BOLD, 20));
        refreshButton.setForeground(Color.LIGHT_GRAY);
        refreshButton.setBackground(Color.DARK_GRAY);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SalesHistory();
                dispose();
            }
        });

        datePanel.add(refreshButton);
        header.add(datePanel);
        main.add(header, BorderLayout.NORTH);

        // Summary table
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("Date");
        tableModel.addColumn("Total Sell Price");

        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.GRAY);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Serif", Font.BOLD, 20));

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBackground(Color.BLACK);
        cellRenderer.setForeground(Color.WHITE);
        table.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        main.add(scrollPane, BorderLayout.CENTER);

        // Detail table
        detailTableModel = new DefaultTableModel();
        detailTable = new JTable(detailTableModel);
        detailTableModel.addColumn("PROCODE");
        detailTableModel.addColumn("PRONAME");
        detailTableModel.addColumn("COSTPRICE");
        detailTableModel.addColumn("SELLPRICE");

        detailTable.setBackground(Color.BLACK);
        detailTable.setForeground(Color.WHITE);
        detailTable.setGridColor(Color.GRAY);

        JTableHeader detailTableHeader = detailTable.getTableHeader();
        detailTableHeader.setBackground(Color.BLACK);
        detailTableHeader.setForeground(Color.WHITE);
        detailTableHeader.setFont(new Font("Serif", Font.BOLD, 20));

        DefaultTableCellRenderer detailCellRenderer = new DefaultTableCellRenderer();
        detailCellRenderer.setBackground(Color.BLACK);
        detailCellRenderer.setForeground(Color.WHITE);
        detailTable.setDefaultRenderer(Object.class, detailCellRenderer);

        JScrollPane detailScrollPane = new JScrollPane(detailTable);
        main.add(detailScrollPane, BorderLayout.SOUTH);

        add(sidePanel, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);

        loadData();

        // Add mouse listener to table for double-click events
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    String selectedDate = tableModel.getValueAt(row, 0).toString();
                    loadDetailData(selectedDate);
                }
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/inventory";
        String user = "root";
        String password = "password123";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT DATE(`timestamp`) AS sale_date, SUM(`product_sell_price`) AS total_sell_price " +
                    "FROM `currectsales` " +
                    "GROUP BY sale_date";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String saleDate = resultSet.getString("sale_date");
                double totalSellPrice = resultSet.getDouble("total_sell_price");

                // Add the row to the tableModel
                tableModel.addRow(new Object[]{saleDate, totalSellPrice});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDetailData(String selectedDate) {
        System.out.println("Selected Date: " + selectedDate);
        String url = "jdbc:mysql://localhost:3306/inventory";
        String user = "root";
        String password = "password123";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT `idcurrectsales`, `product_name`, `product_cost_price`, `product_sell_price` " +
                    "FROM `currectsales` " +
                    "WHERE DATE(`timestamp`) = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, selectedDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Clear previous data from detailTableModel
            detailTableModel.setRowCount(0);

            while (resultSet.next()) {
                String productId = resultSet.getString("idcurrectsales");
                String productName = resultSet.getString("product_name");
                double costPrice = resultSet.getDouble("product_cost_price");
                double sellPrice = resultSet.getDouble("product_sell_price");

                // Add the row to the detailTableModel
                detailTableModel.addRow(new Object[]{productId, productName, costPrice, sellPrice});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SalesHistory::new);
    }
}
