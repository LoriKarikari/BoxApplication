package doos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

public class DozenApplicatie {

	private static ArrayList<Doos<Verpakking>> dozenList = new ArrayList<>();
	private static ArrayList<Doos<Verpakking>> doosGeel = new ArrayList<>();
	private static ArrayList<Doos<Verpakking>> doosBruin = new ArrayList<>(); 
	private static BufferedReader reader = null;
	private static BufferedWriter writer = null;
	
	public static void main(String[] args) throws IOException, InterruptedException {
			
		DozenApplicatie doos = new DozenApplicatie();
	
		Thread LeesThread = new Thread(new  Runnable() {
			public void run() {
				try {
					dozenList = doos.ReadFile(reader, "dozen.txt", dozenList, getAmountOfLines("dozen.txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread SplitDozenThread = new Thread(new Runnable() {
			public void run() {
				doos.SplitBoxes(dozenList,doosGeel,doosBruin);
			}
		});
		
		Thread SorteerDozenThread = new Thread(new Runnable() {
			public void run() {
				doos.SortCollection(doosGeel);
				doos.SortCollection(doosBruin);
				doos.SortCollection(dozenList);
			}
		});
		
		Thread WriteBoxesToFileThread = new Thread(new Runnable() {
			public void run() {
				try {
					doos.WriteBoxesToFile(writer, doosGeel, "DoosGeel.txt");
					doos.WriteBoxesToFile(writer, doosBruin, "DoosBruin.txt");	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		Thread WriteGewichtenThread = new Thread(new Runnable() {
			public void run() {
				try {
					doos.SchrijfZwaarste(writer, dozenList, "zwaar.txt", 50);
					doos.SchrijfLichtste(writer, dozenList, "lichtste.txt", 50);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread WriteFilePropertiesThread = new Thread(new Runnable() {
			public void run() {
				try {
					doos.printFileSpecs(writer, "zwaar.txt", "FileProperties.txt");
				} catch (InvalidPathException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		LeesThread.start();
		LeesThread.join();
		SplitDozenThread.start();
		SplitDozenThread.join();
		SorteerDozenThread.start();
		SorteerDozenThread.join();
		WriteBoxesToFileThread.start();
		WriteGewichtenThread.start();
		WriteFilePropertiesThread.start();		
	}
	
	private void SortCollection(ArrayList<Doos<Verpakking>> doos)
	{
		for(Doos<Verpakking> d : doos)
		{
			System.out.println(d.toString());
		}
		Collections.sort(doos);
		for(Doos<Verpakking> d : doos)
		{
			System.out.println(d.toString());
		}
	}
	private void SchrijfZwaarste(BufferedWriter writer, ArrayList<Doos<Verpakking>> dozenList, String Bestand, int amountOfLines) throws IOException
	{
		try
		{
			writer = new BufferedWriter(new FileWriter(Bestand));
			for(int i = dozenList.size()-amountOfLines;i<dozenList.size();i++)
			{
				writer.write(dozenList.get(i).toString());
				writer.newLine();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		finally
		{
			if(writer != null)
			{
				writer.close();
			}
		}
		
	}
	
	private void SchrijfLichtste(BufferedWriter writer, ArrayList<Doos<Verpakking>> dozenList, String Bestand, int amountOfLines) throws IOException
	{
		try
		{
			writer = new BufferedWriter(new FileWriter(Bestand));
			for(int i = 0; i<amountOfLines;i++)
			{
				writer.write(dozenList.get(i).toString());
				writer.newLine();
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		finally
		{
			if(writer != null)
			{
				writer.close();
			}
		}
	}
	
	private void WriteBoxesToFile(BufferedWriter writer, ArrayList<Doos<Verpakking>> dozenList, String bestand) throws IOException
	{
		try
		{
			writer = new BufferedWriter(new FileWriter(bestand));
			for(Doos<Verpakking> d : dozenList)
			{
				writer.write(d.toString());
				writer.newLine();
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		finally
		{
			if(writer != null)
			{
				writer.close();
			}
		}
	}
	
	private void SplitBoxes(ArrayList<Doos<Verpakking>> dozenList, ArrayList<Doos<Verpakking>> doosGeel, ArrayList<Doos<Verpakking>> doosBruin)
	{
		for(Doos<Verpakking> d : dozenList)
		{
			if(d.getKleur() == Kleur.Geel)
			{
				doosGeel.add(d);
			}
			else
			{
				doosBruin.add(d);
			}
		}		
	}

	private static long getAmountOfLines(String bestand) throws IOException {
		Stream<String> lines = null;
		try {
			Path p = Paths.get(bestand);
			lines = Files.lines(p);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return lines.count();
	}

	private void AddObj(ArrayList<Doos<Verpakking>> dozenList, Doos<Verpakking> doos) {
		dozenList.add(doos);
	}

	private Doos<?> dozenFunctie(String line, Kleur kleur) {
		String[] array = new String[6];
		array = line.split(";");
		Doos<?> d = null;
		switch (array[0]) {
		case "Hout":
			d = new Doos<Hout>(Double.parseDouble(array[1]), Double.parseDouble(array[2]), kleur,
					Double.parseDouble(array[4]), Boolean.parseBoolean(array[5]));
			break;
		case "Metaal":
			d = new Doos<Metaal>(Double.parseDouble(array[1]), Double.parseDouble(array[2]), kleur,
					Double.parseDouble(array[4]), Boolean.parseBoolean(array[5]));
			break;
		case "Papier":
			d = new Doos<Papier>(Double.parseDouble(array[1]), Double.parseDouble(array[2]), kleur,
					Double.parseDouble(array[4]), Boolean.parseBoolean(array[5]));
			break;
		case "Plastiek":
			d = new Doos<Plastiek>(Double.parseDouble(array[1]), Double.parseDouble(array[2]), kleur,
					Double.parseDouble(array[4]), Boolean.parseBoolean(array[5]));
			break;
		}
		return d;
	}

	private ArrayList<Doos<Verpakking>> ReadFile(BufferedReader reader, String bestand, ArrayList<Doos<Verpakking>> dozenList,long amountOfLines) throws IOException {
		try {
			String[] dozenArray = new String[6];
			reader = new BufferedReader(new FileReader(bestand));
			for (int i = 0; i < amountOfLines; i++) {
				String line = reader.readLine();
				dozenArray = line.split(";");
				if (dozenArray[3].equals("geel")) {
					System.out.println(line);
					AddObj(dozenList, (Doos<Verpakking>) dozenFunctie(line, Kleur.Geel));
					System.out.println(dozenList.size());
				} else {
					System.out.println(line);
					AddObj(dozenList, (Doos<Verpakking>) dozenFunctie(line, Kleur.Bruin));
					System.out.println(dozenList.size());
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return dozenList;
	}
	
	private void printFileSpecs(BufferedWriter writer, String bestandOmFileTeMaken, String wegschrijfBestand) throws IOException, InvalidPathException, FileNotFoundException
	{
		try
		{
			Path p1 = Paths.get(bestandOmFileTeMaken);
			writer = new BufferedWriter(new FileWriter(wegschrijfBestand));
			DosFileAttributes attr = Files.readAttributes(p1,DosFileAttributes.class);
			writer.write("Hidden: " + attr.isHidden());
			writer.newLine();
			writer.write("Grootte: " + attr.size());
			writer.newLine();
			writer.write("Aanmaakdatum: " + attr.creationTime());
			writer.newLine();
			writer.write("Readonly: " + attr.isReadOnly());
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		catch(InvalidPathException ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println(ex.getMessage());
		}
		finally
		{
			if(writer != null)
			{
				writer.close();
			}
		}
		
		
	}

}
