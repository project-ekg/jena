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
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDB;
import org.apache.jena.tdb.TDBFactory;
import org.apache.jena.tdb.TDBLoader;
import java.io.BufferedWriter;

public class BAAD_query_test
{
	static String Data = "C:\\Users\\sanid\\eclipse-workspace\\jena\\src\\jena_practise\\demo_ont2.ttl";

	public static void main(String[] args) 
	{
		RDFConnection conn = RDFConnection.connect(Data);
				conn.load(Data) ;
				QueryExecution qExec = conn.query("SELECT DISTINCT ?s { ?s ?p ?o }") ;
				ResultSet rs = qExec.execSelect() ;
				while(rs.hasNext()) {
				  QuerySolution qs = rs.next() ;
				  Resource subject = qs.getResource("s") ;
				  System.out.println("Subject: " + subject) ;
				}
				qExec.close() ;
				conn.close() ;
		
	}
}