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
import org.hibernate.SessionFactory;

import com.tang.drinkOrder.model.DrinkOrderVO;

import hibernate.util.HibernateUtil;

public class CompositeQuery_DrinkOrder {
	
	private static SessionFactory factory;
	
	private CompositeQuery_DrinkOrder() {
		factory = HibernateUtil.getSessionFactory();
	}
	
	private static Session getSession() {
		return factory.getCurrentSession();
	}
	
	
	//放複合查詢的判斷
	public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<DrinkOrderVO> root, String columnName, String value) {
		
		Predicate predicate = null;
		
		if("drinkOrderID".equals(columnName) || 
				"userID".equals(columnName)  ||
				"storeID".equals(columnName) )
			predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
		else if ("drinkOrderStatus".equals(columnName))
			predicate = builder.equal(root.get(columnName), Byte.valueOf(value));
		
		return predicate; 
	}
	
	
	//根據判斷 寫複合查詢
	public static List<DrinkOrderVO> getAllC(Map<String, String[]> map){
		
		Map<String, String> map2 = new HashMap<>();
		Set<Map.Entry<String, String[]>> entry = map.entrySet() ;

		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<DrinkOrderVO> criteriaQuery = builder.createQuery(DrinkOrderVO.class);
		Root<DrinkOrderVO> root = criteriaQuery.from(DrinkOrderVO.class);
		List<Predicate> predicateList = new ArrayList<>();
		
		for(Map.Entry<String, String[]> row : entry) {
			String key = row.getKey();
			if("action".equals(key)) {
				continue;
			}
			
			String value = row.getValue()[0];
			if(value==null || value.isEmpty()) {
				continue;
			}
			map2.put(key, value);
		}
		
		Set<String> keys = map.keySet();
		
		if(keys.contains("drinkOrderStartCreateTime") && keys.contains("drinkOrderEndCreateTime")) {
			predicateList.add(builder.between(root.get("drinkOrderCreateTime"), Timestamp.valueOf(map.get("drinkOrderStartCreateTime")[0]), Timestamp.valueOf(map.get("drinkOrderEndCreateTime")[0])));
		}else if (keys.contains("drinkOrderStartCreateTime") && !(keys.contains("drinkOrderEndCreateTime"))) {
			predicateList.add(builder.greaterThan(root.get("drinkOrderCreateTime"), Timestamp.valueOf(map.get("drinkOrderStartCreateTime")[0])));
		}else if ( !(keys.contains("drinkOrderStartCreateTime")) && keys.contains("drinkOrderEndCreateTime")) {
			predicateList.add(builder.lessThan(root.get("drinkOrderCreateTime"), Timestamp.valueOf(map.get("drinkOrderEndCreateTime")[0])));
		}
		
		for (String key : keys) {
			String value = map.get(key)[0];
			predicateList.add(get_aPredicate_For_AnyDB(builder, root, key, value.trim()));
		}
		criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
		criteriaQuery.orderBy(builder.asc(root.get("drinkOrderCreateTime")));
			
		TypedQuery<DrinkOrderVO> query = getSession().createQuery(criteriaQuery);
		return query.getResultList();
			
	}
}
