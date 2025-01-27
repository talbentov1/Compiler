   
import java.io.*;

import java_cup.runtime.Symbol;
import AST.*;
import IR.*;

public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Parser p;
		Symbol s;
		AST_PROGRAM AST;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);
			AST_Node.fileWriter = file_writer;
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);
			
			/*******************************/
			/* [4] Initialize a new parser */
			/*******************************/
			p = new Parser(l, file_writer);

			/***********************************/
			/* [5] 3 ... 2 ... 1 ... Parse !!! */
			/***********************************/
			AST = (AST_PROGRAM) p.parse().value;
			
			/*************************/
			/* [6] Print the AST ... */
			/*************************/
			AST.PrintMe();

			/**************************/
			/* [7] Semant the AST ... */
			/**************************/
			AST.SemantMe();
			file_writer.write("OK\n");

			/**********************/
			/* [8] IR the AST ... */
			/**********************/
			AST.IRme();
			IR.getInstance().printIRCommands();

			/*************************/
			/* [9] build CFG and run analysis */
			/*************************/
			Uninitialized_Variable_Analysis.prepare();
			Uninitialized_Variable_Analysis analysis = new Uninitialized_Variable_Analysis(file_writer);
			analysis.runAnalysis();
			
			/*************************/
			/* [10] Close output file */
			/*************************/
			file_writer.close();

			/*************************************/
			/* [10] Finalize AST GRAPHIZ DOT file */
			/*************************************/
			AST_GRAPHVIZ.getInstance().finalizeFile();			
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


