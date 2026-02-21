package Ex_3;

import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class find {
	
	public static String[] findFile(String path, String filename) {
		Path pathSearch = Paths.get(path);
		
		if (Files.exists(pathSearch)) {										// need to check if path exists first
			if (Files.isDirectory(pathSearch)) {							// if its a directory then apply find to each E
				List<String> found = new ArrayList<>();						//had to bring in Arraylist due to dynamic size of return list
				try (Stream<Path> stream = Files.list(pathSearch)){			// all of this had to go in try Catch for IO or it would given an issue
					stream.forEach(p -> {									//Stream.forEach is the iteration method for a path
						String[] result = findFile(p.toString(), filename);
						Collections.addAll(found, result);
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
				return found.toArray(new String[0]);						//whole directories sub-results sent back to parent in expected type
			} else if (Files.isRegularFile(pathSearch)) {					//the docs show that Regular Files is what i should be looking through, ill try to avoid things that arent directories or regular files.
				if (pathSearch.getFileName().toString().equals(filename)) { //almost made a mistake with using a == b instead of a.equals(b) for string, glad i caught it
					return new String[] { pathSearch.toString() };
				} else {return new String[0];}
			} else {return new String[0];}									//return of empty string lists for remaining else cases, just need them to be merged back with main call
			
		} else {return new String[0];}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (String loc : findFile("C:\\Users\\redey\\Documents\\D copy\\Dropbox copy\\Hunain Col 4", "titanic.csv")) {
			System.out.println(loc);  //for test case made sure to move file into 3 different sub folders
		}
	}

}
