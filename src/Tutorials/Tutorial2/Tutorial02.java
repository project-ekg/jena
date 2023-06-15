package jena_practise;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.VCARD;
public class Tutorial02 
{

	public static void main(String[] args) 
	{
		// Singly hanging resource
		
		/*
		
		String URI = "https://abcData/animal";
		
		Model m = ModelFactory.createDefaultModel();
		
		Resource animal = m.createResource(URI);
		animal.addProperty(VCARD.FN, "Elephant");
		
		m.write(System.out,"Turtle");
		
		*/
		
		
		// some definitions
		String personURI    = "http://somewhere/JohnSmith";
		String givenName    = "John";
		String familyName   = "Smith";
		String fullName     = givenName + " " + familyName;

		// create an empty Model
		Model model = ModelFactory.createDefaultModel();

		// create the resource
		//   and add the properties cascading style
		Resource johnSmith
		  = model.createResource(personURI)
		         .addProperty(VCARD.FN, fullName)
		         .addProperty(VCARD.N,
		                      model.createResource()
		                           .addProperty(VCARD.Given, givenName)
		                           .addProperty(VCARD.Family, familyName));
		
		//model.write(System.out,"Turtle");
		
		// now write the model in a pretty form
		RDFDataMgr.write(System.out, model, Lang.RDFXML );

	}

}
