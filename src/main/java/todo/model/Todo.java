package todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Todo {
    private long id;
    private String title;
    private Date deadline;
    private ToDoStatus status;
    private User user;
    private Date creatdate;


}
