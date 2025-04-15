import javax.swing.*;

public class ProductTabbedGUI extends JFrame {
    public ProductTabbedGUI() {
        super("Products");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 400);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Add Products", new RandProductMaker());
        tabs.addTab("Search Products", new RandProductSearch());

        add(tabs);
    }
}
