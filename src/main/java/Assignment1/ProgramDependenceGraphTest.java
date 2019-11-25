package Assignment1;

import java.io.*;
import java.util.*;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import analysis.*;

/**
 * Created by Hakeem on 06/11/2019.
 */

public class ProgramDependenceGraphTest {

	/**
	 * This is a sample test to check the results of your implementation. You can
	 * modify it to suit your tastes
	 */
	Graph graph;
	ProgramDependenceGraph pdg;
	CodeToolkit ctk = new CodeToolkit();

	public ProgramDependenceGraphTest(String classPath) {
		try {
			File folder = new File(classPath);
			
			FileInputStream in;
			ClassReader classReader;
			ClassNode cn;
			
			
			/*if(folder.listFiles().equals(null))
				System.out.println("NOT FUND!!!!");*/
				
			for (File f : folder.listFiles()) {
				cn = new ClassNode(Opcodes.ASM4);
				String filepath = f.getPath();
				//pdg = new ProgramDependenceGraph(new ClassNode(),new MethodNode());
				//Graph cfg = pdg.getCFG();
				
				in = new FileInputStream(filepath);
				
				
				
				if(filepath.trim().endsWith(".class"))// if it's a class file
				{	
			
					classReader = new ClassReader(in);
					classReader.accept(cn, 0);
				
					for (MethodNode mn : (List<MethodNode>) cn.methods) {
						pdg = new ProgramDependenceGraph(cn, mn);
						graph = pdg.computeResult();
						
						Set<Node> nodeList = graph.getNodes();
						List<Node> list = new ArrayList<Node>(nodeList); 
						Node n = list.get(list.size()/2); // For example, let us get the backward slice of the middle Node
						System.out.println(cn.name + ", " + mn.name + ", " + testTightness() + ", " + testOverlap() + ", " + testBackwardSlice(n));
						
						System.out.println(graph); // this prints the digraph
						System.out.println("Cyclomatic complexity of" + cn.name + " is " + pdg.getCC());
					
						}
						in.close();
				}
				else if(filepath.trim().endsWith(".java"))// if it's a java file
				{
					//System.out.println("Java file : " + f.getName() + " "  ctk.getLOC(f.getPath()) );
					System.out.println("LOC of " +  f.getName() + " is " + ctk.getLOC(filepath));
					System.out.println("Function Point of " +  f.getName() + " is " + ctk.getFP(filepath));
					
					//System.out.println("Cyclomatic complexity of" + f.getName() + " is" + ctk.getCC(pdg.getCFG()));
				}
			}
		
			System.out.println("Done");
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	private Set<Node> testBackwardSlice(Node n) {
		return pdg.backwardSlice(n);
	}

	private double testTightness() {
		return pdg.computeTightness();
	}

	private double testOverlap() {
		return pdg.computeOverlap();
	}

	public static void main(String[] args) throws IOException {
		// Set the path to point to the class you want to test
		//String path = "../assignment-1-zabdulz/bin/net/sf/freecol/tools/";
		//String path = "/s_home/ib137/Desktop/class_fold/";
		String path = "/s_home/ib137/Desktop/reassign/ProgramSlicer-master/INPUTS";
		//print the header to the CSV file
		System.out.println("ClassName, MethodName, Tighness, Overlap, BackWardSlice");
		File folder = new File(path);
		if(folder.isDirectory())
			System.out.println("Directory");
		else
			System.out.println("Not directory");
		ProgramDependenceGraphTest pgdt = new ProgramDependenceGraphTest(path);
	}

}
