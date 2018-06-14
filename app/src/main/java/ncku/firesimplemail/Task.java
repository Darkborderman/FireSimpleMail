package ncku.firesimplemail;

public class Task extends TaskHead{

    private Text[] text;

    public Task(Id id,String from,String to,String title){
        super(id,from,to,title);
    }
    public Task(Id id,String from,String to,String title,Text[] text){
        super(id,from,to,title);
        this.text=text;
    }
}
