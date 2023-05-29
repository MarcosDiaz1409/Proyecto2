import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import dataAccessLayer.EmbeddedNeo4j;

/**
 * Servlet implementation class DeleteMovieServlet
 */
@WebServlet("/DeleteMovieServlet")
public class DeleteMovieServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    JSONObject myResponse = new JSONObject();
			 	
		JSONArray deleteResult = new JSONArray();
		String title = request.getParameter("title");
		
		try ( EmbeddedNeo4j neo4jDriver = new EmbeddedNeo4j( "bolt://44.210.137.6:7687", "neo4j", "dares-brain-tar" ) )
        {
		 	String myResultTx = neo4jDriver.deleteMovie(title);
        	
		 	myResponse.put("resultado", myResultTx);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			myResponse.put("resultado", "Error: " + e.getMessage());
		}
 	
 	
		out.println(myResponse);
		out.flush();
		
	}
	
}
	