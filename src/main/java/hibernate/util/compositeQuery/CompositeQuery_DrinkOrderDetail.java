package hibernate.util.compositeQuery;import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.tang.drinkOrderDetail.model.DrinkOrderDetailVO;

public class CompositeQuery_DrinkOrderDetail {

	@Autowired
	private static SessionFactory sessionFactory;
	
	
	private static Session getSession() {
		return sessionFactory.getCurrentSession(); 
	}
	
	public static  Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<DrinkOrderDetailVO> root, String columnName, String value) {
		
		Predicate predicate = null;
		
		if("drinkOrderDetailID".equals(columnName))
			predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
		return predicate;
	}
	
	public static List<DrinkOrderDetailVO> getAllC(Map<String, String[]> map){
		
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<DrinkOrderDetailVO> criteriaQuery = builder.createQuery(DrinkOrderDetailVO.class);
		Root<DrinkOrderDetailVO> root = criteriaQuery.from(DrinkOrderDetailVO.class);
		List<Predicate> predicateList = new ArrayList<>();
		
		Set<String> keys = map.keySet();
		int count = 0;
		for (String key : keys) {
			String value = map.get(key)[0];
			if (value != null && value.trim().length() != 0 && !"action".equals(key)) {
				count++;
				predicateList.add(get_aPredicate_For_AnyDB(builder, root, key, value.trim()));
				System.out.println("有送出查詢資料的欄位數count = " + count);
			}
		}
		System.out.println("predicateList.size()=" + count);
		criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
		criteriaQuery.orderBy(builder.asc(root.get("drinkOrderDetailID")));
		
		TypedQuery<DrinkOrderDetailVO> query = getSession().createQuery(criteriaQuery);
		return query.getResultList();
	}
}
