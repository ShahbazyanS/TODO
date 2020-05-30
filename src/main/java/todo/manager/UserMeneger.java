package todo.manager;

import todo.db.DBConnectionProvider;
import todo.model.Gender;
import todo.model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserMeneger {
    private Connection connection;

    public UserMeneger() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addUser(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into user (name,surname,gender,age,email,password) Values(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, String.valueOf(user.getGender()));
        preparedStatement.setInt(4, user.getAge());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setString(6, user.getPassword());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            user.setId(id);
        }
    }

    public List<User> getAllUser() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
        List<User> users = new LinkedList<User>();
        while (resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setSurname(resultSet.getString(3));
            user.setGender(Gender.valueOf(resultSet.getString(4)));
            user.setAge(resultSet.getInt(5));
            user.setEmail(resultSet.getString(6));
            user.setPassword(resultSet.getString(7));
            users.add(user);
        }
        return users;
    }
}
