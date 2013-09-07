package iot.jackson.analysehtml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AnalyseHtml {
	private String html;
	
	public static void main(String[]args) {
		AnalyseHtml analyseHtml = new AnalyseHtml();
		analyseHtml.getHtml("http://www.2345.com/");
		analyseHtml.saveFile("word.html");
		analyseHtml.readFile("word.html");
		analyseHtml.getLinks();
	}
	

	public String getHtml(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
			//读入内容流，并以字符串形式返回，这里指定网页编码是UTF-8
				html = EntityUtils.toString(entity,"utf-8");
				//System.out.println(html);
				EntityUtils.consume(entity);//关闭内容流
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
	
	public void saveFile(String fileName) {
		File file = new File(fileName);
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			bufferedWriter.write(html);
			bufferedWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void readFile(String fileName) {
		File file = new File(fileName);
		String string;
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while((string = bufferedReader.readLine()) != null) {
					System.out.println(string);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void getLinks() {
		Document doc = Jsoup.parse(html);
		Elements links = doc.select("a[href]");
	    Elements media = doc.select("[src]");
	    Elements imports = doc.select("link[href]");
	    System.out.println(media.size());
	    for (Element src : media) {
            if (src.tagName().equals("img"))
                System.out.println( src.tagName()+" "+src.attr("abs:src")+src.attr("width")+
                		src.attr("height")+
                       (src.attr("alt")+20));
            else
                System.out.println(src.tagName()+" "+src.attr("abs:src"));
        }
	    
	    System.out.println(imports.size());
	    
        for (Element link : imports) {
            System.out.println(link.tagName()+" "+link.attr("abs:href")+link.attr("rel"));
            
        }
        
	    System.out.println(links.size());
	    for (Element link : links) {
            System.out.println(link.attr("abs:href")+link.text()+35);
        }
    }
}
