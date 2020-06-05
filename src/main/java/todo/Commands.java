package todo;

public interface Commands {

    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;

    int LOGOUT = 0;
    int ADD_NEW_TODO = 1;
    int MY_ALL_LIST = 2;
    int MY_TODO_LIST = 3;
    int MY_IN_PROGRESS_LIST = 4;
    int MY_FINISHED_LIST = 5;
    int CHANGE_TODO_STATUS = 6;
    int DELETE_TODO = 7;

    static void printMainCommands() {
        System.out.println("Please input " + EXIT + " from exit");
        System.out.println("Please input " + LOGIN + " from login");
        System.out.println("Please input " + REGISTER + " from register");
    }

    static void printUserCommands() {
        System.out.println("Please input " + LOGOUT + " from logout");
        System.out.println("Please input " + ADD_NEW_TODO + " from add new todo");
        System.out.println("Please input " + MY_ALL_LIST + " from print all todo");
        System.out.println("Please input " + MY_TODO_LIST + " from print my todo list");
        System.out.println("Please input " + MY_IN_PROGRESS_LIST + " from print my in progress todo");
        System.out.println("Please input " + MY_FINISHED_LIST + " from print my finished todo");
        System.out.println("Please input " + CHANGE_TODO_STATUS + " from change todo status");
        System.out.println("Please input " + DELETE_TODO + "from delete todo");
    }
}
