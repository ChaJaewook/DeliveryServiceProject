package jwc.com.control;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jwc.com.httphandler.HttpHandler;
import jwc.com.model.DeliveryServiceModel;
@RestController
@RequestMapping("api")

public class DeliveryServiceControl {
	
	HttpHandler _httpHandler=new HttpHandler();
	Document doc=null;
	@GetMapping("/Condition")
	public String getCondition()
	{
		return "Good Condition";
	}
	
	@PostMapping(value="/SearchDelivery")
	public String SearchDelivery(@RequestBody DeliveryServiceModel serviceModel) throws IOException
	{
		String serviceName=serviceModel.getServiceName();
		String invoiceNumber=serviceModel.getInvoiceNumber();
		
		switch(serviceName)
		{
			case "rozen":
				SearchRozen(invoiceNumber);
				break;
			case "hanjin":
				SearchHanjin(invoiceNumber);
				break;
			case "CJ":
				SearchCJ(invoiceNumber);
				break;
			case "Lotte":
				SearchLotte(invoiceNumber);
				break;
			default:
				break;
		}
		return "";
	}
	
	public void SearchRozen(String invoiceNumber) throws IOException 
	{
		//invoiceNumber : 운송장 번호
		String baseURL="https://www.ilogen.com";
		String path="";
		String reData="";
		String postData="";
		
		//Condition Check
		//Set Header
		path="/web/personal/tkSearch";
		_httpHandler.AddRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		_httpHandler.AddRequestHeader("Accept-Encoding","gzip, deflate, br");
		_httpHandler.AddRequestHeader("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
		_httpHandler.AddRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
		//%EB%A1%9C%EC%A0%A0%ED%83%9D%EB%B0%B0 => URL Decode => 로젠택배
		_httpHandler.AddRequestHeader("Referer","https://search.naver.com/search.naver?where=nexearch&sm=tab_jum&query=%EB%A1%9C%EC%A0%A0%ED%83%9D%EB%B0%B0");
		_httpHandler.AddRequestHeader("Connection","keep-alive");
		_httpHandler.AddRequestHeader("Host","www.ilogen.com");
		
		_httpHandler.Send(baseURL+path);
		reData=_httpHandler.getResponseText();
		
		/*//Send Data
		_httpHandler.AddRequestHeader("Referer", "https://www.ilogen.com/web/personal/tkSearch?t=1");
		path="/web/personal/tkSearch";
		_httpHandler.Send(baseURL+path);
		reData=_httpHandler.getResponseText();*/
		
		
		//Send Data
		_httpHandler.AddRequestHeader("Referer", "https://www.ilogen.com/web");
		path="/web/personal/trace/"+invoiceNumber;
		_httpHandler.Send(baseURL+path);
		reData=_httpHandler.getResponseText();
		doc=Jsoup.parse(reData);
		
		Elements data_tkInfo=doc.getElementsByClass("data tkInfo");
		String a=data_tkInfo.attr("th");
	}
	
	public void SearchHanjin(String invoiceNumber) throws IOException
	{
		String baseURL="http://www.hanjin.co.kr";
		String path="";
		String reData="";
		String postData="";
		
		//Condition Check
		path="/kor/Main.do";
		_httpHandler.ClearHeader();
		_httpHandler.AddRequestHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
		_httpHandler.AddRequestHeader("Accept-Encoding","gzip, deflate");
		_httpHandler.AddRequestHeader("Accept-Language","ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
		_httpHandler.AddRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
		_httpHandler.AddRequestHeader("Connection","keep-alive");
		_httpHandler.AddRequestHeader("Host","www.hanjin.co.kr");
		
		_httpHandler.Send(baseURL+path);
		reData=_httpHandler.getResponseText();
		
		path=String.format("/kor/CMS/DeliveryMgr/WaybillResult.do?mCode=MN038&wblnum=%s&schLang=KR&wblnumText=", invoiceNumber);
		_httpHandler.Send(baseURL+path);
		reData=_httpHandler.getResponseText();
		System.out.println(reData);
	}
	
	public void SearchCJ(String invoiceNumber)
	{
		String baseURL="https://www.ilogen.com";
		String path="";
		String reData="";
		String postData="";
	}
	
	public void SearchLotte(String invoiceNumber)
	{
		String baseURL="https://www.ilogen.com";
		String path="";
		String reData="";
		String postData="";
	}
	
}