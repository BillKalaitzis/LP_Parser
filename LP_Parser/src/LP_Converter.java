import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;


public class LP_Converter {
	
	private InputFile input;
	private ArrayList<ArrayList<Integer>> A = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> b = new ArrayList<Integer>();
	private ArrayList<Integer> c;
	private ArrayList<Integer> Eqin = new ArrayList<Integer>();
	private int minmax;
	
	
	public LP_Converter(String path) throws IOException{
		
		input = new InputFile(path);
		convert();
		System.out.println("Minmax: "+ minmax + "\nA: " + A + "\nb: " + b + "\nc: " + c + "\nEqin: " + Eqin);
		save();
	}
	
	public void convert() throws IOException{
		
		String currStr;
		int index = 0;
		while(true){
			currStr = input.br.readLine();

			if(currStr.trim().equals("")){
				System.out.println("Please dont leave empty lines");
				System.exit(1);
			}
			else{
				try{
					if(currStr.replaceAll("\\s", "").equals("end"))
						break;
				}catch(NullPointerException npe){
					System.out.println("Keyword 'end' not detected");
					System.exit(1);
				}
				stringParser(currStr,index);
				}
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
			getEqin(str,index);
			
		}
		
		else if(index > 1){
			getMultipliers(str,index);
			getRHS(str,index);
			getEqin(str,index);
		}
		
		
	}
	
	public void detect_minmax(String str){
		
		if(str.substring(0, 3).toLowerCase().equals("min") )
			minmax = -1;
		else if(str.substring(0, 3).toLowerCase().equals("max") )
			minmax = 1;
		else{
			System.out.println("Please specify the type of the linear problem");
			System.exit(1);
		}
	}
	
	public void detect_st(String str){
		
		if(!str.substring(0,3).toLowerCase().equals("s.t")){
			System.out.println("No constraints have been detected, "
							 + "make sure to enter the keyword <S.T> before your constraints" );
			System.exit(1);
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
		
		if(signal > 1) {
			checkNumOfVars(indexes.size(),tmp);
		}
		
				
		for(int i=0;i<indexes.size();i++){
			if(i==0)
				d.add(Integer.parseInt(tmp.substring(0, indexes.get(0))));
			else {
				try{
				d.add(Integer.parseInt(tmp.substring(indexes.get(i-1)+2, indexes.get(i))));
				}catch(NumberFormatException nfe){
					System.out.println("The right half side of your constraints contains restricted characters");
					System.exit(1);
				}
				
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
			System.out.println("Your objective function contains restricted characters (<,>,=)");
			System.exit(1);
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
					System.exit(1);
				}
			}
			tmp = str.substring(ptr+1, str.length());	
			try{
			b.add(Integer.parseInt(tmp));
			}catch(NumberFormatException nfe){
				System.out.println("The right half side of your constraints contains restricted characters\nOR doesn't contain any numbers at all");
				System.exit(1);
			}
		}
		
		else{
			System.out.println("One of your constaints doesn't contain a right half side");
			System.exit(1);
			}
	}
	
	
	public void getEqin(String str, int index){
		
		String tmp = str;
		
		if(index == 1)
			tmp = tmp.substring(3, str.length());
		
		if(tmp.contains("<"))
			Eqin.add(-1);
		else if(tmp.contains(">"))
			Eqin.add(1);
		else if(tmp.contains("="))
			Eqin.add(0);
		else //This error will be captured by the getRHS method
			return;
		
	}
	
	public void save(){
		OutputFile output = new OutputFile(A,b,c,Eqin,minmax);
	}
	
	public void checkNumOfVars(int size, String tmp){
		if(size > c.size() && tmp.lastIndexOf('x') < tmp.indexOf('=')){
			System.out.println("Your constraints contain more variables than your objective function");
			System.exit(1);
		}
		
		
	}

}
