import java.io.*;
import java.nio.file.*;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV 파일 형식으로 데이터를 저장/로드하는 클래스
 * 라이브러리 없이 순수 Java만 사용!
 */
public class FileManager {
    
    /**
     * 프로젝트 루트의 data 디렉토리를 자동으로 찾습니다
     */
    private static String getDataDirectory() {
        String workingDir = System.getProperty("user.dir");
        File currentDir = new File(workingDir);
        
        while (currentDir != null && !currentDir.getName().equals("shop")) {
            currentDir = currentDir.getParentFile();
        }
        
        if (currentDir == null) {
            currentDir = new File(workingDir);
        }
        
        String dataDir = new File(currentDir, "data").getAbsolutePath();
        return dataDir;
    }
    
    /**
     * CSV 파일에서 List<User>를 읽습니다
     */
    public static List<User> readUserListFromCsv(String filename) {
        try {
            String dataDir = getDataDirectory();
            String filepath = new File(dataDir, filename).getAbsolutePath();
            Path path = Paths.get(filepath);
            
            System.out.println("📂 읽기 경로: " + filepath);
            
            if (!Files.exists(path)) {
                System.out.println("📁 파일이 없습니다. 새로 생성됩니다: " + filepath);
                return null;
            }
            
            // CSV 파일 읽기
            List<String> lines = Files.readAllLines(path);
            List<User> users = new ArrayList<>();
            
            // 첫 번째 줄은 헤더이므로 건너뛰기
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                
                // 쉼표로 분리
                String[] parts = line.split(",");
                
                if (parts.length < 6) continue;
                
                String userId = parts[0].trim();
                String password = parts[1].trim();
                String name = parts[2].trim();
                String address = parts[3].trim();
                String phone = parts[4].trim();
                boolean isAdmin = Boolean.parseBoolean(parts[5].trim());
                
                User user;
                if (isAdmin) {
                    user = new Admin(userId, password, name, address, phone);
                } else {
                    user = new Customer(userId, password, name, address, phone);
                }
                
                users.add(user);
            }
            
            System.out.println("✅ 파일 로드 완료: " + filepath);
            System.out.println("📊 로드된 회원 수: " + users.size());
            
            return users;
            
        } catch (Exception e) {
            System.out.println("❌ 파일 읽기 실패: " + filename);
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * List<User>를 CSV 파일에 저장합니다
     */
    public static void writeUserListToCsv(String filename, List<User> users) {
        try {
            String dataDir = getDataDirectory();
            
            // data 폴더 생성
            File dataDirFile = new File(dataDir);
            if (!dataDirFile.exists()) {
                dataDirFile.mkdirs();
                System.out.println("📁 폴더 생성: " + dataDir);
            }
            
            String filepath = new File(dataDir, filename).getAbsolutePath();
            
            System.out.println("💾 저장 경로: " + filepath);
            System.out.println("📊 저장할 회원 수: " + users.size());
            
            // CSV 헤더
            StringBuilder csv = new StringBuilder();
            csv.append("userId,password,name,address,phone,isAdmin\n");
            
            // 각 회원 정보를 CSV 형식으로 추가
            for (User user : users) {
                String isAdmin = user instanceof Admin ? "true" : "false";
                
                csv.append(user.getUserId()).append(",");
                csv.append(user.getPassword()).append(",");
                csv.append(user.getName()).append(",");
                csv.append(user.getAddress()).append(",");
                csv.append(user.getPhone()).append(",");
                csv.append(isAdmin).append("\n");
            }
            
            // CSV 파일 저장
            Files.write(Paths.get(filepath), csv.toString().getBytes("UTF-8"),
                       StandardOpenOption.CREATE,
                       StandardOpenOption.WRITE,
                       StandardOpenOption.TRUNCATE_EXISTING);
            
            System.out.println("✅ 파일 저장 완료: " + filepath);
            
        } catch (Exception e) {
            System.out.println("❌ 파일 저장 실패: " + filename);
            e.printStackTrace();
        }
    }
    
    /**
     * CSV 파일에서 List<Product>를 읽습니다
     */
    public static List<Product> readProductListFromCsv(String filename, List<Category> categories) {
        try {
            String dataDir = getDataDirectory();
            String filepath = new File(dataDir, filename).getAbsolutePath();
            Path path = Paths.get(filepath);
            
            System.out.println("📂 상품 읽기 경로: " + filepath);
            
            if (!Files.exists(path)) {
                System.out.println("📁 상품 파일이 없습니다: " + filepath);
                return null;
            }
            
            // CSV 파일 읽기
            List<String> lines = Files.readAllLines(path);
            List<Product> products = new ArrayList<>();
            
            // 첫 번째 줄은 헤더이므로 건너뛰기
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                
                // 쉼표로 분리
                String[] parts = line.split(",");
                
                if (parts.length < 6) continue;
                
                int productId = Integer.parseInt(parts[0].trim());
                String productName = parts[1].trim();
                int price = Integer.parseInt(parts[2].trim());
                int quantity = Integer.parseInt(parts[3].trim());
                int categoryId = Integer.parseInt(parts[4].trim());
                String description = parts[5].trim();
                
                // 카테고리 찾기
                Category category = null;
                for (Category cat : categories) {
                    if (cat.getCategoryId() == categoryId) {
                        category = cat;
                        break;
                    }
                }
                
                if (category != null) {
                    Product product = new Product(productId, productName, price, quantity, category, description);
                    products.add(product);
                }
            }
            
            System.out.println("✅ 상품 파일 로드 완료: " + filepath);
            System.out.println("📊 로드된 상품 수: " + products.size());
            
            return products;
            
        } catch (Exception e) {
            System.out.println("❌ 상품 파일 읽기 실패: " + filename);
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * List<Product>를 CSV 파일에 저장합니다
     */
    public static void writeProductListToCsv(String filename, List<Product> products) {
        try {
            String dataDir = getDataDirectory();
            String filepath = new File(dataDir, filename).getAbsolutePath();
            
            System.out.println("💾 상품 저장 경로: " + filepath);
            System.out.println("📊 저장할 상품 수: " + products.size());
            
            // CSV 헤더
            StringBuilder csv = new StringBuilder();
            csv.append("productId,productName,price,quantity,categoryId,categoryName,description\n");
            
            // 각 상품 정보를 CSV 형식으로 추가
            for (Product product : products) {
                csv.append(product.getProductId()).append(",");
                csv.append(product.getProductName()).append(",");
                csv.append(product.getPrice()).append(",");
                csv.append(product.getQuantity()).append(",");
                csv.append(product.getCategory().getCategoryId()).append(",");
                csv.append(product.getCategory().getCategoryName()).append(",");
                csv.append(product.getDescription()).append("\n");
            }
            
            // CSV 파일 저장
            Files.write(Paths.get(filepath), csv.toString().getBytes("UTF-8"),
                       StandardOpenOption.CREATE,
                       StandardOpenOption.WRITE,
                       StandardOpenOption.TRUNCATE_EXISTING);
            
            System.out.println("✅ 상품 파일 저장 완료: " + filepath);
            
        } catch (Exception e) {
            System.out.println("❌ 상품 파일 저장 실패: " + filename);
            e.printStackTrace();
        }
    }
    
    /**
     * 현재 프로젝트 루트를 반환합니다
     */
    public static String getProjectRoot() {
        String workingDir = System.getProperty("user.dir");
        File currentDir = new File(workingDir);
        
        while (currentDir != null && !currentDir.getName().equals("shop")) {
            currentDir = currentDir.getParentFile();
        }
        
        if (currentDir == null) {
            currentDir = new File(workingDir);
        }
        
        return currentDir.getAbsolutePath();
    }
}
