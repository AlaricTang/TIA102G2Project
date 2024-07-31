package hibernate.util.compositeQuery;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.xyuan.productOrder.model.ProductOrderVO;

import hibernate.util.HibernateUtil;

public class CompositeQuery_ProductOrder {

	private static SessionFactory factory;
	
	private CompositeQuery_ProductOrder() {
		factory = HibernateUtil.getSessionFactory();
	}
	
	private static Session getSession() {
		return factory.getCurrentSession();
	}
	
	
	//複合查詢的判斷
	public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<ProductOrderVO> root, String columnName, String value) {
		
		Predicate predicate = null;
		
		if("productQrderID".equals(columnName) || "userID".equals(columnName) || "productOrderStatus".equals(columnName))
			predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
		else if("productOrderStatus".equals(columnName))
			predicate = builder.equal(root.get(columnName), Byte.valueOf(value));
		else if("productOrderCreateTime".equals(columnName))
			predicate = builder.equal(root.get(columnName), Timestamp.valueOf(value));
		return predicate;
		
	}
	
	
	//複合查詢的內容
	public static List<ProductOrderVO> getAllC(Map<String, String[]> map) {
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<ProductOrderVO> criteriaQuery = builder.createQuery(ProductOrderVO.class);
		Root<ProductOrderVO> root = criteriaQuery.from(ProductOrderVO.class);
		List<Predicate> predicateList = new ArrayList<>();
		
		Set<String> keys = map.keySet();
		int count = 0;
		for(String key : keys) {
			String value = map.get(key)[0];
			if(value != null && value.trim().length() != 0 && !"action".equals(key)) {
				count++;
				predicateList.add(get_aPredicate_For_AnyDB(builder, root, key, value.trim()));
				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		System.out.println("predicateList.size()=" + count);
		criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
		criteriaQuery.orderBy(builder.asc(root.get("productOrderCreateTime")));
		
		TypedQuery<ProductOrderVO> query = getSession().createQuery(criteriaQuery);
		
		return query.getResultList();
	}



}
