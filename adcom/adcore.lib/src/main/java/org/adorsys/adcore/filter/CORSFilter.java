/*package org.adorsys.adcore.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
*//**
 * we will disable this class later for production
 * just for allow CORS request and then make easier web frontend developement
 *//*
@WebFilter("/*")
public class CORSFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "OPTIONS,POST,PUT,DELETE,GET,HEAD");
		response.setHeader("Access-Control-Max-Age", "1209600");
		//response.setHeader("content-type", "application/json");
		response.setHeader("Access-Control-Allow-Headers", "*");
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}*/