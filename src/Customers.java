import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Customers extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public Customers() {
        setTitle("Customer List");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Side panel
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

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (item) {
                        case "Products":
                            new product(); // Assuming you have a class named 'Products'
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

        // Main panel
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.BLACK);

        // Header panel
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.BLACK);

        // Panel for welcome and product labels
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBackground(Color.BLACK);

        // Panel for product label and search panel
        JPanel productSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        productSearchPanel.setBackground(Color.BLACK);

        JLabel productLabel = new JLabel("CUSTOMER LIST");
        productLabel.setFont(new Font("Serif", Font.BOLD, 20));
        productLabel.setForeground(Color.LIGHT_GRAY);
        productSearchPanel.add(productLabel);

        // Add space between "PRODUCTS" label and search components
        productSearchPanel.add(Box.createHorizontalStrut(20));

        JLabel searchLabel = new JLabel("Search :");
        searchLabel.setFont(new Font("Serif", Font.BOLD, 20));
        searchLabel.setForeground(Color.LIGHT_GRAY);
        productSearchPanel.add(searchLabel);

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Serif", Font.PLAIN, 15));
        searchField.setBackground(Color.BLACK);
        searchField.setForeground(Color.WHITE);
        productSearchPanel.add(searchField);

        labelPanel.add(productSearchPanel, BorderLayout.WEST);

        JButton searchButton = new JButton("FIND");
        searchButton.setBackground(Color.BLACK);
        searchButton.setForeground(Color.WHITE);
        productSearchPanel.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().toLowerCase();
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
                table.setRowSorter(sorter);

                if (searchText.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1));
                }
            }
        });

        // Panel for Add and Refresh buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.BLACK);

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Serif", Font.BOLD, 20));
        addButton.setForeground(Color.LIGHT_GRAY);
        addButton.setBackground(Color.DARK_GRAY);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);

        JButton refreshButton = new JButton("REFRESH");
        refreshButton.setFont(new Font("Serif", Font.BOLD, 20));
        refreshButton.setForeground(Color.LIGHT_GRAY);
        refreshButton.setBackground(Color.DARK_GRAY);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);

        buttonPanel.add(refreshButton);
        buttonPanel.add(addButton);
        header.add(labelPanel);
        header.add(buttonPanel);

        main.add(header, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("ID");
        tableModel.addColumn("NAME");
        tableModel.addColumn("Phone");

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

        add(sidePanel, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);

        loadData();

        setLocationRelativeTo(null);
        setVisible(true);

        // Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popup = new JFrame("Add New Customer");
                popup.setResizable(false);
                popup.setLayout(new GridLayout(4, 2, 10, 10));
                popup.setSize(400, 300);
                popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                popup.getContentPane().setBackground(Color.BLACK);

                JLabel nameLabel = new JLabel("Name");
                JTextField nameField = new JTextField();

                JLabel phoneLabel = new JLabel("Phone");
                JTextField phoneField = new JTextField();

                JButton saveButton = new JButton("SAVE");
                nameLabel.setForeground(Color.LIGHT_GRAY);
                nameLabel.setFont(new Font("Serif", Font.BOLD, 15));
                nameField.setBackground(Color.BLACK);
                nameField.setForeground(Color.WHITE);
                nameField.setFont(new Font("Serif", Font.PLAIN, 15));

                phoneLabel.setForeground(Color.LIGHT_GRAY);
                phoneLabel.setFont(new Font("Serif", Font.BOLD, 15));
                phoneField.setBackground(Color.BLACK);
                phoneField.setForeground(Color.WHITE);
                phoneField.setFont(new Font("Serif", Font.PLAIN, 15));

                saveButton.setBackground(Color.DARK_GRAY);
                saveButton.setForeground(Color.LIGHT_GRAY);
                saveButton.setFont(new Font("Serif", Font.BOLD, 15));
                saveButton.setFocusPainted(false);
                saveButton.setBorderPainted(false);

                popup.add(nameLabel);
                popup.add(nameField);
                popup.add(phoneLabel);
                popup.add(phoneField);
                popup.add(new JLabel());
                popup.add(saveButton);

                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String customerName = nameField.getText();
                        String customerPhone = phoneField.getText();

                        insertCustomer(customerName, customerPhone);
                        tableModel.setRowCount(0);
                        loadData();

                        popup.dispose();
                    }
                });

                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            }
        });

        // Refresh button action
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.setRowCount(0);
                loadData();
            }
        });
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/inventory"; // Change as per your DB config
        String user = "root"; // DB user
        String password = "password123"; // DB password

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT `id`, `NAME`, `phone` FROM `customerlist`;")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("NAME");
                String phone = resultSet.getString("phone");

                tableModel.addRow(new Object[]{id, name, phone});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertCustomer(String name, String phone) {
        String url = "jdbc:mysql://localhost:3306/inventory"; // Change as per your DB config
        String user = "root"; // DB user
        String password = "password123"; // DB password

        String insertQuery = "INSERT INTO `customerlist` (`NAME`, `phone`) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phone);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Customers::new);
    }
}
