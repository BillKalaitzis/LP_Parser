import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class OutputFile {

	protected BufferedWriter bw;
	
	public OutputFile(ArrayList<ArrayList<Integer>> A, ArrayList<Integer> b, ArrayList<Integer> c,ArrayList<Integer> Eqin,int minmax){
		
		super();
		writeFile(A,b,c,Eqin,minmax);
		System.out.println("Data successfully saved on file");
		
	}
	
	public void writeFile(ArrayList<ArrayList<Integer>> A, ArrayList<Integer> b, ArrayList<Integer> c,ArrayList<Integer> Eqin,int minmax){
		
		File output = new File("Data/output.txt");
		try {
			bw =  new BufferedWriter(new FileWriter(output));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			bw.write("minmax = " + minmax + "\n\n");
			bw.write("A = " );
			int counter = 0;
			for(ArrayList<Integer> i:A){
				if(counter == 0)
					bw.write(i.toString() + "\n");
				else
					bw.write("    " + i.toString()+ "\n");
				counter++;
			}
			bw.write("\nb = " + b + "\n\n");
			bw.write("c = " + c + "\n\n");
			bw.write("Eqin = " + Eqin);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
