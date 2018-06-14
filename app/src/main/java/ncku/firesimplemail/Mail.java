package ncku.firesimplemail;

import java.io.Serializable;

public class Mail extends MailHead implements Serializable{

    private String body;

    public Mail(Id id,String from,String to,String title,String body){
        super(id,from,to,title);
        this.body=body;
    }
    public String getBody(){
        return "Hello";
    }
}
