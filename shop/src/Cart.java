import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * 장바구니 클래스
 * 상품을 추가할 때 재고를 자동으로 감소시킵니다
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<OrderItem> cartItems;
    
    public Cart() {
        this.cartItems = new ArrayList<>();
    }
    
    public void addItem(OrderItem item) {
        Product product = item.getProduct();
        int requestedQuantity = item.getQuantity();
        
        // 재고 확인
        if (product.getQuantity() < requestedQuantity) {
            System.out.println("❌ 재고가 부족합니다!");
            System.out.println("   현재 재고: " + product.getQuantity() + "개");
            System.out.println("   요청 수량: " + requestedQuantity + "개");
            return;
        }
        
        // 재고 감소 (메모리에서만)
        product.setQuantity(product.getQuantity() - requestedQuantity);
        System.out.println("📉 재고 감소: " + product.getProductName() + 
                         " (" + requestedQuantity + "개 -> 남은 재고: " + 
                         product.getQuantity() + "개)");
        
        // 같은 상품이 이미 있으면 수량 추가
        for (OrderItem existingItem : cartItems) {
            if (existingItem.getProduct().getProductId() == product.getProductId()) {
                existingItem.setQuantity(existingItem.getQuantity() + requestedQuantity);
                System.out.println("✅ " + product.getProductName() + "의 수량이 추가되었습니다.");
                return;
            }
        }
        
        // 없으면 새로 추가
        cartItems.add(item);
        System.out.println("✅ " + product.getProductName() + "이(가) 장바구니에 담겼습니다.");
    }
    
    public void removeItem(int productId) {
        for (OrderItem item : cartItems) {
            if (item.getProduct().getProductId() == productId) {
                // 재고 복원 (장바구니에서 제거할 때)
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() + item.getQuantity());
                
                cartItems.remove(item);
                System.out.println("✅ " + product.getProductName() + "이(가) 제거되었습니다.");
                System.out.println("📈 재고 복원: " + product.getProductName() + 
                                 " (재고: " + product.getQuantity() + "개)");
                return;
            }
        }
        System.out.println("❌ 해당 상품을 찾을 수 없습니다.");
    }
    
    public void viewCart() {
        System.out.println("\n=== 장바구니 ===");
        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            return;
        }
        
        for (int i = 0; i < cartItems.size(); i++) {
            OrderItem item = cartItems.get(i);
            System.out.println((i + 1) + ". " + item.getProduct().getProductName() + 
                             ": " + item.getQuantity() + "개 = " + 
                             item.getItemPrice() + "원");
        }
        System.out.println("─────────────────────");
        System.out.println("총액: " + getTotalPrice() + "원");
    }
    
    public int getTotalPrice() {
        int total = 0;
        for (OrderItem item : cartItems) {
            total += item.getItemPrice();
        }
        return total;
    }
    
    public List<OrderItem> getItems() { 
        return cartItems; 
    }
    
    /**
     * 주문 완료 후 장바구니를 비웁니다 (재고 복원 안 함)
     */
    public void clearAfterOrder() {
        cartItems.clear();
        System.out.println("🗑️  장바구니가 비워졌습니다.");
    }
    
    /**
     * 사용자가 수동으로 장바구니를 비웁니다 (재고 복원)
     */
    public void clearByUser() {
        // 장바구니 비울 때 재고 복원
        for (OrderItem item : cartItems) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
        }
        
        cartItems.clear();
        System.out.println("🗑️  장바구니가 비워졌습니다.");
    }
    
    /**
     * 레거시 호환성을 위한 clear() 메서드
     * (재고 복원 안 함 - 주문 완료 후 사용)
     */
    public void clear() {
        clearAfterOrder();
    }
    
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
