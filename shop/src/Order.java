import java.util.List;
import java.util.ArrayList;

/**
 * 주문 클래스
 */
public class Order {
    private int orderId;
    private Customer customer;
    private List<OrderItem> items;           // 주문 상품들
    private String orderDate;                // 주문 일시
    private String shippingStatus;           // 상태: 준비중, 배송중, 배송완료
    private String trackingNumber;           // 운송장 번호 (KR + 숫자3개)
    private int totalPrice;
    
    public Order(int orderId, Customer customer, String orderDate) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.orderDate = orderDate;
        this.shippingStatus = "준비중";
        this.trackingNumber = generateTrackingNumber();
        this.totalPrice = 0;
    }
    
    public void addItem(OrderItem item) {
        items.add(item);
        totalPrice += item.getItemPrice();
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
    
    public int getTotalPrice() { 
        return totalPrice; 
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
        System.out.println("총액: " + totalPrice + "원");
        System.out.println("배송지: " + customer.getAddress());
    }
    
    @Override
    public String toString() {
        return "주문" + orderId + " - " + customer.getName() + " (" + totalPrice + "원)";
    }
}
