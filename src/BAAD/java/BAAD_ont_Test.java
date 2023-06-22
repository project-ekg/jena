package jena_practise;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.OWL2;
import org.apache.jena.vocabulary.VCARD;

public class BAAD_ont_Test 
{
	public static void main( String[] args ) 
    {
        //Global Namesapce
    	String URI = "http://www.owl-ontologies.com/OntologyBase.owl#";
    	String NS = "https://BAAD#";
    	
    	
    	OntModel model = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM_RULE_INF);
        
    	Resource OWL = model.getResource(URI);
    	
    	// Variables == CSV.read
    	String GroupURI    			= "http://somewhere/Group#";
    	String GroupID    			= "ID";
    	String COWMasterCountryName	= "Country";
    	String Fatalities   		= "Num";
    	String Territories    		= "1/0 " ;
    	    	
    	OntClass Continent = model.createClass( NS +"Continent");
    	
    	//In Iterator
    	OntClass Country
  	  	= (OntClass) model.createClass(NS+"Country")
  	  	.addProperty(OWL2.annotatedProperty,"LATITUDE")
  	  	.addProperty(OWL2.annotatedProperty,"LONGITUDE");
    	
    	Individual Group
  	  	= (Individual) model.createIndividual(GroupURI, Country)    	
       .addProperty(OWL2.annotatedProperty, GroupURI + GroupID)
       .addProperty(OWL2.annotatedProperty, GroupURI + Fatalities)
       .addProperty(OWL2.annotatedProperty, GroupURI + COWMasterCountryName)
       .addProperty(OWL2.annotatedProperty, GroupURI + Territories);
 
    	Continent.addSubClass(Country);
        
    	model.write(System.out,"TURTLE");
        		

    }
}