import java.util.*;
import java.io.Serializable;

class Flight implements Serializable{
	private int flightNo; 
	private int start;
	private int end;
	
	
	Flight(){
	}
	
	Flight(int flightNo, int start, int end){
		this.flightNo = flightNo;
		this.start = start;
		this.end = end;
	}
	
	public void setStart(int start){
		this.start = start;
	}
	
	public void setEnd(int end){
		this.end = end;
	}
	
	public int getFlightNo(){
		return flightNo;
	}
	
	public int getStart(){
		return start;
	}
	
	public int getEnd(){
		return end;
	}
	
	public void display(){
		System.out.println("Flight " + flightNo+"\t"+start+"\t"+end);
	}
}