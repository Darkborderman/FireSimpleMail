package ncku.firesimplemail;

import java.io.Serializable;

public class Text implements Serializable {

    public String[] getAllText(){
        String[] text=new String[10];
        return text;
    }
    public String getText(int index){
        return "Hello";
    }
}
