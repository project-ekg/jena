package jena_practise;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.tdb.TDBLoader;
import java.io.BufferedWriter;

public class BAAD
{
	public static void main(String[] args) 
	{
		//Change location to the data-set on your local host
		TDB.getContext().set(TDB.symUnionDefaultGraph, true);
		String Directory = "C:\\Users\\sanid\\Jena.cair\\datasets\\BAAD_1_Lethality";
		String Ont_file = "location";
		String graphURI = "location";
		Dataset dataset = TDBFactory.createDataset(Directory);
		
		Model model = dataset.getNamedModel(graphURI);
		
		TDBLoader.loadModel(model, Ont_file);
		dataset.addNamedModel(graphURI, model);
	
		
		//	READ DATA-SET
		dataset.begin(ReadWrite.READ);
			// Get model inside the transaction
		//Model model = dataset.getDefaultModel() ;
		dataset.end() ;
		
		/*
		//WRITE DATA-SET
		dataset.begin(ReadWrite.WRITE) ;
			//model = dataset.getDefaultModel() ;
		dataset.end() ;
		*/
		
		
		//QUERY
		
		String querystring = "Enter query here";
		Query query = QueryFactory.create(querystring);
		QueryExecution qexec = QueryExecutionFactory.create(query, dataset);
		ResultSet results = qexec.execSelect();
		ResultSetFormatter.outputAsCSV((ResultSet) System.out);
		
	}
}