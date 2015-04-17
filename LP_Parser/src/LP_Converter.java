import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;


public class LP_Converter {
	
	private InputFile input;
	private ArrayList<ArrayList<Integer>> A = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> b = new ArrayList<Integer>();
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
		while(true){
			currStr = input.br.readLine();
			if(currStr.replaceAll("\\s", "").equals("end"))
				break;
			stringParser(currStr,index);
			index++;
			
		}
		
	}
	
	public void stringParser(String str,int index){
		
		if(index == 0){
			detect_minmax(str);
			getMultipliers(str,index);
			checkForSymbols(str);
			}
			
		else if(index == 1){
			detect_st(str);	
			getMultipliers(str,index);
			getRHS(str,index);
			
		}
		
		else if(index > 1){
			getMultipliers(str,index);
			getRHS(str,index);
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
		
		String tmp ;
		
		if(signal == 0 || signal == 1)
			tmp= str.substring(3, str.length());
		
		else
			tmp = str;
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
			else {//BUG
				d.add(Integer.parseInt(tmp.substring(indexes.get(i-1)+2, indexes.get(i))));
				
			}
		}
		
		if(signal == 0){
			c = new ArrayList<Integer>(d);
		}
		
		else if(signal == 1){
			A.add(new ArrayList<Integer>());
			A.get(signal-1).addAll(d);
		}
		
		else if(signal > 1 ){
			A.add(new ArrayList<Integer>());
			A.get(signal-1).addAll(d);	
		}
		
		
	}
	
	public void checkForSymbols(String str){
		
		if(str.substring(3, str.length()).contains("<") ||
		   str.substring(3, str.length()).contains(">")	||
		   str.substring(3, str.length()).contains("=")){
			System.out.println("Your objective function contains forbidden characters (<,>,=)");
			return;
		}
	}
	
	public void getRHS(String str, int index){
		
		int ptr=0;
		int rhs;
		int counter = 0; 
		String tmp;
		
		if(index == 1)
			str = str.substring(3, str.length());
		str = str.replaceAll("\\s", "");
		
		if(str.contains("<") || str.contains(">") || str.contains("=")){
			for(int i=0; i < str.length(); i++){
				if(str.charAt(i) == '='){
					counter++;
					ptr =  i;
				}
				if(counter > 1){
					System.out.println("Your LP containes more than 1 equals signs");
					return;
				}
			}
			tmp = str.substring(ptr+1, str.length());	
			b.add(Integer.parseInt(tmp));
		}
		
		else
			System.out.println("Your LP doesn't contain a right half side");
	}
	
	

}
