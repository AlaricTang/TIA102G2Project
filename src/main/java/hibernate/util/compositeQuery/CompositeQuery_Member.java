package hibernate.util.compositeQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.Query;
import com.ellie.member.model.MemberVO;
import java.util.*;

public class CompositeQuery_Member {

    // 根據欄位名稱和值生成對應的 Predicate
    public static Predicate get_aPredicate_For_Member(CriteriaBuilder builder, Root<MemberVO> root, String columnName, String value) {
        Predicate predicate = null;

        if ("memberID".equals(columnName)) { // 用於 Integer
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
        } else if ("memberAcc".equals(columnName) || "memberName".equals(columnName) || "memberEmail".equals(columnName)) { // 用於 String
            predicate = builder.like(root.get(columnName), "%" + value + "%");
        } else if ("memberGender".equals(columnName)) { // 用於 Integer
            predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
//        } else if ("memberCreateDate".equals(columnName)) { // 用於 Timestamp
//            predicate = builder.equal(root.get(columnName), java.sql.Timestamp.valueOf(value));
        }

        return predicate;
    }

    @SuppressWarnings("unchecked")
    public static List<MemberVO> getAllC(Map<String, String[]> map, Session session) {

        Transaction tx = session.beginTransaction();
        List<MemberVO> list = null;
        try {
            // 創建 CriteriaBuilder
            CriteriaBuilder builder = session.getCriteriaBuilder();
            // 創建 CriteriaQuery
            CriteriaQuery<MemberVO> criteriaQuery = builder.createQuery(MemberVO.class);
            // 創建 Root
            Root<MemberVO> root = criteriaQuery.from(MemberVO.class);

            List<Predicate> predicateList = new ArrayList<>();

            // 遍歷查詢條件
            Set<String> keys = map.keySet();
            int count = 0;
            for (String key : keys) {
                String value = map.get(key)[0];
                if (value != null && !value.trim().isEmpty() && !"action".equals(key)) {
                    count++;
                    predicateList.add(get_aPredicate_For_Member(builder, root, key, value.trim()));
                    System.out.println("有送出查詢資料的欄位數 count = " + count);
                }
            }
            System.out.println("predicateList.size() = " + predicateList.size());
            criteriaQuery.where(predicateList.toArray(new Predicate[0]));
            criteriaQuery.orderBy(builder.asc(root.get("memberID")));
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
