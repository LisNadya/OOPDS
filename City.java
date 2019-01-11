import java.util.*;
import java.io.Serializable;

class City implements Serializable{
	private int id;
	private String name;
	private ArrayList <Attraction> attractions = new ArrayList <>();
	
	City(){
	}
	
	City(int id, String name){
		this.id=id;
		this.name=name;
	}
	
	public int getID(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public int getAttractionListSize(){
		return attractions.size();
	}
	
	public void setID(int id){
		this.id=id;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setAttraction(Attraction a){ 
		attractions.add(a);
	}
	
	public boolean checkID(int typeID, int id){
		boolean check=false;
		
		for(int i=0;i<attractions.size();i++){
			if(typeID==5){
				if (id==attractions.get(i).getID())
					check = true;
			}
			
			else{
				if(typeID==attractions.get(i).getTypeID()){
					if (id==attractions.get(i).getID())
						check = true;
				}
			}
		}
		return check;
	}
	
	public void deleteAttraction(int userg){
		for(int i=0; i<attractions.size(); i++){
			if(attractions.get(i).getID() == userg){
				Attraction a = attractions.get(i);
				attractions.remove(a);
			}
		}
	}
	
	public void getAttraction(int userg){ //userg is type id
		attractions.sort(Comparator.comparing(Attraction::getName));
	
		if(userg==1||userg==2||userg==3||userg==4){
			for(int i=0; i<attractions.size(); i++){
				if(attractions.get(i).getTypeID() == userg){
					Attraction a = attractions.get(i);
					a.display();
				}
			}
		}
		
		else{
			for(int i=0; i<attractions.size(); i++){
				Attraction a = attractions.get(i);
				a.display();
			}
		}
	}
	
	public void getAttPlace(int userh){ //userh is id
		for(int i=0; i<attractions.size(); i++){
			if(attractions.get(i).getID() == userh){
				Attraction a = attractions.get(i);
				System.out.println(a);
			}
		}
	}
	
	public int getAttID(int i){
		return attractions.get(i).getID();
	}
	public String getAttName(int i){
		return attractions.get(i).getName();
	}
	public int getAttTypeID(int i){
		return attractions.get(i).getTypeID();
	}
	
	public int getAttListSize(){
		return attractions.size();
	}
	
	public void display(){
		System.out.println(id + "\t"+ name);
	}
}