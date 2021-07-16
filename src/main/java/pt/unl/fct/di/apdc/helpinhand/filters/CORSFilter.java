package pt.unl.fct.di.apdc.helpinhand.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements Filter {
	private static final Logger LOG = Logger.getLogger(CORSFilter.class.getName());
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub~
//		System.out.print("we're here");
		LOG.warning("we're here");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST, PATCH");
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, X-Request-With");

		HttpServletResponse resp = (HttpServletResponse) servletResponse;
		
		 if (request.getMethod().equals("OPTIONS")) {
	            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
	            return;
	        }
		 
		    chain.doFilter(request, servletResponse);
		    
		    
		    
		    
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
