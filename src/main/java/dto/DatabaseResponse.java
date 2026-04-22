package dto;

public class DatabaseResponse {
    private boolean success;
    private String message;
    private UserDTO userData;    
    private String apiKey;  
    
    public DatabaseResponse() {}

    public DatabaseResponse(boolean success, String message, UserDTO userData, String apiKey) {
        this.success = success;
        this.message = message;
        this.userData = userData;
        this.apiKey = apiKey;
    }
    
    public void deinitializeDB() {
        this.success = false;
        this.message = "";
        this.userData = new UserDTO();
        this.apiKey = "";
    }

    /*
    GETTERS
     */
    public boolean getSucess() {
        return this.success;
    }

    public String getMessage() {
        return this.message;
    }

    public UserDTO getUserData() {
        return this.userData;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    /*
    SETTERS 
    */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserData(UserDTO userData) {
        this.userData = userData;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
