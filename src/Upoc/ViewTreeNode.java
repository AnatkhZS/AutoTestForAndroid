package Upoc;

public class ViewTreeNode {
	private String name;
	private ViewTreeNodeList nodeList;
	
	public ViewTreeNode(){
		this.name=null;
		this.nodeList=new ViewTreeNodeList();
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setList(ViewTreeNodeList list){
		this.nodeList=list;
	}
	public ViewTreeNodeList getList(){
		return this.nodeList;
	}
	public void addChild(ViewTreeNode n){
		nodeList.add(n);
	}
}
