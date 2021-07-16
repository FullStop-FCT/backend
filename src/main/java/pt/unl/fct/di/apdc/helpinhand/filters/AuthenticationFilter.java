package pt.unl.fct.di.apdc.helpinhand.filters;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.net.HttpHeaders;

import pt.unl.fct.di.apdc.helpinhand.api.Authorize;
import pt.unl.fct.di.apdc.helpinhand.api.Secured;
import pt.unl.fct.di.apdc.helpinhand.resources.ActivityResource;
import pt.unl.fct.di.apdc.helpinhand.util.Roles;

//@Secured
@Provider
//@Authorize
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

//	private static final String REALM = "example";
//	private static final String AUTHENTICATION_SCHEME = "Bearer";
//	
//	
//	@Override
//	public void filter(ContainerRequestContext requestContext) throws IOException {
//		// TODO Auto-generated method stub
//		
//		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//		
//		
//		if(!isTokenBasedAuthentication(authorizationHeader)) {
//			abortWithUnauthorized(requestContext);
//			return;
//		}
//		
//		String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
//		
//		try {
//			validateToken(token);
//		}catch(Exception e) {
//			abortWithUnauthorized(requestContext);
//		}
//
//	}
//	
//	
//	private boolean isTokenBasedAuthentication(String authorizationHeader) {
//	    // Check if the Authorization header is valid
//        // It must not be null and must be prefixed with "Bearer" plus a whitespace
//        // The authentication scheme comparison must be case-insensitive
//        return authorizationHeader != null && authorizationHeader.toLowerCase()
//                    .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
//    }
//
//    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
//
//        // Abort the filter chain with a 401 status code response
//        // The WWW-Authenticate header is sent along with the response
//        requestContext.abortWith(
//                Response.status(Response.Status.UNAUTHORIZED)
//                        .header(HttpHeaders.WWW_AUTHENTICATE, 
//                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
//                        .build());
//    }
//
//    private void validateToken(String token) throws Exception {
//        // Check if the token was issued by the server and if it's not expired
//        // Throw an Exception if the token is invalid
//    }
//
//    @Secured
//    @Provider
//    @Priority(Priorities.AUTHORIZATION)
//    public class AuthorizationFilter implements ContainerRequestFilter {
//
//        @Context
//        private ResourceInfo resourceInfo;
//
//        @Override
//        public void filter(ContainerRequestContext requestContext) throws IOException {
//
//            // Get the resource class which matches with the requested URL
//            // Extract the roles declared by it
//            Class<?> resourceClass = resourceInfo.getResourceClass();
//            List<Roles> classRoles = extractRoles(resourceClass);
//
//            // Get the resource method which matches with the requested URL
//            // Extract the roles declared by it
//            Method resourceMethod = resourceInfo.getResourceMethod();
//            List<Roles> methodRoles = extractRoles(resourceMethod);
//
//            try {
//
//                // Check if the user is allowed to execute the method
//                // The method annotations override the class annotations
//                if (methodRoles.isEmpty()) {
//                    checkPermissions(classRoles);
//                } else {
//                    checkPermissions(methodRoles);
//                }
//
//            } catch (Exception e) {
//                requestContext.abortWith(
//                    Response.status(Response.Status.FORBIDDEN).build());
//            }
//        }
//
//        // Extract the roles from the annotated element
//        private List<Roles> extractRoles(AnnotatedElement annotatedElement) {
//            if (annotatedElement == null) {
//                return new ArrayList<Roles>();
//            } else {
//                Secured secured = annotatedElement.getAnnotation(Secured.class);
//                if (secured == null) {
//                    return new ArrayList<Roles>();
//                } else {
//                    Roles[] allowedRoles = secured.value();
//                    return Arrays.asList(allowedRoles);
//                }
//            }
//        }
//
//        private void checkPermissions(List<Roles> allowedRoles) throws Exception {
//        	
//        	
//            // Check if the user contains one of the allowed roles
//            // Throw an Exception if the user has not permission to execute the method
//        }
//    }
	
	
	
//	private final secre
	private static final Logger LOG = Logger.getLogger(AuthenticationFilter.class.getName());
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException{
		String authHeaderVal = requestContext.getHeaderString("Authorization");
		
		
		LOG.warning("are we inside here");
		
		
        //consume JWT i.e. execute signature validation
        if(authHeaderVal.startsWith("Bearer")){
        try {
           // validate(authHeaderVal.split(" ")[1]);
        	System.out.println("yeap we got here finally");
        } catch (Exception ex) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
        }else{
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
//		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
//		
//		try {
//			String token = authorizationHeader.substring("Bearer".length()).trim();
////			String encoded = Base64.getEncoder().encodeToString(token.getTokenID().getBytes());
//			
//			byte[] decoded = Base64.getDecoder().decode("NzU4ZWIxNzUtY2ViNi00MjQ5LTlhNGQtNTc5YzdjYTcxZmEy");
//			
//			Algorithm algorithm = Algorithm.HMAC512(decoded);
//			
//			DecodedJWT jwt = JWT.decode(token);
//			
//			JWTVerifier verifier = JWT.require(algorithm)
//					.withIssuer(jwt.getIssuer())
//					.withClaim("role", jwt.getClaim("role").toString())
//					.build();
//			
//			DecodedJWT jwt2 = verifier.verify(token);
//		
//			
//		}catch(Exception e) {
//			requestContext
//			.abortWith(Response.status(Response.Status.UNAUTHORIZED)
//					.build());
//		}
		
	}
	
	
//	private void validate(String a) {
//		RsaJsonWebKey rsaJsonWebKey = getCachedRSAKey(); //should be the same as the one used to build the JWT previously
//
//		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
//		                         .setRequireSubject() // the JWT must have a subject claim
//		                          .setVerificationKey(rsaJsonWebKey.getKey()) // verify the signature with the public key
//		                          .build(); // create the JwtConsumer instance
//
//		JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);
//	}
    
}
