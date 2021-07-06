package pt.unl.fct.di.apdc.helpinhand.resources;



import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.WriteChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.HttpMethod;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.Storage.SignUrlOption;
import com.google.cloud.storage.StorageOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;
import java.util.Collection;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("serial")
@MultipartConfig
@WebServlet(
	    name = "webServlet",
	    urlPatterns = { "/upload"},
	    loadOnStartup = 1)
public class MediaResourceServlet extends HttpServlet {
//	private static final MultipartConfigElement MULTI_PART_CONFIG = new MultipartConfigElement("c:/temp");
	
//	 
//	@Override
//	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
//		
//		Path objectPath = Paths.get(req.getPathInfo());
//		
//		if(objectPath.getNameCount()!=2) {
//			throw new IllegalArgumentException("The URL is not formed as expected. " +
//					"Excpectin /gcs/<bucket>/<objet>");
//		}
//		
//		String bucketName = objectPath.getName(0).toString();
//		String srcFileName = objectPath.getName(1).toString();
//		
//		//String filePath = "\\"+"gcs"+"\\"+bucketName+"\\"+srcFileName;
//		
//	    Storage storage = StorageOptions.getDefaultInstance().getService();
//	    BlobId blobId = BlobId.of(bucketName, srcFileName);
//	    System.out.println(" content" + req.getContentType());
//	    BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(req.getContentType()).build();
//	    
//	    Part part = req.getPart("image");
//	    InputStream is = part.getInputStream();
//
//	    storage.create(blobInfo, is);
////	    Part filePart = req.getPart("HelpinHand.png");
////	    InputStream is = filePart.getInputStream();
////	    storage.create(blobInfo, Files.readAllBytes(Paths.get(objectPath.toString())));
////	    storage.create(blobInfo, is);
////	    Blob blob = storage.create(blobInfo, req.getInputStream());
//
//		
//	}

	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		Path objectPath = Paths.get(req.getPathInfo());   
		
		if(objectPath.getNameCount()!=2) {
			throw new IllegalArgumentException("The URL is not formed as expected. " +
					"Excpectin /gcs/<bucket>/<objet>");
		}
//		String kfPath = "helpin-hand-bcba981f0c85.json";
		
		String kfPath = "helpinhand-318217-9163cf9cdb72.json";
		
		ServiceAccountCredentials credentials = ServiceAccountCredentials.fromStream(new FileInputStream(kfPath));
		
		
//		SignUrlOption.signWith(ServiceAccountCredentials.fromStream(new FileInputStream(kfPath)
		
		String bucketName = objectPath.getName(0).toString();
		String srcFileName = objectPath.getName(1).toString();
//		Storage.SignUrlOption.signWith(ServiceAccountSigner);
//		Storage storage = StorageOptions.getDefaultInstance().getService();
		
		
		
		
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("helpinhand-318217").build().getService();
	    
		BlobId blobId = BlobId.of(bucketName, srcFileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(req.getContentType()).build();
//		URL signedUrl = storage.signUrl(BlobInfo.newBuilder(bucketName,srcFileName).build(), 7, TimeUnit.DAYS);
		
//		URL signedUrl = storage.signUrl(blobInfo, 7, TimeUnit.DAYS, Storage.SignUrlOption.httpMethod(HttpMethod.POST));
		URL signedUrl = storage.signUrl(blobInfo, 7, TimeUnit.DAYS, Storage.SignUrlOption.signWith(ServiceAccountCredentials.fromStream(new FileInputStream(kfPath))));
		
		Part filePart = req.getPart("image");
		InputStream is = filePart.getInputStream();
		
//		System.out.println(req.getInputStream());
		//
		int readBytes = -1;
		int lengthOfBuffer = req.getContentLength();
//		InputStream input = req.getInputStream();
		InputStream input = is;
		byte[] buffer  = new byte[lengthOfBuffer];
		ByteArrayOutputStream output = new ByteArrayOutputStream(lengthOfBuffer);
		while((readBytes = input.read(buffer, 0, lengthOfBuffer)) != -1) {
		  output.write(buffer, 0, readBytes);
		}
		byte[] finalOutput = output.toByteArray();
		

		try(WriteChannel writer = storage.writer(signedUrl)){
//			writer.write(ByteBuffer.wrap(req.ge, 0, 0));
			writer.write(ByteBuffer.wrap(finalOutput,0, finalOutput.length));
		}
		
		
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Storage storage = StorageOptions.getDefaultInstance().getService();
		
		Path objectPath = Paths.get(request.getPathInfo());
		
		if(objectPath.getNameCount()!=2) {
			throw new IllegalArgumentException("The URL is not formed as expected. " +
					"Excpectin /gcs/<bucket>/<objet>");
		}
		
		String bucketName = objectPath.getName(0).toString();
		String srcFileName = objectPath.getName(1).toString();
		
		
		Blob blob = storage.get(BlobId.of(bucketName, srcFileName));
		blob.downloadTo(response.getOutputStream());
		
	}
	
	
//	@Override
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	
//		Path filePath = Paths.get(request.getPathInfo());
//	
//		if(filePath.getNameCount()!=2) {
//			throw new IllegalArgumentException("The URL is not formed as expected. " +
//					"Excpectin /gcs/<bucket>/<objet>");
//		}
//	
//		String bucketName = filePath.getName(0).toString();
//		String objectName = filePath.getName(1).toString();
//
////		Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
//		Storage storage = StorageOptions.getDefaultInstance().getService();
//		BlobId blobId = BlobId.of(bucketName, objectName);
//		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//		System.out.println("this is filePath " + filePath.toString());
//		System.out.println("this is bucket" + filePath.getName(0).toString());
//		System.out.println("this is filePath " + filePath.getFileName().toString());
//		System.out.println("this is link " + request.getServletPath());
//
//		storage.create(blobInfo, Files.readAllBytes(Paths.get(request.getServletPath()+"/"+bucketName+"/"+filePath.getFileName().toString())));
//
//	}
	
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
