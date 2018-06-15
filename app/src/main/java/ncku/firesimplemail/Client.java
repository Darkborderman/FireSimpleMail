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
        MailHead[] mailHeads=new MailHead[5];
        mailHeads[0]=new MailHead("1","from","to","title");
        mailHeads[1]=new MailHead("2","from","to","title");
        mailHeads[2]=new MailHead("3","from","to","title");
        mailHeads[3]=new MailHead("4","from","to","title");
        mailHeads[4]=new MailHead("5","from","to","title");



        return mailHeads;
    }

    public Mail getMail(String id){
        Mail mail=new Mail("from","to","title","body");
        return mail;
    }

    public boolean sendMail(Mail mail){
        return true;
    }

    public TaskHead[] getAllTask(){
        TaskHead[] taskHeads=new TaskHead[10];
        return taskHeads;
    }

    public Task getTask(String id){
        Task task=new Task("from","to","title");
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
