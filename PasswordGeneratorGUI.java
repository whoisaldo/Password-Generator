// this will render the gui components for the frontend
// this class will inherit from the JFrame class
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;


public class PasswordGeneratorGUI extends JFrame {
    private PasswordGenerator passwordGenerator;

    public PasswordGeneratorGUI() {
        // this will render frame and add a title as well
        super("Password Generator");

        // Set the size of the GUI
        setSize(630, 640);

        // Make it so people can't resize it
        setResizable(false);

        // Setting the layout to be null so we have control of the size of the components within the app
        setLayout(null);

        // Terminate the program when X is pressed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Centers the GUI
        setLocationRelativeTo(null);

        //init password generastor
passwordGenerator = new PasswordGenerator();
        // Rendering the GUI components
        addGuiComponents(); // Call the method here
    }

    private void addGuiComponents() {
        // Create title text
        JLabel titleLabel = new JLabel("Password Generator");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, 540, 39);

        // Add the label to the frame
        add(titleLabel);

        //create the result text area
        JTextArea passwordOutput = new JTextArea();

        //prevent the editing the text area
        passwordOutput.setEditable(false);
        passwordOutput.setFont(new Font("Dialog", Font.BOLD, 32));

        //add the scrollability
        JScrollPane passwordOutputPane = new JScrollPane(passwordOutput);
        passwordOutputPane.setBounds(25, 97, 479, 70);

//creates a black border around the text area
        passwordOutputPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(passwordOutputPane);

        //create password length label
        JLabel passwordLengthLabel = new JLabel("Password Length:");
        passwordLengthLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordLengthLabel.setBounds(25,215,272,39 );
        add(passwordLengthLabel);
//password length input
        JTextArea passwordLengthInputArea = new JTextArea();
        passwordLengthInputArea.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordLengthInputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordLengthInputArea.setBounds(310, 215, 192, 39);
        add(passwordLengthInputArea);


        //create toggle buttons
        //uppercase letter toggle
        JToggleButton uppercaseToggle = new JToggleButton("Uppercase");
        uppercaseToggle.setBounds(25,302,225,56);
        uppercaseToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        uppercaseToggle.setBackground(Color.ORANGE);
        add(uppercaseToggle);

        //lowercase letter toggle
        JToggleButton lowercaseToggle = new JToggleButton("Lowercase");
        lowercaseToggle.setBounds(375,302,225,56);
        lowercaseToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        //lowercaseToggle.setBackground(new Color(160, 32, 240));
        lowercaseToggle.setBackground(Color.ORANGE);
        add(lowercaseToggle);

        //Numbers toggle
        JToggleButton numbersToggle = new JToggleButton("Numbers");
        numbersToggle.setBounds(25,402,225,56);
        numbersToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        numbersToggle.setBackground(Color.ORANGE);
        add(numbersToggle);

        //Symbols toggle
        JToggleButton symbolsToggle = new JToggleButton("Symbols");
        symbolsToggle.setBounds(375,402,225,56);
        symbolsToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        symbolsToggle.setBackground(Color.ORANGE);
        add(symbolsToggle);

    //Generate button

        JButton generateButton = new JButton("Generate");
        generateButton.setBounds(200,502,225,50 );
        generateButton.setFont(new Font("Dialog", Font.PLAIN, 26));
        generateButton.setBackground(Color.ORANGE);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //validates generates a password only when length > 0 and one of the toggled buttons is pressed
                if(passwordLengthInputArea.getText().length() <= 0) return;
                boolean anyToggleSelected = lowercaseToggle.isSelected() ||
                        uppercaseToggle.isSelected() ||
                        numbersToggle.isSelected() ||
                        symbolsToggle.isSelected();


    //generate password
                //copnverts the text in to an integer value
                try {
                    int passwordLength = Integer.parseInt(passwordLengthInputArea.getText());
                    if (passwordLength > 0 && anyToggleSelected) {
                        String generatedPassword = passwordGenerator.generatePassword(
                                passwordLength,
                                uppercaseToggle.isSelected(),
                                lowercaseToggle.isSelected(),
                                symbolsToggle.isSelected(),
                                numbersToggle.isSelected());


                        passwordOutput.setText(generatedPassword);
                    } else {
                        passwordOutput.setText("Invalid length!");
                    }
                } catch (NumberFormatException ex) {
                    passwordOutput.setText("Enter a number!");
                }

            }
        });
        add(generateButton);


    //credits
       JButton creditsPanel = new JButton("CREDITS");
       creditsPanel.setBackground(Color.BLACK);
       creditsPanel.setForeground(Color.ORANGE);
       creditsPanel.setBounds(425,575, 200, 26);
       creditsPanel.setFont(new Font("Dialog", Font.PLAIN, 26));
       add(creditsPanel);
       // Add action listener to credits button
        creditsPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreditsDialog();
            }
        });
    }
    private void showCreditsDialog() {
        // Create a new dialog for credits
        JDialog creditsDialog = new JDialog(this, "Credits", true);
        creditsDialog.setSize(400, 200);
        creditsDialog.setLocationRelativeTo(this);

        // Set layout
        creditsDialog.setLayout(new FlowLayout());

        // Add credits text
        JLabel creatorLabel = new JLabel("Created by: Ali Younes");
        creatorLabel.setFont(new Font("Comic", Font.PLAIN, 32));
        creditsDialog.add(creatorLabel);

        // Add clickable link
        JLabel linkLabel = new JLabel("Visit my website");
        linkLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        linkLabel.setForeground(Color.BLUE);
        linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add mouse listener to handle clicks
        linkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/whoisaldo"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        creditsDialog.add(linkLabel);

        // Show the dialog
        creditsDialog.setVisible(true);
    }







}

