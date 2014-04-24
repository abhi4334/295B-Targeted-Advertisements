package webservice.call;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("deprecation")
public class WebCall {

	JSONObject obj = new JSONObject();
	
	private String makeCall() throws ClientProtocolException, IOException, JSONException
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://localhost:8080/WebService/rest/hello");
		
		/*ResponseHandler<InventoryInfo> rh = new  ResponseHandler<InventoryInfo>() {
			
			@Override
			public InventoryInfo handleResponse(HttpResponse response)
					throws ClientProtocolException, IOException {
		        StatusLine statusLine = response.getStatusLine();
		        HttpEntity entity = response.getEntity();
		        if (statusLine.getStatusCode() >= 300) {
		            throw new HttpResponseException(
		                    statusLine.getStatusCode(),
		                    statusLine.getReasonPhrase());
		        }
		        if (entity == null) {
		            throw new ClientProtocolException("Response contains no content");
		        }
		        Gson gson = new GsonBuilder().create();
		        ContentType contentType = ContentType.getOrDefault(entity);
		        Charset charset = contentType.getCharset();
		        Reader reader = new InputStreamReader(entity.getContent(), charset);
		        return gson.fromJson(reader, InventoryInfo.class);
			}
		};*/
		System.out.println("Executing request....");
		CloseableHttpResponse temp = httpclient.execute(httpget);
		HttpEntity entity = temp.getEntity();
		
		if (entity != null) {
	        InputStream instream = entity.getContent();
	            // do something useful
	        	System.out.println("instream "+instream.toString());
	        	System.out.println("read: "+instream.read());
	        
	    }
		String abc = EntityUtils.toString(entity);
		//obj = (JSONObject) httpclient.execute(httpget);
		//InventoryInfo inventoryInfo = (InventoryInfo) obj.get("a");
		//InventoryInfo inventoryInfo = (InventoryInfo) httpclient.execute(httpget);
		return abc;
	}
	
	public static void main(String args[])
	{
		try {
		WebCall webcall = new WebCall();
		String invInfo = webcall.makeCall();
		//System.out.println("hello "+invInfo.getId());
		System.out.println("abc : "+invInfo);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
