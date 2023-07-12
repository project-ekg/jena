package model;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL2;

public class model 
{
	public static void main( String[] args ) throws Exception 
    {
		
		//kindly verify file paths
		
		String ds_file_path 		= "/home/ubuntu/eclipse-workspace/map/src/data/BAAD.csv";
//		String ds_file_path 		= "/home/ubuntu/eclipse-workspace/map/src/data/demo.csv";7
		String rdf_output_file_path = "/home/ubuntu/eclipse-workspace/map/src/data/BAAD_Lethality_ont.ttl";
//		String rdf_output_file_path = "/home/ubuntu/eclipse-workspace/map/src/data/demo_ont_new.ttl";
		String query_file_path 		= "/home/ubuntu/eclipse-workspace/map/src/data/query.txt";
		String cord_file_path 		= "/home/ubuntu/eclipse-workspace/map/src/data/cords.json";
		int Total_Rows 				= 392;
		int Total_Cols 				= 9;
		String[][] data = readArrayFromFile(ds_file_path, Total_Cols);


		//------------------------- MODEL-CREATION ------------------------------

        //Global Name-space
    	String URI = "http://www.owl-ontologies.com/OntologyBase.owl#";
    	String NS = "https://BAAD#";
    	OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM_RULE_INF);
    	Resource OWL = model.getResource(URI);   	    	
    	OntClass World = model.createClass( NS + "World");
		int rp = 1;
		
		// Continent Iterator loop
    	for ( int cont = 0 ; cont < Total_Rows; cont++)
    	{
    		if( rp == (Total_Rows)) {break;}		
			String continent = data[rp][3];	    				
			OntClass Continent = model.createClass( NS + continent);
		
			//Country Iterator loop
	    	for ( int coun = 0 ; coun < Total_Rows ; coun++)
    		{
    			if (rp == Total_Rows || data[rp][3] != continent) {break;}		
    			String country = data[rp][6];
    			String Latitude  = data[rp][4];
            	String Longitude = data[rp][5];
            	Property lat_prop = ResourceFactory.createProperty(NS+"latitude");
            	Property long_prop = ResourceFactory.createProperty(NS+"longitude");
    			
            	Resource Country
    	  	  	= model.createResource(NS + country)
    	  	  	.addProperty(lat_prop, model.createTypedLiteral(Latitude))
    	  	  	.addProperty(long_prop, model.createTypedLiteral(Longitude));
   			
    			//Group Iterator loop
    	    	for ( int grp = 0 ; grp < Total_Rows ; grp++)
        		{
        			if(rp == Total_Rows || data[rp][6] != country) {break;}
        			String group = data[rp][1];
        	    	//Read Properties for Group
        			String GroupURI    			= data[rp][1];
        	    	String GroupID    			= data[rp][0];
        	    	String COWMasterCountryName	= data[rp][2];
        	    	String Fatalities   		= data[rp][7];
        	    	String Territories    		= data[rp][8];
        	    	Property cow_prop	= ResourceFactory.createProperty(NS+"COWMasterCountryName");
                	Property fat_prop	= ResourceFactory.createProperty(NS+"Fatalities");
                	Property terr_prop	= ResourceFactory.createProperty(NS+"Territories");
                	Property gid_prop	= ResourceFactory.createProperty(NS+"GroupID");
        			
        			Individual Group
        	  	  	= (Individual) model.createIndividual(GroupURI, Country)    	
        	       .addProperty(gid_prop,	model.createTypedLiteral(GroupID))
        	       .addProperty(fat_prop,	model.createTypedLiteral(Fatalities))
        	       .addProperty(cow_prop,	model.createTypedLiteral(COWMasterCountryName))
        	       .addProperty(terr_prop,	model.createTypedLiteral(Territories));  
        		
        			rp++;
        		}		
        		Continent.addSubClass(Country);
    		}
	    	World.addSubClass(Continent);	
		}		
        
    	/* CODE FOR WRITING RDF INTO NEW FILE (if required) */
//    	model.write(System.out,"TURTLE");
    	System.out.println("Model Created \n");
    	
		FileWriter outfile1 = new FileWriter(rdf_output_file_path);
		try 
		{
	    	model.write(outfile1, "TURTLE");
//			ResultSet results = model.write(rdf_output_file_path);
//			outfile.write(results);
			System.out.println("ttl file written into");
			outfile1.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("error in writing ttl file");

		}
    	 	
    	
//------------------------- 	QUERY	 ------------------------------
    	
    //QUERY-READ
    	String querystring = "";
    	try 
    	{
			File queryfile = new File(query_file_path);
			Scanner myReader = new Scanner(queryfile);
			while (myReader.hasNextLine()) 
			  {
				String str;
				str = myReader.nextLine();
			    querystring = querystring + "\n" + str ;

			  }
			  myReader.close();
			  System.out.println(querystring + "\n");
			  System.out.println("Query Read \n");
		} 
    	catch (FileNotFoundException e) 
    	{
			// TODO Auto-generated catch block
			System.out.println("Query File not found \n");
			e.printStackTrace();
		}
    	
    	
    // QUERY-EXECUTE AND WRITE
    	Query query = QueryFactory.create(querystring);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
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
			System.out.println("error in writing coordinates");

		}
		qexec.close();
	}
	
	
	
//--------------------------	CSV TO 2D ARRAY FUNCTION	------------------------
	
	public static String[][] readArrayFromFile(String filename, int amountofFields) throws IOException 
	{
	    List<String> recordList = new ArrayList<String>();
	    
	    String delimiter = ",";
	    String currentLine = "";
	    
    	FileReader fr = new FileReader(filename);
    	BufferedReader br = new BufferedReader(fr);
    	
    	while((currentLine = br.readLine())!= null)
    	{
    		recordList.add(currentLine);
    	}
    	
    	int recordCount = recordList.size();
    	
    	String arraytoReturn[][] = new String[recordCount][amountofFields];
    	String[] data;
    	
    	for(int i = 0; i<recordCount ; i++)
    	{
    		data = recordList.get(i).split(delimiter);

    		for(int j = 0; j < data.length ; j++)
    		{
	    		arraytoReturn[i][j] = data[j];
    		}
	    	
    	}	
	    	return arraytoReturn;
	}
}