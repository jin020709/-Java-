import java.util.List;
import java.util.ArrayList;

/**
 * 쇼핑몰 관리 시스템
 */
public class ShoppingMallSystem {
    private List<User> users;              // 회원 정보 (고객 + 관리자)
    private List<Product> products;        // 상품 목록
    private List<Order> allOrders;         // 전체 주문 내역
    private List<Category> categories;     // 카테고리
    private User currentUser;              // 현재 로그인한 사용자
    private int nextOrderId;               // 다음 주문번호
    
    public ShoppingMallSystem() {
        this.users = new ArrayList<>();
        this.products = new ArrayList<>();
        this.allOrders = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.currentUser = null;
        this.nextOrderId = 1001;
        
        initializeData();  // 초기 데이터 설정
    }
    
    // 초기 데이터 설정 (테스트용)
    private void initializeData() {
        // 카테고리 추가
        Category fresh = new Category(1, "신선식품");
        Category preserved = new Category(2, "보존식품");
        categories.add(fresh);
        categories.add(preserved);
        
        // 샘플 상품 추가
        products.add(new Product(1, "사과", 5000, 100, fresh, "맛있는 빨간 사과"));
        products.add(new Product(2, "딸기", 8000, 50, fresh, "신선한 딸기"));
        products.add(new Product(3, "김", 3000, 200, preserved, "구운 김"));
        products.add(new Product(4, "라면", 2000, 150, preserved, "맛있는 라면"));
    }
    
    // 회원가입
    public void signup(String userId, String password, String name, 
                      String address, String phone, boolean isAdmin) {
        // 아이디 중복 확인
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                System.out.println("이미 존재하는 아이디입니다.");
                return;
            }
        }
        
        User newUser;
        if (isAdmin) {
            newUser = new Admin(userId, password, name, address, phone);
            System.out.println("관리자 계정이 생성되었습니다.");
        } else {
            newUser = new Customer(userId, password, name, address, phone);
            System.out.println("고객 계정이 생성되었습니다.");
        }
        
        users.add(newUser);
        System.out.println(name + "님의 회원가입이 완료되었습니다.");
    }
    
    // 로그인
    public User login(String userId, String password) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                this.currentUser = user;
                System.out.println(user.getName() + "님이 로그인되었습니다.");
                return user;
            }
        }
        System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
        return null;
    }
    
    // 로그아웃
    public void logout() {
        if (currentUser != null) {
            System.out.println(currentUser.getName() + "님이 로그아웃되었습니다.");
            currentUser = null;
        }
    }
    
    // 주문 생성
    public Order createOrder() {
        if (!(currentUser instanceof Customer)) {
            System.out.println("고객만 주문할 수 있습니다.");
            return null;
        }
        
        Customer customer = (Customer) currentUser;
        Cart cart = customer.getCart();
        
        if (cart.isEmpty()) {
            System.out.println("장바구니가 비어있습니다.");
            return null;
        }
        
        Order order = new Order(nextOrderId++, customer, java.time.LocalDateTime.now().toString());
        
        // 장바구니의 모든 상품을 주문에 추가
        for (OrderItem item : cart.getItems()) {
            order.addItem(item);
        }
        
        allOrders.add(order);
        customer.addOrder(order);
        
        System.out.println("\n✓ 주문이 완료되었습니다.");
        order.displayOrderInfo();
        
        cart.clear();  // 장바구니 비우기
        
        return order;
    }
    
    // 메인 메뉴 표시
    public void showMainMenu() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║     쇼핑몰 시스템 메인메뉴     ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("0. 종료");
    }
    
    // Getter methods
    public List<User> getUsers() { 
        return users; 
    }
    
    public List<Product> getProducts() { 
        return products; 
    }
    
    public List<Order> getAllOrders() { 
        return allOrders; 
    }
    
    public List<Category> getCategories() { 
        return categories; 
    }
    
    public User getCurrentUser() { 
        return currentUser; 
    }
    
    public int getNextOrderId() {
        return nextOrderId;
    }
}
