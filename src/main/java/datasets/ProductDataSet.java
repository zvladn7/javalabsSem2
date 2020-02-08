package datasets;

public class ProductDataSet {

    private long id;
    private long prodId;
    private String title;
    private double cost;

    public ProductDataSet(long id, long prodId, String title, double cost) {
        this.id = id;
        this.prodId = prodId;
        this.title = title;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public long getProdId() {
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
                .append(",\nprodID: ")
                .append(prodId)
                .append(",\nTitle: ")
                .append(title)
                .append(",\nCost: ")
                .append(cost)
                .append("\n");
        return sb.toString();
    }
}
