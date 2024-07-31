package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.hibernate.SessionFactory;

import hibernate.util.HibernateUtil;


@WebFilter(urlPatterns = { "/com/*" })
public class OpenSessionInViewFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			System.out.println("filter open transaction");
			factory.getCurrentSession().beginTransaction();
			System.out.println("===== begin transaction =====");
			chain.doFilter(req, res);
			factory.getCurrentSession().getTransaction().commit();
			System.out.println("=========== commit ==========");
		} catch (Exception e) {
			factory.getCurrentSession().getTransaction().rollback();
			System.out.println("========== rollback =========");
			System.out.println("filter 錯誤:");
			e.printStackTrace();
			System.out.println("filter 錯誤:");
			chain.doFilter(req, res);
		}
	}

}