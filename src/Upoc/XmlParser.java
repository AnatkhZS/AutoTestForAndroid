package Upoc;
import java.io.File;
 
import java.util.ArrayList;
import java.util.List;   

import org.jdom.Document;   
import org.jdom.Element;   
import org.jdom.input.SAXBuilder;   


public class XmlParser  {
	String f_rid="";	
	public static void main(String args[]){
		List l=new XmlParser().run("D:\\ViewTree1.xml");
		for(int i=0;i<l.size();i++){
			System.out.println((String)l.get(i));
		}
	}   
	
	List run(String path){
		List list = null;
		try { 
			SAXBuilder builder = new SAXBuilder(); 
			Document doc = builder.build(new File(path)); 
			Element foo = doc.getRootElement();
			list=new ArrayList();
			list=DFS(foo,list);
		} catch (Exception e) { 
			e.printStackTrace(); 
			} 
		return list;
	}
	
	List DFS(Element e,List list){
		String buff=""+e.getAttributeValue("resource-id");
		if(!buff.equals("")){
			f_rid=buff;
		}
		
		String rid="";
		List allChildren = e.getChildren(); 
		if(allChildren.size()<=0){
			rid=rid+e.getAttributeValue("resource-id");
			if(!rid.equals("")){
				if(isInList(list,rid)){
					String classname=e.getAttributeValue("class");
					String bounds=e.getAttributeValue("bounds");
					String xPath="//"+classname+"[contains(@bounds,'"+bounds+"')]";
					list.add(xPath);
				}else{
					list.add(rid);
				}
			}else{
				if(!isInList(list,f_rid)){
					list.add(f_rid);
				}
			}
			return list;
		}else{
			for(int i=0;i<allChildren.size();i++) {
				DFS((Element)allChildren.get(i),list);
			}
			return list;
		} 
		

	}
	String form(String str){
		String result="";
		String[] array=str.split(" ");
		for(int i=0;i<array.length;i++){
			if(!array[i].equals("")){
				result=result+array[i]+" ";
			}
		}
		if(result.length()<=0){
			
		}else{
			result=result.substring(0, result.length()-1);
		}
		return result;
	}
	boolean isInList(List l,String str){
		for(int i=0;i<l.size();i++){
			String buff=(String)l.get(i);
			if(buff.equals(str)){
				return true;
			}
		}
		return false;
	}
	
}   
  