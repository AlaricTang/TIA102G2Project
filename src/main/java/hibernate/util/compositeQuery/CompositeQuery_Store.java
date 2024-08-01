package hibernate.util.compositeQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.Query;
import com.ellie.store.model.StoreVO;
import java.util.*;

public class CompositeQuery_Store {

    // 根據欄位名稱和值生成對應的 Predicate
    public static Predicate get_aPredicate_For_Store(CriteriaBuilder builder, Root<StoreVO> root, String columnName, String value) {
        Predicate predicate = null;

        if ("storeID".equals(columnName)) { // 用於 Integer
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        } else if ("storeAcc".equals(columnName) || "storeName".equals(columnName) || "storeAddr".equals(columnName) || "storePhone".equals(columnName)) { // 用於 String
            predicate = builder.like(root.get(columnName), "%" + value + "%");
        } else if ("storeCups".equals(columnName)) { // 用於 Integer
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        } else if ("storeCreateDate".equals(columnName)) { // 用於 Date
            predicate = builder.equal(root.get(columnName), java.sql.Date.valueOf(value));
        }

        return predicate;
    }

    @SuppressWarnings("unchecked")
    public static List<StoreVO> getAllC(Map<String, String[]> map, Session session) {

        Transaction tx = session.beginTransaction();
        List<StoreVO> list = null;
        try {
            // 創建 CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // 創建 CriteriaQuery
            CriteriaQuery<StoreVO> criteriaQuery = builder.createQuery(StoreVO.class);
            // 創建 Root
            Root<StoreVO> root = criteriaQuery.from(StoreVO.class);

            List<Predicate> predicateList = new ArrayList<>();

            // 遍歷查詢條件
            Set<String> keys = map.keySet();
            int count = 0;
            for (String key : keys) {
                String value = map.get(key)[0];
                if (value != null && !value.trim().isEmpty() && !"action".equals(key)) {
                    count++;
                    predicateList.add(get_aPredicate_For_Store(builder, root, key, value.trim()));
                    System.out.println("有送出查詢資料的欄位數 count = " + count);
                }
            }
            System.out.println("predicateList.size() = " + predicateList.size());
            criteriaQuery.where(predicateList.toArray(new Predicate[0]));
            criteriaQuery.orderBy(builder.asc(root.get("storeID")));
            // 創建 javax.persistence.Query
            Query query = session.createQuery(criteriaQuery);
            list = query.getResultList();

            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex; 
        } finally {
            session.close();
        }

        return list;
    }
}
