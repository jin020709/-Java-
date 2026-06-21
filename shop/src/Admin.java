import java.util.List;

public class Admin extends User {
    private static final long serialVersionUID = 1L;

    private String adminLevel;

    public Admin(String userId, String password, String name, String address, String phone) {
        super(userId, password, name, address, phone);
        this.adminLevel = "ADMIN";
    }
    
    @Override
    public void showMenu() {
        System.out.println("\n=== 관리자 메뉴 ===");
        System.out.println("1. 상품 추가");
        System.out.println("2. 상품 삭제");
        System.out.println("3. 상품 재고관리");
        System.out.println("4. 고객 주문내역 확인");
        System.out.println("5. 고객 배송현황 변경");
        System.out.println("0. 로그아웃");
    }

    public void addProduct(int productId, String productName, int price, Category category, String description, List<Product> products) {
        System.out.println("\n=== 상품 추가 ===");

        Product newProduct = new Product(productId, productName, price, 50, category, description);
        products.add(newProduct);

        System.out.println("상품명: " + productName);
        System.out.println("가격: " + price + "원");
        System.out.println("카테고리: " + category.getCategoryName());
        System.out.println("상품이 실제 리스트에 정상 추가되었습니다.");
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
    
    public void updateProductInventory(int productId, int newQuantity, List<Product> products) {
        System.out.println("\n=== 상품 재고관리 ===");
        for (Product product : products) {
            if (product.getProductId() == productId) {
                int oldQuantity = product.getQuantity();
                product.setQuantity(newQuantity);
                System.out.println("상품명: " + product.getProductName());
                System.out.println("기존 재고: " + oldQuantity + "개");
                System.out.println("변경된 재고: " + newQuantity + "개");
                System.out.println("✅ 재고가 성공적으로 변경되었습니다.");
                return;
            }
        }
        System.out.println("해당 상품을 찾을 수 없습니다.");
    }
    
    public void viewAllOrders(List<Order> allOrders) {
        System.out.println("\n=== 고객 주문내역 확인 ===");
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
    
    public void updateShippingStatus(int orderId, String status, List<Order> allOrders) {
        System.out.println("\n=== 고객 배송현황 변경 ===");
        for (Order order : allOrders) {
            if (order.getOrderId() == orderId) {
                order.setShippingStatus(status);
                System.out.println("주문번호 " + orderId + "의 배송상태가 '" + status + "'로 변경되었습니다.");
                System.out.println("운송장번호: " + order.getTrackingNumber());
                System.out.println("고객명: " + order.getCustomer().getName());
                System.out.println("배송지: " + order.getCustomer().getAddress());
                return;
            }
        }
        System.out.println("해당 주문을 찾을 수 없습니다.");
    }
    
    public String getAdminLevel() { 
        return adminLevel; 
    }
}
