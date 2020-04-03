package sn.microkernel.components;

import java.net.URLEncoder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import sn.microkernel.MKController;
import sn.microkernel.component.model.ProcessModel;

@Service
public class InterProcessCom {


	public String mapNameToAddr(String srv)
	{
		ProcScheduler proc=MKController.kernel.procMgr;
		for(ProcessModel p:proc.processes)
		{
			if(p.server.name.equals(srv))
			{
				
				return "http://127.0.0.1:"+p.port;
			}
		}
		
		return "";
	}
	public String call(String src,String dest,String req)
	{
		
		String url=mapNameToAddr(dest);
		if(url.length()<2)
		{
			return "Server Not found or unavailable";
		}
		
		
		try {
			return sendGet(url+"/?"+URLEncoder.encode(req, "UTF-8").replace("%3D", "="));
		} catch (Exception e) {
			
			e.printStackTrace();
			return "";
		}
	}
	
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

	
	 private String sendGet(String url) throws Exception {

		 System.out.println(">>Call Ext server --> "+url);
	        HttpGet request = new HttpGet(url);

	        try (CloseableHttpResponse response = httpClient.execute(request)) {
 
	            System.out.println(response.getStatusLine().toString());

	            HttpEntity entity = response.getEntity();
	            Header headers = entity.getContentType();
	            System.out.println(headers);

	            if (entity != null) { 
	                String result = EntityUtils.toString(entity);
	                //System.out.println(result);
	                System.out.println("Response OK");
	                return result;
	            }
	            

	        }

            System.out.println("Response Err");
	        return "";

	    }

	 
}
