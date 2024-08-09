package com;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ken.notify.model.NotifyRepository;
import com.ken.notify.model.NotifyVO;

@SpringBootApplication
public class Test_Application_CommandLineRunner_Notify implements CommandLineRunner {
    // 這個程式就是從Application改過來的 下面第37的run方法就是CommandLineRunner提供的
	@Autowired   //注入NotifyRepository介面
	NotifyRepository repository ;
	// 取得連線
	@Autowired
    private SessionFactory sessionFactory;
	
	public static void main(String[] args) {
        SpringApplication.run(Test_Application_CommandLineRunner_Notify.class);
    }

    @Override
    public void run(String...args) throws Exception {

    	//● 新增
//		DeptVO deptVO = new DeptVO(); // 部門POJO
//		deptVO.setDeptno(1);

//		NotifyVO NotifyVO1 = new NotifyVO();
//		NotifyVO1.setUserID(1);
//		NotifyVO1.setNotifySubject("通知給王小美");
//		NotifyVO1.setNotifyMessage("通知測試1");
//		repository.save(NotifyVO1);
    	
    	    // 使用自定义查询方法查找并按 notifyTime 降序排序
    	List<NotifyVO> list1 = repository.findByUserIDOrderByNotifyTimeDesc(1);
    	   for (NotifyVO aNotify : list1) {
    	     System.out.print(aNotify.getNotifyID() + ",");
    	     System.out.print(aNotify.getUserID() + ",");
    	     System.out.print(aNotify.getNotifySubject() + ",");
    	     System.out.print(aNotify.getNotifyMessage() + ",");
    	     System.out.print(aNotify.getNotifyTime() + ",");
    	     System.out.println();
    	    }

		//● 修改
//		NotifyVO NotifyVO2 = new NotifyVO();
//		NotifyVO2.setNotifyID(5);
//		NotifyVO2.setNotifyName("紅茶拿鐵");
//		NotifyVO2.setNotifyPrice(45);
//		NotifyVO2.setNotifyPic(null);
//		NotifyVO2.setNotifyTag("紅茶");
//		NotifyVO2.setNotifyStatus((byte) 1);
//		NotifyVO2.setNotifyUpdateDate(new java.sql.Date(System.currentTimeMillis()));
//		NotifyVO2.setEditedByMemberID(2);
////		NotifyVO2.setComm(new Double(200));
////		NotifyVO2.setNotifyVO(NotifyVO);
//		repository.save(NotifyVO2);
		
		//● 刪除   //●●● --> NotifyloyeeRepository.java 內自訂的刪除方法 但因為我們是複合組件 所以刪不掉
//		repository.deleteByNotifyno(7014);
		
		//● 刪除   //XXX --> Repository內建的刪除方法目前無法使用，因為有@ManyToOne
		//System.out.println("--------------------------------");
		//repository.deleteById(7001);      
		//System.out.println("--------------------------------");

    	//● 查詢-findByPrimaryKey (多方Notify2.hbm.xml必須設為lazy="false")(優!)
//    	Optional<NotifyVO> optional = repository.findById(1); //把後面的7001改成ename就可以直接放到service用 這邊是用在getOneService
//		NotifyVO NotifyVO3 = optional.get();
//		System.out.print(NotifyVO3.getNotifyID() + ",");
//		System.out.print(NotifyVO3.getUserID() + ",");
//		System.out.print(NotifyVO3.getNotifySubject() + ",");
//		System.out.print(NotifyVO3.getNotifyMessage() + ",");
//		System.out.print(NotifyVO3.getNotifyTime() + ",");
//		System.out.print(NotifyVO3.getCreatedByMemberID());
		// 注意以下三行的寫法 (優!)
//		System.out.print(NotifyVO3.getNotifyVO().getDeptno() + ",");
//		System.out.print(NotifyVO3.getNotifyVO().getDname() + ",");
//		System.out.print(NotifyVO3.getNotifyVO().getLoc());
//		System.out.println("\n---------------------");
      
    	
		//● 查詢-getAll (多方Notify2.hbm.xml必須設為lazy="false")(優!)
//    	List<NotifyVO> list = repository.findAll(); 
    	// 這個findall是 NotifyRespository 繼承 JPA 裡面提供的
//		for (NotifyVO aNotify : list) {
//			System.out.print(aNotify.getNotifyID() + ",");
//			System.out.print(aNotify.getUserID() + ",");
//			System.out.print(aNotify.getNotifySubject() + ",");
//			System.out.print(aNotify.getNotifyMessage() + ",");
//			System.out.print(aNotify.getNotifyTime() + ",");
			// 注意以下三行的寫法 (優!)
//			System.out.print(aNotify.getNotifyVO().getDeptno() + ",");
//			System.out.print(aNotify.getNotifyVO().getDname() + ",");
//			System.out.print(aNotify.getNotifyVO().getLoc());
//			System.out.println();
//		}


		//● 複合查詢-getAll(map) 配合 req.getParameterMap()方法 回傳 java.util.Map<java.lang.String,java.lang.String[]> 之測試
//		Map<String, String[]> map = new TreeMap<String, String[]>();
//		map.put("NotifyID", new String[] { "1" });
//		map.put("NotifyName", new String[] { "綠茶" });
//		map.put("NotifyPrice", new String[] { "50" });
//		map.put("NotifyTag", new String[] { "綠茶" });
//		map.put("NotifyStatus", new String[] { "1" });
//		map.put("NotifyUpdateDate", new String[] { "2024-08-01" });
//		map.put("NotifyCreateDate", new String[] { "2024-08-01" });
//		map.put("editedByMemberID", new String[] { "1" });
//		map.put("CreatedByMemberID", new String[] { "1" });
//		
//		List<NotifyVO> list2 = CompositeQuery_Notify.getAllC(map,sessionFactory.openSession());
//		for (NotifyVO aNotify : list2) {
//			System.out.print(aNotify.getNotifyID() + ",");
//			System.out.print(aNotify.getNotifyName() + ",");
//			System.out.print(aNotify.getNotifyPrice() + ",");
//			System.out.print(aNotify.getNotifyDes() + ",");
//			System.out.print(aNotify.getNotifyTag() + ",");
//			System.out.print(aNotify.getNotifyStatus() + ",");
//			System.out.print(aNotify.getNotifyUpdateDate() + ",");
//			System.out.print(aNotify.getNotifyCreateDate() + ",");
//			System.out.print(aNotify.getEditedByMemberID() + ",");
//			System.out.print(aNotify.getCreatedByMemberID() + ",");
			
//			// 注意以下三行的寫法 (優!)
//			System.out.print(aNotify.getNotifyVO().getDeptno() + ",");
//			System.out.print(aNotify.getNotifyVO().getDname() + ",");
//			System.out.print(aNotify.getNotifyVO().getLoc());
//			System.out.println();
//		}
		

		//● (自訂)條件查詢
//		List<NotifyVO> list3 = repository.findByOthers(7001,"%K%",java.sql.Date.valueOf("1981-11-17"));
//		if(!list3.isNotifyty()) {
//			for (NotifyVO aNotify : list3) {
//				System.out.print(aNotify.getNotifyno() + ",");
//				System.out.print(aNotify.getEname() + ",");
//				System.out.print(aNotify.getJob() + ",");
//				System.out.print(aNotify.getHiredate() + ",");
//				System.out.print(aNotify.getSal() + ",");
//				System.out.print(aNotify.getComm() + ",");
//				// 注意以下三行的寫法 (優!)
//				System.out.print(aNotify.getNotifyVO().getDeptno() + ",");
//				System.out.print(aNotify.getNotifyVO().getDname() + ",");
//				System.out.print(aNotify.getNotifyVO().getLoc());
//				System.out.println();
//			}
//		} else System.out.print("查無資料\n");
//		System.out.println("--------------------------------");

    }
}
