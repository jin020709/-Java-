/**
 * 주문 항목 (상품 + 수량)
 */
public class OrderItem {
    private Product product;
    private int quantity;
    private int itemPrice;  // 구매 당시 가격
    
    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.itemPrice = product.getPrice() * quantity;
    }
    
    // Getter methods
    public Product getProduct() { 
        return product; 
    }
    
    public int getQuantity() { 
        return quantity; 
    }
    
    public int getItemPrice() { 
        return itemPrice; 
    }
    
    // Setter methods
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.itemPrice = product.getPrice() * quantity;
    }
    
    @Override
    public String toString() {
        return product.getProductName() + " x " + quantity + " = " + itemPrice + "원";
    }
}
