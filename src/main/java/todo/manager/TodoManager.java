package todo.manager;

import todo.db.DBConnectionProvider;
import todo.model.Status;
import todo.model.Todo;
import todo.model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TodoManager {

    private Connection connection;

    public TodoManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addTodo(Todo todo) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("Insert into todo (name, create_date,deadline,status, user_id) Values(?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, todo.getName());
        preparedStatement.setDate(2, todo.getCreatdate());
        preparedStatement.setString(3, todo.getDeadline());
        preparedStatement.setString(4, String.valueOf(todo.getStatus()));
        preparedStatement.setInt(5, todo.getUser().getId());
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            int anInt = generatedKeys.getInt(1);
            todo.setId(anInt);
        }
    }

    public List<Todo> selectAllTodo(User user) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE user_id = '" + user.getId() + "'");
        List<Todo> todos = new LinkedList<Todo>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt(1));
            todo.setName(resultSet.getString(2));
            todo.setCreatdate(resultSet.getDate(3));
            todo.setDeadline(resultSet.getString(4));
            todo.setStatus(Status.valueOf(resultSet.getString(5)));
            todo.setUser(user);
            todos.add(todo);
            if (todo.getUser().equals(user)){
                System.out.println(todo);
            }

        }
        return todos;
    }
    public List<Todo> myProgressList(User user) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE status = 'IN_PROGRESS' AND user_id = '" + user.getId() + "'");
        List<Todo> todos = new LinkedList<Todo>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt(1));
            todo.setName(resultSet.getString(2));
            todo.setCreatdate(resultSet.getDate(3));
            todo.setDeadline(resultSet.getString(4));
            todo.setStatus(Status.valueOf(resultSet.getString(5)));
            todo.setUser(user);
            todos.add(todo);
            System.out.println(todo);
        }
        return todos;
    }

    public List<Todo> myFinishedList(User user) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE status = 'FINISHED' AND user_id = '" + user.getId() + "'" );
        List<Todo> todos = new LinkedList<Todo>();
        while (resultSet.next()) {
            Todo todo = new Todo();
            todo.setId(resultSet.getInt(1));
            todo.setName(resultSet.getString(2));
            todo.setCreatdate(resultSet.getDate(3));
            todo.setDeadline(resultSet.getString(4));
            todo.setStatus(Status.valueOf(resultSet.getString(5)));
            todo.setUser(user);
            todos.add(todo);
            System.out.println(todo);
        }

        return todos;
    }

    public void changeTodoStatus(User user, int id, Enum<Status> status) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE todo SET status = ? WHERE user_id = ? AND id = ?", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,String.valueOf(status));
        preparedStatement.setInt(2,user.getId());
        preparedStatement.setInt(3,id);
        preparedStatement.executeUpdate();

    }


        public void deleteTodoById(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM todo WHERE id = ?");
          preparedStatement.setInt(1,id);
          preparedStatement.executeUpdate();
    }

}
