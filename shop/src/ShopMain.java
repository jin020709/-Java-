import java.util.Scanner;
import java.util.List;

/**
 * 메인 클래스 - 프로그램 시작점
 */
public class ShopMain {
    private ShoppingMallSystem system;
    private Scanner scanner;
    
    public ShopMain() {
        this.system = new ShoppingMallSystem();
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("════════════════════════════════════════");
        System.out.println("   환영합니다! 쇼핑몰 시스템입니다.");
        System.out.println("════════════════════════════════════════");
        
        boolean running = true;
        while (running) {
            if (system.getCurrentUser() == null) {
                // 로그인 전 메뉴
                running = showMainMenu();
            } else if (system.getCurrentUser() instanceof Admin) {
                // 관리자 메뉴
                running = showAdminMenu();
            } else {
                // 고객 메뉴
                running = showCustomerMenu();
            }
        }
        
        System.out.println("\n프로그램을 종료합니다.");
        scanner.close();
    }
    
    // 로그인 전 메인 메뉴
    private boolean showMainMenu() {
        system.showMainMenu();
        System.out.print("\n선택: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                signup();
                break;
            case "2":
                login();
                break;
            case "0":
                return false;
            default:
                System.out.println("잘못된 선택입니다.");
        }
        return true;
    }
    
