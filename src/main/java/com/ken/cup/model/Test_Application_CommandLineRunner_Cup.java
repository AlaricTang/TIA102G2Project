//package com.ken.cup.model;
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
//import hibernate.util.compositeQuery.CompositeQuery_Cup;
//
//@SpringBootApplication
//public class Test_Application_CommandLineRunner_Cup implements CommandLineRunner {
//    // 這個程式就是從Application改過來的 下面第37的run方法就是CommandLineRunner提供的
//	@Autowired   //注入CupRepository介面
//	CupRepository repository ;
//	// 取得連線
//	@Autowired
//    private SessionFactory sessionFactory;
//	
//	public static void main(String[] args) {
//        SpringApplication.run(Test_Application_CommandLineRunner_Cup.class);
//    }
//
//    @Override
//    public void run(String...args) throws Exception {
//
//    	//● 新增
////		DeptVO deptVO = new DeptVO(); // 部門POJO
////		deptVO.setDeptno(1);
//
////		CupVO CupVO1 = new CupVO();
////		CupVO1.setUserID(1);
////		CupVO1.setStoreID(2);
////		CupVO1.setCupStatus(1);
////		CupVO1.setCupCreateDate(new java.sql.Date(System.currentTimeMillis()));
////		CupVO1.setMemberID(1);
////		repository.save(CupVO1);
//
//		//● 修改
////		CupVO CupVO2 = new CupVO();
////		CupVO2.setCupID(1);
////		CupVO2.setUserID(1);
////		CupVO2.setCupStatus(2);
////		CupVO2.setCupRentDate(new java.sql.Date(System.currentTimeMillis()));
////		CupVO2.setMemberID(1);
////		repository.save(CupVO2);
//		
//		//● 刪除   //●●● --> CuployeeRepository.java 內自訂的刪除方法 但因為我們是複合組件 所以刪不掉
////		repository.deleteByCupno(7014);
//		
//		//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
//		//System.out.println("--------------------------------");
//		//repository.deleteById(7001);      
//		//System.out.println("--------------------------------");
//
//    	//● 查詢-findByPrimaryKey (多方Cup2.hbm.xml必須設為lazy="false")(優!)
////    	Optional<CupVO> optional = repository.findById(1); //把後面的7001改成ename就可以直接放到service用 這邊是用在getOneService
////		CupVO CupVO3 = optional.get();
////		System.out.print(CupVO3.getCupID() + ",");
////		System.out.print(CupVO3.getUserID() + ",");
////		System.out.print(CupVO3.getStoreID() + ",");
////		System.out.print(CupVO3.getCupStatus() + ",");
////		System.out.print(CupVO3.getCupRentDate() + ",");
////		System.out.print(CupVO3.getCupCreateDate() + ",");
////		System.out.print(CupVO3.getMemberID() + ",");
////		// 注意以下三行的寫法 (優!)
////		System.out.print(CupVO3.getDeptVO().getDeptno() + ",");
////		System.out.print(CupVO3.getDeptVO().getDname() + ",");
////		System.out.print(CupVO3.getDeptVO().getLoc());
////		System.out.println("\n---------------------");
//      
//    	
//		//● 查詢-getAll (多方Cup2.hbm.xml必須設為lazy="false")(優!)
////    	List<CupVO> list = repository.findAll(); // 這個findall是 CupRespository 繼承 JPA 裡面提供的
////		for (CupVO aCup : list) {
////			System.out.print(aCup.getCupID() + ",");
////			System.out.print(aCup.getUserID() + ",");
////			System.out.print(aCup.getStoreID() + ",");
////			System.out.print(aCup.getCupStatus() + ",");
////			System.out.print(aCup.getCupRentDate() + ",");
////			System.out.print(aCup.getCupCreateDate() + ",");
////			System.out.print(aCup.getMemberID() + ",");
//			// 注意以下三行的寫法 (優!)
////			System.out.print(aCup.getDeptVO().getDeptno() + ",");
////			System.out.print(aCup.getDeptVO().getDname() + ",");
////			System.out.print(aCup.getDeptVO().getLoc());
////			System.out.println();
////		}
//
//
//		//● 複合查詢-getAll(map) 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
//		Map<String, String[]> map = new TreeMap<String, String[]>();
////		map.put("cupID", new String[] { "1" });
////		map.put("userID", new String[] { "1" });
////		map.put("storeID", new String[] { "2" });
////		map.put("memberID", new String[] { "1" });
////		map.put("cupStatus", new String[] { "1" });
////		map.put("comm", new String[] { "0.0" });
////		map.put("deptno", new String[] { "1" });
////		
//		List<CupVO> list2 = CompositeQuery_Cup.getAllC(map,sessionFactory.openSession());
//		for (CupVO aCup : list2) {
//			System.out.print(aCup.getCupID() + ",");
//			System.out.print(aCup.getUserID() + ",");
//			System.out.print(aCup.getStoreID() + ",");
//			System.out.print(aCup.getCupStatus() + ",");
//			System.out.print(aCup.getCupRentDate() + ",");
//			System.out.print(aCup.getCupCreateDate() + ",");
//			System.out.print(aCup.getMemberID() + ",");
////			// 注意以下三行的寫法 (優!)
////			System.out.print(aCup.getDeptVO().getDeptno() + ",");
////			System.out.print(aCup.getDeptVO().getDname() + ",");
////			System.out.print(aCup.getDeptVO().getLoc());
//			System.out.println();
//		}
//		
//
//		//● (自訂)條件查詢
////		List<CupVO> list3 = repository.findByOthers(7001,"%K%",java.sql.Date.valueOf("1981-11-17"));
////		if(!list3.isCupty()) {
////			for (CupVO aCup : list3) {
////				System.out.print(aCup.getCupno() + ",");
////				System.out.print(aCup.getEname() + ",");
////				System.out.print(aCup.getJob() + ",");
////				System.out.print(aCup.getHiredate() + ",");
////				System.out.print(aCup.getSal() + ",");
////				System.out.print(aCup.getComm() + ",");
////				// 注意以下三行的寫法 (優!)
////				System.out.print(aCup.getDeptVO().getDeptno() + ",");
////				System.out.print(aCup.getDeptVO().getDname() + ",");
////				System.out.print(aCup.getDeptVO().getLoc());
////				System.out.println();
////			}
////		} else System.out.print("查無資料\n");
////		System.out.println("--------------------------------");
//
//    }
//}
