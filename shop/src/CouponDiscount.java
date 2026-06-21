import java.io.Serializable;

/**
 * Discountable 인터페이스를 구현한 쿠폰 할인 클래스
 * 쿠폰의 할인율을 적용합니다
 */
public class CouponDiscount implements Discountable, Serializable {
    private static final long serialVersionUID = 1L;
    
    private Coupon coupon;
    
    public CouponDiscount(Coupon coupon) {
        this.coupon = coupon;
    }
    
    @Override
    public int applyDiscount(int originalPrice) {
        int discountAmount = getDiscountAmount(originalPrice);
        return originalPrice - discountAmount;
    }
    
    @Override
    public String getDiscountInfo() {
        return coupon.getCouponCode() + " - " + coupon.getDiscountRate() + "% 할인";
    }
    
    @Override
    public int getDiscountAmount(int originalPrice) {
        return originalPrice * coupon.getDiscountRate() / 100;
    }
    
    public Coupon getCoupon() {
        return coupon;
    }
}
