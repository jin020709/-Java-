import java.util.List;
import java.util.ArrayList;

/**
 * 장바구니 클래스
 */
public class Cart {
    private List<OrderItem> cartItems;
    
    public Cart() {
        this.cartItems = new ArrayList<>();
    }
    
    public void addItem(OrderItem item) {
        // 같은 상품이 이미 있으면 수량 추가
        for (OrderItem existingItem : cartItems) {
            if (existingItem.getProduct().getProductId() == item.getProduct().getProductId()) {
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
                System.out.println(item.getProduct().getProductName() + "의 수량이 추가되었습니다.");
                return;
            }
        }
        
        // 없으면 새로 추가
        cartItems.add(item);
        System.out.println(item.getProduct().getProductName() + "이(가) 장바구니에 담겼습니다.");
    }
    
    public void removeItem(int productId) {
        for (OrderItem item : cartItems) {
            if (item.getProduct().getProductId() == productId) {
                cartItems.remove(item);
                System.out.println(item.getProduct().getProductName() + "이(가) 제거되었습니다.");
                return;
            }
        }
        System.out.println("해당 상품을 찾을 수 없습니다.");
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
    
    public void clear() {
        cartItems.clear();
        System.out.println("장바구니가 비워졌습니다.");
    }
    
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
