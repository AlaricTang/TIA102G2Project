package hibernate.util.compositeQuery;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.xyuan.productOrder.model.ProductOrderVO;

public class CompositeQuery_ProductOrder {


	
	
	//做複合查詢的判斷
	public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<ProductOrderVO> root, String columnName, String value) {
		
		Predicate predicate = null;
		
		if("productOrderID".equals(columnName) || 
			"userID".equals(columnName))
			predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
		else if("productOrderStatus".equals(columnName))
			predicate = builder.equal(root.get(columnName), Byte.valueOf(value));
		else if("productOrderPayStatus".equals(columnName))
			predicate = builder.equal(root.get(columnName), Byte.valueOf(value));
		
		return predicate;
	}
	
	
	//複合查詢的內容
	public static List<ProductOrderVO> getAllC(Map<String, String> map, Session session) {
		Transaction tx = session.beginTransaction();
		String startCreateTime = null ,endCreateTime = null;
		List<ProductOrderVO> list = null;
		try {
			Map<String, String> map2 = new HashMap<>();
			Set<Map.Entry<String, String>> entry = map.entrySet();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ProductOrderVO> criteriaQuery = builder.createQuery(ProductOrderVO.class);
			Root<ProductOrderVO> root = criteriaQuery.from(ProductOrderVO.class);
			List<Predicate> predicateList = new ArrayList<>();
			
			for(Map.Entry<String, String> row : entry) {
				String key = row.getKey();
				if("action".equals(key)) {
					continue;
				}
				String value = row.getValue();
				if(value==null || value.isEmpty()) {
					continue;
				}
				map2.put(key, value);
			}
			
			Set<String> keys = map2.keySet();
			for(String key : keys) {
				String value = map2.get(key);
				Predicate predicate = get_aPredicate_For_AnyDB(builder, root, key, value.trim());
				if(predicate != null) {
					predicateList.add(predicate);
				}
			}
			
			if(keys.contains("productOrderStartCreateTime")) {
				 startCreateTime = map2.get("productOrderStartCreateTime").replace("T", " ") + ":00";
			}
			if(keys.contains("productOrderEndCreateTime")) {
				 endCreateTime = map2.get("productOrderEndCreateTime").replace("T", " ") + ":00";
			}
			
			if(keys.contains("productOrderStartCreateTime") && keys.contains("productOrderEndCreateTime")) {
				predicateList.add(builder.between(root.get("productOrderCreateTime"), Timestamp.valueOf(startCreateTime), Timestamp.valueOf(endCreateTime)));
			}else if (keys.contains("productOrderStartCreateTime") && !(keys.contains("productOrderEndCreateTime"))) {
				predicateList.add(builder.greaterThan(root.get("productOrderCreateTime"), Timestamp.valueOf(startCreateTime)));
			}else if ( !(keys.contains("productOrderStartCreateTime")) && keys.contains("productOrderEndCreateTime")) {
				predicateList.add(builder.lessThan(root.get("productOrderCreateTime"), Timestamp.valueOf(endCreateTime)));
			}
					
			
			criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
			criteriaQuery.orderBy(builder.asc(root.get("productOrderCreateTime")));
			
			TypedQuery<ProductOrderVO> query = session.createQuery(criteriaQuery);
			list =  query.getResultList();
			tx.commit();
		}catch (RuntimeException ex) {
			if (tx != null)
				tx.rollback();
			throw ex; // System.out.println(ex.getMessage()); 
		} finally {
			session.close();
			// HibernateUtil.getSessionFactory().close();
		}
		
		return list;
	}



}
