/**
 * 카테고리 클래스
 */
public class Category {
    private int categoryId;
    private String categoryName;
    
    public Category(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
    
    public int getCategoryId() { 
        return categoryId; 
    }
    
    public String getCategoryName() { 
        return categoryName; 
    }
    
    public void setCategoryName(String categoryName) { 
        this.categoryName = categoryName; 
    }
    
    @Override
    public String toString() {
        return categoryName;
    }
}
