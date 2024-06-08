import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

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
        String[] menu = {"Home", "Products", "Customers", "Sales", "Purchase", "Users"};

        for (String item : menu) {
            JButton button = new JButton(item);
            button.setForeground(Color.LIGHT_GRAY);
            button.setBackground(Color.DARK_GRAY);
            button.setFocusPainted(false);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBorderPainted(false);
            sidepanal.add(button);
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
                    // Logic for logout (e.g., close current window, show login window)
                    dispose();
                    new LOGIN(); // Assuming you have a LoginPage class for login
                }
            }
        });

        // Main panel
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(Color.BLACK);  // Set main panel background to black

        // Header panel
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.BLACK);  // Set header panel background to black

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

        //action listner for seaarch button
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

                // Popup components
                JLabel procodelabel = new JLabel("PROCODE");
                JTextField procodefield = new JTextField();

                JLabel pronamelabel = new JLabel("PRONAME");
                JTextField pronamefield = new JTextField();

                JLabel costpricelabel = new JLabel("COSTPRICE");
                JTextField costpricefield = new JTextField();

                JLabel sellpricelabel = new JLabel("SELLPRICE");
                JTextField sellpricefield = new JTextField();

                JButton save = new JButton("SAVE");

                // Setting styles for the popup
                procodelabel.setForeground(Color.LIGHT_GRAY);
                procodelabel.setFont(new Font("Serif", Font.BOLD, 15));
                procodefield.setBackground(Color.BLACK);
                procodefield.setForeground(Color.WHITE);
                procodefield.setFont(new Font("Serif", Font.PLAIN, 15));

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

                popup.add(procodelabel);
                popup.add(procodefield);
                popup.add(pronamelabel);
                popup.add(pronamefield);
                popup.add(costpricelabel);
                popup.add(costpricefield);
                popup.add(sellpricelabel);
                popup.add(sellpricefield);
                popup.add(new JLabel());
                popup.add(save);

                // Action listener for the save button
                save.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String productCode = procodefield.getText();
                        String productName = pronamefield.getText();
                        double costPrice = Double.parseDouble(costpricefield.getText());
                        double sellPrice = Double.parseDouble(sellpricefield.getText());

                        tableModel.addRow(new Object[]{productCode, productName, costPrice, sellPrice});
                        popup.dispose();
                    }
                });

                popup.setLocationRelativeTo(null);
                popup.setVisible(true);
            }
        });

        addButtonPanel.add(addButton, BorderLayout.EAST);
        header.add(labelPanel);
        header.add(addButtonPanel);

        main.add(header, BorderLayout.NORTH);

        // Table setup
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("PROCODE");
        tableModel.addColumn("PRONAME");
        tableModel.addColumn("COSTPRICE");
        tableModel.addColumn("SELLPRICE");

        // Set table and header background and foreground colors
        table.setBackground(Color.BLACK);
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.GRAY);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(Color.BLACK);
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setFont(new Font("Serif", Font.BOLD, 20));

        // Custom cell renderer to set background and foreground colors for table cells
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setBackground(Color.BLACK);
        cellRenderer.setForeground(Color.WHITE);
        table.setDefaultRenderer(Object.class, cellRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        main.add(scrollPane, BorderLayout.CENTER);

        // Add panels to the frame
        add(sidepanal, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);

        // Load data from the database
        loadData();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadData() {
        String url = "jdbc:mysql://localhost:3306/inventory"; // Change as per your DB config
        String user = "root"; // DB user
        String password = "password"; // DB password

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT * FROM products";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String productCode = resultSet.getString("productcode");
                String productName = resultSet.getString("productname");
                double costPrice = resultSet.getDouble("costprice");
                double sellPrice = resultSet.getDouble("sellprice");

                tableModel.addRow(new Object[]{productCode, productName, costPrice, sellPrice});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(product::new);
    }
}
