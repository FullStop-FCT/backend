package pt.unl.fct.di.apdc.helpinhand.filters;

import java.io.IOException;
import java.util.logging.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.github.cdimascio.dotenv.Dotenv;
import pt.unl.fct.di.apdc.helpinhand.api.Authorize;


@Provider
@Authorize
public class AdditionalRequestHeadersFilter implements ContainerRequestFilter {

	public AdditionalRequestHeadersFilter() {}
	
	private static final Logger LOG = Logger.getLogger(AdditionalRequestHeadersFilter.class.getName());
	private static final String SECRET="f7b038a6-f515-4516-93f0-ffa59c7fd00e";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
//		LOG.warning("we're in request headers filter now");
		
		
		requestContext.getHeaders().add("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, X-Requested-With, Authorization");
		requestContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		requestContext.getHeaders().add("Access-Control-Allow-Methods", "HEAD,GET,PUT,POST,DELETE,OPTIONS,PATCH");
		
		String authHeaderVal = requestContext.getHeaderString("Authorization");
		

		
//		LOG.warning("are we inside here");
		
		
        //consume JWT i.e. execute signature validation
        if(authHeaderVal.startsWith("Bearer")){
        	try {
//        		LOG.warning("val:"+ authHeaderVal);
        		String token = authHeaderVal;
        		DecodedJWT jwtDecoded = JWT.decode(token.split(" ")[1]);
//        		LOG.warning("decoded: "+ jwtDecoded.toString());
        		// validate(authHeaderVal.split(" ")[1]);
        		
        		String issuer = jwtDecoded.getIssuer();
//        		LOG.warning("user: " + issuer);
        		
//        		Dotenv dotenv = Dotenv.load();
//
//				String secret = dotenv.get("SECRET");
        		
        		Algorithm algorithm = Algorithm.HMAC512(SECRET);
        		JWTVerifier verifier = JWT.require(algorithm)
        				.withIssuer(issuer)
        				.build();
        		DecodedJWT jwt = verifier.verify(token.split(" ")[1]);
        		
//        		System.out.println("yeap we got here finally");
        	} catch (Exception ex) {
        		LOG.warning(ex.getMessage());
        		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        	}
        }else{
        		requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
	}

}
