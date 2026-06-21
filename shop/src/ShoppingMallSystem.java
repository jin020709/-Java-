import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * 쇼핑몰 관리 시스템
 * 쿠폰 시스템 & 카테고리 할인 기능 포함
 */
public class ShoppingMallSystem {
    private List<User> users;
    private List<Product> products;
    private List<Order> allOrders;
    private List<Category> categories;
    private List<CategoryDiscount> categoryDiscounts;
    private User currentUser;
    private int nextOrderId;
    
    // CSV 파일명
    private static final String USERS_FILE = "users.csv";
    private static final String PRODUCTS_FILE = "fruit.csv";

    public ShoppingMallSystem() {
        this.users = new ArrayList<>();
        this.products = new ArrayList<>();
        this.allOrders = new ArrayList<>();
        this.categories = new ArrayList<>();
        this.categoryDiscounts = new ArrayList<>();
        this.currentUser = null;
        this.nextOrderId = 1001;

        initializeData();
        initializeCategoryDiscounts();
        loadProductsFromFile();
        loadUsersFromFile();
        
        System.out.println("\n📂 프로젝트 루트: " + FileManager.getProjectRoot());
    }

    // 초기 데이터 설정 (카테고리만)
    private void initializeData() {
        Category domestic = new Category(1, "국산과일");
        Category imported = new Category(2, "수입과일");
        Category convenient = new Category(3, "간편과일");
        Category seasonal = new Category(4, "제철과일(여름 기준)");
        categories.add(domestic);
        categories.add(imported);
        categories.add(convenient);
        categories.add(seasonal);
    }

    // 카테고리 할인 초기화
    private void initializeCategoryDiscounts() {
        categoryDiscounts.add(new CategoryDiscount(
            categories.get(0), 
            15, 
            "국산과일 특별할인"
        ));
        
        categoryDiscounts.add(new CategoryDiscount(
            categories.get(3), 
            10, 
            "제철과일 할인"
        ));
    }

    // CSV 파일에서 상품 로드
    private void loadProductsFromFile() {
        System.out.println("\n📂 상품 정보를 로드 중입니다...");
        
        List<Product> loadedProducts = FileManager.readProductListFromCsv(PRODUCTS_FILE, categories);
        
        if (loadedProducts != null) {
            this.products = loadedProducts;
        } else {
            System.out.println("📝 저장된 상품이 없습니다.");
            this.products = new ArrayList<>();
        }
    }

    // CSV 파일에서 사용자 로드
    private void loadUsersFromFile() {
        System.out.println("\n📂 저장된 회원 정보를 로드 중입니다...");
        
        List<User> loadedUsers = FileManager.readUserListFromCsv(USERS_FILE);
        
        if (loadedUsers != null) {
            this.users = loadedUsers;
        } else {
            System.out.println("📝 저장된 회원이 없습니다. 새로 시작합니다.");
            this.users = new ArrayList<>();
        }
    }

    // CSV 파일에 사용자 저장
    public void saveUsersToFile() {
        System.out.println("\n💾 회원 정보를 저장 중입니다...");
        FileManager.writeUserListToCsv(USERS_FILE, this.users);
    }

