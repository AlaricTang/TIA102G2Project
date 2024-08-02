//package com.ken.cupRecord.model;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.TreeMap;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import hibernate.util.compositeQuery.CompositeQuery_CupRecord;
//
//
//@SpringBootApplication
//public class Test_Application_CommandLineRunner_CupRecord implements CommandLineRunner {
//    // 這個程式就是從Application改過來的 下面第37的run方法就是CommandLineRunner提供的
//	@Autowired   //注入CupRecordRepository介面
//	CupRecordRepository repository ;
//	// 取得連線
//	@Autowired
//    private SessionFactory sessionFactory;
//	
//	public static void main(String[] args) {
//        SpringApplication.run(Test_Application_CommandLineRunner_CupRecord.class);
//    }
//
//    @Override
//    public void run(String...args) throws Exception {
//
//    	//● 新增
////		DeptVO deptVO = new DeptVO(); // 部門POJO
////		deptVO.setDeptno(1);
//
////		CupRecordVO cupRecordVO1 = new CupRecordVO();
////		cupRecordVO1.setUserID(2); // 2號使用者借杯子
////		cupRecordVO1.setCupID(5); // 借的杯子是5號
////		cupRecordVO1.setDrinkOrderID(2); // 在訂單編號為2的地方借的 一個訂單可能借很多杯子
////		cupRecordVO1.setStoreRentID(1); // 1號店出租的(幸福咖啡)
////		cupRecordVO1.setStoreReturnID(1); // 1號店還杯子的(幸福咖啡)
////		cupRecordVO1.setCupRecordRentDate(java.sql.Date.valueOf("2024-07-28")); // 7/28借的
////		cupRecordVO1.setCupRecordReturnDate(java.sql.Date.valueOf("2024-07-28")); // 當天還
////		CupRecordVO1.setJob("MANAGER");
////		CupRecordVO1.setHiredate(java.sql.Date.valueOf("2005-01-01"));
////		CupRecordVO1.setSal(new Double(50000));
////		CupRecordVO1.setComm(new Double(500));
////		CupRecordVO1.setDeptVO(deptVO);
////		repository.save(cupRecordVO1);
//
//		//● 修改 基本上應該不用去做修改紀錄
////		CupRecordVO CupRecordVO2 = new CupRecordVO();
////		CupRecordVO2.setCupRecordID(1);
////		CupRecordVO2.setUserID(2); // 2號使用者借杯子
////		CupRecordVO2.setCupID(5); // 借的杯子是5號
////		CupRecordVO2.setDrinkOrderID(2); // 在訂單編號為2的地方借的 一個訂單可能借很多杯子
////		CupRecordVO2.setStoreRentID(1); // 1號店出租的(幸福咖啡)
////		CupRecordVO2.setStoreReturnID(1); // 1號店還杯子的(幸福咖啡)
////		CupRecordVO2.setCupRecordRentDate(java.sql.Date.valueOf("2024-07-28")); // 7/28借的
////		CupRecordVO2.setCupRecordReturnDate(java.sql.Date.valueOf("2024-07-28")); // 當天還
////		CupRecordVO2.setEname("吳永志2");
////		CupRecordVO2.setJob("MANAGER2");
////		CupRecordVO2.setHiredate(java.sql.Date.valueOf("2002-01-01"));
////		CupRecordVO2.setSal(new Double(20000));
////		CupRecordVO2.setComm(new Double(200));
////		CupRecordVO2.setDeptVO(deptVO);
////		repository.save(CupRecordVO2);
//		
//		//● 刪除   //●●● --> CupRecordloyeeRepository.java 內自訂的刪除方法 但因為我們是複合組件 所以刪不掉
////		repository.deleteByCupRecordno(7014);
//		
//		//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
//		//System.out.println("--------------------------------");
//		//repository.deleteById(7001);      
//		//System.out.println("--------------------------------");
//
//    	//● 查詢-findByPrimaryKey (多方CupRecord2.hbm.xml必須設為lazy="false")(優!)
////    	Optional<CupRecordVO> optional = repository.findById(2); // 把後面的7001改成ename就可以直接放到service用 這邊是用在getOneService
////		CupRecordVO CupRecordVO3 = optional.get();
////		System.out.print(CupRecordVO3.getCupRecordID() + ",");
////		System.out.print(CupRecordVO3.getUserID() + ",");
////		System.out.print(CupRecordVO3.getCupID() + ",");
////		System.out.print(CupRecordVO3.getDrinkOrderID() + ",");
////		System.out.print(CupRecordVO3.getStoreRentID() + ",");
////		System.out.print(CupRecordVO3.getStoreReturnID() + ",");
////		System.out.print(CupRecordVO3.getCupRecordRentDate() + ",");
////		System.out.print(CupRecordVO3.getCupRecordReturnDate() + ",");
////		// 注意以下三行的寫法 (優!)
////		System.out.print(CupRecordVO3.getDeptVO().getDeptno() + ",");
////		System.out.print(CupRecordVO3.getDeptVO().getDname() + ",");
////		System.out.print(CupRecordVO3.getDeptVO().getLoc());
////		System.out.println("\n---------------------");
//      
//    	
//		//● 查詢-getAll (多方CupRecord2.hbm.xml必須設為lazy="false")(優!)
////    	List<CupRecordVO> list = repository.findAll(); // 這個findall是 CupRecordRespository 繼承 JPA 裡面提供的
////		for (CupRecordVO aCupRecord : list) {
////			System.out.print(aCupRecord.getCupRecordID() + ",");
////			System.out.print(aCupRecord.getUserID() + ",");
////			System.out.print(aCupRecord.getCupID() + ",");
////			System.out.print(aCupRecord.getDrinkOrderID() + ",");
////			System.out.print(aCupRecord.getStoreRentID() + ",");
////			System.out.print(aCupRecord.getStoreReturnID() + ",");
////			System.out.print(aCupRecord.getCupRecordRentDate() + ",");
////			System.out.print(aCupRecord.getCupRecordReturnDate() + ",");
////			// 注意以下三行的寫法 (優!)
////			System.out.print(aCupRecord.getDeptVO().getDeptno() + ",");
////			System.out.print(aCupRecord.getDeptVO().getDname() + ",");
////			System.out.print(aCupRecord.getDeptVO().getLoc());
////			System.out.println();
////		}
//
//
//		//● 複合查詢-getAll(map) 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
//		Map<String, String[]> map = new TreeMap<String, String[]>();
////		map.put("cupRecordID", new String[] { "2" });
////		map.put("userID", new String[] { "2" });
////		map.put("cupID", new String[] { "5" });
////		map.put("drinkOrderID", new String[] { "2" });
////		map.put("storeRentID", new String[] { "1" });
////		map.put("storeReturnID", new String[] { "1" });
////		map.put("cupRecordRentDate", new String[] { "2024-07-28" });
////		map.put("cupRecordReturnDate", new String[] { "2024-07-29" });
////		map.put("ename", new String[] { "KING" });
////		map.put("job", new String[] { "PRESIDENT" });
////		map.put("hiredate", new String[] { "1981-11-17" });
////		map.put("sal", new String[] { "5000.5" });
////		map.put("comm", new String[] { "0.0" });
////		map.put("deptno", new String[] { "1" });
////		
//		List<CupRecordVO> list2 = CompositeQuery_CupRecord.getAllC(map,sessionFactory.openSession());
//		for (CupRecordVO aCupRecord : list2) {
//			System.out.print(aCupRecord.getCupRecordID() + ",");
//			System.out.print(aCupRecord.getUserID() + ",");
//			System.out.print(aCupRecord.getCupID() + ",");
//			System.out.print(aCupRecord.getDrinkOrderID() + ",");
//			System.out.print(aCupRecord.getStoreRentID() + ",");
//			System.out.print(aCupRecord.getStoreReturnID() + ",");
//			System.out.print(aCupRecord.getCupRecordRentDate() + ",");
//			System.out.print(aCupRecord.getCupRecordReturnDate() + ",");
////			// 注意以下三行的寫法 (優!)
////			System.out.print(aCupRecord.getDeptVO().getDeptno() + ",");
////			System.out.print(aCupRecord.getDeptVO().getDname() + ",");
////			System.out.print(aCupRecord.getDeptVO().getLoc());
//			System.out.println();
//		}
//		
//
//		//● (自訂)條件查詢
////		List<CupRecordVO> list3 = repository.findByOthers(7001,"%K%",java.sql.Date.valueOf("1981-11-17"));
////		if(!list3.isCupRecordty()) {
////			for (CupRecordVO aCupRecord : list3) {
////				System.out.print(aCupRecord.getCupRecordno() + ",");
////				System.out.print(aCupRecord.getEname() + ",");
////				System.out.print(aCupRecord.getJob() + ",");
////				System.out.print(aCupRecord.getHiredate() + ",");
////				System.out.print(aCupRecord.getSal() + ",");
////				System.out.print(aCupRecord.getComm() + ",");
////				// 注意以下三行的寫法 (優!)
////				System.out.print(aCupRecord.getDeptVO().getDeptno() + ",");
////				System.out.print(aCupRecord.getDeptVO().getDname() + ",");
////				System.out.print(aCupRecord.getDeptVO().getLoc());
////				System.out.println();
////			}
////		} else System.out.print("查無資料\n");
////		System.out.println("--------------------------------");
//
//    }
//}
