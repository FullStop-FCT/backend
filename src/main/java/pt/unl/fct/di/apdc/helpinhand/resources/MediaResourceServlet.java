package pt.unl.fct.di.apdc.helpinhand.resources;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.HttpMethod;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.SignUrlOption;
import com.google.cloud.storage.StorageOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;




public class MediaResourceServlet extends HttpServlet {
	
	
	 
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Path objectPath = Paths.get(req.getPathInfo());
		
		if(objectPath.getNameCount()!=2) {
			throw new IllegalArgumentException("The URL is not formed as expected. " +
					"Excpectin /gcs/<bucket>/<objet>");
		}
		
		String bucketName = objectPath.getName(0).toString();
		String srcFileName = objectPath.getName(1).toString();
		
		//String filePath = "\\"+"gcs"+"\\"+bucketName+"\\"+srcFileName;
		
	    Storage storage = StorageOptions.getDefaultInstance().getService();
	    BlobId blobId = BlobId.of(bucketName, srcFileName);
	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(req.getContentType()).build();
//	    storage.create(blobInfo, Files.readAllBytes(Paths.get(objectPath.toString())));
		Blob blob = storage.create(blobInfo, req.getInputStream());


	    
		
	}

	
//	@Override
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//            // Bucket parameters 
////            String bucketName = "MY-BUCKET-NAME";
//			String bucketName = "helpin-hand.appspot.com";
//            String blobName = "MY-BLOB-NAME";
//            String keyPath = "/PATH-TO-SERVICE-ACCOUNT-KEY/key.json";
//
//
//            BlobId blobId = BlobId.of(bucketName, blobName);
//            Storage storage = StorageOptions.getDefaultInstance().getService();
//
//            // Create signed URL with SignUrlOptions
//            URL signedUrl = storage.signUrl(BlobInfo.newBuilder(bucketName, blobName).build(), 14, TimeUnit.DAYS,
//                                            SignUrlOption.signWith(ServiceAccountCredentials.fromStream(new FileInputStream(keyPath))),
//                                            SignUrlOption.httpMethod(HttpMethod.PUT));
//
//            // Contents to upload to the Blob
//            String content = "My-File-contents";
//
//            // Build UrlFetch request
//            HTTPRequest upload_request = new HTTPRequest(signedUrl, HTTPMethod.PUT);
//            upload_request.setPayload(content.getBytes(StandardCharsets.UTF_8));
//
//            // Set request to have an uploadType=resumable
//            HTTPHeader set_resumable = new HTTPHeader("uploadType", "resumable");
//            upload_request.setHeader(set_resumable);
//            URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
//
//            // Do an asynchronous call to the signed URL with the contents
//            fetcher.fetchAsync(upload_request);
//
//            // Return response to App Engine handler call
//            response.setContentType("text/plain");
//            response.getWriter().println("Hello Storage");
//    }
	
}
