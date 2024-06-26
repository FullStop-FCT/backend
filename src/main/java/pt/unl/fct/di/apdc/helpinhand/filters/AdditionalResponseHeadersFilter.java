package pt.unl.fct.di.apdc.helpinhand.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class AdditionalResponseHeadersFilter implements ContainerResponseFilter {
	
	public AdditionalResponseHeadersFilter() {}
	private static final Logger LOG = Logger.getLogger(AdditionalResponseHeadersFilter.class.getName());
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
				responseContext.getHeaders().add("Access-Control-Allow-Methods", "HEAD,GET,PUT,POST,DELETE,OPTIONS,PATCH");
				responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
				responseContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, X-Requested-With, Authorization");
				requestContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, X-Requested-With, Authorization");
				requestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
				requestContext.getHeaders().add("Access-Control-Allow-Methods", "PATCH");
				
				
//				LOG.warning("we're in additional response headers");

	}
	
}