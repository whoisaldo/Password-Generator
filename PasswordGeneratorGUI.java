// this will render the gui components for the frontend
// this class will inherit from the JFrame class
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PasswordGeneratorGUI extends JFrame {
    private PasswordGenerator passwordGenerator;

    // ─── (1) NEW FIELDS FOR “SAVE PASSWORD” AND FILE EXPORT ───────────────────
    private JTextField usernameField;
    private JButton saveButton;
    private DefaultListModel<String> savedPasswordsModel;
    private JList<String> savedPasswordsList;
    private JScrollPane savedScrollPane;
    // ─────────────────────────────────────────────────────────────────────────

    public PasswordGeneratorGUI() {
        // this will render frame and add a title as well
        super("Password Generator");

        // Set the size of the GUI
        setSize(730, 800);

        // Make it so people can't resize it
        setResizable(false);

        // Setting the layout to be null so we have control of the size of the components within the app
        setLayout(null);

        // Terminate the program when X is pressed
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Centers the GUI
        setLocationRelativeTo(null);

        // init password generator
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
        add(titleLabel);

        // create the result text area
        JTextArea passwordOutput = new JTextArea();

        // prevent editing the text area
        passwordOutput.setEditable(false);
        passwordOutput.setFont(new Font("Dialog", Font.BOLD, 32));

        // add scrollability
        JScrollPane passwordOutputPane = new JScrollPane(passwordOutput);
        passwordOutputPane.setBounds(25, 97, 579, 70);
        // creates a black border around the text area
        passwordOutputPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(passwordOutputPane);

        // create password length label
        JLabel passwordLengthLabel = new JLabel("Password Length:");
        passwordLengthLabel.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordLengthLabel.setBounds(25, 215, 272, 39);
        add(passwordLengthLabel);

        // password length input
        JTextArea passwordLengthInputArea = new JTextArea();
        passwordLengthInputArea.setFont(new Font("Dialog", Font.PLAIN, 32));
        passwordLengthInputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        passwordLengthInputArea.setBounds(310, 215, 192, 39);
        add(passwordLengthInputArea);

        // create toggle buttons
        // uppercase letter toggle
        JToggleButton uppercaseToggle = new JToggleButton("Uppercase");
        uppercaseToggle.setBounds(25, 302, 225, 56);
        uppercaseToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        uppercaseToggle.setBackground(Color.ORANGE);
        add(uppercaseToggle);

        // lowercase letter toggle
        JToggleButton lowercaseToggle = new JToggleButton("Lowercase");
        lowercaseToggle.setBounds(375, 302, 225, 56);
        lowercaseToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        lowercaseToggle.setBackground(Color.ORANGE);
        add(lowercaseToggle);

        // numbers toggle
        JToggleButton numbersToggle = new JToggleButton("Numbers");
        numbersToggle.setBounds(25, 402, 225, 56);
        numbersToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        numbersToggle.setBackground(Color.ORANGE);
        add(numbersToggle);

        // symbols toggle
        JToggleButton symbolsToggle = new JToggleButton("Symbols");
        symbolsToggle.setBounds(375, 402, 225, 56);
        symbolsToggle.setFont(new Font("Dialog", Font.PLAIN, 26));
        symbolsToggle.setBackground(Color.ORANGE);
        add(symbolsToggle);

        // ─── (2) “SAVE PASSWORD” COMPONENTS ────────────────────────────────────

        // (a) “Username” Label + TextField
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 26));
        usernameLabel.setBounds(25, 470, 200, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 26));
        usernameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        usernameField.setBounds(225, 470, 300, 30);
        add(usernameField);

        // (b) “Save Password” Button
        saveButton = new JButton("Save Password");
        saveButton.setFont(new Font("Dialog", Font.PLAIN, 22));
        saveButton.setBackground(Color.ORANGE);
        saveButton.setBounds(25, 515, 200, 50);
        add(saveButton);

        // (c) JList + ScrollPane to display saved (username, password) entries
        savedPasswordsModel = new DefaultListModel<>();
        savedPasswordsList = new JList<>(savedPasswordsModel);
        savedPasswordsList.setFont(new Font("Monospaced", Font.PLAIN, 16));
        savedScrollPane = new JScrollPane(savedPasswordsList);
        savedScrollPane.setBounds(225, 515, 379, 150);
        savedScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(savedScrollPane);

        // (d) Hook up the Save Button’s ActionListener
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = passwordOutput.getText().trim();

                // if either field is empty, show a warning
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            PasswordGeneratorGUI.this,
                            "Please enter a username and generate a password first.",
                            "Missing Data",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                // format “username ‐ password”
                String entry = username + " ‐ " + password;

                // Show a JFileChooser “Save As…” dialog so user can pick file/location
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Export Saved Password");
                // Optional: only allow .txt files
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
                chooser.setFileFilter(filter);

                int userSelection = chooser.showSaveDialog(PasswordGeneratorGUI.this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = chooser.getSelectedFile();
                    // Ensure it has “.txt” extension (if user didn’t type it)
                    String path = fileToSave.getAbsolutePath();
                    if (!path.toLowerCase().endsWith(".txt")) {
                        fileToSave = new File(path + ".txt");
                    }

                    // Write the entry into that file (append mode = true so multiple entries stack)
                    try (FileWriter writer = new FileWriter(fileToSave, true)) {
                        writer.write(entry + System.lineSeparator());
                        JOptionPane.showMessageDialog(
                                PasswordGeneratorGUI.this,
                                "Saved to:\n" + fileToSave.getAbsolutePath(),
                                "Export Successful",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(
                                PasswordGeneratorGUI.this,
                                "Error saving to file.",
                                "File Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }

                // Add the entry into the on-screen JList and clear username field
                savedPasswordsModel.addElement(entry);
                usernameField.setText("");
            }
        });
        // ─────────────────────────────────────────────────────────────────────────

        // Generate button
        JButton generateButton = new JButton("Generate");
        generateButton.setBounds(200, 675, 225, 50);
        generateButton.setFont(new Font("Dialog", Font.PLAIN, 26));
        generateButton.setBackground(Color.ORANGE);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // validate: generate only when length > 0 and one of the toggles is pressed
                if (passwordLengthInputArea.getText().length() <= 0) return;
                boolean anyToggleSelected = lowercaseToggle.isSelected() ||
                        uppercaseToggle.isSelected() ||
                        numbersToggle.isSelected() ||
                        symbolsToggle.isSelected();

                // convert the text into an integer value
                try {
                    int passwordLength = Integer.parseInt(passwordLengthInputArea.getText());
                    if (passwordLength > 0 && anyToggleSelected) {
                        String generatedPassword = passwordGenerator.generatePassword(
                                passwordLength,
                                uppercaseToggle.isSelected(),
                                lowercaseToggle.isSelected(),
                                symbolsToggle.isSelected(),
                                numbersToggle.isSelected()
                        );
                        // set the generated password into the output area
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

        // credits button
        JButton creditsPanel = new JButton("CREDITS");
        creditsPanel.setBackground(Color.BLACK);
        creditsPanel.setForeground(Color.ORANGE);
        creditsPanel.setBounds(425, 675, 200, 26);
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

    // Main method to launch the GUI
    public static void main(String[] args) {
        // Always launch Swing GUIs on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PasswordGeneratorGUI();
            }
        });
    }
}
