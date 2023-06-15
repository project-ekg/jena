package jena_practise;
import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.*;

public class Tutorial05 {

	public static void main(String[] args) {
		// create an empty model
        Model model = ModelFactory.createDefaultModel();

        // use the RDFDataMgr to find the input file
        InputStream in = RDFDataMgr.open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException("File: " + inputFileName + " not found");
        }

        // read the RDF/XML file
        model.read(in, null);

        // write it to standard out
        model.write(System.out);

	}

}
