package lab4.executor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {

    private final Connection connection;

    public Executor(Connection connection) {
        this.connection = connection;
    }

    public int executeUpdate(String updateQuery) {
        int amountOfUpdatedLines = 0;
        try (Statement statement = connection.createStatement()) {
            amountOfUpdatedLines = statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

        return amountOfUpdatedLines;
    }

    public <T> T executeQuery(String query, ResultHandler<T> resultHandler) {
        T answerForQueryData = null;
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
            try (ResultSet resultSet = statement.getResultSet()) {
                answerForQueryData = resultHandler.handle(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return answerForQueryData;
    }

}
