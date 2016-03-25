import java.io.IOException;
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;  
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements; 

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class ParseDemo{
	
	public static final String URL="https://www.tollbrothers.com";
	 public static final String path="C://kamal/demo.csv";
	
	Pattern letters=Pattern.compile("\\D+");
	Pattern number=Pattern.compile("\\d+");
	
	public List<String> display(){
		
		List<String> innerlist=new ArrayList<String>();
		List<String> mainpages=new ArrayList<String>();
		int count=0;
		try{
			String innerurl=null,childlink=null,link=null;
			Document doc=Jsoup.connect(URL).get();
			
			Elements middlelink=doc.select("div.quad-columns-divider>ul");
			//
			for(Element element:middlelink){
				
				Elements ele=element.select("li>a");
				//System.out.println(ele);
				for(Element e:ele){
					
					//System.out.println("link:"+e.attr("href"));
					innerurl=URL.concat(e.attr("href"));
					//System.out.println(innerurl);
					innerlist.add(innerurl);
					//break;
				}
				//break;
			}
			
			Iterator it=innerlist.iterator();
			while(it.hasNext()){
			
				childlink=(String)it.next();
				Document doc1=Jsoup.connect(childlink).get();
				
				Elements lastlink=doc1.select("div.search-community-container>a");
				
				for(Element e:lastlink){
				
					link=childlink.concat(e.attr("href"));
					link=e.attr("href");
					System.out.println(link);
					mainpages.add(link);
					count++;
					//break;
				}
			}
				System.out.println("Total:"+count);
			
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		return mainpages;
	}
	
	public List<Document> fetchPages(List<String> mainpages){
	
		List<Document> doc=new ArrayList<Document>();
		try{
		
			for(String pages:mainpages){
				
				doc.add(Jsoup.connect(pages).timeout(60*1000).get());
				
			}
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		return doc;
	}
	
	
	public List<Element> fetchStoreNodes(List<Document> docs) {
        List<Element> elements = new ArrayList<Element>();

        
        for (Document doc : docs) {
            if (doc != null) {
                Elements element = doc.select("div.activeTab>div>aside");
				//System.out.println(element);
                if (element != null) {
                    for (Element storeEle : element) {
                        
                        if(storeEle.text().toLowerCase().contains("coming soon!")) {
                            continue; 
                        }
                        elements.add(storeEle);
                    }
                }
            }
			
        }
        
        System.out.println("Total stores: "+elements.size());
        return elements;
    }
	
	public String parseAddress(Element element) {
        String txt = element.select("ul>li").get(2).text();
		int index=txt.indexOf(",");
		if(index==-1){
			Element ele=element.select("ul>li").get(1);
			txt=ele.ownText();
			txt=txt.split(",")[0];
		}
		else{
			
			txt=txt.split(":")[1];
			txt=txt.split(",")[0];
		}
		
		System.out.println("address:"+txt);
		
        //txt = txt.substring(0, txt.indexOf("<br>"));
        return txt;
    }
	
	public String parseCity(Element element) {
        String txt = element.select("ul>li").get(2).text();
		
		if(txt!=null){
			txt=txt.split(",")[0];
			int index=txt.lastIndexOf(" ");
			txt=txt.substring(index+1);
			
			System.out.println("city:"+txt);
		}
		else{
			
			txt=" ";
		}
		return txt;
    }
	
	public String parseHours(Element element){
	
		String txt=null;
		
		Element ele=element.select("ul>li").get(1);
		txt=ele.ownText();
		int index=txt.indexOf(",");
		if(index!=-1){
			
			txt=" ";
		}
		System.out.println("hours:"+txt);
		return txt;
	}
	
	public String parseCommunity(Element element){
	
		Element ele=element.select("ul>li").get(5);
		String txt=ele.ownText();
		System.out.println("community:"+txt);
		int index=txt.indexOf(",");
		if(index!=-1){
		
			txt=" ";
		}
		if(txt.isEmpty()){
			
			txt=" ";
		}
		return txt;
	}
	
	public String parseState(Element element) {
        String txt = element.select("ul>li").get(2).text();
		if(txt!=null){
			int index=txt.indexOf(",");
			if(index!=-1){
				
				txt=txt.split(",")[1];
			}
			else{
				 // Element ele=element.select("ul>li").get(1);
				 // txt=ele.ownText();
				
				// int ind=txt.indexOf(",");
				// if(ind!=-1){
					
					// txt=txt.split(",")[1];
					
				// }
				// else{
					
					// txt=" ";
				// }
				txt=" ";
			}
			Matcher m=letters.matcher(txt);
			while(m.find()){
				
				txt=m.group();
			}
		 
		}
		else{
			
			txt=" ";
		}
		 System.out.println("state:"+txt);
		return txt;
    }
	
	public String parseZip(Element element){
	
		String txt = element.select("ul>li").get(2).text();
		if(txt!=null){
				int index=txt.indexOf(",");
				if(index!=-1){
					txt=txt.split(",")[1];
				}
				else{
					// Element ele=element.select("ul>li").get(1);
					// txt=ele.ownText();
					// int ind=txt.indexOf(",");
					// if(ind!=-1){
						
						// txt=txt.split(",")[1];
						
					// }
					// else{
						
						// txt=" ";
					// }
					txt=" ";
				}
			Matcher m=number.matcher(txt);
			while(m.find()){
			
				txt=m.group();
			}
		}
		else{
			
			txt=" ";
		}
		System.out.println("zip:"+txt);
		return txt;
	}
	
	public String parsePhone(Element element){
	
		String txt=element.select("p>a>span").text();
		
		System.out.println("phone is:"+txt);
		
		return txt;
	}
	
	public List<String> displayInformation(List<Element> elements){
		
		List<String> readerdata=new ArrayList<String>();
		for(Element e:elements){
		
			 StringBuilder line = new StringBuilder();
			 
			// this.parseAddress(e);
			// this.parseCity(e);
			// this.parseState(e);
			// this.parseZip(e);
			// this.parseHours(e);
			// this.parseCommunity(e);
			// this.parsePhone(e);
			line.append(this.parseCommunity(e));
			line.append(",");
			line.append(this.parseAddress(e));
			line.append(",");
			line.append(this.parseCity(e));
			line.append(",");
			line.append(this.parseState(e));
			line.append(",");
			line.append(this.parseZip(e));
			line.append(",");
			line.append(this.parsePhone(e));
			line.append(",");
			line.append(this.parseHours(e));
			line.append("\r\n");
			System.out.println("************");
			readerdata.add(line.toString());
		}
		//System.out.println(readerdata);
		return readerdata;
	}
	
	public static void main(String args[]){
	
		ParseDemo demo=new ParseDemo();
		List<String> ss=demo.display();
		//demo.displayInformation(demo.fetchPages(demo.fetchStoreNodes(ss)));
		List<Document> docm=demo.fetchPages(ss);
		List<String> out=demo.displayInformation(demo.fetchStoreNodes(docm));
		WriteCSV csv=new WriteCSV();
		csv.writeOperation(out,path);
		//List<String> ss=demo.fetchMainPages();
		//System.out.println(demo.fetchPages(ss));
		//List<Element> elements=demo.fetchStoreNodes(demo.fetchPages(ss));
		//demo.displayInfomation(elements);
	}	
}
		
		
		
		
		
