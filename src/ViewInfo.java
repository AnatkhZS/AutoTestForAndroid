import java.util.List;


public class ViewInfo {
	private String activityName;
	private String fatherActivity;
	private int hash;
	private List component;
	private int point;
	public ViewInfo(){
		activityName=null;
		fatherActivity=null;
		hash=0;
		component=null;
		point=0;
	}
	
	void setActivity(String name){
		activityName=name;
	}
	void setFatherActivity(String name){
		fatherActivity=name;
	}
	void setHash(int hash){
		this.hash=hash;
	}
	void setComponent(List l){
		component=l;
	}
	boolean setPoint(int p){
		if(p<component.size()){
			point=p;
			return true;
		}else{
			return false;
		}
	}
	String getActivity(){
		return activityName;
	}
	String getFatherActivity(){
		return fatherActivity;
	}
	int getHash(){
		return hash;
	}
	List getComponent(){
		return component;
	}
	int getPoint(){
		return point;
	}
}
