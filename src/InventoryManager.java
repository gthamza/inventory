import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryManager extends JFrame {

    public InventoryManager() {
        // Frame settings
        setTitle("Inventory Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Sidebar menu panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.DARK_GRAY);
        sidebarPanel.setLayout(new GridLayout(10, 1, 0, 10));
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));

        String[] menuItems = {"Home", "Products", "Customers", "Sales", "Purchase", "Users"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setForeground(Color.LIGHT_GRAY);
            button.setBackground(Color.DARK_GRAY);
            button.setFocusPainted(false);
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBorderPainted(false);
            sidebarPanel.add(button);


            if(item.equals("Products")){
           button.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   new product();
               }
           });
            }
        }

        // Logout button
        JButton logoutButton = new JButton("Sign out");
        logoutButton.setForeground(Color.ORANGE);
        logoutButton.setBackground(Color.DARK_GRAY);
        logoutButton.setFocusPainted(false);
        logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
        logoutButton.setBorderPainted(false);
        sidebarPanel.add(logoutButton);

        // Main content panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.BLACK);

        JLabel welcomeLabel = new JLabel("Welcome,  Admin.(HAMZA)");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.LIGHT_GRAY);
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);

        JLabel userLabel = new JLabel("User:  Admin (HAMZA)");
        userLabel.setFont(new Font("Serif", Font.PLAIN, 15));
        userLabel.setForeground(Color.LIGHT_GRAY);
        headerPanel.add(userLabel, BorderLayout.EAST);

        // Info label
        JLabel infoLabel = new JLabel("<html>Manage your inventory, transactions and personnel, all in one place.<br>Click on the Menu button to start.</html>");
        infoLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        infoLabel.setForeground(Color.LIGHT_GRAY);
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(infoLabel, BorderLayout.CENTER);

        // Add panels to frame
        add(sidebarPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Logout button action
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


        // Frame settings
        setSize(800, 600);
        setLocationRelativeTo(null); // Center the frame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryManager::new);
    }
}