package server;

import java.sql.*;

public class DbConnection {
    // database connection parameters
    String url = "jdbc:mysql://localhost:3306/mtchallenge";
    String username = "root";
    String password = "root";
    Connection connection;
    Statement statement;

    public DbConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        this.statement = connection.createStatement();
    }

    public void create(String sqlCommand) throws SQLException {
        this.statement.execute(sqlCommand);
    }

    public ResultSet read(String sqlCommand) throws SQLException {
        return this.statement.executeQuery(sqlCommand);
    }

    public void update(String sqlCommand) throws SQLException {
        this.statement.execute(sqlCommand);
    }

    public void delete(String sqlCommand) throws SQLException {
        this.statement.execute(sqlCommand);
    }

    public void close() throws SQLException {
        if (this.statement != null) this.statement.close();
        if (this.connection != null) this.connection.close();
    }

    public void createParticipant(String username, String firstname, String lastname, String emailAddress, String dob, String regNo, String imagePath) throws SQLException {
        String sql = "INSERT INTO `participant` (`username`, `firstname`, `lastname`, `emailAddress`, `dob`, `regNo`, `imagePath`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, firstname);
            stmt.setString(3, lastname);
            stmt.setString(4, emailAddress);
            stmt.setString(5, dob);
            stmt.setString(6, regNo);
            stmt.setString(7, imagePath);
            stmt.executeUpdate();
        }
    }

    public void createParticipantRejected(String username, String firstname, String lastname, String emailAddress, String dob, String regNo, String imagePath) throws SQLException {
        String sql = "INSERT INTO `rejectedparticipant` (`username`, `firstname`, `lastname`, `emailAddress`, `dob`, `regNo`, `imagePath`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, firstname);
            stmt.setString(3, lastname);
            stmt.setString(4, emailAddress);
            stmt.setString(5, dob);
            stmt.setString(6, regNo);
            stmt.setString(7, imagePath);
            stmt.executeUpdate();
        }
    }
}
