package todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    private int id;
    private String name;
    private Date creatdate;
    private String deadline;
    private Status status;
    private User user;

    public Todo(String name, Date date, String deadline, Status status, User user) {
        this.name = name;
        this.creatdate =  date;
        this.status = status;
        this.user = user;
    }
}
