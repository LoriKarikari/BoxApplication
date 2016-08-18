package doos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class BoxApplication {

	private static ArrayList<Box<?>> boxList = new ArrayList<>();
	private static ArrayList<Box<?>> yellowBox = new ArrayList<>();
	private static ArrayList<Box<?>> brownBox = new ArrayList<>(); 
	private static BufferedReader reader = null;
	private static BufferedWriter writer = null;
	
	public static void main(String[] args) throws IOException, InterruptedException {
			
		BoxApplication box = new BoxApplication();
	
		Thread readThread = new Thread(new  Runnable() {
			public void run() {
				try {
					BoxApplication.boxList = box.readFunction(reader, "Boxes.txt", getAmountOfLines("Boxes.txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread splitBoxThread = new Thread(new Runnable() {
			public void run() {
				box.splitBoxes(BoxApplication.boxList,BoxApplication.yellowBox,BoxApplication.brownBox);
			}
		});
		
		Thread sortBoxThread = new Thread(new Runnable() {
			public void run() {
				box.SortCollection(BoxApplication.yellowBox);
				box.SortCollection(BoxApplication.brownBox);
				box.SortCollection(BoxApplication.boxList);
			}
		});
		
		Thread writeBoxesToFileThread = new Thread(new Runnable() {
			public void run() {
				try {
					box.writeBoxesToFile(writer, BoxApplication.yellowBox, "BoxYellow.txt");
					box.writeBoxesToFile(writer, BoxApplication.brownBox, "BoxBrown.txt");	
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		Thread writeWeightThread = new Thread(new Runnable() {
			public void run() {
				try {
					box.writeHeavy(writer, BoxApplication.boxList, "Heavy.txt", 50);
					box.writeLight(writer, BoxApplication.boxList, "Light.txt", 50);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		Thread writeFilePropertiesThread = new Thread(new Runnable() {
			public void run() {
				try {
					box.printFileSpecs(writer, "Heavy.txt", "FileProperties.txt");
				} catch (InvalidPathException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		readThread.start();
		readThread.join();
		splitBoxThread.start();
		splitBoxThread.join();
		sortBoxThread.start();
		sortBoxThread.join();
		writeBoxesToFileThread.start();
		writeWeightThread.start();
		writeFilePropertiesThread.start();	
		
		writeBoxesToFileThread.join();
		writeWeightThread.join();
		writeFilePropertiesThread.join();
		
		box.printYellowBoxesTenHeigthWidth(BoxApplication.yellowBox);
		box.printDangerBoxes(BoxApplication.boxList);
	}
	
	private void SortCollection(ArrayList<Box<? extends Package>> doos)
	{
		Collections.sort(doos);
	}
	
private void writeHeavy(BufferedWriter writer, ArrayList<Box<? extends Package>> boxList, String file, int amountOfLines) throws IOException
	{
		try
		{
			writer = new BufferedWriter(new FileWriter(file));
			for(int i = boxList.size()-amountOfLines;i<boxList.size();i++)
			{
				writer.write(boxList.get(i).toString());
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
	
	private void writeLight(BufferedWriter writer, ArrayList<Box<? extends Package>> boxList, String file, int amountOfLines) throws IOException
	{
		try
		{
			writer = new BufferedWriter(new FileWriter(file));
			for(int i = 0; i<amountOfLines;i++)
			{
				writer.write(boxList.get(i).toString());
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
	
	private void writeBoxesToFile(BufferedWriter writer, ArrayList<Box<? extends Package>> boxList, String file) throws IOException
	{
		try
		{
			writer = new BufferedWriter(new FileWriter(file));
			for(Box<? extends Package> d : boxList)
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
	
	private void splitBoxes(ArrayList<Box<?>> boxesList, ArrayList<Box<?>> yellowBoxList, ArrayList<Box<?>> brownBoxList)
	{
		for(Box<?> d : boxesList)
		{
			if(d.getColor() == Color.YELLOW)
			{
				yellowBoxList.add(d);
			}
			else
			{
				brownBoxList.add(d);
			}
		}		
	}

	private static long getAmountOfLines(String file) throws IOException {
		Stream<String> lines = null;
		try {
			Path p = Paths.get(file);
			lines = Files.lines(p);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return lines.count();
	}

	private void addObj(ArrayList<Box<?>> boxList, Box<?> box) {
		boxList.add(box);
	}

	private Box<? extends Package> boxFunction(String line, Color color) {
		String[] array = new String[6];
		array = line.split(";");
		Box<?> d = null;
		switch (array[0]) {
		case "Wood":
			d = new Box<Wood>(Double.parseDouble(array[1]), Double.parseDouble(array[2]), color,
					Double.parseDouble(array[4]), Boolean.parseBoolean(array[5]));
			break;
		case "Metal":
			d = new Box<Metal>(Double.parseDouble(array[1]), Double.parseDouble(array[2]), color,
					Double.parseDouble(array[4]), Boolean.parseBoolean(array[5]));
			break;
		case "Paper":
			d = new Box<Paper>(Double.parseDouble(array[1]), Double.parseDouble(array[2]), color,
					Double.parseDouble(array[4]), Boolean.parseBoolean(array[5]));
			break;
		case "Plastic":
			d = new Box<Plastic>(Double.parseDouble(array[1]), Double.parseDouble(array[2]), color,
					Double.parseDouble(array[4]), Boolean.parseBoolean(array[5]));
			break;
		}
		return d;
	}

	private ArrayList<Box<? extends Package>> readFunction(BufferedReader reader, String bestand,long amountOfLines) throws IOException {
		ArrayList<Box<? extends Package>> boxesList = new ArrayList<>();
		try {
			String[] boxArray = new String[6];
			reader = new BufferedReader(new FileReader(bestand));
			String line ="";
			for (int i = 0; i < amountOfLines; i++) {
				line = reader.readLine();
				boxArray = line.split(";");
				if (boxArray[3].equals("Yellow")) {
					addObj(boxesList, boxFunction(line, Color.YELLOW));
				} else {
					addObj(boxesList, boxFunction(line, Color.BROWN));
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return boxesList;
	}
	
	private void printFileSpecs(BufferedWriter writer, String fileNameToGetPath, String fileNameToWrite) throws IOException, InvalidPathException, FileNotFoundException
	{
		try
		{
			Path p1 = Paths.get(fileNameToGetPath);
			writer = new BufferedWriter(new FileWriter(fileNameToWrite));
			DosFileAttributes attr = Files.readAttributes(p1,DosFileAttributes.class);
			writer.write("Hidden: " + attr.isHidden());
			writer.newLine();
			writer.write("Size: " + attr.size());
			writer.newLine();
			writer.write("Create date: " + attr.creationTime());
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

	private void printYellowBoxesTenHeigthWidth(ArrayList<Box<? extends Package>> yboxList)
	{
		yboxList.stream().filter(s->s.getWidth()==10.0).filter(s->s.getHeight()==10.0).forEach(System.out::println);
	}
	
	private void printDangerBoxes(ArrayList<Box<? extends Package>> dangerBoxes)
	{
		dangerBoxes.stream().filter(s->s.isDanger()==true).forEach(System.out::println);
	}
}
