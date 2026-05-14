package Projects;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.*;

public class BasicTextEditorLogin extends JFrame {

    private static final Color BG = new Color(15, 23, 42);
    private static final Color CARD = new Color(17, 24, 39);
    private static final Color PANEL = new Color(30, 41, 59);
    private static final Color BORDER = new Color(51, 65, 85);
    private static final Color TEXT = new Color(229, 231, 235);
    private static final Color MUTED = new Color(148, 163, 184);

    private static final Color BLUE = new Color(56, 189, 248);
    private static final Color GREEN = new Color(34, 197, 94);
    private static final Color ORANGE = new Color(245, 158, 11);
    private static final Color RED = new Color(239, 68, 68);
    private static final Color PURPLE = new Color(168, 85, 247);
    private static final Color CYAN = new Color(6, 182, 212);

    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "1234";

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPassword;
    private JLabel loginStatus;

    private JTextArea textArea;
    private JLabel currentFileLabel;
    private JLabel statusLabel;
    private JLabel statsLabel;

    private File currentFile;

    public BasicTextEditorLogin() {
        super("Machine Problem No. 4 - Basic Text Editor with Simple Login Form");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 620);
        setLocationRelativeTo(null);
        setResizable(false);

        applyLookAndFeel();
        buildUI();
    }

    private void applyLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    private void buildUI() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(buildLoginPanel(), "LOGIN");
        mainPanel.add(buildEditorPanel(), "EDITOR");

        setContentPane(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
    }

    private JPanel buildLoginPanel() {
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(BG);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(420, 350));
        card.setBackground(CARD);
        card.setBorder(new LineBorder(BORDER, 1, true));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel title = new JLabel("Simple Login Form");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Login first to access the text editor");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel userPanel = new JPanel(new BorderLayout(0, 6));
        userPanel.setOpaque(false);
        JLabel userLabel = createLabel("Username:");
        usernameField = createTextField();
        userPanel.add(userLabel, BorderLayout.NORTH);
        userPanel.add(usernameField, BorderLayout.CENTER);

        JPanel passPanel = new JPanel(new BorderLayout(0, 6));
        passPanel.setOpaque(false);
        JLabel passLabel = createLabel("Password:");
        passwordField = new JPasswordField();
        styleTextField(passwordField);

        showPassword = new JCheckBox("Show Password");
        showPassword.setOpaque(false);
        showPassword.setForeground(MUTED);
        showPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        showPassword.addActionListener(e -> {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });

        passPanel.add(passLabel, BorderLayout.NORTH);
        passPanel.add(passwordField, BorderLayout.CENTER);
        passPanel.add(showPassword, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        buttonPanel.setOpaque(false);

        RoundedButton loginBtn = new RoundedButton("Login", BLUE);
        RoundedButton clearBtn = new RoundedButton("Clear", ORANGE);
        RoundedButton exitBtn = new RoundedButton("Exit", RED);

        loginBtn.addActionListener(e -> performLogin());
        clearBtn.addActionListener(e -> clearLoginFields());
        exitBtn.addActionListener(e -> System.exit(0));

        buttonPanel.add(loginBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(exitBtn);

        loginStatus = new JLabel("Use username: admin   password: 1234");
        loginStatus.setForeground(MUTED);
        loginStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(title);
        card.add(Box.createVerticalStrut(6));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(25));
        card.add(userPanel);
        card.add(Box.createVerticalStrut(15));
        card.add(passPanel);
        card.add(Box.createVerticalStrut(20));
        card.add(buttonPanel);
        card.add(Box.createVerticalStrut(18));
        card.add(loginStatus);

        root.add(card);
        return root;
    }

    private void performLogin() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            loginStatus.setText("Please enter both username and password.");
            loginStatus.setForeground(ORANGE);
            JOptionPane.showMessageDialog(this,
                    "Username and password are required.",
                    "Missing Input",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (user.equals(VALID_USERNAME) && pass.equals(VALID_PASSWORD)) {
            loginStatus.setText("Login successful.");
            loginStatus.setForeground(GREEN);
            JOptionPane.showMessageDialog(this,
                    "Login successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            clearLoginFields();
            loginStatus.setText("Use username: admin   password: 1234");
            loginStatus.setForeground(MUTED);
            cardLayout.show(mainPanel, "EDITOR");
        } else {
            loginStatus.setText("Invalid username or password.");
            loginStatus.setForeground(RED);
            JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearLoginFields() {
        usernameField.setText("");
        passwordField.setText("");
        showPassword.setSelected(false);
        passwordField.setEchoChar('•');
        loginStatus.setText("Fields cleared.");
        loginStatus.setForeground(MUTED);
    }

    private JPanel buildEditorPanel() {
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel header = new GradientHeader();
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(14, 18, 14, 18));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Basic Text Editor");
        title.setForeground(Color.BLACK);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JLabel subtitle = new JLabel("Create, edit, open, and save text documents");
        subtitle.setForeground(Color.BLACK);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);

        currentFileLabel = new JLabel("Current File: Unsaved Document");
        currentFileLabel.setForeground(Color.BLACK);
        currentFileLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));

        header.add(titlePanel, BorderLayout.WEST);
        header.add(currentFileLabel, BorderLayout.EAST);

        JPanel toolbar = new JPanel(new GridLayout(1, 7, 10, 0));
        toolbar.setBackground(BG);

        RoundedButton newBtn = new RoundedButton("New", BLUE);
        RoundedButton openBtn = new RoundedButton("Open", CYAN);
        RoundedButton saveBtn = new RoundedButton("Save", GREEN);
        RoundedButton clearBtn = new RoundedButton("Clear", ORANGE);
        RoundedButton aboutBtn = new RoundedButton("About", PURPLE);
        RoundedButton logoutBtn = new RoundedButton("Logout", new Color(100, 116, 139));
        RoundedButton exitBtn = new RoundedButton("Exit", RED);

        toolbar.add(newBtn);
        toolbar.add(openBtn);
        toolbar.add(saveBtn);
        toolbar.add(clearBtn);
        toolbar.add(aboutBtn);
        toolbar.add(logoutBtn);
        toolbar.add(exitBtn);

        JPanel topSection = new JPanel(new BorderLayout(0, 12));
        topSection.setBackground(BG);
        topSection.add(header, BorderLayout.NORTH);
        topSection.add(toolbar, BorderLayout.SOUTH);

        root.add(topSection, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        textArea.setBackground(PANEL);
        textArea.setForeground(TEXT);
        textArea.setCaretColor(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(new EmptyBorder(12, 12, 12, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new LineBorder(BORDER, 1, true));
        scrollPane.getViewport().setBackground(PANEL);

        root.add(scrollPane, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(CARD);
        footer.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(10, 12, 10, 12)
        ));

        statusLabel = new JLabel("Ready.");
        statusLabel.setForeground(TEXT);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        statsLabel = new JLabel("Words: 0 | Characters: 0");
        statsLabel.setForeground(MUTED);
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(statsLabel, BorderLayout.EAST);

        root.add(footer, BorderLayout.SOUTH);

        newBtn.addActionListener(e -> newDocument());
        openBtn.addActionListener(e -> openFile());
        saveBtn.addActionListener(e -> saveFile());
        clearBtn.addActionListener(e -> clearEditor());
        aboutBtn.addActionListener(e -> showAbout());
        logoutBtn.addActionListener(e -> logout());
        exitBtn.addActionListener(e -> System.exit(0));

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateStats(); }
            public void removeUpdate(DocumentEvent e) { updateStats(); }
            public void changedUpdate(DocumentEvent e) { updateStats(); }
        });

        return root;
    }

    private void newDocument() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Create a new document?\nUnsaved changes may be lost.",
                "New Document",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            textArea.setText("");
            currentFile = null;
            currentFileLabel.setText("Current File: Unsaved Document");
            setStatus("New document created.");
            updateStats();
        }
    }

    private void clearEditor() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Clear all text?",
                "Clear Text",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            textArea.setText("");
            setStatus("Text cleared.");
            updateStats();
        }
    }

    private void openFile() {
        JFileChooser chooser = new JFileChooser();
        int choice = chooser.showOpenDialog(this);

        if (choice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }

                textArea.setText(content.toString());
                currentFile = selectedFile;
                currentFileLabel.setText("Current File: " + currentFile.getName());
                setStatus("File opened successfully.");
                updateStats();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Failed to open file.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                setStatus("Open failed.");
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            JFileChooser chooser = new JFileChooser();
            int choice = chooser.showSaveDialog(this);

            if (choice == JFileChooser.APPROVE_OPTION) {
                currentFile = chooser.getSelectedFile();
            } else {
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile))) {
            writer.write(textArea.getText());
            currentFileLabel.setText("Current File: " + currentFile.getName());
            setStatus("File saved successfully.");
            JOptionPane.showMessageDialog(this,
                    "File saved successfully.",
                    "Saved",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Failed to save file.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            setStatus("Save failed.");
        }
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            textArea.setText("");
            currentFile = null;
            currentFileLabel.setText("Current File: Unsaved Document");
            setStatus("Logged out.");
            updateStats();
            cardLayout.show(mainPanel, "LOGIN");
        }
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(this,
                "Machine Problem No. 4\n" +
                        "Basic Text Editor with Simple Login Form\n\n" +
                        "Features:\n" +
                        "- Simple login form\n" +
                        "- Show/Hide password\n" +
                        "- New file\n" +
                        "- Open text file\n" +
                        "- Save text file\n" +
                        "- Clear text\n" +
                        "- Word and character count\n" +
                        "- Logout and Exit",
                "About",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStats() {
        String text = textArea.getText();
        int chars = text.length();

        String trimmed = text.trim();
        int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;

        statsLabel.setText("Words: " + words + " | Characters: " + chars);
    }

    private void setStatus(String message) {
        statusLabel.setText(message);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        styleTextField(field);
        return field;
    }

    private void styleTextField(JTextField field) {
        field.setPreferredSize(new Dimension(0, 38));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setBackground(PANEL);
        field.setForeground(TEXT);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new CompoundBorder(
                new LineBorder(BORDER, 1, true),
                new EmptyBorder(8, 10, 8, 10)
        ));
    }

    static class RoundedButton extends JButton {
        private final Color fill;

        RoundedButton(String text, Color fill) {
            super(text);
            this.fill = fill;
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(10, 16, 10, 16));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color color = fill;
            if (getModel().isPressed()) color = fill.darker();
            else if (getModel().isRollover()) color = fill.brighter();

            g2.setColor(color);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

            g2.setColor(getForeground());
            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    }

    static class GradientHeader extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class CompoundBorder extends javax.swing.border.CompoundBorder {
        public CompoundBorder(javax.swing.border.Border outsideBorder, javax.swing.border.Border insideBorder) {
            super(outsideBorder, insideBorder);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MachineProblem4().setVisible(true));
    }
}