# Box Application
Exercise on:
- Generics
- Lambda expressions
- File.IO
- File attributes
- Collections
- (multi)threading
:
## Exercise

### Intro

Attached you will find the file Boxes.txt.This file contains 1100 lines with the following Box properties:
- Type
- Width
- Height
- Color
- Weight
- Danger

### Classes

Create a generic class Box with which you can specify the type of box.

The types of boxes are:
- Wood 
- Metal 
- Paper 
- Plastic 
These are all subclasses of the class Package.

### ENUM
Create an ENUM to specify the color of the box. Available colors are YELLOW and BROWN.

### Goal

Make sure that you can only create Box objects with one of the specified types.

Read the Boxes.txt file (seperate thread) and create a Box object for every line.
Add these Box objects to the appropriate collection.

Sort the boxes by color and make sure that every color has its own collection. 
Sort these collections by weight from light to heavy.
Then you write all these boxes to BoxYellow.txt and BoxBrown.txt.

Write the 50 heaviest boxes to the file Heavy.txt and make sure this happens on a seperate thread.
Do the same for the 50 lightest boxes and Light.txt

Print all boxes with a height and width of 10.0 (Stream)
Print all dangerous boxes.

### File Attributes 

Get the path of Heavy.txt and look for the following file properties:
- isHidden
- size
- creation date
- readOnly

Write these properties to FileProperties.txt.

### JAR

Ensure that you can run the application through a .jar file.

### NOTE
Try to run the threads simultaneously whenever it's possible. Decide which threads have to wait and which don't.

## Contributors

B.D - Student of PXL University College (Belgium)
