package pt.unl.fct.di.apdc.helpinhand.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.api.client.json.Json;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import pt.unl.fct.di.apdc.helpinhand.api.Authorize;
import pt.unl.fct.di.apdc.helpinhand.data.Database;

import java.net.URI;

//STRIPE NECESSARY IMPORTS --------

import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import io.github.cdimascio.dotenv.Dotenv;



@Path("/")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PaymentResource {

	
	
	private static final Logger LOG = Logger.getLogger(PaymentResource.class.getName());
	
	private static final String STRIPE_API_KEY="sk_test_51JCXBCJs2li8rk8icagz797P5REq00LTg6EJj1SUPzcvP0VxU69o0aTpHT0J7JGzJL8V1hCjUnnVNgP0cu5ya2N9006Tni60dc";
	private static final String SECRET="f7b038a6-f515-4516-93f0-ffa59c7fd00e";
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	Database database = new Database();
	
	private final Gson g = new Gson();
	
	private Transaction txn = datastore.newTransaction();
	private KeyFactory factory = datastore.newKeyFactory();
	
	private static Gson gson = new Gson();
	
	
	public PaymentResource() {		
	}
	
//	static class CreatePayment{
//		@SerializedName("items")
//		Object[] items;
//		
//		
//		public Object[] getItems() {
//			return items;
//		}
//	}
//	
//	static class CreatePaymentResponse {
//		private String clientSecret;
//		
//		public CreatePaymentResponse(String clientSecret) {
//			this.clientSecret = clientSecret;
//		}
//	}
//	
//	static int calculateOrderAmount(Object[] items) {
//		// Replace this constant with a calculation of the order's amount
//	    // Calculate the order total on the server to prevent
//	    // users from directly manipulating the amount on the client
//	    return 1400;
//	}
//	
//	private String getUsername(HttpHeaders header) {
//		String authHeaderVal = header.getHeaderString("Authorization");
//		DecodedJWT jwtDecoded = JWT.decode(authHeaderVal.split(" ")[1]);
//		String username = jwtDecoded.getIssuer();
//		return username;
//	}
	
	
//	@Authorize
//	@POST
//	@Path("/create-payment-intent")
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response doPay(String username, String request) {
////		String username = getUsername(header);
//		
//		LOG.warning("Attemp to make a payment for user "+ username);
//		
//		Transaction txn = datastore.newTransaction();
//		
//		Key userKey = database.getUserKey(username);
//		
//		try {
//				Entity userEntity = txn.get(userKey);
//				
//				if(userEntity == null) {
//					txn.rollback();
//					LOG.warning("No such user");
//					return Response.status(Status.FORBIDDEN).build();
//				}
//				
//				Stripe.apiKey = "sk_test_51JCXBCJs2li8rk8icagz797P5REq00LTg6EJj1SUPzcvP0VxU69o0aTpHT0J7JGzJL8V1hCjUnnVNgP0cu5ya2N9006Tni60dc";
//				
//			
//				CreatePayment postBody = gson.fromJson(request, CreatePayment.class);
//				
//				PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
//						.setCurrency("eur")
//						.setAmount(new Long(calculateOrderAmount(postBody.getItems())))
//						.build();
//				
//				PaymentIntent intent = PaymentIntent.create(createParams);
//				
//				CreatePaymentResponse paymentResponse = new CreatePaymentResponse(intent.getClientSecret());
//				
//				
//				txn.commit();
//				return Response.ok(gson.toJson(paymentResponse)).build();
//				
//				
//				
////				return Response.ok(" {} ").build(); 
//				
////				return Response.temporaryRedirect(new URI(session.getUrl())).build();
//				
//				
//		}catch(Exception e) {
//			txn.rollback();
//			LOG.warning("exception "+ e.toString());
//			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
//		}finally {
//			if(txn.isActive()) {
//				txn.rollback();
//				LOG.warning("entered finally");
//				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
//			}
//		}
//		
//	}
//	
	
	
	
	@Authorize
	@POST
	@Path("/create-checkout-session")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doPay(String username) {
//		String username = getUsername(header);
		LOG.warning("Attemp to make a payment for user ");
		
		Transaction txn = datastore.newTransaction();
		
//		String id = UUID.randomUUID().toString();
//		String token = header.getHeaderString("Authorization").split(" ")[1];
		
		
		Date now = new Date(System.currentTimeMillis());
		Date later = new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(30));
		
		
		Key userKey = factory.setKind("User").newKey(username);
		Entity userEntity = txn.get(userKey);
		
		String name = userEntity.getString("user_name");
		
		Algorithm algorithm = Algorithm.HMAC512(SECRET);
		String jwtToken = JWT.create()
				.withClaim("id", "4cbb7c32-fd2d-46ce-8d3b-d85a9d50bede")
				.withClaim("name", name)
				.withIssuedAt(now)
				.withExpiresAt(later)
				.withIssuer(username)
				.sign(algorithm);
		
//		Key userKey = database.getUserKey(username);
		
		try {
//				Entity userEntity = txn.get(userKey);
//				
//				if(userEntity == null) {
//					txn.rollback();
//					LOG.warning("No such user");
//					return Response.status(Status.FORBIDDEN).build();
//				}
//				
//				Dotenv dotenv = Dotenv.load();
//
//				String STRIPE_API_KEY = dotenv.get("STRIPE_API_KEY");
				Stripe.apiKey = STRIPE_API_KEY;
//				Stripe.apiKey = "sk_test_51JCXBCJs2li8rk8icagz797P5REq00LTg6EJj1SUPzcvP0VxU69o0aTpHT0J7JGzJL8V1hCjUnnVNgP0cu5ya2N9006Tni60dc";
				
				SessionCreateParams params = 
						SessionCreateParams.builder().addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
						.setMode(SessionCreateParams.Mode.PAYMENT)
//						.setSuccessUrl("http://localhost:3000/rest/create-checkout-session" + "?success=true")
//						.setCancelUrl("http://localhost:3000/rest/create-checkout-session" + "?canceled=true")
						.setSuccessUrl("http://localhost:3000/certificate/"+jwtToken)
						.setCancelUrl("http://localhost:3000/"+username)
						.addLineItem(
								SessionCreateParams.LineItem.builder()
								.setQuantity(1L)
								.setPriceData(
										SessionCreateParams.LineItem.PriceData.builder()
										.setCurrency("eur")
										.setUnitAmount(299L)
										.setProductData(
												SessionCreateParams.LineItem.PriceData.ProductData.builder()
												.setName("Certificado")
												.build())
										.build())
								.build())
						.build();
				
				Session session = Session.create(params);
				
				
				
				
				txn.commit();
//				return Response.ok(" {} ").build(); 
//				LOG.warning(session.getUrl());
//				LOG.warning("uri: " + new URI(session.getUrl()));
				return Response.status(Status.OK).entity(g.toJson(session.getUrl())).build();
//				return Response.seeOther(new URI(session.getUrl())).build();
//				return Response.temporaryRedirect(new URI(session.getUrl())).build();
				
				
		}catch(Exception e) {
			txn.rollback();
			LOG.warning("exception "+ e.toString());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
		}finally {
			if(txn.isActive()) {
				txn.rollback();
				LOG.warning("entered finally");
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}
		
	}											
	
}
