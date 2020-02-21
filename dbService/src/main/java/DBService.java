import dao.ProductsDAO;
import java.io.PrintWriter;
import java.sql.*;

public class DBService implements AutoCloseable {

    private static String WRONG_ARGUMENT_MESSAGE = "The number of arguments is more or less than it was expected!";

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

    private void add(String[] splitedCommand) {
        if (!productsDAO.ifExist(splitedCommand[1])) {
            productsDAO.add(splitedCommand[1], Double.parseDouble(splitedCommand[2]));
        } else {
            out.println("This product has been already added to the data base!\n" +
                    "Please try to change the name or update the cost of this product by command" +
                    "\"\\change_price 'product' 'new_price'\"");
        }
    }

    private void delete(String[] splitedCommand) {
        if (splitedCommand.length == 2) {
            productsDAO.remove(splitedCommand[1]);
        } else {
            out.println(WRONG_ARGUMENT_MESSAGE);
        }
    }

    private void showAll(String[] splitedCommand) {
        if (splitedCommand.length == 1) {
            productsDAO.getList().forEach(System.out::println);
        } else {
            out.println(WRONG_ARGUMENT_MESSAGE);
        }
    }

    private void showPrice(String[] splitedCommand) {
        if (splitedCommand.length == 2) {
            out.println(productsDAO.getCost(splitedCommand[1]));
        } else {
            out.println(WRONG_ARGUMENT_MESSAGE);
        }
    }

    private void changeCost(String[] splitedCommand) {
        if (splitedCommand.length == 3) {
            productsDAO.changeCost(splitedCommand[1], Double.parseDouble(splitedCommand[2]));
        } else {
            out.println(WRONG_ARGUMENT_MESSAGE);
        }
    }

    private void filterByPrice(String[] splitedCommand) {
        if (splitedCommand.length == 3) {
            productsDAO.getFilteredList(Double.parseDouble(splitedCommand[1]), Double.parseDouble(splitedCommand[2])).forEach(out::println);
        } else {
            out.println(WRONG_ARGUMENT_MESSAGE);
        }
    }

    public void apply(String command) {
        String[] splitedCommand = command.split(" ");
        if (splitedCommand.length > 3) {
            out.println(WRONG_ARGUMENT_MESSAGE);
        } else {
            switch (splitedCommand[0]) {
                case "/add": {
                    add(splitedCommand);
                    break;
                }
                case "/delete": {
                    delete(splitedCommand);
                    break;
                }
                case "/show_all": {
                    showAll(splitedCommand);
                    break;
                }
                case "/price": {
                    showPrice(splitedCommand);
                    break;
                }
                case "/change_price": {
                    changeCost(splitedCommand);
                    break;
                }
                case "/filter_by_price": {
                    filterByPrice(splitedCommand);
                    break;
                }
                default: {
                    out.println("!!!UNKNOWN OPERATION!!!");
                }
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
