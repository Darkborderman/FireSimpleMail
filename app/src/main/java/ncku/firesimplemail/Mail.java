package ncku.firesimplemail;

import java.io.Serializable;

public class Mail implements Serializable{

    private String body;
    private String from,to,title;

    public Mail(String from,String to,String title,String body){
        this.from=from;
        this.to=to;
        this.title=title;
        this.body=body;
    }
    public Mail(String from,String to,String title){
        this.from=from;
        this.to=to;
        this.title=title;
    }

    public String getTitle() {
        return title;
    }
    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }

    public String getBody(){
        return "Hello";
    }
}
