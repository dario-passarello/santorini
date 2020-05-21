package view;

public class ActivityNotAllowedException extends RuntimeException{
    public ActivityNotAllowedException(){
        super();
    }

    public ActivityNotAllowedException(String message){
        super(message);
    }
}
