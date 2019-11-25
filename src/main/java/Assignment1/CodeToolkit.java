package Assignment1;

import java.io.*;
import analysis.*;

public class CodeToolkit{
	
	 public int getLOC(String filepath) throws IOException,FileNotFoundException
	    {
			
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(filepath));
			
	    	int linecount = 0;
	    	int emptylinecount=0;
	    	int singlecommentcount=0;
	    	int multicommentcount=0;
	    	int loc=0;
	    	boolean ismulticomment=false;
	    	String line;
	    	while ((line = reader.readLine())!=null) 
	    	{
	    		linecount++;
	    		if(line.trim().equals(""))
	    		{
	    			emptylinecount++;
	    		}
	    		else if((line.trim().startsWith("/*"))&&(line.trim().endsWith("*/")))
	    		{
	    			ismulticomment=false;
	    			multicommentcount++;
	    		}
	    		else if(line.trim().startsWith("/*"))
	    		{
	    			ismulticomment=true;
	    			multicommentcount++;
	    		}
	    		else if(line.trim().endsWith("*/"))
	    		{
	    			ismulticomment=false;
	    			multicommentcount++;
	    		}
	    		else if(line.trim().startsWith("//"))
	    		{
	    			singlecommentcount++;
	    		}
	    		else if(ismulticomment)//normal line part of multicomment
	    		{
	    			
	    				multicommentcount++; // 
	    			
	    		}
	    		
	    	}
	    	reader.close();
	    	
	    	loc = linecount -(emptylinecount+singlecommentcount+multicommentcount);
	    
	    	return loc;
	    }
	 
	 
	 
	 public int getCC(Graph cfg)
	    {
	    	int V = cfg.getNodeCount();
	    	int E = cfg.getEdgeCount();
	    	return E-V+2; // cyclomatic complexity
	    	
	    	
	    }
	 
	 public double getFP(String filepath)throws IOException,FileNotFoundException
	 {
		 int scale =3;
		 int CFP = 14*scale;
		 double CAF = 0.65 + (0.01*CFP);
		 int EI = 10;
		 int EO = 7;
		 int EQ = 4;
		 int fsEI = 1;// replace with 
		 int fsEO = 1;// replace with
		 int fsEQ = 1;// replace with
		 double UFP = EI*fsEI + EO*fsEO + EQ*fsEQ;
		 return UFP*CAF;
	 }

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
