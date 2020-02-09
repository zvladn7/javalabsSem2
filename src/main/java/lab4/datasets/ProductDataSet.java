package lab4.datasets;

public class ProductDataSet {

    private int id;
    private String prodId;
    private String title;
    private double cost;

    public ProductDataSet(int id, String prodId, String title, double cost) {
        this.id = id;
        this.prodId = prodId;
        this.title = title;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getProdId() {
        return prodId;
    }

    public String getTitle() {
        return title;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product:\n")
                .append("id: ")
                .append(id)
                .append(",prodID: ")
                .append(prodId)
                .append(",title: ")
                .append(title)
                .append(",cost: ")
                .append(cost)
                .append("\n");
        return sb.toString();
    }
}