    // 회원가입
    private void signup() {
        System.out.println("\n╔════ 회원가입 ════╗");
        System.out.print("아이디: ");
        String userId = scanner.nextLine().trim();
        
        System.out.print("비밀번호: ");
        String password = scanner.nextLine().trim();
        
        System.out.print("이름: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("주소: ");
        String address = scanner.nextLine().trim();
        
        System.out.print("연락처: ");
        String phone = scanner.nextLine().trim();
        
        System.out.print("관리자 계정입니까? (Y/N): ");
        String isAdminStr = scanner.nextLine().trim().toUpperCase();
        boolean isAdmin = isAdminStr.equals("Y");
        
        system.signup(userId, password, name, address, phone, isAdmin);
    }
    
    // 로그인
    private void login() {
        System.out.println("\n╔════ 로그인 ════╗");
        System.out.print("아이디: ");
        String userId = scanner.nextLine().trim();
        
        System.out.print("비밀번호: ");
        String password = scanner.nextLine().trim();
        
        system.login(userId, password);
    }
    
    // 고객 메뉴
    private boolean showCustomerMenu() {
        Customer customer = (Customer) system.getCurrentUser();
        customer.showMenu();
        System.out.print("\n선택: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                showAllProducts(customer);
                break;
            case "2":
                searchProduct(customer);
                break;
            case "3":
                customer.viewOrderHistory();
                break;
            case "4":
                showCartMenu(customer);
                break;
            case "5":
                showMyInfoMenu(customer);
                break;
            case "0":
                system.logout();
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
        return true;
    }
    
    // 상품 전체 보기
    private void showAllProducts(Customer customer) {
        List<Product> products = system.getProducts();
        System.out.println("\n╔════ 상품 전체보기 ════╗");
        
        // 카테고리별 분류
        for (Category category : system.getCategories()) {
            System.out.println("\n[" + category.getCategoryName() + "]");
            for (Product product : products) {
                if (product.getCategory().getCategoryId() == category.getCategoryId()) {
                    System.out.println(product.getProductId() + ". " + 
                                     product.getProductName() + " - " + 
                                     product.getPrice() + "원 (재고: " + 
                                     product.getQuantity() + "개)");
                }
            }
        }
    }
    
    // 상품 조회
    private void searchProduct(Customer customer) {
        System.out.print("\n조회할 상품번호: ");
        try {
            int productId = Integer.parseInt(scanner.nextLine().trim());
            customer.searchProduct(productId, system.getProducts());
            
            System.out.print("\n1. 구매하기  2. 장바구니에 담기  3. 뒤로가기: ");
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    // 수량 입력
                    System.out.print("수량: ");
                    int quantity = Integer.parseInt(scanner.nextLine().trim());
                    
                    // 장바구니에 담고 바로 주문
                    for (Product product : system.getProducts()) {
                        if (product.getProductId() == productId) {
                            Order order = system.createOrder();
                            break;
                        }
                    }
                    break;
                    
                case "2":
                    // 장바구니에 담기
                    System.out.print("수량: ");
                    int qty = Integer.parseInt(scanner.nextLine().trim());
                    
                    for (Product product : system.getProducts()) {
                        if (product.getProductId() == productId) {
                            customer.getCart().addItem(new OrderItem(product, qty));
                            break;
                        }
                    }
                    break;
                    
                case "3":
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하세요.");
        }
    }
    
    // 장바구니 메뉴
    private void showCartMenu(Customer customer) {
        customer.viewCart();
        
        if (customer.getCart().isEmpty()) {
            return;
        }
        
        System.out.print("\n1. 일괄구매  2. 장바구니비우기  3. 뒤로가기: ");
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                // 주문 생성
                system.createOrder();
                break;
            case "2":
                customer.getCart().clear();
                break;
            case "3":
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }
    
    // 내정보 메뉴
    private void showMyInfoMenu(Customer customer) {
        customer.viewMyInfo();
        
        System.out.print("\n1. 내정보수정  2. 회원탈퇴  3. 뒤로가기: ");
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                System.out.print("새 주소: ");
                String newAddress = scanner.nextLine().trim();
                System.out.print("새 연락처: ");
                String newPhone = scanner.nextLine().trim();
                customer.updateInfo(newAddress, newPhone);
                break;
            case "2":
                System.out.print("정말로 탈퇴하시겠습니까? (Y/N): ");
                if (scanner.nextLine().trim().toUpperCase().equals("Y")) {
                    customer.deleteAccount();
                    system.logout();
                }
                break;
            case "3":
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }
    
    // 관리자 메뉴
    private boolean showAdminMenu() {
        Admin admin = (Admin) system.getCurrentUser();
        admin.showMenu();
        System.out.print("\n선택: ");
        
        String choice = scanner.nextLine().trim();
        
        switch (choice) {
            case "1":
                addProduct(admin);
                break;
            case "2":
                deleteProduct(admin);
                break;
            case "3":
                updateShippingStatus(admin);
                break;
            case "4":
                admin.viewAllOrders(system.getAllOrders());
                break;
            case "0":
                system.logout();
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
        return true;
    }
    
    // 상품 추가
    private void addProduct(Admin admin) {
        System.out.println("\n╔════ 상품추가 ════╗");
        System.out.println("카테고리 선택:");
        List<Category> categories = system.getCategories();
        
        for (Category cat : categories) {
            System.out.println(cat.getCategoryId() + ". " + cat.getCategoryName());
        }
        
        System.out.print("카테고리 번호: ");
        try {
            int catId = Integer.parseInt(scanner.nextLine().trim());
            Category selectedCategory = null;
            
            for (Category cat : categories) {
                if (cat.getCategoryId() == catId) {
                    selectedCategory = cat;
                    break;
                }
            }
            
            if (selectedCategory == null) {
                System.out.println("존재하지 않는 카테고리입니다.");
                return;
            }
            
            System.out.print("상품명: ");
            String productName = scanner.nextLine().trim();
            
            System.out.print("가격: ");
            int price = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("수량: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("설명: ");
            String description = scanner.nextLine().trim();
            
            int newProductId = system.getProducts().size() + 1;
            Product newProduct = new Product(newProductId, productName, price, quantity, selectedCategory, description);
            system.getProducts().add(newProduct);
            
            admin.addProduct(productName, price, selectedCategory, description);
            
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하세요.");
        }
    }
    
    // 상품 삭제
    private void deleteProduct(Admin admin) {
        System.out.println("\n╔════ 상품삭제 ════╗");
        System.out.print("삭제할 상품번호: ");
        try {
            int productId = Integer.parseInt(scanner.nextLine().trim());
            admin.deleteProduct(productId, system.getProducts());
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하세요.");
        }
    }
    
    // 배송 현황 변경
    private void updateShippingStatus(Admin admin) {
        System.out.println("\n╔════ 배송현황변경 ════╗");
        System.out.print("주문번호: ");
        try {
            int orderId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.println("배송상태 선택:");
            System.out.println("1. 준비중");
            System.out.println("2. 배송중");
            System.out.println("3. 배송완료");
            System.out.print("선택: ");
            
            String statusChoice = scanner.nextLine().trim();
            String status = "";
            
            switch (statusChoice) {
                case "1":
                    status = "준비중";
                    break;
                case "2":
                    status = "배송중";
                    break;
                case "3":
                    status = "배송완료";
                    break;
                default:
                    System.out.println("잘못된 선택입니다.");
                    return;
            }
            
            admin.updateShippingStatus(orderId, status, system.getAllOrders());
            
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하세요.");
        }
    }
    
    public static void main(String[] args) {
        ShopMain app = new ShopMain();
        app.run();
    }
}
