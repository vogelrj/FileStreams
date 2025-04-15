import javax.swing.*;
import java.awt.*;
import java.io.*;

public class RandProductMaker extends JPanel {
    private JTextField nameField, descField, costField;
    private JButton addButton, clearButton;
    private JLabel statusLabel;
    private int recordCount = 0;
    private RandomAccessFile raf;
    private File dataFile;

    public RandProductMaker() {
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField();
        descField = new JTextField();
        costField = new JTextField();
        Dimension fieldSize = new Dimension(200, 24);
        nameField.setPreferredSize(fieldSize);
        descField.setPreferredSize(fieldSize);
        costField.setPreferredSize(fieldSize);

        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(descField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Cost:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(costField, gbc);

        addButton = new JButton("Add Product");
        clearButton = new JButton("Clear");
        Dimension buttonSize = new Dimension(120, 28);
        addButton.setPreferredSize(buttonSize);
        clearButton.setPreferredSize(buttonSize);

        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(addButton, gbc);
        gbc.gridx = 1;
        inputPanel.add(clearButton, gbc);

        add(inputPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("No file loaded | Record Count: 0");
        add(statusLabel, BorderLayout.SOUTH);

        try {
            dataFile = new File("data/ProductDataRAF.dat");
            raf = new RandomAccessFile(dataFile, "rw");
            recordCount = (int) (raf.length() / Product.RECORD_SIZE);
            updateStatusBar();
        } catch (IOException e) {
            showError("Unable to open or create data file.");
        }

        addButton.addActionListener(e -> addRecord());
        clearButton.addActionListener(e -> clearFields());
    }

    private void addRecord() {
        String name = nameField.getText().trim();
        String desc = descField.getText().trim();
        String costStr = costField.getText().trim();

        if (name.isEmpty() || desc.isEmpty() || costStr.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        try {
            double cost = Double.parseDouble(costStr);
            String generatedID = String.format("P%05d", recordCount + 1);

            int confirm = JOptionPane.showConfirmDialog(this,
                    String.format("Add this product?\n\nName: %s\nDescription: %s\nCost: $%.2f", name, desc, cost),
                    "Confirm Entry", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            Product product = new Product(generatedID, name, desc, cost);
            raf.seek(recordCount * Product.RECORD_SIZE);
            product.writeToRandomAccessFile(raf);
            recordCount++;

            clearFields();
            updateStatusBar();
        } catch (NumberFormatException ex) {
            showError("Cost must be a valid number.");
        } catch (IOException ex) {
            showError("Error writing to file.");
        }
    }

    private void clearFields() {
        nameField.setText("");
        descField.setText("");
        costField.setText("");
    }

    private void updateStatusBar() {
        String fileName = (dataFile != null) ? dataFile.getName() : "No file loaded";
        statusLabel.setText(String.format("%s | Record Count: %d", fileName, recordCount));
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
