import java.io.Serializable;
import java.time.LocalDate;

/**
 * 쿠폰 정보를 저장하는 클래스
 * 할인율, 사용 여부, 만료일 관리
 */
public class Coupon implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String couponCode;      // 쿠폰 코드 (예: "WELCOME10")
    private int discountRate;       // 할인율 (예: 10, 20)
    private boolean isUsed;         // 사용 여부
    private LocalDate expiryDate;   // 만료일
    
    public Coupon(String couponCode, int discountRate, LocalDate expiryDate) {
        this.couponCode = couponCode;
        this.discountRate = discountRate;
        this.isUsed = false;
        this.expiryDate = expiryDate;
    }
    
    /**
     * 쿠폰이 사용 가능한지 확인합니다
     */
    public boolean isValid() {
        // 사용하지 않았고 && 만료되지 않았음
        return !isUsed && LocalDate.now().isBefore(expiryDate);
    }
    
    /**
     * 쿠폰을 사용 처리합니다
     */
    public void use() {
        this.isUsed = true;
    }
    
    // Getter methods
    public String getCouponCode() { 
        return couponCode; 
    }
    
    public int getDiscountRate() { 
        return discountRate; 
    }
    
    public boolean isUsed() { 
        return isUsed; 
    }
    
    public LocalDate getExpiryDate() { 
        return expiryDate; 
    }
    
    @Override
    public String toString() {
        return couponCode + " (" + discountRate + "% 할인)";
    }
}
