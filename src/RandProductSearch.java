import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;

public class RandProductSearch extends JPanel {
    private JTextField searchField;
    private JTextArea resultArea;
    private JButton chooseFileButton, searchButton;
    private File selectedFile;
    private JLabel statusLabel;
    private long totalRecords = 0;

    public RandProductSearch() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        chooseFileButton = new JButton("Choose File");
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.setEnabled(false);

        topPanel.add(chooseFileButton);
        topPanel.add(new JLabel("Search Name:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        resultArea = new JTextArea(20, 60);
        resultArea.setEditable(false);
        resultArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(resultArea);

        statusLabel = new JLabel("No File Selected | Total Product Count: 0");

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        chooseFileButton.addActionListener(this::chooseFile);
        searchButton.addActionListener(this::searchProducts);
    }

    private void chooseFile(ActionEvent e) {
        JFileChooser chooser = new JFileChooser(new File("data"));
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            searchButton.setEnabled(true);
            updateStatusBar(false, 0); // before search
            resultArea.setText("Selected file: " + selectedFile.getAbsolutePath() + "\n");
        }
    }

    private void searchProducts(ActionEvent e) {
        String keyword = searchField.getText().trim().toLowerCase();

        if (selectedFile == null || !selectedFile.exists()) {
            JOptionPane.showMessageDialog(this, "Please select a valid .dat file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a product name to search.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultArea.setText("");

        try (RandomAccessFile raf = new RandomAccessFile(selectedFile, "r")) {
            totalRecords = raf.length() / Product.RECORD_SIZE;
            int matchCount = 0;
            StringBuilder resultBuilder = new StringBuilder();

            for (int i = 0; i < totalRecords; i++) {
                raf.seek(i * Product.RECORD_SIZE);
                Product p = Product.readFromRandomAccessFile(raf);
                if (p.getName().toLowerCase().contains(keyword)) {
                    resultBuilder.append(String.format("%-8s| %-15s| %-20s| %.2f\n",
                            p.getID(), p.getName(), p.getDescription(), p.getCost()));
                    matchCount++;
                }
            }

            resultArea.append("Found " + matchCount + " matching results:\n\n");
            resultArea.append(String.format("%-8s| %-15s| %-20s| %s\n", "ID", "Name", "Description", "Cost"));
            resultArea.append("--------|----------------|----------------------|--------\n");
            resultArea.append(resultBuilder.toString());

            updateStatusBar(true, matchCount); // after search

        } catch (IOException ex) {
            resultArea.setText("Error reading file: " + ex.getMessage());
            statusLabel.setText("Error reading file | Displaying 0 of 0 records");
        }
    }

    private void updateStatusBar(boolean afterSearch, int matchCount) {
        if (selectedFile == null || !selectedFile.exists()) {
            statusLabel.setText("No File Selected | Total Product Count: 0");
            return;
        }

        if (afterSearch) {
            statusLabel.setText(String.format("%s | Displaying %d of %d records",
                    selectedFile.getName(), matchCount, totalRecords));
        } else {
            try (RandomAccessFile raf = new RandomAccessFile(selectedFile, "r")) {
                totalRecords = raf.length() / Product.RECORD_SIZE;
                statusLabel.setText(String.format("%s | Total Product Count: %d", selectedFile.getName(), totalRecords));
            } catch (IOException e) {
                statusLabel.setText("Error reading file | Total Product Count: 0");
            }
        }
    }
}
