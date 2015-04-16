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
			getMultipliers(str,0);
			checkForSymbols(str);
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
	
	public void getMultipliers(String str,int signal){
		
		ArrayList<Integer>indexes = new ArrayList<Integer>();
		ArrayList<Integer>d = new ArrayList<Integer>();
		
		String tmp = str.substring(3, str.length());
		tmp = tmp.replaceAll("\\s", "");
		
		int index = 0;
		while(index < tmp.length()){
			if(tmp.charAt(index) == 'x')
				indexes.add(index);
			index++;
		}
		
		for(int i=0;i<indexes.size();i++){
			if(i==0)
				d.add(Integer.parseInt(tmp.substring(0, indexes.get(0))));
			else
				d.add(Integer.parseInt(tmp.substring(indexes.get(i-1)+2, indexes.get(i))));
		}
		
		if(signal == 0){
			c = new ArrayList<Integer>(d);
		}
		
		//more signal codes to be added
		
	}
	
	public void checkForSymbols(String str){
		
		if(str.substring(3, str.length()).contains("<") ||
		   str.substring(3, str.length()).contains(">")	||
		   str.substring(3, str.length()).contains("=")){
			System.out.println("Your objective function contains forbidden characters (<,>,=)");
			return;
		}
	}
	

}
