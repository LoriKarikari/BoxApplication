package boxapplication;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.*;
import java.util.*;
import java.util.stream.IntStream;

public class BoxApplication {

	private Path path = Paths.get("Boxes.txt");
	private List<Box<?>> boxes = new ArrayList<>();
	private List<Box<?>> yellowBoxes = new ArrayList<>();
	private List<Box<?>> brownBoxes = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException, IOException {

		BoxApplication boxApplication = new BoxApplication();

		Runnable read = () -> {
			try {
				boxApplication.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		Thread t1 = new Thread(read);
		t1.start();
		t1.join();

		Runnable seperateColors = () -> {
			boxApplication.seperateColors();
		};
		Thread t2 = new Thread(seperateColors);
		t2.start();
		t2.join();

		
		boxApplication.writeHeavy();
		boxApplication.writeLight();
		boxApplication.printYellow();
		boxApplication.printDangerous();
		boxApplication.writeProperties();
	}

	/*
	 * read boxes from file
	 * throws instead of try/catch to apply them both
	 */
	private void read() throws IOException {
		BufferedReader reader = Files.newBufferedReader(path, Charset.defaultCharset());
		String line;
		while ((line = reader.readLine()) != null) {
			String[] data = line.split(";");
			String type = data[0];
			double height = Double.parseDouble(data[1]);
			double weight = Double.parseDouble(data[4]);
			double width = Double.parseDouble(data[2]);
			boolean danger = Boolean.parseBoolean(data[5]);
			Color color;

			if (data[3].equals("Yellow")) {
				color = Color.YELLOW;
			} else {
				color = Color.BROWN;
			}

			// check the type of box to add the proper object
			switch (type) {
			case "Wood":
				Box<Wood> woodBox = new Box<>(height, width, weight, danger, color);
				boxes.add(woodBox);
				break;
			case "Paper":
				Box<Paper> paperBox = new Box<>(height, width, weight, danger, color);
				boxes.add(paperBox);
				break;
			case "Metal":
				Box<Metal> metalBox = new Box<>(height, width, weight, danger, color);
				boxes.add(metalBox);
				break;
			case "Plastic":
				Box<Paper> plasticBox = new Box<>(height, width, weight, danger, color);
				boxes.add(plasticBox);
				break;
			default:
				break;
			}
		}
	}
	
	/*
	 * general write method
	 * try/catch exception used here
	 */
	private void write(Path path, List<?> list) {
		try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
			list.stream()
			.forEach(item -> {
				try {
					writer.write(item.toString() +"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// create a collection for each color 
	private void seperateColors() {
		boxes.stream().forEach(box -> {
			if (box.getColor() == Color.BROWN) {
				brownBoxes.add(box);
			} else {
				yellowBoxes.add(box);
			}
		});
		Collections.sort(brownBoxes);
		Collections.sort(yellowBoxes);
		
		Path yellowPath = Paths.get("BoxYellow.txt");
		Path brownPath = Paths.get("BoxBrown.txt");
		
		write(brownPath, brownBoxes);
		write(yellowPath, yellowBoxes);
	}

	/*
	 * write top 50 heaviest boxes to a file
	 */
	public void writeHeavy() {
		List<Box<?>> heavyBoxes = new ArrayList<>(); // create new ArrayList for lightest boxes
		
		Collections.sort(boxes); // sort boxes by weight (high to low), this is determined by the compareTo() method in the Box class
		IntStream.range(0, 50) // get the first 50 boxes
		.mapToObj(box -> boxes.get(box))
		.forEach(box -> heavyBoxes.add(box)); // add heaviest boxes to ArrayList
		
		Path path = Paths.get("Heavy.txt"); // create location for file
		write(path, heavyBoxes); // call the write method with path and new list
	}
	
	/*
	 * write top 50 lightest boxes to a file
	 * same as writeHeavy() but with reverse sort order
	 */
	public void writeLight() {
		List<Box<?>> lightBoxes = new ArrayList<>(); 
		
		Collections.sort(boxes, Collections.reverseOrder()); // sort boxes by weight (low to high)
		IntStream.range(0,  50) 
		.mapToObj(box -> boxes.get(box))
		.forEach(box -> lightBoxes.add(box)); 
	
		Path path = Paths.get("Light.txt"); 
		write(path, lightBoxes);
	}
	
	/*
	 * print all yellow boxes with a height and width of 10
	 */
	private void printYellow() {
		System.out.println("---------------------Overview of all 10x10 yellow boxes---------------------");
		yellowBoxes.stream()
		.filter(boxes -> boxes.getWidth() == 10 && boxes.getHeight() == 10)
		.map(boxes -> boxes.toString())
		.forEach(System.out::println);;
	}

	/*
	 * print all dangerous boxes
	 */
	private void printDangerous() {
		System.out.println("---------------------Overview of dangerous boxes---------------------");
		boxes.stream()
		.filter(boxes -> boxes.getDanger() == true)
		.map(box -> box.toString())
		.forEach(System.out::println);
	}
	
	/*
	 * write properties of Heavy.txt to a file
	 */
	private void writeProperties() throws IOException {
		List<String> attributes = new ArrayList<>(); 
		Path path = Paths.get("Heavy.txt");
		Path writePath = Paths.get("FileProperies.txt");
		
		// Unix/Linux
		PosixFileAttributes attr = Files.readAttributes(path, PosixFileAttributes.class);
		attributes.add("Size: " +attr.size());
		attributes.add("Creation Time: " +attr.creationTime());
		
		// Winblows/DOS, werken niet met Posix
		// DosFileAttributes attr = Files.readAttributes(path, DosFileAttributes.class);
		// attributes.add("Hidden: " +attr.isHidden()); 
		// attributes.add("Read Only: " +attr.isReadOnly());
		
		write(writePath, attributes);
	}
}


