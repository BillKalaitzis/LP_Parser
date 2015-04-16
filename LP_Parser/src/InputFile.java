import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;


public class InputFile {
	
	protected BufferedReader br;
	
	public InputFile(String path){
		
		super();
		br = readFile(path);
		
	}
	
	public BufferedReader readFile(String path){
		
		File input = new File(path);
		try {
			BufferedReader br = new BufferedReader(new FileReader(input));
			return br;
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			return null;
		}
		
	}
	
	
	
}
