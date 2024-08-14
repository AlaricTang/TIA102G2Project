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

import com.tang.jibeiProduct.model.JibeiProductVO;

public class CompositeQuery_JibeiProduct {
	
	//放複合查詢的判斷
	public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<JibeiProductVO> root, String columnName, String value) {
		
		Predicate predicate = null;
		
		if( "drinkID".equals(columnName) || 
		    "jibeiProductPrice".equals(columnName) ||
		    "memberID".equals(columnName))
			predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
		else if ("jibeiProductStatus".equals(columnName))
			predicate = builder.equal(root.get(columnName), Byte.valueOf(value));
		else if ( "jibeiProductDes".equals(columnName))
			predicate = builder.like(root.get(columnName), "%" + value + "%");
		
		return predicate;
		}
	//根據判斷 寫複合查詢
	public static List<JibeiProductVO> getAllC(Map<String, String[]> map, Session session){
		Transaction tx = session.beginTransaction();
		List<JibeiProductVO> list = null;
		try {
			Map<String, String> map2 = new HashMap<>();
			Set<Map.Entry<String, String[]>> entry = map.entrySet();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<JibeiProductVO> criteriaQuery = builder.createQuery(JibeiProductVO.class);
			Root<JibeiProductVO> root = criteriaQuery.from(JibeiProductVO.class);
			List<Predicate> predicateList = new ArrayList<Predicate>();
			
			for(Map.Entry<String, String[]> row : entry) {
				String key = row.getKey();
				if("action".equals(key)) { //雖然應該不會有action
					continue;
				}
				String[] value = row.getValue();
				if(value==null || value[0].isEmpty()) {
					continue;
				}
				map2.put(key, value[0]);
			}
			Set<String> keys = map2.keySet();
			for (String key : keys) {
				String value = map2.get(key);
				predicateList.add(get_aPredicate_For_AnyDB(builder, root, key, value.trim()));
			}
			if(keys.contains("jibeiProductStartCreateTime") && keys.contains("jibeiProductEndCreateTime")) {
				predicateList.add(builder.between(root.get("jibeiProductCreateTime"), Timestamp.valueOf(map2.get("jibeiProductStartCreateTime")), Timestamp.valueOf(map2.get("jibeiProductEndCreateTime"))));
			}else if (keys.contains("jibeiProductStartCreateTime") && !(keys.contains("jibeiProductEndCreateTime"))) {
				predicateList.add(builder.greaterThan(root.get("jibeiProductCreateTime"), Timestamp.valueOf(map2.get("jibeiProductStartCreateTime"))));
			}else if ( !(keys.contains("jibeiProductStartCreateTime")) && keys.contains("jibeiProductEndCreateTime")) {
				predicateList.add(builder.lessThan(root.get("jibeiProductCreateTime"), Timestamp.valueOf(map2.get("jibeiProductEndCreateTime"))));
			}
			
			
			criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
			criteriaQuery.orderBy(builder.asc(root.get("jibeiProductID")));
			
			TypedQuery<JibeiProductVO> query = session.createQuery(criteriaQuery);
			list = query.getResultList();

			tx.commit();
		} catch (RuntimeException ex) {
			if (tx != null)
				tx.rollback();
			throw ex; 
		} finally {
			session.close();
		}

		return list;
	}
}
