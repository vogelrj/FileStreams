import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.file.StandardOpenOption.CREATE;

public class ProductWriter {
    public static void main (String[] args) {

        ArrayList<Product> products = new ArrayList<>();
        SafeInputObj safe_input = new SafeInputObj();

        File workingDirectory = new File(System.getProperty("user.dir"));
        Path file = Paths.get(workingDirectory.getPath(), "src/data/ProductTestData.txt");
        boolean done = false;
        String ID = "";
        String name = "";
        String desc = "";
        double cost = 0;

        do {
            ID = safe_input.getNonZeroLenString("Enter Product ID [6 digits]");
            name = safe_input.getNonZeroLenString("Enter Product Name");
            desc = safe_input.getNonZeroLenString("Enter Product Description");
            cost = safe_input.getRangedDouble("Enter the Product Cost", 0, 9999);

            Product productRec = new Product(ID, name, desc, cost);

            products.add(productRec);

            done = safe_input.getYNConfirm("Are you done adding Product Records?");
        } while (!done);

        for (Product p : products)
            System.out.println(p.toString());

        writeProductsListToFile(products, file);
    }

    private static void writeProductsListToFile(ArrayList<Product> products, Path file) {
            try (
                    OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))
            ) {
                for (Product p : products) {
                    String rec = p.toCSV();
                    writer.write(rec, 0, rec.length());
                    writer.newLine();
                }
                writer.close();
                System.out.println("Data file written!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
