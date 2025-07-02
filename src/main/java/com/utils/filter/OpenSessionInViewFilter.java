package com.utils.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

import org.hibernate.SessionFactory;

import hibernate.util.HibernateUtil;


@WebFilter(urlPatterns = { "/*" })
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