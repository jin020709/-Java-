import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String userId;
    protected String password;
    protected String name;
    protected String address;
    protected String phone;

    public User(String userId, String password, String name, String address, String phone) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
    
    public abstract void showMenu();
    
    public String getUserId() { 
        return userId; 
    }
    
    public String getPassword() {
        return password; 
    }
    
    public String getName() {
        return name; 
    }
    
    public String getAddress() {
        return address; 
    }
    
    public String getPhone() {
        return phone; 
    }
    
    public void setAddress(String address) { 
        this.address = address; 
    }
    
    public void setPhone(String phone) {
        this.phone = phone; 
    }
}
