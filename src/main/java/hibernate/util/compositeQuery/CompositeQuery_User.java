package hibernate.util.compositeQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.Query;
import com.ellie.user.model.UserVO;
import java.util.*;

public class CompositeQuery_User {

    // 根據欄位名稱和值生成對應的 Predicate
    public static Predicate get_aPredicate_For_User(CriteriaBuilder builder, Root<UserVO> root, String columnName, String value) {
        Predicate predicate = null;

        if ("userId".equals(columnName)) { // 用於 Integer
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        } else if ("userEmail".equals(columnName) || "userName".equals(columnName)) { // 用於 String
            predicate = builder.like(root.get(columnName), "%" + value + "%");
        } else if ("userGender".equals(columnName)) { // 用於 Integer
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        }

        return predicate;
    }

    @SuppressWarnings("unchecked")
    public static List<UserVO> getAllC(Map<String, String[]> map, Session session) {

        Transaction tx = session.beginTransaction();
        List<UserVO> list = null;
        try {
            // 創建 CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // 創建 CriteriaQuery
            CriteriaQuery<UserVO> criteriaQuery = builder.createQuery(UserVO.class);
            // 創建 Root
            Root<UserVO> root = criteriaQuery.from(UserVO.class);

            List<Predicate> predicateList = new ArrayList<>();

            // 遍歷查詢條件
            Set<String> keys = map.keySet();
            int count = 0;
            for (String key : keys) {
                String value = map.get(key)[0];
                if (value != null && !value.trim().isEmpty() && !"action".equals(key)) {
                    count++;
                    predicateList.add(get_aPredicate_For_User(builder, root, key, value.trim()));
                    System.out.println("有送出查詢資料的欄位數 count = " + count);
                }
            }
            System.out.println("predicateList.size() = " + predicateList.size());
            criteriaQuery.where(predicateList.toArray(new Predicate[0]));
            criteriaQuery.orderBy(builder.asc(root.get("userId")));
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

