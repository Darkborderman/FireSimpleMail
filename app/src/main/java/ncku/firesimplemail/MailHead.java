package ncku.firesimplemail;

import java.io.Serializable;

public class MailHead extends Head implements Serializable {

    public MailHead(Id id,String from,String to,String title){
        super(id,from,to,title);
    }
}
