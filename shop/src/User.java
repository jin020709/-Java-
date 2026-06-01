import java.util.List;
import java.util.ArrayList;

/**
 * 추상 클래스 - 모든 사용자의 기본 정보
 */
public abstract class User {
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
    
    // Getter methods
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
    
    // Setter methods
    public void setAddress(String address) { 
        this.address = address; 
    }
    
    public void setPhone(String phone) { 
        this.phone = phone; 
    }
}
