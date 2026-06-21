import java.io.Serializable;

/**
 * Discountable 인터페이스를 구현한 카테고리 할인 클래스
 * 특정 카테고리의 상품에만 할인을 적용합니다
 * 
 * 예: 국산과일 카테고리는 15% 할인
 */
public class CategoryDiscount implements Discountable, Serializable {
    private static final long serialVersionUID = 1L;
    
    private Category category;      // 할인 대상 카테고리
    private int discountRate;       // 할인율
    private String discountName;    // 할인 이름 (예: "국산과일 특별할인")
    
    public CategoryDiscount(Category category, int discountRate, String discountName) {
        this.category = category;
        this.discountRate = discountRate;
        this.discountName = discountName;
    }
    
    @Override
    public int applyDiscount(int originalPrice) {
        int discountAmount = getDiscountAmount(originalPrice);
        return originalPrice - discountAmount;
    }
    
    @Override
    public String getDiscountInfo() {
        return discountName + " (" + category.getCategoryName() + " " + discountRate + "% 할인)";
    }
    
    @Override
    public int getDiscountAmount(int originalPrice) {
        return originalPrice * discountRate / 100;
    }
    
    /**
     * 주어진 상품이 이 할인 대상 카테고리에 속하는지 확인합니다
     */
    public boolean isApplicable(Product product) {
        return product.getCategory().getCategoryId() == category.getCategoryId();
    }
    
    // Getter methods
    public Category getCategory() {
        return category;
    }
    
    public int getDiscountRate() {
        return discountRate;
    }
    
    public String getDiscountName() {
        return discountName;
    }
}
