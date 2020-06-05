package todo.manager;

import todo.db.DBConnectionProvider;
import todo.model.ToDoStatus;
import todo.model.Todo;
import todo.model.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ToDoManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private UserManager userManager = new UserManager();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public Todo getById(long id) {
        String sql = "SELECT * FROM todol WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return getTodoFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(long id, ToDoStatus status) {
        String sql = "UPDATE todo SET status = '" + status + "' WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM  todo WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean create(Todo todo) {
        String sql = "INSERT INTO todo(title, deadline,status,user_id) VALUES (?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, todo.getTitle());
            if (todo.getDeadline() != null) {
                statement.setString(2, sdf.format(todo.getDeadline()));
            } else {
                statement.setString(2, null);
            }
            statement.setString(3, todo.getStatus().name());
            statement.setLong(4, todo.getUser().getId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                todo.setId(rs.getLong(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Todo getTodoFromResultSet(ResultSet resultSet) {
        try {
            try {
                return Todo.builder()
                        .id(resultSet.getLong(1))
                        .title(resultSet.getString(2))
                        .deadline(resultSet.getString(3) == null ? null : sdf.parse(resultSet.getString(3)))
                        .status(ToDoStatus.valueOf(resultSet.getString(4)))
                        .user(userManager.getById(resultSet.getLong(5)))
                        .creatdate(sdf.parse(resultSet.getString(6)))
                        .build();
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Todo> getAllTodosByUserAndStatus(Long userId, ToDoStatus status) {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todo WHERE user_id = ? AND status = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, userId);
            statement.setString(2, status.name());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                todos.add(getTodoFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }

    public List<Todo> getAllTodosByUser(Long userId) {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todo WHERE user_id = " + userId;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                todos.add(getTodoFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todos;
    }
}
