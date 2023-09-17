package mapping;

import model.Developer;

public class DeveloperResponse {

    private Developer developer ;

    private String message ;
    private int Status;

    public DeveloperResponse(Developer developer, String message, int status) {
        this.developer = developer;
        this.message = message;
        Status = status;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
