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
        mailHeads[0]=new MailHead("1","from","to","mail");
        mailHeads[1]=new MailHead("2","from","to","mail");
        mailHeads[2]=new MailHead("3","from","to","mail");
        mailHeads[3]=new MailHead("4","from","to","mail");
        mailHeads[4]=new MailHead("5","from","to","mail");



        return mailHeads;
    }

    public Mail getMail(String id){
        Mail mail=new Mail("from","to","getMail","body");
        return mail;
    }

    public boolean sendMail(Mail mail){
        return true;
    }

    public TaskHead[] getAllTask(){
        TaskHead[] taskHeads=new TaskHead[5];
        taskHeads[0]=new TaskHead("1","from","to","task");
        taskHeads[1]=new TaskHead("2","from","to","task");
        taskHeads[2]=new TaskHead("3","from","to","task");
        taskHeads[3]=new TaskHead("4","from","to","task");
        taskHeads[4]=new TaskHead("5","from","to","task");
        return taskHeads;
    }

    public Task getTask(String id){
        Task task=new Task("from","to","getTask");
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
