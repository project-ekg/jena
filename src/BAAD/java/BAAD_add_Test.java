package jena_practise;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.VCARD;

public class BAAD_add_Test 
{
	public static void main( String[] args ) throws Exception 
    {
		String file = "C:\\Users\\sanid\\OneDrive\\Desktop\\Jena.local\\Datasets\\BAAD_1_Lethality\\demo.csv";
		int Total_Rows = 7;
		int Total_Cols = 9;
		String[][] data = readArrayFromFile(file,9);
        //Test readArrayFromFile function
//		System.out.println(data[6][1]);
        
        //Global Name-space
    	String URI = "http://www.owl-ontologies.com/OntologyBase.owl#";
    	String NS = "https://BAAD#";
    	
    	OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM_RULE_INF);
        
    	Resource OWL = model.getResource(URI);   	    	
    	
    	OntClass World = model.createClass( NS + "World");
    	
//    	row_ptr		= null;
//		continent	= null;
//		country		= null;
//		group		= null;
		int rp = 0;
    	for ( int cont = 0 ; cont < Total_Rows; cont++)
    	{
    		if( rp == (Total_Rows-1)) {break;}
    		
			
		//Initialise pointer for continent as null value
		// Continent Iterator loop
			String continent = data[rp][3];	    				
			OntClass Continent = model.createClass( NS + continent);
	
			//Initialise pointer for Country as null value
			//Country Iterator loop
	    	for ( int coun = 0 ; coun < Total_Rows ; coun++)
    		{
    			if (data[rp][3] != continent) {break;}
    			
    			String country = data[rp][6];
    			
    			String Latitude  = data[rp][4];
            	String Longitude = data[rp][5];
    			
    			OntClass Country
    	  	  	= (OntClass) model.createClass(NS + country)
    	  	  	.addProperty(OWL2.annotatedProperty, NS+Latitude)
    	  	  	.addProperty(OWL2.annotatedProperty, NS+Longitude);
    			
    			
    			//Initialise pointer for Group as null value
    			//Group Iterator loop
    	    	for ( int grp = 0 ; grp < Total_Rows ; grp++)
        		{
        			if(data[rp][6] != country) {break;}

        			rp++;
        			String group = data[rp][1];
        			
        	    	//Read Properties for Group
        			String GroupURI    			= data[rp][1];
        	    	String GroupID    			= data[rp][0];
        	    	String COWMasterCountryName	= data[rp][2];
        	    	String Fatalities   		= data[rp][7];
        	    	String Territories    		= data[rp][8];
        			
        			Individual Group
        	  	  	= (Individual) model.createIndividual(GroupURI, Country)    	
        	       .addProperty(OWL2.annotatedProperty, GroupURI + GroupID)
        	       .addProperty(OWL2.annotatedProperty, GroupURI + Fatalities)
        	       .addProperty(OWL2.annotatedProperty, GroupURI + COWMasterCountryName)
        	       .addProperty(OWL2.annotatedProperty, GroupURI + Territories);            			
        		}
        		
        		Continent.addSubClass(Country);
    		}
        		
		}	
        
    	/* CODE FOR WRITING RDF INTO NEW FILE */
    	
    	model.write(System.out,"TURTLE"); 

    }
	
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