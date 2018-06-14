package ncku.firesimplemail;

import java.io.Serializable;

public class Id implements Serializable {

    int id;

    public Id(int id){
        this.id=id;
    }
    public String getId(){
        return "Hello";
    }
    public String toString(){
        return "Hello";
    }
}
