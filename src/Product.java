public class Product {
    private String ID;           // static
    private String name;
    private String description;
    private double cost;


    public Product(String ID, String name, String description, double cost) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    /**
     * Returns the ID of the person obj
     *
     * @return ID as str value.
     */
    public String getID() {
        return ID;
    }

    /**
     * Returns the Name of the person obj
     *
     * @return Name as str value.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name Value
     *
     * @param name str value
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the person obj
     *
     * @return Description as str value.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description Value
     *
     * @param description str value
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the cost of the person obj
     *
     * @return cost as double value.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Sets the cost Value
     *
     * @param cost double value
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Produces a str representation of the product object.
     *
     * @return Product object as a CSV format.
     */
    @Override
    public String toString() {
        return "Product{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }

    /**
     * Produces a CSV representation of the product object.
     *
     * @return Product object as a CSV format.
     */
    public String toCSV() {
        return String.format("%s, %s, %s, %.2f",
                ID, name, description, cost);
    }


    /**
     * Produces a JSON-formatted string of the product obj.
     *
     * @return Product object in JSON format.
     */
    public String toJSON() {
        return String.format("{\"ID\":\"%s\",\"name\":\"%s\",\"description\":\"%s\",\"cost\":%.2f}",
                ID, name, description, cost);
    }

    /**
     * Produces an XML-formatted string of the product obj.
     *
     * @return Product object in XML format.
     */
    public String toXML() {
        return String.format("<Product>" +
                "<ID>%s</ID>" +
                "<Name>%s</Name>" +
                "<Description>%s</Description>" +
                "<Cost>%.2f</Cost>" +
                "</Product>", ID, name, description, cost);
    }



}
