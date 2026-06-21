import java.util.Scanner;
import java.util.List;

/**
 * 메인 클래스 - 프로그램 시작점
 * 할인 적용 후 주문 완료 표시
 * 장바구니 비우기는 마지막에 표시
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
                running = showMainMenu();
            } else if (system.getCurrentUser() instanceof Admin) {
                running = showAdminMenu();
            } else {
                running = showCustomerMenu();
            }
        }

        System.out.println("\n프로그램을 종료합니다.");
        system.saveUsersToFile();
        
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

        if (isAdmin) {
            System.out.print("관리자 코드를 입력하세요: ");
            String adminCode = scanner.nextLine().trim();

            if (!adminCode.equals("0000")) {
                System.out.println("오류: 관리자 코드가 올바르지 않습니다.");
                return;
            }
        }

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

    // 상품 카테고리별 보기
    private void showAllProducts(Customer customer) {
        List<Category> categories = system.getCategories();

        while (true) {
            System.out.println("\n╔════ 상품 카테고리별 보기 ════╗");
            for (int i = 0; i < categories.size(); i++) {
                System.out.println((i + 1) + ". " + categories.get(i).getCategoryName());
            }
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            String catChoice = scanner.nextLine().trim();
            if (catChoice.equals("0")) return;

            int catIdx;
            try {
                catIdx = Integer.parseInt(catChoice) - 1;
            } catch (NumberFormatException e) {
                System.out.println("잘못된 선택입니다.");
                continue;
            }
            if (catIdx < 0 || catIdx >= categories.size()) {
                System.out.println("잘못된 선택입니다.");
                continue;
            }

            Category selected = categories.get(catIdx);
            List<Product> allProducts = system.getProducts();

            List<Product> categoryProducts = new java.util.ArrayList<>();
            for (Product p : allProducts) {
                if (p.getCategory().getCategoryId() == selected.getCategoryId()) {
                    categoryProducts.add(p);
                }
            }

            while (true) {
                System.out.println("\n╔════ " + selected.getCategoryName() + " ════╗");
                if (categoryProducts.isEmpty()) {
                    System.out.println("등록된 상품이 없습니다.");
                } else {
                    for (int i = 0; i < categoryProducts.size(); i++) {
                        Product p = categoryProducts.get(i);
                        System.out.printf("%d. %s - %,d원  (재고: %d개) (상품번호: %d)%n",
                                (i + 1), p.getProductName(), p.getPrice(), p.getQuantity(), p.getProductId());
                    }
                }
                System.out.println("0. 뒤로가기");
                System.out.print("선택: ");

                String prodChoice = scanner.nextLine().trim();
                if (prodChoice.equals("0")) break;

                int prodIdx;
                try {
                    prodIdx = Integer.parseInt(prodChoice) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("잘못된 선택입니다.");
                    continue;
                }
                if (prodIdx < 0 || prodIdx >= categoryProducts.size()) {
                    System.out.println("잘못된 선택입니다.");
                    continue;
                }

                Product chosenProduct = categoryProducts.get(prodIdx);
                System.out.print("몇 개 담을까요? 수량: ");
                try {
                    int qty = Integer.parseInt(scanner.nextLine().trim());
                    if (qty <= 0) {
                        System.out.println("1개 이상 입력하세요.");
                        continue;
                    }
                    customer.getCart().addItem(new OrderItem(chosenProduct, qty));
                } catch (NumberFormatException e) {
                    System.out.println("숫자를 입력하세요.");
                }
            }
        }
    }

    // 상품 상세조회 및 구매
    private void searchProduct(Customer customer) {
        System.out.print("\n조회할 상품번호: ");
        try {
            int productId = Integer.parseInt(scanner.nextLine().trim());
            customer.searchProduct(productId, system.getProducts());

            System.out.print("\n1. 구매하기  2. 장바구니에 담기  3. 뒤로가기: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("수량: ");
                    int quantity = Integer.parseInt(scanner.nextLine().trim());

                    for (Product product : system.getProducts()) {
                        if (product.getProductId() == productId) {
                            Order order = system.createDirectOrder(product, quantity);
                            if (order != null) {
                                selectDiscount(order, false);  // false = 장바구니 아님
                            }
                            break;
                        }
                    }
                    break;

                case "2":
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
                Order order = system.createOrder();
                if (order != null) {
                    selectDiscount(order, true);  // true = 장바구니에서의 구매
                }
                break;
            case "2":
                // ✅ clearByUser() 사용 (재고 복원)
                customer.getCart().clearByUser();
                break;
            case "3":
                break;
            default:
                System.out.println("잘못된 선택입니다.");
        }
    }

    /**
     * 할인을 선택하고 적용한 후 주문 완료 표시
     * @param order 주문 객체
     * @param isCartOrder 장바구니에서의 구매 여부 (true면 마지막에 장바구니 비우기)
     */
    private void selectDiscount(Order order, boolean isCartOrder) {
        Customer customer = (Customer) system.getCurrentUser();
        boolean discountApplied = false;
        
        // 할인 선택 반복 (실패 시 다시 선택 가능)
        while (!discountApplied) {
            System.out.println("\n╔════ 할인 적용 ════╗");
            System.out.println("쿠폰을 사용하시겠습니까?");
            System.out.println("1. 사용");
            System.out.println("2. 즉시구매 (할인 없음)");
            System.out.print("선택: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    // 할인 선택 진행
                    boolean discountSelected = showDiscountOptions(customer, order);
                    if (discountSelected) {
                        discountApplied = true;  // 성공적으로 적용됨
                    }
                    // discountSelected = false면 다시 선택할 수 있음 (반복)
                    break;
                    
                case "2":
                    System.out.println("✅ 할인 없이 구매하셨습니다.");
                    discountApplied = true;
                    break;
                    
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
        
        // ✅ 할인 선택 완료 후 최종 주문 정보 표시!
        System.out.println();
        order.displayOrderInfo();
        
        // ✅ 마지막에 장바구니 비우기 (장바구니 구매인 경우만)
        if (isCartOrder) {
            System.out.println();
            customer.getCart().clear();
        }
    }

    /**
     * 할인 옵션 표시 및 선택
     * @return 할인이 성공적으로 적용되었으면 true, 실패하면 false
     */
    private boolean showDiscountOptions(Customer customer, Order order) {
        System.out.println("\n╔════ 사용 가능한 할인 ════╗");
        
        List<Coupon> availableCoupons = customer.getAvailableCoupons();
        List<CategoryDiscount> categoryDiscounts = system.getCategoryDiscounts();
        
        java.util.List<Object> discountOptions = new java.util.ArrayList<>();
        
        int optionNum = 1;
        
        // 쿠폰 표시
        System.out.println("\n📌 보유 쿠폰:");
        if (availableCoupons.isEmpty()) {
            System.out.println("사용 가능한 쿠폰이 없습니다.");
        } else {
            for (Coupon coupon : availableCoupons) {
                System.out.println(optionNum + ". " + coupon.getCouponCode() + 
                                 " (" + coupon.getDiscountRate() + "% 할인)");
                discountOptions.add(coupon);
                optionNum++;
            }
        }
        
        // 카테고리 할인 표시
        System.out.println("\n🏷️  카테고리 할인:");
        for (CategoryDiscount catDiscount : categoryDiscounts) {
            System.out.println(optionNum + ". " + catDiscount.getDiscountName() + 
                             " (" + catDiscount.getDiscountRate() + "% 할인)");
            discountOptions.add(catDiscount);
            optionNum++;
        }
        
        System.out.println("0. 할인 적용 안 함");
        System.out.print("선택: ");
        
        try {
            int selectedOption = Integer.parseInt(scanner.nextLine().trim());
            
            if (selectedOption == 0) {
                System.out.println("✅ 할인 없이 구매하셨습니다.");
                return true;  // 성공 (할인 없이 진행)
                
            } else if (selectedOption > 0 && selectedOption <= discountOptions.size()) {
                Object selected = discountOptions.get(selectedOption - 1);
                
                if (selected instanceof Coupon) {
                    Coupon coupon = (Coupon) selected;
                    CouponDiscount couponDiscount = new CouponDiscount(coupon);
                    order.setDiscount(couponDiscount);
                    coupon.use();
                    System.out.println("✅ " + coupon.getCouponCode() + "이(가) 적용되었습니다!");
                    return true;  // 성공
                    
                } else if (selected instanceof CategoryDiscount) {
                    CategoryDiscount catDiscount = (CategoryDiscount) selected;
                    
                    // 주문의 모든 상품이 해당 카테고리인지 확인
                    boolean applicable = true;
                    for (OrderItem item : order.getItems()) {
                        if (!catDiscount.isApplicable(item.getProduct())) {
                            applicable = false;
                            break;
                        }
                    }
                    
                    if (applicable) {
                        order.setDiscount(catDiscount);
                        System.out.println("✅ " + catDiscount.getDiscountName() + "이(가) 적용되었습니다!");
                        return true;  // 성공
                        
                    } else {
                        // ❌ 실패 - 바로 할인 선택 화면으로 돌아가기
                        System.out.println("\n❌ 이 할인은 " + catDiscount.getCategory().getCategoryName() + 
                                         "에만 적용됩니다.");
                        System.out.println("현재 주문에는 다른 카테고리 상품이 포함되어 있습니다.");
                        
                        // ✅ Y/N 묻지 말고 바로 return false (다시 선택하게)
                        return false;  // 할인 선택 화면으로 자동 돌아가기
                    }
                }
            } else {
                System.out.println("잘못된 선택입니다.");
                return false;  // 실패 - 다시 선택
            }
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하세요.");
            return false;  // 실패 - 다시 선택
        }
        
        return false;  // 기본값 (실패)
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
                    // ✅ system.deleteUser() 호출 (CSV에서도 삭제됨)
                    system.deleteUser(customer);
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
                updateProductInventory(admin);
                break;
            case "4":
                admin.viewAllOrders(system.getAllOrders());
                break;
            case "5":
                updateShippingStatus(admin);
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
        System.out.println("\n╔════ 상품 추가 ════╗");
        System.out.println("카테고리 선택:");
        List<Category> categories = system.getCategories();
        
        for (Category cat : categories) {
            System.out.println(cat.getCategoryId() + ". " + cat.getCategoryName());
        }
        
        System.out.print("카테고리 선택 (번호 또는 이름): ");
        String categoryInput = scanner.nextLine().trim();
        Category selectedCategory = null;
        
        if (categoryInput.matches("^[0-9]+$")) {
            int catId = Integer.parseInt(categoryInput);
            
            for (Category cat : categories) {
                if (cat.getCategoryId() == catId) {
                    selectedCategory = cat;
                    break;
                }
            }
        } else {
            for (Category cat : categories) {
                if (cat.getCategoryName().equals(categoryInput)) {
                    selectedCategory = cat;
                    break;
                }
            }
        }
        
        if (selectedCategory == null) {
            System.out.println("⚠️ 올바른 카테고리를 입력하세요.");
            return;
        }
        
        System.out.println("✅ 카테고리 '" + selectedCategory.getCategoryName() + "'이(가) 선택되었습니다.");
        
        System.out.print("상품명: ");
        String productName = scanner.nextLine().trim();
        
        System.out.print("가격: ");
        int price = 0;
        try {
            price = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ 오류: 가격은 숫자만 입력해야 합니다.");
            return;
        }
        
        System.out.print("수량: ");
        int quantity = 0;
        try {
            quantity = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ 오류: 수량은 숫자만 입력해야 합니다.");
            return;
        }
        
        System.out.print("설명: ");
        String description = scanner.nextLine().trim();
        
        int newProductId = system.getProducts().size() + 1;
        Product newProduct = new Product(newProductId, productName, price, quantity, selectedCategory, description);
        system.getProducts().add(newProduct);
        
        admin.addProduct(newProductId, productName, price, selectedCategory, description, system.getProducts());
        System.out.println("✅ 상품이 성공적으로 추가되었습니다.");
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

    // 상품 재고관리
    private void updateProductInventory(Admin admin) {
        System.out.println("\n╔════ 상품 재고관리 ════╗");
        System.out.print("변경할 상품번호: ");
        try {
            int productId = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("변경할 수량: ");
            int newQuantity = Integer.parseInt(scanner.nextLine().trim());
            
            if (newQuantity < 0) {
                System.out.println("❌ 재고는 0개 이상이어야 합니다.");
                return;
            }
            
            admin.updateProductInventory(productId, newQuantity, system.getProducts());
            
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하세요.");
        }
    }

    // 배송 현황 변경
    private void updateShippingStatus(Admin admin) {
        System.out.println("\n╔════ 고객 배송현황 변경 ════╗");
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
