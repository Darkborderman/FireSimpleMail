package ncku.firesimplemail;

import java.io.Serializable;

public class TaskHead extends Head implements Serializable {

    public TaskHead(Id id,String from,String to,String title){
        super(id,from,to,title);
    }
}
