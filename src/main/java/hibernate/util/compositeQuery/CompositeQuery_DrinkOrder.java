package hibernate.util.compositeQuery;import java.sql.Timestamp;
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

import com.tang.drinkOrder.model.DrinkOrderVO;

public class CompositeQuery_DrinkOrder {	
	
	//放複合查詢的判斷
	public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<DrinkOrderVO> root, String columnName, String value) {
		
		Predicate predicate = null;
		if("drinkOrderID".equals(columnName) || 
				"userID".equals(columnName)  ||
				"storeID".equals(columnName) )
			predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
		else if ("drinkOrderStatus".equals(columnName))
			predicate = builder.equal(root.get(columnName), Integer.valueOf(value).byteValue());
		
		return predicate; 
	}
	
	
	//根據判斷 寫複合查詢
	public static List<DrinkOrderVO> getAllC(Map<String, String> map,Session session){
		String startCreateTime = null ,endCreateTime = null;
		Transaction tx = session.beginTransaction();
		List<DrinkOrderVO> list = null;
		try {
			Map<String, String> map2 = new HashMap<>();
			Set<Map.Entry<String, String>> entry = map.entrySet() ;
	
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<DrinkOrderVO> criteriaQuery = builder.createQuery(DrinkOrderVO.class);
			Root<DrinkOrderVO> root = criteriaQuery.from(DrinkOrderVO.class);
			List<Predicate> predicateList = new ArrayList<>();
			
			for(Map.Entry<String, String> row : entry) {
				String key = row.getKey();
				if("action".equals(key)) { //雖然應該不會有action
					continue;
				}
				String value = row.getValue();
				if(value==null || value.isEmpty()) {
					continue;
				}
				map2.put(key, value);
			}
			
			Set<String> keys = map2.keySet();
			for (String key : keys) {
				String value = map2.get(key);
				predicateList.add(get_aPredicate_For_AnyDB(builder, root, key, value.trim()));
			}
			
			
			if(keys.contains("drinkOrderStartCreateTime")) {
				 startCreateTime = map2.get("drinkOrderStartCreateTime").replace("T", " ") + ":00";
			}
			if(keys.contains("drinkOrderEndCreateTime")) {
				 endCreateTime = map2.get("drinkOrderEndCreateTime").replace("T", " ") + ":00";
			}
			
			if(keys.contains("drinkOrderStartCreateTime") && keys.contains("drinkOrderEndCreateTime")) {
				predicateList.add(builder.between(root.get("drinkOrderCreateTime"), Timestamp.valueOf(startCreateTime), Timestamp.valueOf(endCreateTime)));
			}else if (keys.contains("drinkOrderStartCreateTime") && !(keys.contains("drinkOrderEndCreateTime"))) {
				predicateList.add(builder.greaterThan(root.get("drinkOrderCreateTime"), Timestamp.valueOf(startCreateTime)));
			}else if ( !(keys.contains("drinkOrderStartCreateTime")) && keys.contains("drinkOrderEndCreateTime")) {
				predicateList.add(builder.lessThan(root.get("drinkOrderCreateTime"), Timestamp.valueOf(endCreateTime)));
			}
			
			criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
			criteriaQuery.orderBy(builder.asc(root.get("drinkOrderCreateTime")));
				
			TypedQuery<DrinkOrderVO> query = session.createQuery(criteriaQuery);
			
			list = query.getResultList();
			
			tx.commit();
		} catch (RuntimeException ex) {
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
