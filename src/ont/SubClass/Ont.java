package jena_practise;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.VCARD;
public class onto 
{
  
	public static void main(String[] args) 
	{
		String NS = "https://animals#";
		OntModel m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RULE_INF);
		  OntClass A = m.createClass( NS + "A" );
		  OntClass B = m.createClass( NS + "B" );
		  OntClass C = m.createClass( NS + "C" );
		  OntClass D = m.createClass( NS + "D" );
		  A.addSubClass( B );
		  A.addSubClass( C );
		  C.addSubClass( D );
		  
		RDFDataMgr.write(System.out,m, Lang.TURTLE);

	}

}
