package todo;

import todo.manager.TodoManager;
import todo.manager.UserMeneger;
import todo.model.Gender;
import todo.model.Status;
import todo.model.Todo;
import todo.model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main implements Commands {

    public static Scanner scanner = new Scanner(System.in);
    public static UserMeneger userMeneger = new UserMeneger();
    public static TodoManager todoManager = new TodoManager();
    public static User currentUser = null;

    public static void main(String[] args) throws SQLException {
        userMeneger.getAllUser();
        boolean isRun = true;
        while (isRun) {
            Commands.commands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    login();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                default:
                    System.out.println("wrong command!");
            }
        }
    }

    private static void login() throws SQLException {
        System.out.println("please input email and password for login");
        String s = scanner.nextLine();
        String[] str = s.split(",");
        List<User> allUser = userMeneger.getAllUser();
        for (User user : allUser) {
            if (user.getEmail().equals(str[0]) && user.getPassword().equals(str[1])) {
                currentUser = user;
                loginUser();
            }
        }
    }

    private static void loginUser() {
        boolean isRun = true;
        while (isRun) {
            Commands.userCommand();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_TODO:
                    addTodo();
                    break;
                case MY_LIST:
                    try {
                        todoManager.selectAllTodo(currentUser);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case MY_IN_PROGRESS_LIST:
                    try {
                        todoManager.myProgressList(currentUser);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case MY_FINISHED_LIST:
                    try {
                        todoManager.myFinishedList(currentUser);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case CHANGE_TODO_STATUS:
                    changeTodoStatus();
                    break;
                case DELETE_TODO_BY_ID:
                    delleteTodo();
                    break;

            }
        }
    }

    private static void changeTodoStatus() {
        try {
            todoManager.selectAllTodo(currentUser);
            System.out.println("please input id for change status");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println("please input new status ");
            String status = scanner.nextLine();
            todoManager.changeTodoStatus(currentUser, id, Status.valueOf(status));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private static void delleteTodo() {
        try {
            List<Todo> todos = todoManager.selectAllTodo(currentUser);
            for (Todo todo : todos) {
                System.out.println(todo);
            }
            System.out.println("please input id for delete to do");
            int id = Integer.parseInt(scanner.nextLine());
            todoManager.deleteTodoById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addTodo() {
        System.out.println("please input name, deadline, status for add todo");
        try {
            String s = scanner.nextLine();
            String[] str = s.split(",");
            Todo todo = new Todo();
            todo.setName(str[0]);
            todo.setDeadline(str[1]);
            todo.setStatus(Status.valueOf(str[2].toUpperCase()));
            todo.setUser(currentUser);
            todoManager.addTodo(todo);
        } catch (SQLException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private static void registerUser() {
        System.out.println("please input name, surname, gender, age, email, password for register");
        try {
            String s = scanner.nextLine();
            String[] str = s.split(",");
            User user = new User();
            user.setName(str[0]);
            user.setSurname(str[1]);
            user.setGender(Gender.valueOf(str[2].toUpperCase()));
            user.setAge(Integer.parseInt(str[3]));
            user.setEmail(str[4]);
            user.setPassword(str[5]);
            userMeneger.addUser(user);
        } catch (SQLException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
