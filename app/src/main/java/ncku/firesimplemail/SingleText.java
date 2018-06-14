package ncku.firesimplemail;

import java.io.Serializable;

public class SingleText implements Serializable {

    private String text;

    public SingleText(String text){
        this.text=text;
    }

    public String getText() {
        return text;
    }
}
