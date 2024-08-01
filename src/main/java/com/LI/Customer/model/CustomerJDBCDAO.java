package com.LI.Customer.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.emp.model.EmpVO;

public class CustomerJDBCDAO implements CustomerDAO_interface {
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/tia102g2?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = "INSERT INTO customer(customer_name,customer_email,customer_phone,customer_subject,customer_message)VALUES(?,?,?,?,?)";
	private static final String UPDATE = "UPDATE customer set customer_name=?, customer_email=?, customer_phone=?, customer_subject=?,customer_message=? where customer_id = ?";
	private static final String DELETE = "DELETE FROM customer where customer_id = ?";
	private static final String GET_ALL_STMT = "SELECT customer_id,customer_name,customer_email,customer_phone,customer_subject,customer_message,customer_time FROM customer order by customer_id asc";
	private static final String GET_ONE_STMT = "SELECT customer_id,customer_name,customer_email,customer_phone,customer_subject,customer_message,customer_time FROM customer where customer_id = ?";

	@Override
	public void insert(CustomerVO customerVO) {

		// 宣告
		Connection con = null; // ：連線的變數
		PreparedStatement pstmt = null; // ：預先準備的語法

		try {

			Class.forName(driver); // 載入驅動
			con = DriverManager.getConnection(url, userid, passwd); // 建立連線
			pstmt = con.prepareStatement(INSERT_STMT); // 送出sql指令

			pstmt.setString(1, customerVO.getCustomer_name());
			pstmt.setString(2, customerVO.getCustomer_email());
			pstmt.setString(3, customerVO.getCustomer_phone());
			pstmt.setString(4, customerVO.getCustomer_subject());
			pstmt.setString(5, customerVO.getCustomer_message());
//			pstmt.setTimestamp(6, customerVO.getCustomer_time());

			pstmt.executeUpdate(); // 執行更新
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void update(CustomerVO customerVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, customerVO.getCustomer_name());
			pstmt.setString(2, customerVO.getCustomer_email());
			pstmt.setString(3, customerVO.getCustomer_phone());
			pstmt.setString(4, customerVO.getCustomer_subject());
			pstmt.setString(5, customerVO.getCustomer_message());
//			pstmt.setTimestamp(6, customerVO.getCustomer_time());
			pstmt.setInt(6, customerVO.getCustomer_id());

			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer customer_id) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, customer_id);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public CustomerVO findByPrimaryKey(Integer customer_id) {

		CustomerVO customerVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, customer_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// customerVO 也稱為 Domain objects
				customerVO = new CustomerVO();
				customerVO.setCustomer_id(rs.getInt("customer_id"));
				customerVO.setCustomer_name(rs.getString("customer_name"));
				customerVO.setCustomer_email(rs.getString("customer_email"));
				customerVO.setCustomer_phone(rs.getString("customer_phone"));
				customerVO.setCustomer_subject(rs.getString("customer_subject"));
				customerVO.setCustomer_message(rs.getString("customer_message"));
				customerVO.setCustomer_time(rs.getTimestamp("customer_time"));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return customerVO;
	}

	@Override
	public List<CustomerVO> getAll() {
		List<CustomerVO> list = new ArrayList<CustomerVO>();
		CustomerVO customerVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// customerVO 也稱為 Domain objects
				customerVO = new CustomerVO();
				customerVO.setCustomer_id(rs.getInt("customer_id"));
				customerVO.setCustomer_name(rs.getString("customer_name"));
				customerVO.setCustomer_email(rs.getString("customer_email"));
				customerVO.setCustomer_phone(rs.getString("customer_phone"));
				customerVO.setCustomer_subject(rs.getString("customer_subject"));
				customerVO.setCustomer_message(rs.getString("customer_message"));
				customerVO.setCustomer_time(rs.getTimestamp("customer_time"));
				list.add(customerVO); // Store the row in the list
			}

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {

		CustomerJDBCDAO dao = new CustomerJDBCDAO();

		// 新增
		CustomerVO customerVO = new CustomerVO();
		customerVO.setCustomer_name("小菜");
		customerVO.setCustomer_email("lunch@yahoo.com");
		customerVO.setCustomer_phone("0987654321");
		customerVO.setCustomer_subject("請問");
		customerVO.setCustomer_message("飲料裡有菜瓜布");
//		customerVO.setCustomer_time(new Timestamp(System.currentTimeMillis()));
		dao.insert(customerVO);

		// 修改
//		CustomerVO customerVO1 = new CustomerVO();
//		customerVO1.setCustomer_name("小菜");
//		customerVO1.setCustomer_email("lunch@yahoo.com");
//		customerVO1.setCustomer_phone("0987654321");
//		customerVO1.setCustomer_subject("請問");
//		customerVO1.setCustomer_message("飲料沒有料");
//		customerVO1.setCustomer_time(new Timestamp(System.currentTimeMillis()));
//		
//		customerVO1.setCustomer_id(3);
//		dao.update(customerVO1);

		// 刪除
//		dao.delete("小菜");

//		// 查詢
//		CustomerVO customerVO2 = dao.findByPrimaryKey(8);
//		System.out.print(customerVO2.getCustomer_id() + ",");
//		System.out.print(customerVO2.getCustomer_name() + ",");
//		System.out.print(customerVO2.getCustomer_email() + ",");
//		System.out.print(customerVO2.getCustomer_phone() + ",");
//		System.out.print(customerVO2.getCustomer_subject() + ",");
//		System.out.print(customerVO2.getCustomer_message() + ",");
//		System.out.println(customerVO2.getCustomer_time());
//		System.out.println("---------------------");

		// 查詢
		List<CustomerVO> list = dao.getAll();
		for (CustomerVO aCustomerVO : list) {
			System.out.print(aCustomerVO.getCustomer_id() + ",");
			System.out.print(aCustomerVO.getCustomer_name() + ",");
			System.out.print(aCustomerVO.getCustomer_email() + ",");
			System.out.print(aCustomerVO.getCustomer_phone() + ",");
			System.out.print(aCustomerVO.getCustomer_subject() + ",");
			System.out.print(aCustomerVO.getCustomer_message() + ",");
			System.out.print(aCustomerVO.getCustomer_time());
			System.out.println();

		}

	}
}
