package lab4.dao;

import lab4.executor.Executor;

import java.sql.Connection;

public class ProductsDAO {

    private final Executor executor;

    public ProductsDAO(Connection connection) {
        executor = new Executor(connection);
    }


    public void createTable() {
        executor.executeUpdate("CREATE TABLE products (id INT AUTO_INCREMENT, prodID INT NOT NULL, " +
                "title VARCHAR(256) NOT NULL, cost INT NOT NULL, PRIMARY KEY (id))");
    }

    public void dropTable() {
        executor.executeUpdate("DROP TABLE products");
    }

}
