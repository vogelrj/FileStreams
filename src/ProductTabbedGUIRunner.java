import javax.swing.SwingUtilities;

public class ProductTabbedGUIRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductTabbedGUI app = new ProductTabbedGUI();
            app.setVisible(true);
        });
    }
}
