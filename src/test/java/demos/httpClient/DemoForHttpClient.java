package demos.httpClient;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthenticationStrategy;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

import com.google.common.net.HttpHeaders;

public class DemoForHttpClient {
	
	// request url
	// https://api-qa1.everbridge.net/rest/incidentTemplates/888409690210403/884011643704002?credentials=YWFkbWluLTIwMTUwMjEwMTQwMTI1LXFhMTpFV1Fld3EzMjE%3D&api_key=special-key
	public static void main(String[] args) throws ClientProtocolException, IOException {
		String userName = "aadmin-20150210140125-qa1";
		String password = "EWQewq321";
		String requestUrl = "https://api-qa1.everbridge.net/rest/incidentTemplates/888409690210403/884011643704002";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpget = new HttpGet(requestUrl);
            httpget.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + getCredentials(userName, password));

            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = httpClient.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                EntityUtils.consume(response.getEntity());
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }

	}
	
	public static String getCredentials(String userName, String password){
		String credentialString = userName + ":" + password;
		String credentials = Base64.encodeBase64String(credentialString.getBytes());
		return credentials;
	}

}
