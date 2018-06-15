package ncku.firesimplemail;

import java.io.Serializable;

public class Head implements Serializable {

    private String id, from,to,title;

    public Head(String id,String from,String to,String title){
        this.id=id;
        this.from=from;
        this.to=to;
        this.title=title;
    }
    public String getId(){
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
