package com.utils.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;

import hibernate.util.HibernateUtil;

@Slf4j
@WebFilter(urlPatterns = { "/*" })
public class OpenSessionInViewFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		try {
			log.info("filter open transaction");
			factory.getCurrentSession().beginTransaction();
			log.info("open transaction");
			chain.doFilter(req, res);
			factory.getCurrentSession().getTransaction().commit();
			log.info("commit transaction");
		} catch (Exception e) {
			factory.getCurrentSession().getTransaction().rollback();
			log.error("filter rollback transaction", e);
			throw new ServletException(e); // 直接拋出，不要重複 chain.doFilter
		}
	}

}