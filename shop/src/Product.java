/**
 * 상품 클래스
 */
public class Product {
    private int productId;
    private String productName;
    private int price;
    private int quantity;          // 재고
    private Category category;
    private String description;
    
    public Product(int productId, String productName, int price, 
                   int quantity, Category category, String description) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
    }
    
    // Getter methods
    public int getProductId() { 
        return productId; 
    }
    
    public String getProductName() { 
        return productName; 
    }
    
    public int getPrice() { 
        return price; 
    }
    
    public int getQuantity() { 
        return quantity; 
    }
    
    public Category getCategory() { 
        return category; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    // Setter methods
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
    
    public void setPrice(int price) { 
        this.price = price; 
    }
    
    public void displayProductInfo() {
        System.out.println("상품번호: " + productId);
        System.out.println("상품명: " + productName);
        System.out.println("가격: " + price + "원");
        System.out.println("재고: " + quantity + "개");
        System.out.println("카테고리: " + category.getCategoryName());
        System.out.println("설명: " + description);
    }
    
    @Override
    public String toString() {
        return productId + ". " + productName + " (" + price + "원)";
    }
}
