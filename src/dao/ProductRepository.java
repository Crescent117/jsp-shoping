package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.ProductDTO;
import mvc.controller.JDBConnect;

public class ProductRepository extends JDBConnect {
	
	private ArrayList<ProductDTO> listOfProducts = new ArrayList<ProductDTO>();
	private static ProductRepository instance = new ProductRepository();

	public static ProductRepository getInstance(){
		return instance;
	} 

	public ProductRepository(String id,String  color,String size) {
		
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "select * from  product p "
				+ " left join psize on p_id =s_id "
				+ " left join pcolor  on s_id = c_id "
				+ " where c_color = ? and s_size = ? and p_id = ?";
		try {
			
			psmt = con.prepareStatement(sql);
			psmt.setString(1, color);
			psmt.setString(2, size);
			psmt.setString(3, id);
			rs = psmt.executeQuery();
			if(rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setP_id(rs.getString("p_id"));
				dto.setP_name(rs.getString("p_name"));
				dto.setP_category(rs.getString("p_category"));
				dto.setP_manufacturer(rs.getString("p_manufacturer"));
				dto.setP_fileName(rs.getString("p_filename"));
				dto.setP_unitPrice(rs.getString("p_unitPrice"));
				dto.setP_gender(rs.getString("p_gender"));
				dto.setP_size(rs.getString("s_size"));
				dto.setP_color(rs.getString("c_color"));
				listOfProducts.add(dto);
				System.out.println("1111");

				
			}
			
		} catch (Exception e) {
			System.out.println("데이터베이스 연결이 실패되었습니다.<br>");
			System.out.println("Exception: " + e.getMessage());
		}
//		
		
		
//		Product phone = new Product("P1234", "iPhone 6s", 800000);
//		phone.setDescription("4.7-inch, 1334X750 Renina HD display, 8-megapixel iSight Camera");
//		phone.setCategory("Smart Phone");
//		phone.setManufacturer("Apple");
//		phone.setUnitsInStock(1000);
//		phone.setCondition("New");
//		phone.setFilename("P1234.png");

//		ProductDTO notebook = new ProductDTO("P1235", "LG PC �׷�");
//		notebook.setP_description("13.3-inch, IPS LED display, 5rd Generation Intel Core processors");
//		notebook.setP_category("Notebook");
//		notebook.setP_manufacturer("LG");
//		notebook.setP_fileName("P1235.png");

//		Product tablet = new Product("P1236", "Galaxy Tab S", 900000);
//		tablet.setDescription("212.8*125.6*6.6mm,  Super AMOLED display, Octa-Core processor");
//		tablet.setCategory("Tablet");
//		tablet.setManufacturer("Samsung");
//		tablet.setUnitsInStock(1000);
//		tablet.setCondition("Old");
//		tablet.setFilename("P1236.png");

//		listOfProducts.add(phone);
//		listOfProducts.add(notebook);
//		listOfProducts.add(tablet);
	}

	public ProductRepository() {
		// TODO Auto-generated constructor stub
	}

	public ArrayList<ProductDTO> getAllProducts() {
		return listOfProducts;
	}
	
	public ProductDTO getProductById(String productId) {
		ProductDTO productById = null;
		for (int i = 0; i < listOfProducts.size(); i++) {
			ProductDTO product = listOfProducts.get(i);
			if (product != null && product.getP_id() != null && product.getP_id().equals(productId)) {
				productById = product;
				break;
			}
		}
		return productById;
	}
	
	public void addProduct(ProductDTO product) {
		listOfProducts.add(product);
	}
}
