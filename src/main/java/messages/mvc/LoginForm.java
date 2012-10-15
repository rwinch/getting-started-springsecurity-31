package messages.mvc;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {
    @NotEmpty(message="Username is required")
    private String j_username;

    @NotEmpty(message="Password is required")
    private String j_password;

    public String getJ_username() {
        return j_username;
    }

    public void setJ_username(String j_username) {
        this.j_username = j_username;
    }

    public String getJ_password() {
        return j_password;
    }

    public void setJ_password(String j_password) {
        this.j_password = j_password;
    }

}
