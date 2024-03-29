package com.indicflyair.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.indicflyair.partials.Day;
import java.io.File;

public class DataManager {
	protected String arrtime, deptime, days , source, dest, fl_no;
	private FlightManager mgr;

	public DataManager(FlightManager mgr) {
		this.mgr = mgr;
	}


	public StringTokenizer breakColumn(String oneLine){
		StringTokenizer st=new StringTokenizer(oneLine,"|");		
		return st;
	}

	public static ArrayList<Day> genDayArray(String s)
	{

		ArrayList<Day> d =new ArrayList<Day>();
		if(s.equalsIgnoreCase("DAILY"))
		{
			String[] days= {"Sunday","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
			for(String i:days) 
			{
				Day r=new Day(i);
				d.add(r);
			}

		}
		else 
		{
			StringTokenizer d1 = new StringTokenizer(s,", ");
			while(d1.hasMoreTokens()) 
			{
				String day=d1.nextToken();
				Day r=new Day(day);
				d.add(r);
			}
		}
		return d;
	}
	public ArrayList<Flight> readCsv(String filename, ArrayList<Flight> FlightList){
		try {
			@SuppressWarnings("resource")
			BufferedReader br =new BufferedReader(new FileReader(new File(filename)));
			String input; 
			int count=0;
			while((input=br.readLine())!=null){
				if(count++>1)
				{
					StringTokenizer st =new StringTokenizer(input,",");
					source = st.nextToken();
					FlightList.get(count-2).id=count-2;
					FlightList.get(count-2).setSeats(Integer.parseInt(st.nextToken()));
				}
			}



		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		catch(IOException e){
			System.out.println("IO Error");
		}
		return FlightList;
	}
	
	public void updateDomCsv(int fl_no, int val,String filename)
	{
		try {
			FileWriter csvWriter = new FileWriter(new File(filename));
			csvWriter.append("Number");
			csvWriter.append(",");
			csvWriter.append("Seats");
			csvWriter.append("\n");

			System.out.println(val +" "+ fl_no);
			ArrayList<Flight> l = mgr.domList;
			for(int i = 0;i<l.size();i++)
			{

				if(l.get(i).id == fl_no)
					l.get(i).setSeats(val);
			}
			for(int i = 0; i< l.size();i++)
			{
				Flight f = l.get(i);

				csvWriter.append(String.valueOf(f.getFlightNum()));
				csvWriter.append(",");
				csvWriter.append(String.valueOf(f.getSeats()));
				csvWriter.append("\n");

			}
			csvWriter.flush();
			csvWriter.close();
    	}
    				
		catch(Exception e) {
            e.printStackTrace();
        }
		
	}
	public void updateIntCsv(int fl_no, int val,String filename)
	{
		try {
			FileWriter csvWriter = new FileWriter(new File(filename));
			csvWriter.append("Number");
			csvWriter.append(",");
			csvWriter.append("Seats");
			csvWriter.append("\n");
			System.out.println(val +" "+ fl_no);
			ArrayList<Flight> l = mgr.intList;
			for(int i = 0;i<l.size();i++)
			{

				if(l.get(i).id == fl_no)
					l.get(i).setSeats(val);
			}
			for(int i = 0; i< l.size();i++)
			{
				Flight f = l.get(i);

				csvWriter.append(String.valueOf(f.getFlightNum()));
				csvWriter.append(",");
				csvWriter.append(String.valueOf(f.getSeats()));
				csvWriter.append("\n");

			}
			csvWriter.flush();
			csvWriter.close();
    	}
    				
		catch(Exception e) {
            e.printStackTrace();
        }
		
	}
	public  ArrayList<Flight> loadDomestic(String filename)
	{
		Flight f; ArrayList<Flight> FlightList = new ArrayList<Flight>(); 
		try {
			@SuppressWarnings("resource")
			BufferedReader br =new BufferedReader(new FileReader(new File(filename)));
			String input; 
			DataManager dm=new DataManager(mgr);
			int count=0;
			while((input=br.readLine())!=null){
				if(count++>4)
				{
					StringTokenizer st =dm.breakColumn(input);
					source = st.nextToken();
					source=source.charAt(0)+source.toLowerCase().substring(1);
					dest = st.nextToken();
					dest=dest.charAt(0)+dest.toLowerCase().substring(1);
					days = st.nextToken();
					fl_no = st.nextToken();
					deptime = st.nextToken();
					arrtime = st.nextToken();
					f=new Flight(source,dest,genDayArray(days),fl_no,deptime,arrtime);
					FlightList.add(f);
				}
			}



		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		catch(IOException e){
			System.out.println("IO Error");
		}
		return FlightList;
	}

	public ArrayList<Flight> loadInternational(String filename)
	{
		Flight f; BufferedReader br;
		ArrayList<Flight> FlightList = new ArrayList<Flight>();
		try {
			br=new BufferedReader(new FileReader(new File(filename)));
			String input;
			DataManager dm=new DataManager(mgr);
			int count=0;
			while((input=br.readLine())!=null){
				if(count++>4)
				{
					StringTokenizer st =dm.breakColumn(input);
					source = st.nextToken();
					source=(source.substring(0,source.indexOf('('))).trim();
					dest = "Singapore";
					days = st.nextToken();
					fl_no = st.nextToken();
					String t = st.nextToken();
					deptime = deptimeInt(t);
					arrtime = arrtimeInt(t);
					f=new Flight(source,dest,genDayArray(days),fl_no,deptime,arrtime);
					FlightList.add(f);
				}
			}


		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		catch(IOException e){
			System.out.println("IO Error");
		}

		return FlightList;
	}

	public static String deptimeInt(String s)
	{
		StringTokenizer st= new StringTokenizer(s,"/");
		return st.nextToken();
	}

	public static String arrtimeInt(String s)
	{
		StringTokenizer st= new StringTokenizer(s,"/");
		st.nextToken();
		return st.nextToken();
	}


}

