

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


public class WriteCSV{
	
	File f;
	BufferedWriter writer=null;
	StringBuilder sb=null;
	public void writeOperation(List<String> inputvalue,String path){
		
		try{
			
			f=new File(path);//Display the contents in output html file
			writer=new BufferedWriter(new FileWriter(f));
			sb=new StringBuilder();
			sb.append("Community"+","+"Address"+","+"City"+","+"State"+","+"ZipCode"+","+"Phone"+","+"Hours");
			sb.append("\r\n");
			//Iterator iterate=inputvalue.iterator();
			
			// while(iterate.hasNext()){
			
				// sb.append(iterate.next());
				// System.out.println(sb.toString());
			// }
			for(String ss:inputvalue){
				
				sb.append(ss);
			}
			 sb.append("successfully writed");
			writer.write(sb.toString());
			//writer.close();
			System.out.println("written successfully...");
		}
		catch(Exception e){
			
			e.printStackTrace();
		}
		
		finally{
			try{
			writer.close();
			}
			catch(Exception e){
				
				e.printStackTrace();
			}
		}
	}
}
		