    // 회원가입
    public void signup(String userId, String password, String name,
                       String address, String phone, boolean isAdmin) {
        // 아이디 중복 확인
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                System.out.println("❌ 이미 존재하는 아이디입니다.");
                return;
            }
        }

        User newUser;
        if (isAdmin) {
            newUser = new Admin(userId, password, name, address, phone);
            System.out.println("✅ 관리자 계정이 생성되었습니다.");
        } else {
            newUser = new Customer(userId, password, name, address, phone);
            System.out.println("✅ 고객 계정이 생성되었습니다.");
            
            // 고객일 경우 첫 가입 쿠폰 지급
            Customer customer = (Customer) newUser;
            Coupon welcomeCoupon = new Coupon(
                "WELCOME10", 
                10, 
                LocalDate.now().plusMonths(3)
            );
            customer.addCoupon(welcomeCoupon);
        }

        users.add(newUser);
        System.out.println(name + "님의 회원가입이 완료되었습니다.");
    }

    // 로그인
    public User login(String userId, String password) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPassword().equals(password)) {
                this.currentUser = user;
                System.out.println("✅ " + user.getName() + "님이 로그인되었습니다.");
                return user;
            }
        }
        System.out.println("❌ 아이디 또는 비밀번호가 일치하지 않습니다.");
        return null;
    }

    // 로그아웃
    public void logout() {
        if (currentUser != null) {
            System.out.println(currentUser.getName() + "님이 로그아웃되었습니다.");
            currentUser = null;
        }
    }

    /**
     * 사용자 계정을 삭제합니다 (회원탈퇴)
     * @param user 삭제할 사용자
     */
    public void deleteUser(User user) {
        if (users.remove(user)) {
            System.out.println("✅ 회원탈퇴가 완료되었습니다.");
            System.out.println("📝 사용자의 모든 정보가 삭제되었습니다.");
            currentUser = null;  // 자동으로 로그아웃
        } else {
            System.out.println("❌ 회원탈퇴 처리 중 오류가 발생했습니다.");
        }
    }

    /**
     * 장바구니에서 일괄구매 (장바구니를 비움)
     * 주문 객체만 생성하고 displayOrderInfo는 호출하지 않음
     */
    public Order createOrder() {
        if (!(currentUser instanceof Customer)) {
            System.out.println("❌ 고객만 주문할 수 있습니다.");
            return null;
        }

        Customer customer = (Customer) currentUser;
        Cart cart = customer.getCart();

        if (cart.isEmpty()) {
            System.out.println("❌ 장바구니가 비어있습니다.");
            return null;
        }

        Order order = new Order(nextOrderId++, customer, java.time.LocalDateTime.now().toString());

        // 장바구니의 모든 상품을 주문에 추가
        for (OrderItem item : cart.getItems()) {
            order.addItem(item);
        }

        allOrders.add(order);
        customer.addOrder(order);

        // ← displayOrderInfo() 제거! ShopMain에서 할인 후 호출
        System.out.println("📦 주문이 생성되었습니다. 할인을 선택해주세요.");

        // ← cart.clear() 제거! selectDiscount()에서만 호출

        return order;
    }

    /**
     * 상품 상세보기에서 직접 구매 (장바구니 유지)
     * 주문 객체만 생성하고 displayOrderInfo는 호출하지 않음
     */
    public Order createDirectOrder(Product product, int quantity) {
        if (!(currentUser instanceof Customer)) {
            System.out.println("❌ 고객만 주문할 수 있습니다.");
            return null;
        }

        // 재고 확인
        if (product.getQuantity() < quantity) {
            System.out.println("❌ 재고가 부족합니다!");
            System.out.println("   현재 재고: " + product.getQuantity() + "개");
            System.out.println("   요청 수량: " + quantity + "개");
            return null;
        }

        // 재고 감소
        product.setQuantity(product.getQuantity() - quantity);
        System.out.println("📉 재고 감소: " + product.getProductName() + 
                         " (" + quantity + "개 -> 남은 재고: " + 
                         product.getQuantity() + "개)");

        Customer customer = (Customer) currentUser;
        Order order = new Order(nextOrderId++, customer, java.time.LocalDateTime.now().toString());

        // 주문에 상품 추가
        OrderItem item = new OrderItem(product, quantity);
        order.addItem(item);

        allOrders.add(order);
        customer.addOrder(order);

        // ← displayOrderInfo() 제거! ShopMain에서 할인 후 호출
        System.out.println("📦 주문이 생성되었습니다. 할인을 선택해주세요.");

        return order;
    }

    // 메인 메뉴 표시
    public void showMainMenu() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║     쇼핑몰 시스템 메인메뉴         ║");
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

    public List<CategoryDiscount> getCategoryDiscounts() {
        return categoryDiscounts;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public int getNextOrderId() {
        return nextOrderId;
    }
}
