package hibernate.util.compositeQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ken.cup.model.CupVO;
import com.ken.cupRecord.model.CupRecordVO;

public class CompositeQuery_CupRecord {

	public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<CupRecordVO> root, String columnName,
			String value) {

		Predicate predicate = null;

		if ("cupRecordID".equals(columnName) || "userID".equals(columnName) ||
			"drinkOrderID".equals(columnName) || "cupID".equals(columnName) ||
			"storeRentID".equals(columnName) || "storeReturnID".equals(columnName))
			predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
		else if ("cupRecordRentDate".equals(columnName) || "cupRecordReturnDate".equals(columnName)) {
			predicate = builder.equal(root.get(columnName), java.sql.Date.valueOf(value));
		}
		return predicate;
	}

	@SuppressWarnings("unchecked")
	public static List<CupRecordVO> getAllC(Map<String, String[]> map, Session session) {

//		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		List<CupRecordVO> list = null;
		try {
			// 【●創建 CriteriaBuilder】
			CriteriaBuilder builder = session.getCriteriaBuilder();
			// 【●創建 CriteriaQuery】
			CriteriaQuery<CupRecordVO> criteriaQuery = builder.createQuery(CupRecordVO.class);
			// 【●創建 Root】
			Root<CupRecordVO> root = criteriaQuery.from(CupRecordVO.class);

			List<Predicate> predicateList = new ArrayList<Predicate>();

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
			System.out.println("predicateList.size()=" + predicateList.size());
			criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
			criteriaQuery.orderBy(builder.asc(root.get("cupRecordID")));
			// 【●最後完成創建 javax.persistence.Query●】
			Query query = session.createQuery(criteriaQuery); // javax.persistence.Query; //Hibernate 5 開始 取代原
																// org.hibernate.Query 介面
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
