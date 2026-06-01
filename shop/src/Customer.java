import java.util.List;
import java.util.ArrayList;

/**
 * 고객 클래스
 */
public class Customer extends User {
    private List<Order> orderHistory;  // 구매 내역
    private Cart cart;                 // 장바구니
    
    public Customer(String userId, String password, String name, String address, String phone) {
        super(userId, password, name, address, phone);
        this.orderHistory = new ArrayList<>();
        this.cart = new Cart();
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== " + getName() + "님 환영합니다 ===");
        System.out.println("1. 상품 전체보기");
        System.out.println("2. 상품조회");
        System.out.println("3. 구매내역확인");
        System.out.println("4. 장바구니");
        System.out.println("5. 내정보보기");
        System.out.println("0. 로그아웃");
    }
    
    public void viewAllProducts(List<Product> products) {
        System.out.println("\n=== 상품 전체보기 ===");
        // 카테고리별로 상품 표시
        for (Product product : products) {
            System.out.println(product.getProductId() + ". " + product.getProductName() + 
                             " - " + product.getPrice() + "원");
        }
    }
    
    public void searchProduct(int productId, List<Product> products) {
        System.out.println("\n=== 상품 조회 ===");
        for (Product product : products) {
            if (product.getProductId() == productId) {
                System.out.println("상품명: " + product.getProductName());
                System.out.println("가격: " + product.getPrice() + "원");
                System.out.println("재고: " + product.getQuantity() + "개");
                System.out.println("설명: " + product.getDescription());
                return;
            }
        }
        System.out.println("해당 상품을 찾을 수 없습니다.");
    }
    
    public void viewOrderHistory() {
        System.out.println("\n=== 구매 내역 ===");
        if (orderHistory.isEmpty()) {
            System.out.println("구매 내역이 없습니다.");
            return;
        }
        
        for (Order order : orderHistory) {
            System.out.println("\n주문번호: " + order.getOrderId());
            System.out.println("주문일시: " + order.getOrderDate());
            System.out.println("배송상태: " + order.getShippingStatus());
            System.out.println("운송장번호: " + order.getTrackingNumber());
            System.out.println("총액: " + order.getTotalPrice() + "원");
        }
    }
    
    public void viewCart() {
        System.out.println("\n=== 장바구니 ===");
        List<OrderItem> items = cart.getItems();
        
        if (items.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            return;
        }
        
        for (OrderItem item : items) {
            System.out.println("- " + item.getProduct().getProductName() + 
                             ": " + item.getQuantity() + "개 = " + 
                             item.getItemPrice() + "원");
        }
        System.out.println("총액: " + cart.getTotalPrice() + "원");
    }
    
    public void viewMyInfo() {
        System.out.println("\n=== 내 정보 ===");
        System.out.println("아이디: " + userId);
        System.out.println("이름: " + name);
        System.out.println("주소: " + address);
        System.out.println("연락처: " + phone);
    }
    
    public void updateInfo(String address, String phone) {
        this.address = address;
        this.phone = phone;
        System.out.println("정보가 업데이트되었습니다.");
    }
    
    public void deleteAccount() {
        System.out.println("계정이 삭제되었습니다.");
    }
    
    public Cart getCart() { 
        return cart; 
    }
    
    public List<Order> getOrderHistory() { 
        return orderHistory; 
    }
    
    public void addOrder(Order order) {
        orderHistory.add(order);
    }
}
