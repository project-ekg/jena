package jena_practise;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.VCARD;

public class Test 
{
    String URI = "http://www.owl-ontologies.com/OntologyBase.owl#";
    public static void main( String[] args ) 
    {
    	String NS = "https://animals#";
    	OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_DL_MEM_RULE_INF);
        OntClass Animals  = m.createClass(NS + "Animals");
        OntClass Domestic = m.createClass(NS + "Domestic");
        OntClass Aquatic  = m.createClass(NS + "Aquatic");
        OntClass Wild	  = m.createClass(NS + "Wild");

        Animals.addSubClass(Domestic);
        Animals.addSubClass(Aquatic);
        Animals.addSubClass(Wild);
        
        Individual cat = Domestic.createIndividual(NS + "cat");
        Individual dog = Domestic.createIndividual(NS + "dog");
        Individual cow = Domestic.createIndividual(NS + "cow");
        
        Individual Shark  = Aquatic.createIndividual(NS + "Shark");
        Individual Turtle = Aquatic.createIndividual(NS + "Turtle");
        Individual Fish   = Aquatic.createIndividual(NS + "Fish");
        
        Individual Tiger = Wild.createIndividual(NS + "Tiger");
        Individual Lion = Wild.createIndividual(NS + "Lion");
        Individual Bear = Wild.createIndividual(NS + "Bear");
        
		m.write(System.out,"TURTLE");
        		

    }
}