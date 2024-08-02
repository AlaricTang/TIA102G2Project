//package com.ken.drink.model;
//
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import hibernate.util.compositeQuery.CompositeQuery_Drink;
//
//@SpringBootApplication
//public class Test_Application_CommandLineRunner_Drink implements CommandLineRunner {
//    // 這個程式就是從Application改過來的 下面第37的run方法就是CommandLineRunner提供的
//	@Autowired   //注入DrinkRepository介面
//	DrinkRepository repository ;
//	// 取得連線
//	@Autowired
//    private SessionFactory sessionFactory;
//	
//	public static void main(String[] args) {
//        SpringApplication.run(Test_Application_CommandLineRunner_Drink.class);
//    }
//
//    @Override
//    public void run(String...args) throws Exception {
//
//    	//● 新增
////		DrinkVO DrinkVO = new DrinkVO(); // 部門POJO
////		DrinkVO.setDrinkID(1);
//
////		DrinkVO DrinkVO1 = new DrinkVO();
////		DrinkVO1.setDrinkName("綠茶拿鐵");
////		DrinkVO1.setDrinkPrice(55);
////		DrinkVO1.setDrinkDes("綠茶拿鐵介紹");
////		DrinkVO1.setDrinkPic(null);
////		DrinkVO1.setDrinkTag("綠茶");
////		DrinkVO1.setDrinkStatus((byte) 1);
////		DrinkVO1.setDrinkCreateDate(new java.sql.Date(System.currentTimeMillis()));
////		DrinkVO1.setCreatedByMemberID(1);
////		DrinkVO1.setSal(new Double(50000));
////		DrinkVO1.setComm(new Double(500));
////		DrinkVO1.setDrinkVO(DrinkVO);
////		repository.save(DrinkVO1);
//
//		//● 修改
////		DrinkVO DrinkVO2 = new DrinkVO();
////		DrinkVO2.setDrinkID(5);
////		DrinkVO2.setDrinkName("紅茶拿鐵");
////		DrinkVO2.setDrinkPrice(45);
////		DrinkVO2.setDrinkPic(null);
////		DrinkVO2.setDrinkTag("紅茶");
////		DrinkVO2.setDrinkStatus((byte) 1);
////		DrinkVO2.setDrinkUpdateDate(new java.sql.Date(System.currentTimeMillis()));
////		DrinkVO2.setEditedByMemberID(2);
//////		DrinkVO2.setComm(new Double(200));
//////		DrinkVO2.setDrinkVO(DrinkVO);
////		repository.save(DrinkVO2);
//		
//		//● 刪除   //●●● --> DrinkloyeeRepository.java 內自訂的刪除方法 但因為我們是複合組件 所以刪不掉
////		repository.deleteByDrinkno(7014);
//		
//		//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
//		//System.out.println("--------------------------------");
//		//repository.deleteById(7001);      
//		//System.out.println("--------------------------------");
//
//    	//● 查詢-findByPrimaryKey (多方Drink2.hbm.xml必須設為lazy="false")(優!)
////    	Optional<DrinkVO> optional = repository.findById(6); //把後面的7001改成ename就可以直接放到service用 這邊是用在getOneService
////		DrinkVO DrinkVO3 = optional.get();
////		System.out.print(DrinkVO3.getDrinkID() + ",");
////		System.out.print(DrinkVO3.getDrinkName() + ",");
////		System.out.print(DrinkVO3.getDrinkPrice() + ",");
////		System.out.print(DrinkVO3.getDrinkDes() + ",");
////		System.out.print(DrinkVO3.getDrinkTag() + ",");
////		System.out.print(DrinkVO3.getDrinkStatus() + ",");
////		System.out.print(DrinkVO3.getDrinkUpdateDate() + ",");
////		System.out.print(DrinkVO3.getDrinkCreateDate() + ",");
////		System.out.print(DrinkVO3.getEditedByMemberID() + ",");
////		System.out.print(DrinkVO3.getCreatedByMemberID());
//		// 注意以下三行的寫法 (優!)
////		System.out.print(DrinkVO3.getDrinkVO().getDeptno() + ",");
////		System.out.print(DrinkVO3.getDrinkVO().getDname() + ",");
////		System.out.print(DrinkVO3.getDrinkVO().getLoc());
////		System.out.println("\n---------------------");
//      
//    	
//		//● 查詢-getAll (多方Drink2.hbm.xml必須設為lazy="false")(優!)
////    	List<DrinkVO> list = repository.findAll(); 
//    	// 這個findall是 DrinkRespository 繼承 JPA 裡面提供的
////		for (DrinkVO aDrink : list) {
////			System.out.print(aDrink.getDrinkID() + ",");
////			System.out.print(aDrink.getDrinkName() + ",");
////			System.out.print(aDrink.getDrinkPrice() + ",");
////			System.out.print(aDrink.getDrinkDes() + ",");
////			System.out.print(aDrink.getDrinkTag() + ",");
////			System.out.print(aDrink.getDrinkStatus() + ",");
////			System.out.print(aDrink.getDrinkUpdateDate() + ",");
////			System.out.print(aDrink.getDrinkCreateDate() + ",");
////			System.out.print(aDrink.getEditedByMemberID() + ",");
////			System.out.print(aDrink.getCreatedByMemberID() + ",");
//			// 注意以下三行的寫法 (優!)
////			System.out.print(aDrink.getDrinkVO().getDeptno() + ",");
////			System.out.print(aDrink.getDrinkVO().getDname() + ",");
////			System.out.print(aDrink.getDrinkVO().getLoc());
////			System.out.println();
////		}
//
//
//		//● 複合查詢-getAll(map) 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
//		Map<String, String[]> map = new TreeMap<String, String[]>();
////		map.put("drinkID", new String[] { "1" });
//		map.put("drinkName", new String[] { "綠茶" });
////		map.put("drinkPrice", new String[] { "50" });
////		map.put("drinkTag", new String[] { "綠茶" });
////		map.put("drinkStatus", new String[] { "1" });
////		map.put("DrinkUpdateDate", new String[] { "2024-08-01" });
////		map.put("DrinkCreateDate", new String[] { "2024-08-01" });
////		map.put("editedByMemberID", new String[] { "1" });
////		map.put("CreatedByMemberID", new String[] { "1" });
////		
//		List<DrinkVO> list2 = CompositeQuery_Drink.getAllC(map,sessionFactory.openSession());
//		for (DrinkVO aDrink : list2) {
//			System.out.print(aDrink.getDrinkID() + ",");
//			System.out.print(aDrink.getDrinkName() + ",");
//			System.out.print(aDrink.getDrinkPrice() + ",");
//			System.out.print(aDrink.getDrinkDes() + ",");
//			System.out.print(aDrink.getDrinkTag() + ",");
//			System.out.print(aDrink.getDrinkStatus() + ",");
//			System.out.print(aDrink.getDrinkUpdateDate() + ",");
//			System.out.print(aDrink.getDrinkCreateDate() + ",");
//			System.out.print(aDrink.getEditedByMemberID() + ",");
//			System.out.print(aDrink.getCreatedByMemberID() + ",");
//			
////			// 注意以下三行的寫法 (優!)
////			System.out.print(aDrink.getDrinkVO().getDeptno() + ",");
////			System.out.print(aDrink.getDrinkVO().getDname() + ",");
////			System.out.print(aDrink.getDrinkVO().getLoc());
//			System.out.println();
//		}
//		
//
//		//● (自訂)條件查詢
////		List<DrinkVO> list3 = repository.findByOthers(7001,"%K%",java.sql.Date.valueOf("1981-11-17"));
////		if(!list3.isDrinkty()) {
////			for (DrinkVO aDrink : list3) {
////				System.out.print(aDrink.getDrinkno() + ",");
////				System.out.print(aDrink.getEname() + ",");
////				System.out.print(aDrink.getJob() + ",");
////				System.out.print(aDrink.getHiredate() + ",");
////				System.out.print(aDrink.getSal() + ",");
////				System.out.print(aDrink.getComm() + ",");
////				// 注意以下三行的寫法 (優!)
////				System.out.print(aDrink.getDrinkVO().getDeptno() + ",");
////				System.out.print(aDrink.getDrinkVO().getDname() + ",");
////				System.out.print(aDrink.getDrinkVO().getLoc());
////				System.out.println();
////			}
////		} else System.out.print("查無資料\n");
////		System.out.println("--------------------------------");
//
//    }
//}
