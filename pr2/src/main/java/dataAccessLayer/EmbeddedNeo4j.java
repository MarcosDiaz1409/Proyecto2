/**
 * 
 */
package dataAccessLayer;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;
import org.neo4j.driver.summary.ResultSummary;

import static org.neo4j.driver.Values.parameters;

import java.util.LinkedList;
import java.util.List;
/**
 * @author Administrator
 *
 */
public class EmbeddedNeo4j implements AutoCloseable{

    private final Driver driver;
    

    public EmbeddedNeo4j( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public void printGreeting( final String message )
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run( "CREATE (a:Greeting) " +
                                                     "SET a.message = $message " +
                                                     "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get( 0 ).asString();
                }
            } );
            System.out.println( greeting );
        }
    }
    
    public LinkedList<String> getActors()
    {
    	 try ( Session session = driver.session() )
         {
    		 
    		 
    		 LinkedList<String> actors = session.readTransaction( new TransactionWork<LinkedList<String>>()
             {
                 @Override
                 public LinkedList<String> execute( Transaction tx )
                 {
                     Result result = tx.run( "MATCH (a:Actor) RETURN a.Nombre");
                     LinkedList<String> myactors = new LinkedList<String>();
                     List<Record> registros = result.list();
                     for (int i = 0; i < registros.size(); i++) {
                    	 //myactors.add(registros.get(i).toString());
                    	 myactors.add(registros.get(i).get("a.Nombre").asString());
                     }
                     
                     return myactors;
                 }
             } );
             
             return actors;
         }
    }
    
    
    public LinkedList<String> getMoviesByActor(String actor)
    {
   	 try ( Session session = driver.session() )
        {
   		 
   		 
   		 LinkedList<String> actors = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (tom:Actor {Nombre:'"+ actor +"'})-[:Actua]->(Pelicula) RETURN Pelicula.Nombre");
                	//Result result = tx.run( "MATCH (a:Actor)-[Actua]->(p:Pelicula) WHERE a.Nombre = '"+ actor +"' RETURN p");
                    LinkedList<String> myactors = new LinkedList<String>();
                    List<Record> registros = result.list();
                    for (int i = 0; i < registros.size(); i++) { 
                   	 //myactors.add(registros.get(i).toString());
                   	 myactors.add(registros.get(i).get("Pelicula.Nombre").asString()); 
                    }
                    
                    return myactors;
                }
            } );
            
            return actors;
        }
   }
    
    public LinkedList<String> getMoviesByGenre(String genre)
    {
   	 try ( Session session = driver.session() )
        {
   		 
   		 
   		 LinkedList<String> genres = session.readTransaction( new TransactionWork<LinkedList<String>>()
            {
                @Override
                public LinkedList<String> execute( Transaction tx )
                {
                    Result result = tx.run( "MATCH (pelicula:Pelicula) WHERE pelicula.Genero = '" + genre + "' RETURN pelicula.Nombre");
                	//Result result = tx.run( "MATCH (a:Actor)-[Actua]->(p:Pelicula) WHERE a.Nombre = '"+ actor +"' RETURN p");
                    LinkedList<String> mygenres = new LinkedList<String>();
                    List<Record> registros = result.list();
                    for (int i = 0; i < registros.size(); i++) { 
                   	 //myactors.add(registros.get(i).toString());
                   	 mygenres.add(registros.get(i).get("pelicula.Nombre").asString()); 
                    }
                    
                    return mygenres;
                }
            } );
            
            return genres;
        }
   }
    
    public String insertMovie(int year, String genre ,String title) {
    	try ( Session session = driver.session() )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run( "CREATE (Test:Pelicula {Año:'" + year + "', Genero:'"+ genre +"', Nombre:'"+ title +"'})");
                    
                    return "Pelicula agregada!";
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }
    
    public String deleteMovie(String title) {
    	try ( Session session = driver.session() )
        {
   		 
   		 String result = session.writeTransaction( new TransactionWork<String>()
   		 
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run( "MATCH (p:Pelicula {Nombre:'"+ title +"'}) DELETE p");
                    
                    return "Pelicula eliminada!";
                }
            }
   		 
   		 );
            
            return result;
        } catch (Exception e) {
        	return e.getMessage();
        }
    }

}
