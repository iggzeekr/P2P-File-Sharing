import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    private JFileChooser fileChooser = new JFileChooser();

    public GUI(String title) {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        setLayout(new GridLayout(5, 1));

        // Ust Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem connectItem = new JMenuItem("Connect");
        JMenuItem disconnectItem = new JMenuItem("Disconnect");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(connectItem);
        fileMenu.add(disconnectItem);
        fileMenu.add(exitItem);
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("About");
        helpItem.addActionListener(e -> {
            JFrame helpFrame = new JFrame("Help");
            JTextArea helpText = new JTextArea("\n\n\n\n                    "
            		+ "Sevgi Serra Celepoğlu\n                         "
            		+ " 20190702007");
            helpText.setEditable(false);
            helpFrame.add(helpText);
            helpFrame.setSize(300, 200);
            helpFrame.setLocationRelativeTo(GUI.this); 
            helpFrame.setVisible(true);
        });
        helpMenu.add(helpItem);
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);

        // Root of the P2P shared folder
        JPanel rootPanel = new JPanel();
        rootPanel.setBorder(BorderFactory.createTitledBorder("Root of the P2P shared folder"));
        JTextArea rootTextArea = new JTextArea(2, 20);
        rootTextArea.setBackground(Color.WHITE);
        JButton rootSetButton = new JButton("Set");
        rootSetButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(GUI.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                rootTextArea.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        rootPanel.add(new JScrollPane(rootTextArea));
        rootPanel.add(rootSetButton);
        add(rootPanel);

        // Destination Folder
        JPanel destinationPanel = new JPanel();
        destinationPanel.setBorder(BorderFactory.createTitledBorder("Destination Folder"));
        JTextArea destinationTextArea = new JTextArea(2, 20);
        destinationTextArea.setBackground(Color.WHITE);
        JButton destinationSetButton = new JButton("Set");
        destinationSetButton.addActionListener(e -> {
            int returnVal = fileChooser.showOpenDialog(GUI.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                destinationTextArea.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        });
        destinationPanel.add(new JScrollPane(destinationTextArea));
        destinationPanel.add(destinationSetButton);
        add(destinationPanel);

        // Settings
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBorder(BorderFactory.createTitledBorder("Settings"));
        settingsPanel.setLayout(new GridLayout(1, 2));
        
        JPanel folderExclude = new JPanel();
        folderExclude.setBorder(BorderFactory.createTitledBorder("Folder Exclusion"));
        folderExclude.setLayout(new BorderLayout());
        
        
        JPanel checkboxPanel = new JPanel();
        JCheckBox checkBox = new JCheckBox("Check new files only in the root");
        checkboxPanel.add(checkBox);
        checkboxPanel.setPreferredSize(new Dimension(25, 25));
        folderExclude.add(checkboxPanel,BorderLayout.NORTH);
        
        
        JPanel fileExclude = createExclusionPanel("Exclude files under these folders");
        fileExclude.setPreferredSize(new Dimension(10, 10));
        folderExclude.add(fileExclude,BorderLayout.CENTER );
        
        settingsPanel.add(folderExclude);
        settingsPanel.add(createExclusionPanel("Exclude files matching these masks"));
        add(settingsPanel);

        // Downloading Files
        JPanel downloadingPanel = new JPanel();
        downloadingPanel.setBorder(BorderFactory.createTitledBorder("Downloading Files"));
        JTextArea downloadingTextArea = new JTextArea(7, 75);
        downloadingTextArea.setBackground(Color.WHITE);
        downloadingPanel.add(new JScrollPane(downloadingTextArea));
        add(downloadingPanel);

        // Found Files
        JPanel foundPanel = new JPanel();
        foundPanel.setBorder(BorderFactory.createTitledBorder("Found Files"));
        JTextArea foundTextArea = new JTextArea(7, 75);
        foundTextArea.setBackground(Color.WHITE);
        JButton searchBUTTON = new JButton("Search");
        JTextArea searchTextArea = new JTextArea(2, 50);
        searchTextArea.setBackground(Color.WHITE);
        foundPanel.add(new JScrollPane(foundTextArea));
        foundPanel.add(searchBUTTON);
        foundPanel.add(searchTextArea);
        add(foundPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 1000);
        setVisible(true);
    }

    private JPanel createExclusionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(title));
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> list = new JList<>(listModel);
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(GUI.this, "Enter input:");
            if (input != null) {
                listModel.addElement(input);
            }
        });
        JButton delButton = new JButton("Del");
        delButton.addActionListener(e -> {
            int selectedIndex = list.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        });
        panel.add(new JScrollPane(list));
        panel.add(addButton);
        panel.add(delButton);
        return panel;
    }

}
