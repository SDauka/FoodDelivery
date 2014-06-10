package com.epam.sultangazy.webapp.helper;

import com.epam.sultangazy.webapp.db_pool.ConnectionPool;

import java.sql.*;

public class DaoUtils {
    /**
     * Returns a PreparedStatement of the given connection, set with the given SQL query and the
     * given parameter values.
     *
     * @param connection          The Connection to create the PreparedStatement from.
     * @param sql                 The SQL query to construct the PreparedStatement with.
     * @param returnGeneratedKeys Set whether to return generated keys or not.
     * @param values              The parameter values to be set in the created PreparedStatement.
     * @throws java.sql.SQLException If something fails during creating the PreparedStatement.
     */
    public static PreparedStatement prepareStatement
    (Connection connection, String sql, boolean returnGeneratedKeys, Object[] values) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql,
                returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        setValues(preparedStatement, values);
        return preparedStatement;
    }

    /**
     * Set the given parameter values in the given PreparedStatement.
     *
     * @param preparedStatement The PreparedStatement to set the given parameter values in.
     * @param values            The parameter values to be set in the created PreparedStatement.
     * @throws java.sql.SQLException If something fails during setting the PreparedStatement values.
     */
    public static void setValues(PreparedStatement preparedStatement, Object... values)
            throws SQLException {
        for (int i = 0; i < values.length; i++) {
            preparedStatement.setObject(i + 1, values[i]);
        }
    }


    /**
     * Quietly close the Connection. Any errors will be printed to the stderr.
     *
     * @param connection The Connection to be closed quietly.
     */
    public static void close(Connection connection) {
        ConnectionPool.getInstance().setFreeConnection(connection);
    }

    /**
     * Quietly close the Statement. Any errors will be printed to the stderr.
     *
     * @param statement The Statement to be closed quietly.
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Quietly close the ResultSet. Any errors will be printed to the stderr.
     *
     * @param resultSet The ResultSet to be closed quietly.
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Quietly close the Connection and Statement. Any errors will be printed to the stderr.
     *
     * @param connection The Connection to be closed quietly.
     * @param statement  The Statement to be closed quietly.
     */
    public static void close(Connection connection, Statement statement) {
        close(statement);
        close(connection);
    }

    /**
     * Quietly close the Connection, Statement and ResultSet. Any errors will be printed to the stderr.
     *
     * @param connection The Connection to be closed quietly.
     * @param statement  The Statement to be closed quietly.
     * @param resultSet  The ResultSet to be closed quietly.
     */
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        close(resultSet);
        close(statement);
        close(connection);
    }

}
