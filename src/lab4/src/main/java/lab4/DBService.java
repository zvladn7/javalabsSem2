package main.java.lab4;

import main.java.lab4.dao.ProductsDAO;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBService implements AutoCloseable {

    private final Connection connection;
    private final ProductsDAO productsDAO;
    private final PrintWriter out;

    public DBService(PrintWriter out) {
        this.out = out;
        connection = getConnection();
        productsDAO = new ProductsDAO(connection);
        productsDAO.dropTable();
        productsDAO.createTable();
    }

    public void apply(String command) {
        String[] splitedCommand = command.split(" ");
        switch (splitedCommand[0]) {
            case "/add": {
                productsDAO.add(splitedCommand[1], Double.parseDouble(splitedCommand[2]));
                break;
            }
            case "/delete": {
                productsDAO.remove(splitedCommand[1]);
                break;
            }
            case "/show_all": {
                productsDAO.getList().forEach(System.out::println);
                break;
            }
            case "/price": {
                out.println(productsDAO.getCost(splitedCommand[1]));
                break;
            }
            case "/change_price": {
                productsDAO.changeCost(splitedCommand[1], Double.parseDouble(splitedCommand[2]));
                break;
            }
            case "filter_by_price": {
                productsDAO.getFilteredList(Double.parseDouble(splitedCommand[1]), Double.parseDouble(splitedCommand[2])).forEach(out::println);
                break;
            }
            default: {
                out.println("!!!UNKNOWN OPERATION!!!");
            }
        }
    }

    private static Connection getConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/forLab?user=vlad&password=vlad&serverTimezone=UTC");
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            productsDAO.dropTable();
        }
    }
}
