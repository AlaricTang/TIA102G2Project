package hibernate.util.compositeQuery;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.ken.drink.model.DrinkVO;

public class CompositeQuery_Drink {
	
	public static Predicate get_aPredicate_For_AnyDB(CriteriaBuilder builder, Root<DrinkVO> root, String columnName, String value) {
		
		Predicate predicate = null;
		
		if( "drinkID".equals(columnName) || 
		    "drinkPrice".equals(columnName) ||
		    "editedByMemberID".equals(columnName) ||
			"createdByMemberID".equals(columnName)||
			"editedByMemberID".equals(columnName) ||
			"createdByMemberID".equals(columnName))
			predicate = builder.equal(root.get(columnName), Integer.valueOf(value));
		else if ("drinkStatus".equals(columnName))
			predicate = builder.equal(root.get(columnName), Byte.valueOf(value));
		else if ( "drinkUpdateDate".equals(columnName) ||
				  "drinkCreateDate".equals(columnName))
			predicate = builder.equal(root.get(columnName), java.sql.Date.valueOf(value));
		else if ( "drinkName".equals(columnName) ||
				  "drinkDes".equals(columnName) ||
				  "drinkTag".equals(columnName)) {
			predicate = builder.equal(root.get(columnName), "%" + value + "%");
		}
//		else if ("editedByMemberID".equals(columnName)|| 
//		"createdByMemberID".equals(columnName)){
//	
//}
		return predicate;
	}
	
//	public static List<DrinkVO> getAllC(Map<String, String[]> map, Session session){
//		
//	}
}
