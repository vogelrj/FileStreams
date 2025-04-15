import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class ProductReader {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;

        System.out.println("Please Select the ProductTestData.txt file from the menu.\n");

        try {
            // use the toolkit to get the current working directory
            File workingDirectory = new File(System.getProperty("user.dir"));
            Path filePath = Paths.get(workingDirectory.getPath(), "src/data/ProductTestData.txt");
            chooser.setCurrentDirectory(filePath.getParent().toFile());

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                Path file = selectedFile.toPath();

                // Read the list of Products using our helper method
                ArrayList<Product> products = readProductsFromFile(file);

                System.out.println("\n\nData file read!\n");

                // Print header
                System.out.printf("%-10s %-15s %-25s %10s\n",
                        "ID", "Name", "Description", "Cost");
                System.out.println("===============================================================");

                // Generate the table from the products ArrayList
                for (Product p : products) {
                    System.out.printf("%-10s %-15s %-25s %10.2f\n",
                            p.getID(),
                            p.getName(),
                            p.getDescription(),
                            p.getCost());
                }
            } else {
                System.out.println("Failed to choose a file to process");
                System.out.println("Run the program again!");
                System.exit(0);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found!!!");
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<Product> readProductsFromFile(Path file) throws IOException {
        ArrayList<Product> products = new ArrayList<>();
        final int FIELDS_LENGTH = 4; // ID, name, desc, cost

        try (
                InputStream in = new BufferedInputStream(Files.newInputStream(file));
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))
        ) {
            String rec;
            while ((rec = reader.readLine()) != null) {
                // Split the record into fields
                String[] fields = rec.split(",");
                if (fields.length == FIELDS_LENGTH) {
                    String id   = fields[0].trim();
                    String name = fields[1].trim();
                    String desc = fields[2].trim();
                    double cost = Double.parseDouble(fields[3].trim());

                    // Create a Product from these fields
                    Product p = new Product(id, name, desc, cost);
                    products.add(p);
                } else {
                    System.out.println("Found a record that may be corrupt: ");
                    System.out.println(rec);
                }
            }
        }
        return products;
    }
}
