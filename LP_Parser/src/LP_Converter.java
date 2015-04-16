import java.io.IOException;
import java.util.ArrayList;


public class LP_Converter {
	
	private InputFile input;
	private ArrayList<Integer> A;
	private ArrayList<Integer> b;
	private ArrayList<Integer> c;
	private ArrayList<Integer> Eqin;
	private int minmax;
	
	
	public LP_Converter(String path) throws IOException{
		
		input = new InputFile(path);
		convert();
	}
	
	public void convert() throws IOException{
		
		String currStr;
		int index = 0;
		while((currStr = input.br.readLine())!= null){
			stringParser(currStr,index);
			index++;
		}
		
	}
	
	public void stringParser(String str,int index){
		
		if(index == 0){
			detect_minmax(str);
			}
			
		else if(index == 1){
			detect_st(str);	
		}
		
		
	}
	
	public void detect_minmax(String str){
		
		if(str.substring(0, 3).toLowerCase().equals("min") )
			minmax = -1;
		else if(str.substring(0, 3).toLowerCase().equals("max") )
			minmax = 1;
		else{
			System.out.println("Please specify the type of the linear problem");
			return;
		}
	}
	
	public void detect_st(String str){
		
		if(!str.substring(0,3).toLowerCase().equals("s.t")){
			System.out.println("No constraints have been detected, "
							 + "make sure to enter the keyword <S.T> before your constraints" );
			return;
		}	
	}
	
	

}
