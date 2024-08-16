//package com;
//
//import java.sql.Timestamp;
//import java.util.Date;
//
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import com.ken.notify.model.NotifyRepository;
//import com.tang.campaign.model.CampaignRepository;
//import com.tang.campaignProduct.model.CampaignProductRepository;
//import com.tang.drinkOrder.model.DrinkOrderRepository;
//import com.tang.drinkOrder.model.DrinkOrderVO;
//import com.tang.drinkOrderDetail.model.DrinkOrderDetailRepository;
//import com.tang.jibeiProduct.model.JibeiProductRepository;
//import com.xyuan.product.model.ProductRepository;
//import com.xyuan.product.model.ProductService;
//import com.xyuan.product.model.ProductVO;
//import com.xyuan.productOrder.model.ProductOrderRepository;
//import com.xyuan.productOrder.model.ProductOrderService;
//import com.xyuan.productOrder.model.ProductOrderVO;
//import com.xyuan.productOrderDetail.model.ProductOrderDetailRepository;
//import com.xyuan.productOrderDetail.model.ProductOrderDetailVO;
//
//@SpringBootApplication
//public class Test_Application_CommandLineRunner_tang implements CommandLineRunner {
//    
////	@Autowired
////	DrinkOrderRepository drinkOrderrepository ;
////
////	@Autowired
////	DrinkOrderDetailRepository drinkOrderDetailRepository;
////	
////	@Autowired
////	CampaignProductRepository campaignProductRepository;
////	
////	@Autowired
////	CampaignRepository campaignRepository; 
////	
////	@Autowired
////	JibeiProductRepository jibeiProductRepository;
////	
////	@Autowired
////	NotifyRepository notifyRepository;
////	
////	@Autowired
////    private SessionFactory sessionFactory;
//	
//	@Autowired
//	private ProductOrderDetailRepository productOrderDetailRepository ;
//
//	@Autowired
//	private ProductOrderRepository productOrderRepository ;
//
//	@Autowired
//    private ProductRepository productRepository ;
//	
//	@Autowired
//	ProductService productSvc;
//	
//	@Autowired
//	ProductOrderService productOrderSvc;
//	
//	public static void main(String[] args) {
//        SpringApplication.run(Test_Application_CommandLineRunner_tang.class);
//    }
//
//    @Override
//    public void run(String...args) throws Exception {
//
////    	List<NotifyVO> list = notifyRepository.findByUserIDOrderByNotifyTimeDesc(1);
////    	for (NotifyVO aNotifyVO : list) {
////    		System.out.print(aNotifyVO.getNotifyID()+",");
////    		System.out.print(aNotifyVO.getUserID()+",");
////    		System.out.print(aNotifyVO.getNotifySubject()+",");
////    		System.out.print(aNotifyVO.getNotifyMessage()+",");
////    		System.out.print(aNotifyVO.getNotifyTime());
////    	}
//
//    	
////    	//● 新增
////    	DrinkOrderVO drinkOrderVO = new DrinkOrderVO(); // 部門POJO
////		drinkOrderVO.setUserID(123);
////		drinkOrderVO.setStoreID(321);
////		drinkOrderVO.setDrinkOrderAmount(1);
////		drinkOrderVO.setDrinkOrderPickTime(Timestamp.valueOf("2024-09-01 14:32:00"));
////		drinkOrderVO.setDrinkOrderPayM(Byte.valueOf("1"));
////		drinkOrderVO.setDrinkOrderTTPrice(100);
////		drinkOrderVO.setDrinkOrderStatus(Byte.valueOf("0"));
////		drinkOrderVO.setDrinkOrderPayStatus(Byte.valueOf("0"));
////		Timestamp drinkOrderUpdateTime = new Timestamp(new Date().getTime());
////		drinkOrderVO.setDrinkOrderUpdateTime(drinkOrderUpdateTime);
////		drinkOrderVO.setMemberID(9999);
////		drinkOrderrepository.save(drinkOrderVO);
//		
////    	DrinkOrderDetailVO drinkOrderDetailVO = new DrinkOrderDetailVO();
////    	drinkOrderDetailVO.setDrinkOrderID(3);
////    	drinkOrderDetailVO.setDrinkOrderDetailIsHot(Byte.valueOf("0"));
////    	drinkOrderDetailVO.setDrinkOrderDetailUseCup(Byte.valueOf("1"));
////    	drinkOrderDetailVO.setDrinkOrderDetailPrice(150);
////    	drinkOrderDetailVO.setDrinkOrderDetailAmount(3);
////    	drinkOrderDetailVO.setDrinkOrderDetailIsJibei(Byte.valueOf("0"));
////    	drinkOrderDetailRepository.save(drinkOrderDetailVO);
//    	
////    	CampaignVO campaignVO = new CampaignVO();
////    	campaignVO.setCampaignName("活動A");
////    	campaignVO.setCampaignDiscount(4);
////    	campaignVO.setCampaignDes("本活動沒有商品免費");
////    	campaignVO.setCampaignStartDate(Date.valueOf("2024-07-01"));
////    	campaignVO.setCampaignEndDate(Date.valueOf("2024-09-01"));
////    	campaignVO.setCampaignPic(null);
////    	campaignRepository.save(campaignVO);
//    	
//    	
////    	CampaignProductVO campaignProductVO = new CampaignProductVO();
////    	campaignProductVO.setProductID(3);
////    	campaignProductVO.setCampaignID(90);
////    	campaignProductRepository.save(campaignProductVO);
//    	
////    	JibeiProductVO jibeiProductVO = new JibeiProductVO();
////    	jibeiProductVO.setDrinkID(3);
////    	jibeiProductVO.setJibeiProductPrice(300);
////    	jibeiProductVO.setJibeiProductDes("美式咖啡30杯");
////    	jibeiProductVO.setJibeiProductAmount(30);
////    	jibeiProductVO.setJibeiProductStatus(Byte.valueOf("1"));
////    	Timestamp jibeiProductUpdateTime = new Timestamp(new Date().getTime());
////    	jibeiProductVO.setJibeiProductUpdateTime(jibeiProductUpdateTime);
////    	jibeiProductRepository.save(jibeiProductVO);
//    	
//
////    	ProductOrderVO productOrderVO = new ProductOrderVO();
////    	productOrderVO.setUserID(10);
////    	productOrderVO.setProductOrderAmount(11);
////    	productOrderVO.setProductOrderTTPrice(600);
////    	productOrderVO.setProductOrderStatus(Byte.valueOf("0"));;
////    	productOrderVO.setProductOrderPayStatus(Byte.valueOf("1"));
////    	productOrderVO.setProductOrderAddr("三重區");
////    	productOrderVO.setReceiverMail("effortimp@gmail.com");
////    	productOrderVO.setReceiverPhone("0912345678");
////    	productOrderVO.setReceiverName("小小小小小");
////    	productOrderVO.setProductOrderPayM(Byte.valueOf("0"));
////    	Timestamp productOrderVOupdateTime = new Timestamp(new Date().getTime());
////    	productOrderVO.setProductOrderUpdateTime(productOrderVOupdateTime);
////    	ProductOrderVO newProductOrder = productOrderRepository.save(productOrderVO);
////    	
////    	ProductVO product = new ProductVO();
////    	product.setProductName("品牌一");
////    	product.setProductPrice(100);;
////    	product.setProductInv(50);
////    	product.setProductStatus(Byte.valueOf("1"));
////    	product.setProductTag(Byte.valueOf("3"));
////    	product.setProductUpdateTime(productOrderVOupdateTime);
////    	ProductVO newProductVO = productRepository.save(product);
////    	
////    	
////    	
////    	
////    	
////    	
////    	
////    	ProductOrderDetailVO productOrderDetailVO = new ProductOrderDetailVO();
////    	productOrderDetailVO.setProductOrderDetailPrice(300);
////    	productOrderDetailVO.setProductOrderDetailAmount(60);
////    	productOrderDetailVO.setProductVO(newProductVO);
////    	productOrderDetailVO.setProductOrderVO(newProductOrder);
////    	productOrderDetailRepository.save(productOrderDetailVO);
//    	
//    	
//    	
//    	
//		//● 修改
////		EmpVO empVO2 = new EmpVO();
////		empVO2.setEmpno(7001);
////		empVO2.setEname("吳永志2");
////		empVO2.setJob("MANAGER2");
////		empVO2.setHiredate(java.sql.Date.valueOf("2002-01-01"));
////		empVO2.setSal(new Double(20000));
////		empVO2.setComm(new Double(200));
////		empVO2.setDeptVO(deptVO);
////		repository.save(empVO2);
//		
//		//● 刪除   //●●● --> EmployeeRepository.java 內自訂的刪除方法
////		repository.deleteByEmpno(7014);
//		
//		//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
//		//System.out.println("--------------------------------");
//		//repository.deleteById(7001);      
//		//System.out.println("--------------------------------");
//
//    	//● 查詢-findByPrimaryKey (多方emp2.hbm.xml必須設為lazy="false")(優!)
////    	Optional<EmpVO> optional = repository.findById(7001);
////		EmpVO empVO3 = optional.get();
////		System.out.print(empVO3.getEmpno() + ",");
////		System.out.print(empVO3.getEname() + ",");
////		System.out.print(empVO3.getJob() + ",");
////		System.out.print(empVO3.getHiredate() + ",");
////		System.out.print(empVO3.getSal() + ",");
////		System.out.print(empVO3.getComm() + ",");
////		// 注意以下三行的寫法 (優!)
////		System.out.print(empVO3.getDeptVO().getDeptno() + ",");
////		System.out.print(empVO3.getDeptVO().getDname() + ",");
////		System.out.print(empVO3.getDeptVO().getLoc());
////		System.out.println("\n---------------------");
//      
//    	
//		//● 查詢-getAll (多方emp2.hbm.xml必須設為lazy="false")(優!)
////    	List<EmpVO> list = repository.findAll();
////		for (EmpVO aEmp : list) {
////			System.out.print(aEmp.getEmpno() + ",");
////			System.out.print(aEmp.getEname() + ",");
////			System.out.print(aEmp.getJob() + ",");
////			System.out.print(aEmp.getHiredate() + ",");
////			System.out.print(aEmp.getSal() + ",");
////			System.out.print(aEmp.getComm() + ",");
////			// 注意以下三行的寫法 (優!)
////			System.out.print(aEmp.getDeptVO().getDeptno() + ",");
////			System.out.print(aEmp.getDeptVO().getDname() + ",");
////			System.out.print(aEmp.getDeptVO().getLoc());
////			System.out.println();
////		}
//
//
//		//● 複合查詢-getAll(map) 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
////		Map<String, String[]> map = new TreeMap<String, String[]>();
////		map.put("empno", new String[] { "7001" });
////		map.put("ename", new String[] { "KING" });
////		map.put("job", new String[] { "PRESIDENT" });
////		map.put("hiredate", new String[] { "1981-11-17" });
////		map.put("sal", new String[] { "5000.5" });
////		map.put("comm", new String[] { "0.0" });
////		map.put("deptno", new String[] { "1" });
////		
////		List<EmpVO> list2 = HibernateUtil_CompositeQuery_Emp3.getAllC(map,sessionFactory.openSession());
////		for (EmpVO aEmp : list2) {
////			System.out.print(aEmp.getEmpno() + ",");
////			System.out.print(aEmp.getEname() + ",");
////			System.out.print(aEmp.getJob() + ",");
////			System.out.print(aEmp.getHiredate() + ",");
////			System.out.print(aEmp.getSal() + ",");
////			System.out.print(aEmp.getComm() + ",");
////			// 注意以下三行的寫法 (優!)
////			System.out.print(aEmp.getDeptVO().getDeptno() + ",");
////			System.out.print(aEmp.getDeptVO().getDname() + ",");
////			System.out.print(aEmp.getDeptVO().getLoc());
////			System.out.println();
////		}
//		
//
//		//● (自訂)條件查詢
////		List<EmpVO> list3 = repository.findByOthers(7001,"%K%",java.sql.Date.valueOf("1981-11-17"));
////		if(!list3.isEmpty()) {
////			for (EmpVO aEmp : list3) {
////				System.out.print(aEmp.getEmpno() + ",");
////				System.out.print(aEmp.getEname() + ",");
////				System.out.print(aEmp.getJob() + ",");
////				System.out.print(aEmp.getHiredate() + ",");
////				System.out.print(aEmp.getSal() + ",");
////				System.out.print(aEmp.getComm() + ",");
////				// 注意以下三行的寫法 (優!)
////				System.out.print(aEmp.getDeptVO().getDeptno() + ",");
////				System.out.print(aEmp.getDeptVO().getDname() + ",");
////				System.out.print(aEmp.getDeptVO().getLoc());
////				System.out.println();
////			}
////		} else System.out.print("查無資料\n");
////		System.out.println("--------------------------------");
//
//    }
//}
