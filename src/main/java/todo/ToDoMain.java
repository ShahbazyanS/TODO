package todo;

import todo.manager.ToDoManager;
import todo.manager.UserManager;
import todo.model.ToDoStatus;
import todo.model.Todo;
import todo.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ToDoMain implements Commands {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static ToDoManager toDoManager = new ToDoManager();
    private static User currentUser = null;

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    private static void loginUser() {
        System.out.println("please input email, password");
        try {
            String loginStr = scanner.nextLine();
            String[] loginArr = loginStr.split(",");
            User user = userManager.getByEmailAndPassword(loginArr[0], loginArr[1]);
            if (user != null) {
                currentUser = user;
                loginSuccsess();
            } else {
                System.out.println("Wrong email or password");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong Data");
        }
    }

    private static void loginSuccsess() {
        System.out.println("Welcome " + currentUser.getName());
        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommands();
            int command;
            try {
                command = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command = -1;
            }
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_NEW_TODO:
                    addNewTodo();
                    break;
                case MY_ALL_LIST:
                    printTodo(toDoManager.getAllTodosByUser(currentUser.getId()));
                    break;
                case MY_TODO_LIST:
                    printTodo(toDoManager.getAllTodosByUserAndStatus(currentUser.getId(), ToDoStatus.TODO));
                    break;
                case MY_IN_PROGRESS_LIST:
                    printTodo(toDoManager.getAllTodosByUserAndStatus(currentUser.getId(), ToDoStatus.IN_PROGRESS));
                    break;
                case MY_FINISHED_LIST:
                    printTodo(toDoManager.getAllTodosByUserAndStatus(currentUser.getId(), ToDoStatus.FINISHED));
                    break;
                case CHANGE_TODO_STATUS:
                    changeToDoStatus();
                    break;
                case DELETE_TODO:
                    deleteToDo();
                    break;
                default:
                    System.out.println("Wrong command");
            }
        }
    }

    private static void printTodo(List<Todo> allTodosByUser) {
        for (Todo todo : allTodosByUser) {
            System.out.println(todo);
        }
    }

    private static void addNewTodo() {
        System.out.println("please input title, deadline (yyyy-MM-dd HH:mm:ss)");
        String todoDataStr = scanner.nextLine();
        String[] todoDataArr = todoDataStr.split(",");
        Todo todo = new Todo();
        try {
            String title = todoDataArr[0];
            todo.setTitle(title);
            try {
                if (todoDataArr[1] != null) {
                    todo.setDeadline(sdf.parse(todoDataArr[1]));
                }
            } catch (ArrayIndexOutOfBoundsException e) {

            } catch (ParseException e) {
                System.out.println("Please input date by this format (yyyy-MM-dd HH:mm:ss)");
            }
            todo.setStatus(ToDoStatus.TODO);
            todo.setUser(currentUser);
            if (toDoManager.create(todo)) {
                System.out.println("Todo was added");
            } else {
                System.out.println("Something went wrong");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong data");
        }
    }

    private static void deleteToDo() {
        System.out.println("Please choose todo from list");
        List<Todo> list = toDoManager.getAllTodosByUser(currentUser.getId());
        for (Todo todo : list) {
            System.out.println(todo);
        }
        long id = Long.parseLong(scanner.nextLine());
        Todo byId = toDoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            toDoManager.delete(id);
        } else {
            System.out.println("Wrong id");
        }
    }

    private static void changeToDoStatus() {
        System.out.println("Please choose todo from list");
        List<Todo> list = toDoManager.getAllTodosByUser(currentUser.getId());
        for (Todo todo : list) {
            System.out.println(todo);
        }
        long id = Long.parseLong(scanner.nextLine());
        Todo byId = toDoManager.getById(id);
        if (byId.getUser().getId() == currentUser.getId()) {
            System.out.println("Please chose status");
            System.out.println(Arrays.toString(ToDoStatus.values()));
            ToDoStatus status = ToDoStatus.valueOf(scanner.nextLine());
            if (toDoManager.update(id, status)) {
                System.out.println("Status was change");
            } else {
                System.out.println("Somethig was wrong");
            }
        } else {
            System.out.println("Wrong id");
        }
    }

    private static void registerUser() {
        System.out.println("please input user data (name, surname, email, password)");
        try {
            String userDataStr = scanner.nextLine();
            String[] userDataArr = userDataStr.split(",");
            User userFromManager = userManager.getByEmail(userDataArr[2]);
            if (userFromManager == null) {
                User user = new User();
                user.setName(userDataArr[0]);
                user.setSurname(userDataArr[1]);
                user.setEmail(userDataArr[2]);
                user.setPassword(userDataArr[3]);
                if (userManager.register(user)) {
                    System.out.println("User was successfully added");
                } else {
                    System.out.println("Something went wrong");
                }
            } else {
                System.out.println("User already exist");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Wrong data");
        }
    }
}
