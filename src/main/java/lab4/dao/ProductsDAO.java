package lab4.dao;

import lab4.datasets.ProductDataSet;
import lab4.executor.Executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductsDAO {

    private final Executor executor;

    public ProductsDAO(Connection connection) {
        executor = new Executor(connection);
    }

    public void createTable() {
        executor.executeUpdate("CREATE TABLE products (id INT AUTO_INCREMENT, prodID VARCHAR(256) NOT NULL, " +
                "title VARCHAR(256) NOT NULL, cost DOUBLE NOT NULL, PRIMARY KEY (id))");
    }

    public void dropTable() {
        executor.executeUpdate("DROP TABLE products");
    }

    public void add(String title, double cost) {
        String prodID = UUID.randomUUID().toString();
        executor.executeUpdate("INSERT INTO products (prodID, title, cost) values " +
                "('" + prodID + "','" + title +  "', '" + cost +  " ')");
    }

    public void remove(String title) {
        executor.executeUpdate("DELETE FROM products WHERE title='" + title + "'");
    }

    public void changeCost(String title, double newCost) {
        executor.executeUpdate("UPDATE products SET cost='" + newCost + "' WHERE title='" + title + "'");
    }

    public List<ProductDataSet> getList() {
        return executor.executeQuery("SELECT * FROM products", this::addToList);
    }

    public double getCost(String title) {
        return executor.executeQuery("SELECT (cost) FROM products WHERE title='" + title + "'", resultSet -> {
            resultSet.next();
            return resultSet.getDouble("cost");
        });
    }

    public List<ProductDataSet> getFilteredList(double begin, double end) {
        return executor.executeQuery("SELECT * FROM produtcts WHERE cost>=" + begin + " and cost<=" + end, this::addToList);
    }

    private List<ProductDataSet> addToList(ResultSet resultSet) throws SQLException {
        List<ProductDataSet> products = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String prodID = resultSet.getString(2);
            String title = resultSet.getString(3);
            double cost = resultSet.getDouble(4);

            products.add(new ProductDataSet(id, prodID, title, cost));
        }

        return products;
    }
}
