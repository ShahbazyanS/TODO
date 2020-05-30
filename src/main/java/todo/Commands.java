package todo;

public interface Commands {
    int EXIT = 0;
    int LOGIN = 1;
    int REGISTER = 2;

    int LOGOUT = 0;
    int ADD_TODO = 1;
    int MY_LIST = 2;
    int MY_IN_PROGRESS_LIST = 3;
    int MY_FINISHED_LIST = 4;
    int CHANGE_TODO_STATUS = 5;
    int DELETE_TODO_BY_ID = 6;

    static void commands(){
        System.out.println("please input " + EXIT + " for exit");
        System.out.println("please input " + LOGIN + " for login");
        System.out.println("please input " + REGISTER + " for register");
    }

    static void userCommand(){
        System.out.println("please input " + LOGOUT + " for logout");
        System.out.println("please input " + ADD_TODO + " for add todo");
        System.out.println("please input " + MY_LIST + " for print my all todos");
        System.out.println("please input " + MY_IN_PROGRESS_LIST + " for print my in progress todos");
        System.out.println("please input " + MY_FINISHED_LIST + " for print my finished todos");
        System.out.println("please input " + CHANGE_TODO_STATUS + " for change todo status");
        System.out.println("please input " + DELETE_TODO_BY_ID + " for delete todo by id");
    }
}
