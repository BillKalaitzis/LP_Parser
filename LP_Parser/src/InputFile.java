import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;


public class InputFile {
	
	private File input;
	
	public InputFile(String path){
		
		super();
		readFile(path);
		
		
	}
	
	public BufferedReader readFile(String path){
		
		File input = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(input));
			System.out.println("success!");
			return br;
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return null;
		}
		
	}
	
	
}
