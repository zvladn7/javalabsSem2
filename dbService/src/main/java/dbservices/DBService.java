package dbservices;

import dao.ProductsDAO;
import datasets.ProductDataSet;

import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DBService implements AutoCloseable {

    private static final String WRONG_ARGUMENT_MESSAGE = "The number of arguments is more or less than it was expected!";
    public static final String WRONG_FORMAT_OF_NUMBER_MESSAGE = "You've sent smth instead of number.";
    public static final String NO_PRODUCT_IN_DB_MESSAGE = "There is no product in the data base";
    public static final String DELETE_UNEXISTING_PRODUCT_MESSAGE = NO_PRODUCT_IN_DB_MESSAGE + ", so it wasn't remove from it!";
    public static final String ADD_EXISTING_PRODCUT_MESSAGE = "This product has been already added to the data base! ";


    private final Connection connection;
    private final ProductsDAO productsDAO;
    private final PrintWriter out;
    private Map<String, Consumer<String[]>> operationMap = new HashMap<>();

    public DBService(PrintWriter out) {
        this.out = out;
        connection = getConnection();
        productsDAO = new ProductsDAO(connection);
        productsDAO.dropTable();
        productsDAO.createTable();
        operationMap.put("/add", this::add);
        operationMap.put("/delete", this::delete);
        operationMap.put("/show_all", this::showAll);
        operationMap.put("/price", this::showPrice);
        operationMap.put("/change_price", this::changeCost);
        operationMap.put("/filter_by_price", this::filterByPrice);
    }

    private void add(String[] splitedCommand) {
        if (!productsDAO.ifExist(splitedCommand[1])) {
            try {
                productsDAO.add(splitedCommand[1], Double.parseDouble(splitedCommand[2]));
                out.println("The product was successfully added!");
            } catch (NumberFormatException ex) {
                out.println(WRONG_FORMAT_OF_NUMBER_MESSAGE +  "The the pattern of the command is '/add product price'");
            }
        } else {
            out.println(ADD_EXISTING_PRODCUT_MESSAGE +
                    "Please try to change the name or update the cost of this product by command" +
                    "\"\\change_price 'product' 'new_price'\"");
        }
        out.flush();
    }

    private void delete(String[] splitedCommand) {
        if (splitedCommand.length == 2) {
            if (productsDAO.ifExist(splitedCommand[1])) {
                productsDAO.remove(splitedCommand[1]);
                out.println("The product was removed from the data base successfully!");
            } else {
                out.println(DELETE_UNEXISTING_PRODUCT_MESSAGE);
            }
        } else {
            out.println(WRONG_ARGUMENT_MESSAGE);
        }
        out.flush();
    }

    public List<ProductDataSet> showAll(String[] splitedCommand) {
        if (splitedCommand.length == 1) {
            List<ProductDataSet> list = productsDAO.getList();
            list.forEach(out::println);
            out.flush();
            return list;
        } else {
            out.println(WRONG_ARGUMENT_MESSAGE);
            return null;
        }
    }

    public double showPrice(String[] splitedCommand) {
        if (splitedCommand.length == 2) {
            if (productsDAO.ifExist(splitedCommand[1])) {
                double cost = productsDAO.getCost(splitedCommand[1]);
                out.println(cost);
                out.flush();
                return cost;
            } else {
                out.println(NO_PRODUCT_IN_DB_MESSAGE);
            }
        } else {
            out.println(WRONG_ARGUMENT_MESSAGE);
        }
        out.flush();
        return -1;
    }

    private void changeCost(String[] splitedCommand) {
        if (splitedCommand.length == 3) {
            try{
                if (productsDAO.ifExist(splitedCommand[1])) {
                    productsDAO.changeCost(splitedCommand[1], Double.parseDouble(splitedCommand[2]));
                    out.println("The price of the product was successfully changed!");
                } else {
                    out.println(NO_PRODUCT_IN_DB_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                out.println(WRONG_FORMAT_OF_NUMBER_MESSAGE + " The pattern of the command is '/change_price prodcut new_price'");
            }
        } else {
            out.println(WRONG_ARGUMENT_MESSAGE);
        }
        out.flush();
    }

    public List<ProductDataSet> filterByPrice(String[] splitedCommand) {
        if (splitedCommand.length == 3) {
            try {
                List<ProductDataSet> list = productsDAO.getFilteredList(Double.parseDouble(splitedCommand[1]), Double.parseDouble(splitedCommand[2]));
                list.forEach(out::println);
                out.flush();
                return list;
            } catch (NumberFormatException ex) {
                out.println(WRONG_FORMAT_OF_NUMBER_MESSAGE + " The the pattern of the command is '/filter_by_price from to'" +
                        "(all numbers inclusive!)");
            }
        } else {
            out.println(WRONG_ARGUMENT_MESSAGE);
        }
        out.flush();
        return null;
    }

    public void apply(String command) {
        String[] splitedCommand = command.split(" ");
        if (splitedCommand.length > 3) {
            out.println(WRONG_ARGUMENT_MESSAGE);
        } else {
           if (operationMap.containsKey(splitedCommand[0])) {
               operationMap.get(splitedCommand[0]).accept(splitedCommand);
           } else {
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
