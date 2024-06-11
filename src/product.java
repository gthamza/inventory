import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class product extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public product() {
        setTitle("Product List");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Side panel
        JPanel sidepanal = new JPanel();
        sidepanal.setBackground(Color.DARK_GRAY);
        sidepanal.setLayout(new GridLayout(10, 1, 0, 10));
        sidepanal.setPreferredSize(new Dimension(200, getHeight()));
        String[] menu = {"HOME", "Products", "Customers", " Currect Sales", "History"};

        for (String item : menu) {
            JButton button = new JButton(item);
            button.setForeground(Color.LIGHT_GRAY);
            button.setBackground(Color.DARK_GRAY);
            button.setFocusPainted(false);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBorderPainted(false);
            sidepanal.add(button);

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
                            new Customers(); // Assuming you have a class named 'customer'
                            dispose();
                            break;
                        case "HOME":
                            new HOME();
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
        sidepanal.add(logoutButton);

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

        JLabel productLabel = new JLabel("PRODUCTS");
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

        JButton searchbutton = new JButton("FIND");
        searchbutton.setBackground(Color.black);
        searchbutton.setForeground(Color.white);
        productSearchPanel.add(searchbutton);

        //action listener for search button
        searchbutton.addActionListener(new ActionListener() {
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

        // Panel for Add button
        JPanel addButtonPanel = new JPanel(new BorderLayout());
        addButtonPanel.setBackground(Color.BLACK);

        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Serif", Font.BOLD, 20));
        addButton.setForeground(Color.LIGHT_GRAY);
        addButton.setBackground(Color.DARK_GRAY);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);

        JButton backButton = new JButton("REFRESH");
        backButton.setFont(new Font("Serif", Font.BOLD, 20));
        backButton.setForeground(Color.LIGHT_GRAY);
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(new Font("Serif", Font.BOLD, 20));
        dateLabel.setForeground(Color.LIGHT_GRAY);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date currentDate = new Date();
        JTextField dateField = new JTextField(formatter.format(currentDate));
        dateField.setPreferredSize(new Dimension(120, 25)); // Adjust width as needed
        dateField.setFont(new Font("Serif", Font.PLAIN, 15));
        dateField.setBackground(Color.BLACK);
        dateField.setForeground(Color.WHITE);
        dateField.setEditable(false);

        dateField.setHorizontalAlignment(JTextField.CENTER);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        datePanel.setBackground(Color.BLACK);
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        addButtonPanel.add(datePanel, BorderLayout.CENTER);


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new product();
                dispose();
            }
        });

        // POP UP FOR AT ADD BUTTON
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame popup = new JFrame("Add New Product");
                popup.setResizable(false);
                popup.setLayout(new GridLayout(5, 2, 10, 10));
                popup.setSize(400, 300);
                popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                popup.getContentPane().setBackground(Color.BLACK);

                JLabel pronamelabel = new JLabel("PRONAME");
                JTextField pronamefield = new JTextField();

                JLabel costpricelabel = new JLabel("COSTPRICE");
                JTextField costpricefield = new JTextField();

                JLabel sellpricelabel = new JLabel("SELLPRICE");
                JTextField sellpricefield = new JTextField();

                JButton save = new JButton("SAVE");
                pronamelabel.setForeground(Color.LIGHT_GRAY);
                pronamelabel.setFont(new Font("Serif", Font.BOLD, 15));
                pronamefield.setBackground(Color.BLACK);
                pronamefield.setForeground(Color.WHITE);
                pronamefield.setFont(new Font("Serif", Font.PLAIN, 15));

                costpricelabel.setForeground(Color.LIGHT_GRAY);
                costpricelabel.setFont(new Font("Serif", Font.BOLD, 15));
                costpricefield.setBackground(Color.BLACK);
                costpricefield.setForeground(Color.WHITE);
                costpricefield.setFont(new Font("Serif", Font.PLAIN, 15));

                sellpricelabel.setForeground(Color.LIGHT_GRAY);
                sellpricelabel.setFont(new Font("Serif", Font.BOLD, 15));
                sellpricefield.setBackground(Color.BLACK);
                sellpricefield.setForeground(Color.WHITE);
                sellpricefield.setFont(new Font("Serif", Font.PLAIN, 15));

                save.setBackground(Color.DARK_GRAY);
                save.setForeground(Color.LIGHT_GRAY);
                save.setFont(new Font("Serif", Font.BOLD, 15));
                save.setFocusPainted(false);
                save.setBorderPainted(false);

                popup.add(pronamelabel);
                popup.add(pronamefield);
                popup.add(costpricelabel);
                popup.add(costpricefield);
                popup.add(sellpricelabel);
                popup.add(sellpricefield);
                popup.add(new JLabel());
                popup.add(save);

                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String productName = pronamefield.getText();
                        int costPrice = Integer.parseInt(costpricefield.getText());
                        int sellPrice = Integer.parseInt(sellpricefield.getText());

                        insertStatement(productName, costPrice, sellPrice);
                        tableModel.setRowCount(0);
                        loadData();

                        popup.dispose();
                    }
                });

                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            }
        });
        addButtonPanel.add(backButton, BorderLayout.WEST);
        addButtonPanel.add(addButton, BorderLayout.EAST);
        header.add(labelPanel);
        header.add(addButtonPanel);

        main.add(header, BorderLayout.NORTH);


        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("PROCODE");
        tableModel.addColumn("PRONAME");
        tableModel.addColumn("COSTPRICE");
        tableModel.addColumn("SELLPRICE");

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


        add(sidepanal, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);


        loadData();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/inventory"; // Change as per your DB config
        String user = "root"; // DB user
        String password = "password123"; // DB password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT `products`.`product_id`,\n" +
                    "   `products`.`product_name`,\n" +
                    "    `products`.`product_cost`,\n" +
                    "    `products`.`product_sell_price`\n" +
                    "FROM `inventory`.`products`;\n";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String productCode = resultSet.getString("product_id");
                String productName = resultSet.getString("product_name");
                double costPrice = resultSet.getDouble("product_cost");
                double sellPrice = resultSet.getDouble("product_sell_price");

                tableModel.addRow(new Object[]{productCode, productName, costPrice, sellPrice});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertStatement(String name, int costPrice, int sellPrice) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "password123");
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `inventory`.`products` ( `product_name`, `product_cost`, `product_sell_price`) \n VALUES ( ? , ? , ?)");

            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, costPrice);
            preparedStatement.setInt(3, sellPrice);

            preparedStatement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(product::new);
    }
}
