import java.io.File;
 
import java.util.ArrayList;
import java.util.List;   

import org.jdom.Document;   
import org.jdom.Element;   
import org.jdom.input.SAXBuilder;   


public class XmlParser  {
	public static void main(String args[]){
		int i=2;
		List l=new XmlParser().run(i);
		System.out.println(l.size());
		for(int j=0;j<l.size();j++){
			String str=(String)l.get(j);
			System.out.println(str);
		}
	} 
	
	List run(int i){
		List list = null;
		try { 
			SAXBuilder builder = new SAXBuilder(); 
			Document doc = builder.build(new File("D:\\"+i+".xml")); 
			Element foo = doc.getRootElement();
			list=new ArrayList();
			list=DFS(foo,list);
			/*System.out.println(foo.getAttributeValue("rotation"));
			List allChildren = foo.getChildren(); 
			for(int i=0;i<allChildren.size();i++) { 
				System.out.println(i);
			System.out.print(((Element)allChildren.get(i)).getChild("node").getAttributeValue("class")); 
			System.out.println("车主地址:" + ((Element)all Children.get(i)).getChild("ADDR").getText()); 
			} 
			*/
		} catch (Exception e) { 
			e.printStackTrace(); 
			} 
		return list;
	}
	
	List DFS(Element e,List list){
		
	/*	depth++;
		for(int i=0;i<depth;i++){
			System.out.print(" ");
		}
		System.out.print("index:"+(e.getAttributeValue("index"))+" ");
		System.out.print("text:"+(e.getAttributeValue("text"))+" "); 
		System.out.print("class:"+(e.getAttributeValue("class"))+" "); 
		System.out.print("ResourceId:"+(e.getAttributeValue("resource-id"))+" "); 
		System.out.print("bounds:"+(e.getAttributeValue("bounds"))+" ");
		System.out.println(""); */
		List allChildren = e.getChildren(); 
		if(allChildren.size()<=0){
			return list;
		}else{
			boolean judge=true;
			int j;
			String rid="";
			for(j=0;j<allChildren.size();j++){
				List allGrantChildren=((Element) allChildren.get(j)).getChildren();
				if(allGrantChildren.size()<=0){
					if(judge){
						rid=rid+e.getAttributeValue("resource-id")+" ";
						rid=rid+((Element) allChildren.get(j)).getAttributeValue("resource-id")+" ";
						judge=false;
					}else{
						rid=rid+((Element) allChildren.get(j)).getAttributeValue("resource-id")+" ";
					}
				}
			}
			if(!rid.equals(null)&&!rid.equals("")){
				String buff=form(rid);
				if(!buff.equals("")){
					list.add(form(rid));
				}
				
			}
		/*	if(!judge){
				rid=rid+e.getAttributeValue("resource-id")+" ";
				for(j=0;j<allChildren.size();j++){
					rid=rid+((Element) allChildren.get(j)).getAttributeValue("resource-id")+" ";
				}
				list.add(rid);
				
			} */
			
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
	
}   
  