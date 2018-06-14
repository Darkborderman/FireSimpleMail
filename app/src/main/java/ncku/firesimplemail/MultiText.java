package ncku.firesimplemail;

import java.io.Serializable;

public class MultiText implements Serializable {

    private String[] text;

    public MultiText(String[] text){
        this.text=text;
    }
}
