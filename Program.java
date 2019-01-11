import java.util.*;
import java.io.*;

public class Program{
	public static void main(String[] args){
		ArrayList <City> cities = new ArrayList<>();
		ArrayList <Flight> flights = new ArrayList<>();
		
		int flightCounter = 0;
		
		cities.add(new City(1, "Johor"));
		cities.add(new City(2, "Penang"));
		
		CityOne(cities.get(0));
		
		Scanner input = new Scanner(System.in);
		boolean userMenu = true;
		do{
			int users = PrintMenu();
			/*----------------------System Administrator-------------------------------*/
			if(users==1){
				boolean userAdmin=true;
				do{
					System.out.println("============================================================================");
					System.out.println("||                         SYSTEM ADMINISTRATOR                           ||");
					System.out.println("============================================================================");
					ListTable(cities);
					System.out.println("\n1 - Insert city\n2 - Insert attraction\n3 - Remove city\n4 - Remove attraction\n5 - Save\n6 - Load\n7 - Flight");
					int userc = 0;
					while(userc!=1&&userc!=2&&userc!=3&&userc!=4&&userc!=5&&userc!=6&&userc!=7&&userc!=-1){
						System.out.print("\nChoose a function [-1 to exit]: ");
						userc = input.nextInt();
					}
					if (userc == 1||userc==2)
						InsertFunction(cities, userc, input);
					else if (userc == 3|| userc == 4)
						DeleteFunction(cities, userc, input);
					else if (userc == 5){ //Save
						Scanner oosin = new Scanner(System.in).useDelimiter("[,\\s+]");
						
						/*--------------------- City --------------------*/
						String fileName = "", fileName_flight = "";
						System.out.print("File name: ");
						String fileInput = oosin.next();
						fileName += fileInput + ".txt";
						fileName_flight = fileInput + "Flight.txt";
						try{
							FileOutputStream fos = new FileOutputStream(fileName);
							ObjectOutputStream oos = new ObjectOutputStream(fos);
							for(int i=0; i<cities.size(); i++){
								City c= cities.get(i);
								oos.writeObject(c);
							}
							oos.writeObject(null);
							oos.close();
							fos.close();
							
							FileOutputStream fosFlight = new FileOutputStream(fileName_flight);
							ObjectOutputStream oosFlight = new ObjectOutputStream(fosFlight);
							for(int i=0; i<flights.size(); i++){
								Flight f= flights.get(i);
								oosFlight.writeObject(f);
							}
							oosFlight.close();
							fosFlight.close();
							
							System.out.println("Data successfully written into file...");
						}
						catch (FileNotFoundException e) {
							System.out.println("\n[Error] File not found...");
						} 
						catch (IOException e) {
							System.out.println("\n[Error] Initialization of stream fail...");
							
						} 
					}
					else if (userc == 6){ //Load
						Scanner oisin = new Scanner(System.in).useDelimiter("[,\\s+]");
						String fileName = "", fileName_flight = "";
						System.out.print("File name: ");
						String fileInput = oisin.next();
						fileName += fileInput + ".txt";
						fileName_flight = fileInput + "Flight.txt";
						
						try{	
						    cities.clear();
							flights.clear();
							FileInputStream fis = new FileInputStream(fileName);
							ObjectInputStream ois = new ObjectInputStream(fis);
							boolean cont = true;
							
							while(cont==true){
								City c= (City)ois.readObject();
								if(c != null)
									cities.add(c);
								else
									cont=false;
							}
							
							ois.close();
							fis.close();
							
							FileInputStream fisFlight = new FileInputStream(fileName_flight);
							ObjectInputStream oisFlight = new ObjectInputStream(fisFlight);
							boolean contFlight = true;
							
							while(contFlight==true){
								Flight f= (Flight)oisFlight.readObject();
								if(f != null)
									flights.add(f);
								else
									contFlight=false;
							}
							
							oisFlight.close();
							fisFlight.close();
						}
						catch(EOFException ex){
							System.out.println("\nFile has been loaded...");
						}
						catch (FileNotFoundException e) {
							System.out.println("\n[Error] File not found...");
						} 
						catch (IOException e) {
							System.out.println("\n[Error] Initialization of stream fail...");
							
						} 
						catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					else if(userc == 7){
						FlightFunction(cities,flights, flightCounter, input);
					}
					else
						userAdmin = false;
					if(userAdmin != false&&userc!=7)
						SysPause();
				}while(userAdmin==true);
			}
			/*-----------------------Tourist Attraction Program-------------------------*/
			else if(users==2){
				boolean userCity = true;
				do{
					System.out.println("============================================================================");
					System.out.println("||                     TOURIST ATTRACTION PROGRAM                         ||");
					System.out.println("============================================================================");
					CityTable(cities); 
					System.out.print("\nChoose a city id [-1 to exit]: ");
					int userc = input.nextInt();
					for(int i=0; i<cities.size();i++){
						if(userc==cities.get(i).getID())
							UserFunctions(userc, userCity, cities, flights, input); 
					}
					if(userc == -1){ //userc = -1 is always to exit
						userCity=false;
						break;
					}
					
				}while(userCity==true);
			}
			else if(users==-1){ //users=-1 is always to exit
				userMenu=false;
				break;
			}	
		}while(userMenu==true);
	}
	public static int PrintMenu(){
		Scanner input = new Scanner(System.in);
		
		System.out.println("============================================================================");
		System.out.println("||                                MAIN MENU                               ||");
		System.out.println("============================================================================");
		System.out.println("1 - Admin\n2 - User");
		int users = 0;
		while(users!=1&&users!=2&&users!=-1){
			System.out.print("\nChoose a person id [-1 to exit]: ");
			users = input.nextInt();
		}
		return users;
	}
	/*-------------------------City and Attraction Table Function --------------------------------------------*/
	public static void ListTable(ArrayList <City> cities){
		if(cities.size() != 0){
			System.out.println("----------------------------------------------------------------------------");
			System.out.println("City ID\tCity\tType ID\tType   \tAtt ID\tAtt");
			System.out.println("----------------------------------------------------------------------------");
			for(int h=0; h<cities.size(); h++){
				City c = cities.get(h);
				if(c.getAttListSize()>0){
					for(int i=0; i<c.getAttListSize(); i++){
						String typeName = null;
						if(c.getAttTypeID(i)==1)
							typeName = "Culture";
						else if (c.getAttTypeID(i)==2)
							typeName = "Mall";
						else if (c.getAttTypeID(i)==3)	
							typeName = "Museum";
						System.out.println(c.getID()+"\t"+c.getName()+"\t"+c.getAttTypeID(i)+"\t"+typeName+"\t"+c.getAttID(i)+"\t"+c.getAttName(i));
					}
				}
				else
					System.out.println(c.getID()+"\t"+c.getName());
			}
			System.out.println("----------------------------------------------------------------------------");
		}
		else
			System.out.println("[THERE IS CURRENTLY NO DATA]");
	}
	
	public static void CityTable(ArrayList <City> cities){
		if(cities.size()!=0){
			System.out.println("-----------------------------------------------------");
			System.out.println("ID\tCity Name");
			System.out.println("-----------------------------------------------------");
			for(int i=0; i<cities.size(); i++){
				City c = cities.get(i);
				c.display();
			}
			System.out.println("-----------------------------------------------------");
		}
		else
			System.out.println("[THERE IS CURRENTLY NO DATA]");
	}
	
	/*--------------------------------------------------------------------------------------------*/
	/*                                  ADMIN INTERFACE                                           */
	/*--------------------------------------------------------------------------------------------*/
	/*------------------------------- Insert Function --------------------------------------------*/
	public static void InsertFunction(ArrayList<City>cities, int userc, Scanner input){
		if(userc == 1){
			
			System.out.print("ID of city: ");
			int cityID = input.nextInt();
			
			System.out.print("Name of city: ");
			String cityName = input.nextLine();   
			cities.add(new City(cityID, cityName));
			System.out.print("\n[ "+cityName + " has been added to list of cities ]\n");
		}
		else if(userc == 2){
			if(cities.size() != 0){
				int attCityID  = 0;
				boolean cont = true; 
				City cityName = new City();
				do{
					System.out.print("\nID of city: ");
					attCityID = input.nextInt();
					for(int i=0; i<cities.size(); i++){
						if(cities.get(i).getID() == attCityID){
							cityName = cities.get(i);
							cont = false;
							break;
						}
					}
					if(cont==true)
						System.out.print("City ID does not exist...");
				}while(cont==true);
				
				System.out.println("City selected: "+cityName.getName());
				System.out.println("-----------------------------------------------------");
				System.out.println("ID\tAttraction Types");
				System.out.println("-----------------------------------------------------");
				System. out.println("1\tCulture\n2\tMuseum\n3\tShopping");
				System.out.println("-----------------------------------------------------");
				int attTypeID=0;
				while(attTypeID!=1&&attTypeID!=2&&attTypeID!=3){
					System.out.print("\nType ID of attraction: ");
					attTypeID = input.nextInt();
				}
				System.out.print("ID of attraction: ");
				int attID = input.nextInt();
				System.out.print("Name of attraction: ");
				String attName = input.nextLine();
				
				if (attTypeID == 1){ //culture
					System.out.print("Entrance fee (RM): ");
					int entFee = input.nextInt();
					System.out.print("Religion: ");
					String religion= input.next();
					cityName.setAttraction(new Culture(attID, attName, attTypeID, entFee, religion));
				}
				else if (attTypeID == 2){ //shopping
					int size = 20;
					String[] shopsArray = new String[size];
					boolean shopCont = true;
					String shop = null; 
					int shopsCount=0;
					while(shopCont == true){
						System.out.print("Shop name [0 to quit]: ");
						shop = input.nextLine();
						if(shop.equals("0")){
							shopCont = false;
							break;
						}
						else{
							if(shopsCount > 19){
								size++;
							}
							shopsArray[shopsCount] = shop;
						}
					}
					cityName.setAttraction(new Shopping(attID, attName, attTypeID, shopsArray));
				}
				else if (attTypeID == 3){ //museum
					System.out.print("Years established: ");
					int years = input.nextInt();
					System.out.print("Museum type: ");
					String musType= input.next();
					cityName.setAttraction(new Museum(attID, attName, attTypeID, years, musType));
				}
				
				System.out.println("\n[ "+attName + " has been added to list of "+cityName.getName()+" attractions ]");
			}
			else{
				System.out.println("You have not added any city into the system yet...");
			}
		}
	}
	/*------------------------------- Delete Function --------------------------------------------*/
	public static void DeleteFunction(ArrayList<City>cities, int userc, Scanner input){
		if(cities.size() != 0){
			if(userc == 3){
				System.out.print("\nCity to delete [Enter City ID]: ");
				int userg = input.nextInt();
				for(int i=0; i<cities.size(); i++){
					if(userg == cities.get(i).getID()){
						City deleteCity = cities.get(i);
						cities.remove(deleteCity);
					}
				}
			}
			else if(userc == 4){
				boolean deleteCont = false;
				for(int i=0; i<cities.size(); i++){
					if(cities.get(i).getAttractionListSize() != 0){
						deleteCont = true;
						break;
					}
				}
				if (deleteCont == true){		
					System.out.print("\nCity ID: ");
					int userg = input.nextInt();
					System.out.print("\nAttraction to delete [Enter Att ID]: ");
					int userh = input.nextInt();
					for(int i=0; i<cities.size(); i++){
						if(userg == cities.get(i).getID()){
							cities.get(i).deleteAttraction(userh); 
						}
					}
				}
				else
					System.out.println("There is currently no attraction data to delete");
			}
		}
		else{
			System.out.println("There is currently no data to delete");
		}
	}
	public static void FlightFunction(ArrayList<City>cities, ArrayList<Flight> flights, int flightCounter, Scanner input){
		boolean flightCont = true;
		do{
			System.out.println("=====================================================");
			System.out.println("||                 FLIGHT FUNCTION                 ||");
			System.out.println("=====================================================");
			System.out.println("\nCity Table: ");
			CityTable(cities);
			if(flights.size()!=0){
				System.out.println("\nList of flights;");
				System.out.println("-----------------------------------------------------");
				System.out.println("Flight  \tFrom \tTo");
				System.out.println("-----------------------------------------------------");
				for(int i=0; i<flights.size(); i++){
					flights.get(i).display();
				}
				System.out.println("-----------------------------------------------------");
			}
			else
				System.out.println("[THERE IS CURRENTLY NO FLIGHT DATA]");
			
			System.out.println("1 - Insert flight\n2 - Remove flight\n");
			int userc = 0;
			while(userc!=1&&userc!=2&&userc!=-1){
				System.out.print("Select a function [-1 to exit]: ");
				userc = input.nextInt();
			}
			if(userc == 1){
				CityTable(cities);
				
				System.out.println("Connect cities by flight: ");
				System.out.print("From [Enter City ID]: ");
				int placeFrom = input.nextInt();
				System.out.print("To [Enter City ID]: ");
				int placeTo = input.nextInt();
				flightCounter++;
				
				flights.add(new Flight(flightCounter, placeFrom, placeTo));
				for(int i=0; i<flights.size(); i++){
					if(flights.get(i).getStart()==placeFrom){
						if(flights.get(i).getEnd()==placeTo){
							System.out.println("Flight "+flights.get(i).getFlightNo()+" has been added...");
							break;
						}
					}
				}
			}
			else if(userc == 2){
				if(flights.size()!=0){
					System.out.println("Choose flight no to delete: ");
					int userFlight = input.nextInt();
					for(int i=0; i<flights.size(); i++){
						if (userFlight == flights.get(i).getFlightNo())
							flights.remove(flights.get(i));
						else
							System.out.println("Flight doesn't exist");
					}
				}
				else
					System.out.println("There is no data to be deleted...");
			}
			else
				flightCont = false;
		}while(flightCont == true);
		
	}
	
	/*--------------------------------------------------------------------------------------------*/
	/*                                   USER INTERFACE                                           */
	/*--------------------------------------------------------------------------------------------*/
	/*----------------------------------- User Main   --------------------------------------------*/
	public static void UserFunctions(int userc, boolean userCity, ArrayList<City>cities, ArrayList<Flight>flights, Scanner input){
		boolean userAtt = true;
		userc = userc-1;
		do{
			System.out.println("============================================================================");
			System.out.println("City select: "+cities.get(userc).getName());
			System.out.println("\n1 - Available flight\n2 - Attraction list");
			System.out.print("\nChoose a function to view [-1 to exit]: ");
			int userChoice = input.nextInt();
			if(userChoice == 1){
				System.out.println("============================================================================");
				System.out.println("||                             FLIGHT FUNCTION                            ||");
				System.out.println("============================================================================");
				System.out.println("From: "+cities.get(userc).getName());
				System.out.print("To [Enter city name]: ");
				
				String cName = input.next();
				boolean noExist = true;
				for(int i=0;i<cities.size(); i++){
					if(cities.get(i).getName().equals(cName)){
						int userg = cities.get(i).getID();
						for(int j=0; j<flights.size(); j++){
							if(flights.get(j).getStart() == cities.get(userc).getID()||flights.get(j).getEnd() == cities.get(userc).getID()){
								if(flights.get(j).getEnd() == userg||flights.get(j).getStart() == userg)
									System.out.println("\nFlight is available");
								else
									System.out.println("No flight is available");
								
							}
						}
						noExist = false;
					}
				}
				if(noExist == true)
					System.out.println("\nCity does not exist");
				SysPause();
			}
			else if(userChoice == 2){
				System.out.println("============================================================================");
				String selection="Null";
				City c = cities.get(userc-1);
				System.out.println("List of "+c.getName()+ " Attractions Types");
				System.out.println("============================================================================");
				System.out.println("-----------------------------------------------------");
				System.out.println("ID\tAttraction Types");
				System.out.println("-----------------------------------------------------");
				System.out.println("1\tCulture\n2\tMuseum\n3\tShopping\n5\tAll");
				try{
					int userg = 0;
					while(userg!=1&&userg!=2&&userg!=3&&userg!=4&&userg!=5&&userg!=-1){
						System.out.print("\nChoose an attraction type id [-1 to exit]: ");
						userg = input.nextInt();
					}
					
					if(userg==-1){ //userg = -1 is always to exit
						userAtt = false;
						break;
					}
					else{
						boolean userPlace = true;
						do{
							TitleAttractionList(userc, cities, userg); //prints title list of attractions 
							cities.get(userc).getAttraction(userg); //prints list of attraction places according to type id
							try{
								int userh=0;
								boolean userCheck=false; 
								while(userCheck!=true){
									System.out.print("\nChoose an attraction place id [-1 to exit]: ");
									userh = input.nextInt();
									
									if(userh==-1){ //userh = -1 is always to exit
										userCheck=true;
										userPlace=false;
										break;
									}
									userCheck=cities.get(userc).checkID(userg, userh); //check if place id exists
								}
								if(userCheck==true&&userh!=-1){
									cities.get(userc).getAttPlace(userh); //prints place details
									SysPause();
								}
							}
							catch(InputMismatchException e){ //if any input other than int is placed
								userPlace=true;
								input.nextLine(); //to throw away
								System.out.println("-----------------------------------------------------");
								System.out.println("[Input error: Please enter an integer value]");
								SysPause();
							}
						}while(userPlace == true);
					}
					
				}
				catch(InputMismatchException e){ //if any input other than int is placed
					userAtt=true;
					input.nextLine(); //to throw away
					System.out.println("-----------------------------------------------------");
					System.out.println("[Input error: Please enter an integer value]");
					SysPause();
				}
			}
			else if(userChoice == -1)
				userAtt = false;
			
		}while(userAtt == true);
		
	}
	/*--------------------------------  Print Attraction -----------------------------------------*/
	public static void TitleAttractionList(int userc,ArrayList<City>cities,int userg){
		System.out.println("============================================================================");
		String selection="Null";
		if(userg==1)
			selection = "[Culture]";
		else if(userg==2)
			selection = "[Mall]";
		else if(userg==3)
			selection = "[Museum]";
		else if(userg==4)
			selection = "[All]";
		City c = cities.get(userc-1);
		System.out.println(c.getName()+" Attractions "+selection);
		System.out.println("============================================================================");
		System.out.println("-----------------------------------------------------");
		System.out.println("ID\tPlace Name           \tType ID");
		System.out.println("-----------------------------------------------------");
	}
	public static void SysPause(){
		System.out.print("\nPress Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
	}
	public static void CityOne(City c){
		c.setAttraction(new Culture(11,"Kek Lok Si           ", 1, 2, "Buddhist"));
		c.setAttraction(new Culture(12,"Khoo Kongsi          ", 1, 10, "Buddhist"));
		c.setAttraction(new Culture(13,"St. George           ", 1, 0, "Christian"));
		c.setAttraction(new Museum(31,"Batik Painting Museum",3, 2013, "Art Gallery"));
		c.setAttraction(new Shopping(41,"Gurney Palagon       ", 2, new String[]{"Guardian", "Harnn","Italiannies", "Lunarich", "Rakuzen"}));
	}
}