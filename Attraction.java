import java.util.*;
import java.io.Serializable;

abstract class Attraction implements Serializable{
	private int id;
	private String name;
	private int typeID;
	
	Attraction(){
	}
	
	Attraction(int id, String name, int typeID){
		this.id=id;
		this.name=name;
		this.typeID=typeID;
	}
	
	public void setID(int id){
		this.id=id;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setTypeID(int typeID){
		this.typeID=typeID;
	}
	
	public int getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getTypeID(){
		return typeID;
	}
	
	public void display(){
		System.out.println(id+"\t"+name + "\t" + typeID);
	}
	
	//public abstract String toString();
}

/*----------------- Subclass Culture -------------------*/
class Culture extends Attraction{ 
	private double entranceFee;
	private String religion;
	
	public Culture(){
	}
	
	public Culture(int id, String name, int typeID, double entranceFee, String religion){
		super(id,name,typeID);
		this.entranceFee=entranceFee;	
		this.religion=religion;
	}
	
	public String toString(){
		String dash = "\n-----------------------------------------------------\n";
		return dash + getName()+dash+ "Entrance fee: RM" + entranceFee + "\nReligion: "+ religion;
	}
	
}
/*----------------- Subclass Museum -------------------*/
class Museum extends Attraction{ 
	private String museumType;
	private int year;
	
	public Museum(){
	}
	
	public Museum(int id, String name, int typeID, int year, String museumType){
		super(id,name,typeID);
		this.museumType = museumType;
		this.year=year;
	}
	public String toString(){
		String dash = "\n-----------------------------------------------------\n";
		return dash+getName()+dash+"Museum type: "+museumType+"\nYear established: "+year;
	}
}

/*----------------- Subclass Shopping -------------------*/
class Shopping extends Attraction{
	private String[] shops;
	
	public Shopping(){
	}
	
	public Shopping(int id, String name, int typeID, String[] shops){
		super(id,name,typeID);
		this.shops=shops;
	}
	
	public String toString(){
		String dash = "\n-----------------------------------------------------\n";
		return dash+getName()+dash+"Directory:List of shops: "+Arrays.toString(shops);
	}
	
}