import java.io.IOException;
import java.io.RandomAccessFile;

public class Product {
    private String ID;
    private String name;
    private String description;
    private double cost;

    public static final int NAME_LENGTH = 35;
    public static final int DESC_LENGTH = 75;
    public static final int ID_LENGTH = 6;

    public static final int RECORD_SIZE = (NAME_LENGTH + DESC_LENGTH + ID_LENGTH) * 2 + 8; // 2 bytes per char, 8 for double

    public Product(String ID, String name, String description, double cost) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    private static String padString(String str, int length) {
        if (str.length() > length) return str.substring(0, length);
        return String.format("%-" + length + "s", str);
    }

    public void writeToRandomAccessFile(RandomAccessFile raf) throws IOException {
        raf.writeChars(padString(name, NAME_LENGTH));
        raf.writeChars(padString(description, DESC_LENGTH));
        raf.writeChars(padString(ID, ID_LENGTH));
        raf.writeDouble(cost);
    }

    public static Product readFromRandomAccessFile(RandomAccessFile raf) throws IOException {
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < NAME_LENGTH; i++) name.append(raf.readChar());

        StringBuilder desc = new StringBuilder();
        for (int i = 0; i < DESC_LENGTH; i++) desc.append(raf.readChar());

        StringBuilder id = new StringBuilder();
        for (int i = 0; i < ID_LENGTH; i++) id.append(raf.readChar());

        double cost = raf.readDouble();

        return new Product(id.toString().trim(), name.toString().trim(), desc.toString().trim(), cost);
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("Product[ID='%s', Name='%s', Desc='%s', Cost=%.2f]", ID, name, description, cost);
    }

    public String toCSV() {
        return String.format("%s,%s,%s,%.2f", ID, name, description, cost);
    }

    public String toJSON() {
        return String.format("{\"ID\":\"%s\",\"Name\":\"%s\",\"Description\":\"%s\",\"Cost\":%.2f}",
                ID, name, description, cost);
    }

    public String toXML() {
        return String.format(
                "<Product>" +
                        "<ID>%s</ID>" +
                        "<Name>%s</Name>" +
                        "<Description>%s</Description>" +
                        "<Cost>%.2f</Cost>" +
                        "</Product>",
                ID, name, description, cost
        );
    }
}
