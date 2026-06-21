import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * 주문 클래스
 * 주문 정보, 배송 상태, 할인 정보 관리
 */
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int orderId;
    private Customer customer;
    private List<OrderItem> items;
    private String orderDate;
    private String shippingStatus;
    private String trackingNumber;
    private int originalPrice;      // 할인 전 가격
    private int discountAmount;     // 할인액
    private int totalPrice;         // 최종 가격
    private Discountable discount;  // 할인 정보 (인터페이스 사용!)
    
    public Order(int orderId, Customer customer, String orderDate) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.orderDate = orderDate;
        this.shippingStatus = "준비중";
        this.trackingNumber = generateTrackingNumber();
        this.originalPrice = 0;
        this.discountAmount = 0;
        this.totalPrice = 0;
        this.discount = null;
    }
    
    public void addItem(OrderItem item) {
        items.add(item);
        originalPrice += item.getItemPrice();
        totalPrice = originalPrice;  // 초기값은 원가와 동일
    }
    
    /**
     * 할인을 적용합니다
     */
    public void setDiscount(Discountable discount) {
        this.discount = discount;
        applyDiscount();
    }
    
    /**
     * 할인을 실제로 적용하여 최종 가격 계산
     */
    private void applyDiscount() {
        if (discount != null) {
            this.discountAmount = discount.getDiscountAmount(originalPrice);
            this.totalPrice = discount.applyDiscount(originalPrice);
        } else {
            this.discountAmount = 0;
            this.totalPrice = originalPrice;
        }
    }
    
    private String generateTrackingNumber() {
        // KR + 랜덤 숫자 3개
        int randomNum = (int)(Math.random() * 1000);
        return String.format("KR%03d", randomNum);
    }
    
    // Getter methods
    public int getOrderId() { 
        return orderId; 
    }
    
    public Customer getCustomer() { 
        return customer; 
    }
    
    public List<OrderItem> getItems() { 
        return items; 
    }
    
    public String getOrderDate() { 
        return orderDate; 
    }
    
    public String getShippingStatus() { 
        return shippingStatus; 
    }
    
    public String getTrackingNumber() { 
        return trackingNumber; 
    }
    
    public int getOriginalPrice() { 
        return originalPrice; 
    }
    
    public int getDiscountAmount() { 
        return discountAmount; 
    }
    
    public int getTotalPrice() { 
        return totalPrice; 
    }
    
    public Discountable getDiscount() {
        return discount;
    }
    
    // Setter methods
    public void setShippingStatus(String status) { 
        this.shippingStatus = status; 
    }
    
    public void displayOrderInfo() {
        System.out.println("\n=== 주문 정보 ===");
        System.out.println("주문번호: " + orderId);
        System.out.println("고객명: " + customer.getName());
        System.out.println("주문일시: " + orderDate);
        System.out.println("배송상태: " + shippingStatus);
        System.out.println("운송장번호: " + trackingNumber);
        
        System.out.println("\n주문 상품:");
        for (OrderItem item : items) {
            System.out.println("- " + item.getProduct().getProductName() + 
                             ": " + item.getQuantity() + "개 = " + 
                             item.getItemPrice() + "원");
        }
        
        System.out.println("─────────────────────");
        System.out.println("원가: " + originalPrice + "원");
        
        // 할인 정보 표시
        if (discount != null) {
            System.out.println("할인: " + discount.getDiscountInfo());
            System.out.println("할인액: -" + discountAmount + "원");
        }
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━");
        System.out.println("최종 가격: " + totalPrice + "원");
        System.out.println("배송지: " + customer.getAddress());
    }
    
    @Override
    public String toString() {
        return "주문" + orderId + " - " + customer.getName() + " (" + totalPrice + "원)";
    }
}
