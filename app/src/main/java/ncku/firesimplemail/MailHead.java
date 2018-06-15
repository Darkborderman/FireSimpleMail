package ncku.firesimplemail;

import java.io.Serializable;

public class MailHead extends Head implements Serializable {

    public MailHead(String id,String from,String to,String title){
        super(id,from,to,title);
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
