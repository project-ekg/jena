package model;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;


public class query_test  
{
private static ByteArrayOutputStream outfile;

//	static String file = "text.txt";
	public static void main (String []agrs) throws IOException
	{
		
		String rdf_output_file = "/home/ubuntu/eclipse-workspace/map/src/data/BAAD_Lethality_ont.ttl";
//		String rdf_output_file 		= "/home/ubuntu/eclipse-workspace/map/src/data/demo_ont_new.ttl";
		String cord_file_path 		= "/home/ubuntu/eclipse-workspace/map/src/data/cords.json";
		FileManager.get().addLocatorClassLoader(model.class.getClassLoader());
		Model model = FileManager.get().loadModelInternal(rdf_output_file);
		
		String querystring =
				"prefix owl:  <http://www.w3.org/2002/07/owl#>\n"
				+ "prefix rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n"
				+ "prefix xsd:  <http://www.w3.org/2001/XMLSchema#> \n"
				
				+ "SELECT ?x ?y ?z "
				+ "WHERE { \n"
				
				+ " ?group   	<https://BAAD#Fatalities> 	?z  .	\n"
				
				+ "	?country	<https://BAAD#latitude> 	?x ;	\n"
				+ "   			<https://BAAD#longitude> 	?y . }	\n"
				
				+ " ORDER BY DESC (?z)	\n"
				+ " LIMIT 5";
		
		
		Query query = QueryFactory.create(querystring);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		System.out.println("query created");

		
		// PRINT IN CONSOLE
		
//		ResultSet results = qexec.execSelect();
//
//		try 
//		{
//			System.out.println(results);
//			while(results.hasNext())
//			{
//				QuerySolution qs = results.next();
//				RDFNode Subject 	= qs.get("s");
//				RDFNode Predicate 	= qs.get("p");
//				RDFNode Object 		= qs.get("o");
//				System.out.println(qs.toString());
//				System.out.println("executed");
////				System.out.println(Object);
//			}
//
//		} 
//		
//		catch (Exception e) 
//		{
//			e.printStackTrace();
//			System.out.println("exception");
//
//		}
//		System.out.println(results);
		
		
		// JSON FILE
		
		FileWriter outfile = new FileWriter(cord_file_path);
		try 
		{
			ResultSet results = qexec.execSelect();
			ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
			ResultSetFormatter.outputAsJSON(outputstream, results);
			String json = new String(outputstream.toByteArray());
			outfile.write(json);
			System.out.println("Result Coordinates Written \n");
			System.out.println(json);
			outfile.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("error in writing");

		}
		
		
		qexec.close();
	}
		
}


	    