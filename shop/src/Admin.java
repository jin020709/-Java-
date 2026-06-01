import java.util.List;

/**
 * 관리자 클래스
 */
public class Admin extends User {
    private String adminLevel;  // 관리자 등급
    
    public Admin(String userId, String password, String name, String address, String phone) {
        super(userId, password, name, address, phone);
        this.adminLevel = "ADMIN";
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== 관리자 메뉴 ===");
        System.out.println("1. 상품추가");
        System.out.println("2. 상품삭제");
        System.out.println("3. 고객 배송현황 변경");
        System.out.println("4. 고객 주문내역 확인");
        System.out.println("0. 로그아웃");
    }
    
    public void addProduct(String productName, int price, Category category, String description) {
        System.out.println("\n=== 상품 추가 ===");
        System.out.println("상품명: " + productName);
        System.out.println("가격: " + price + "원");
        System.out.println("카테고리: " + category.getCategoryName());
        System.out.println("상품이 추가되었습니다.");
    }
    
    public void deleteProduct(int productId, List<Product> products) {
        System.out.println("\n=== 상품 삭제 ===");
        for (Product product : products) {
            if (product.getProductId() == productId) {
                System.out.println(product.getProductName() + "이(가) 삭제되었습니다.");
                products.remove(product);
                return;
            }
        }
        System.out.println("해당 상품을 찾을 수 없습니다.");
    }
    
    public void updateShippingStatus(int orderId, String status, List<Order> allOrders) {
        System.out.println("\n=== 배송현황 변경 ===");
        for (Order order : allOrders) {
            if (order.getOrderId() == orderId) {
                order.setShippingStatus(status);
                System.out.println("주문번호 " + orderId + "의 배송상태가 '" + status + "'로 변경되었습니다.");
                System.out.println("운송장번호: " + order.getTrackingNumber());
                return;
            }
        }
        System.out.println("해당 주문을 찾을 수 없습니다.");
    }
    
    public void viewAllOrders(List<Order> allOrders) {
        System.out.println("\n=== 모든 고객 주문내역 ===");
        if (allOrders.isEmpty()) {
            System.out.println("주문 내역이 없습니다.");
            return;
        }
        
        for (Order order : allOrders) {
            System.out.println("\n고객명: " + order.getCustomer().getName());
            System.out.println("주문번호: " + order.getOrderId());
            System.out.println("주문일시: " + order.getOrderDate());
            System.out.println("배송상태: " + order.getShippingStatus());
            System.out.println("운송장번호: " + order.getTrackingNumber());
            System.out.println("총액: " + order.getTotalPrice() + "원");
            
            System.out.println("상품목록:");
            for (OrderItem item : order.getItems()) {
                System.out.println("  - " + item.getProduct().getProductName() + 
                                 ": " + item.getQuantity() + "개");
            }
        }
    }
    
    public String getAdminLevel() { 
        return adminLevel; 
    }
}
