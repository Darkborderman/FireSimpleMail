package ncku.firesimplemail;

import java.io.Serializable;

public class Task extends Mail implements Serializable {

    private Text[] text;

    public Task(String from,String to,String title)
    {
        super(from,to,title);
    }

    @Override
    public String toString() {return getTitle();}
}
