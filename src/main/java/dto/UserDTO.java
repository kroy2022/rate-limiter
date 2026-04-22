package dto;

public class UserDTO {
    private String userId;
    private String plan;

    public UserDTO() {}

    public UserDTO(String userId, String plan) {
        this.userId = userId;
        this.plan = plan;
    }

    public void deinitializeUser() {
        this.userId = "";
        this.plan = "";
    }

    /*
    GETTERS
     */
    public String getUserId() {
        return this.userId;
    }

    public String getPlan() {
        return this.plan;
    }

    /*
    SETTERS
    */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
}   
