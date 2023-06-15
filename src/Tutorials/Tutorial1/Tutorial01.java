package jena_practise;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.VCARD;

public class Tutorial01 
{
    static String personURI    = "http://somewhere/JohnSmith";
    static String fullName     = "John Smith";
    
	public static void main(String[] args) 
	{
		// create an empty model
		Model model = ModelFactory.createDefaultModel();
	
		// create the resource
		Resource johnSmith = model.createResource(personURI);
	
		// add the property
		johnSmith.addProperty(VCARD.FN, fullName);
	  
		model.write(System.out,"Turtle");

	}

}
