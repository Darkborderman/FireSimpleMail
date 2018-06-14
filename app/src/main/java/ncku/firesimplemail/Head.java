package ncku.firesimplemail;

import java.io.Serializable;

public class Head implements Serializable {

    private Id id;
    private String from,to,title;

    public Head(Id id,String from,String to,String title){
        this.id=id;
        this.from=from;
        this.to=to;
        this.title=title;
    }
    public Id getId(){
        return this.id;
    }
    public String getSender(){
        return this.from;
    }
    public String getReceiver(){
        return this.to;
    }
    public String getTitle(){
        return this.title;
    }

}
