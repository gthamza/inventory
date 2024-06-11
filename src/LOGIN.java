import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LOGIN extends JFrame {

    static JLabel LOGIN = new JLabel("SHOP INVENTORY");
    static JLabel USERNAME = new JLabel("USERNAME :");
    static JTextField usertext = new JTextField();
    static JLabel PASSWORDLABEL = new JLabel("PASSWORD :");
    static JTextField password = new JTextField();
    JButton login = new JButton("LOGIN");

    JPanel head = new JPanel();
    JPanel userarea = new JPanel();
    JPanel passwordarea = new JPanel();
    JPanel buttonarea = new JPanel();

    public LOGIN() {
        // frame
        setTitle("LOGIN ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2)); // Layout with two columns
        setResizable(false);
        JPanel greenPanel = new JPanel();
        greenPanel.setBackground(Color.BLACK);

        // image in other panel
        ImageIcon logo = new ImageIcon("C:\\Users\\Hamza Waris\\IdeaProjects\\inventory\\src\\logo.jpg");
        Image scaledImage = logo.getImage().getScaledInstance(425, 350, Image.SCALE_SMOOTH);
        ImageIcon scaledLogo = new ImageIcon(scaledImage);

        // Use a JLabel to display the scaled image
        JLabel imageLabel = new JLabel(scaledLogo);
        greenPanel.add(imageLabel);

        // login data page
        JPanel blackPanel = new JPanel();
        blackPanel.setBackground(Color.BLACK);
        blackPanel.setLayout(new GridLayout(4, 1));

        // login heading
        LOGIN.setFont(new Font("serif", Font.BOLD, 35));
        LOGIN.setForeground(Color.LIGHT_GRAY);

        // user text label
        USERNAME.setFont(new Font("serif", Font.CENTER_BASELINE, 25));
        USERNAME.setForeground(Color.LIGHT_GRAY);

        // user name text field
        usertext.setPreferredSize(new Dimension(150, 24));
        usertext.setForeground(Color.DARK_GRAY);
        usertext.setBackground(Color.LIGHT_GRAY);

        // password label
        PASSWORDLABEL.setFont(new Font("serif", Font.BOLD, 25));
        PASSWORDLABEL.setForeground(Color.lightGray);
        // password in textfield
        password.setPreferredSize(new Dimension(150, 24));
        password.setForeground(Color.DARK_GRAY);
        password.setBackground(Color.LIGHT_GRAY);

        // button area
        buttonarea.setPreferredSize(new Dimension(20, 25));
        buttonarea.setBackground(Color.black);
        buttonarea.setForeground(Color.white);

        // adding GUI components
        head.setBackground(Color.BLACK);
        head.add(LOGIN);
        userarea.setBackground(Color.BLACK);
        userarea.add(USERNAME);
        userarea.add(usertext);
        passwordarea.setBackground(Color.BLACK);
        passwordarea.add(PASSWORDLABEL);
        passwordarea.add(password);
        buttonarea.setBackground(Color.BLACK);
        buttonarea.add(login);
        blackPanel.add(head);
        blackPanel.add(userarea);
        blackPanel.add(passwordarea);
        blackPanel.add(buttonarea);
        add(greenPanel);
        add(blackPanel);


        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String username = usertext.getText();
                String inputpassword = password.getText();

                if (username.equals("waris") && inputpassword.equals("gt")) {
                    new HOME();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });

        // frame
        pack(); // Pack components tightly
        setLocationRelativeTo(null); // Center the frame
        setSize(855, 380);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LOGIN::new);
    }
}
