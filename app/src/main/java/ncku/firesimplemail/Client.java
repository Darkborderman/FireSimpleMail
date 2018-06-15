package ncku.firesimplemail;

public class Client {

    public Client(String serverIP,int port){
    }

    public boolean regist(String account,String password){
        return true;
    }

    public boolean authenticate(String account,String password){
        return true;
    }

    public MailHead[] getAllMail(){
        Id id=new Id(10);
        MailHead[] mailHeads=new MailHead[10];
        mailHeads[0]=new Mail(id,"from","to","title","body");
        mailHeads[1]=new Mail(id,"from","to","title","body");
        return mailHeads;
    }

    public Mail getMail(Id id){
        Mail mail=new Mail(id,"from","to","title","body");
        return mail;
    }

    public boolean sendMail(Mail mail){
        return true;
    }

    public TaskHead[] getAllTask(){
        TaskHead[] taskHeads=new TaskHead[10];
        return taskHeads;
    }

    public Task getTask(Id id){
        Task task=new Task(id,"from","to","title");
        return task;
    }

    public boolean createTask(Task task){
        return true;
    }

    public boolean updataTask(Task task){
        return true;
    }

    public boolean deleteTask(Task task){
        return true;
    }

}
