/**
 * 할인 기능을 정의하는 인터페이스
 * 다양한 할인 방식을 구현할 수 있습니다
 */
public interface Discountable {
    
    /**
     * 할인을 적용하여 최종 가격을 반환합니다
     * @param originalPrice 원래 가격
     * @return 할인 적용 후 최종 가격
     */
    int applyDiscount(int originalPrice);
    
    /**
     * 할인 정보를 문자열로 반환합니다
     * @return 할인 정보 (예: "10% 할인", "국산과일 15% 할인")
     */
    String getDiscountInfo();
    
    /**
     * 할인액을 반환합니다
     * @param originalPrice 원래 가격
     * @return 할인액
     */
    int getDiscountAmount(int originalPrice);
}
