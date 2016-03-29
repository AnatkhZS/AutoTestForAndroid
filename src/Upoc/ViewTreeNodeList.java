package Upoc;

import java.util.ArrayList;

public class ViewTreeNodeList extends ArrayList{
	private ArrayList list=new ArrayList();
	
	public void add(ViewTreeNode n){
		list.add(n);
	}
	public ViewTreeNode get(int index){
		return (ViewTreeNode)list.get(index);
	}
	public int size(){
		return list.size();
	}
}
