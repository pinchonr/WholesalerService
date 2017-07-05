package com.iut.wholesalerservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@Path("/wholesalerService")
public class WholesalerServiceServlet{

	@GET @Path("/restock")
	@Produces(MediaType.APPLICATION_JSON)
	public Response wholesalerReq(@QueryParam("isbn") String isbn, @QueryParam("key") String key, 
			@QueryParam("quantity") int quantite, @QueryParam("from") String from, @QueryParam("to") String to){
		
		Response response = null;
		JSONObject error = new JSONObject();
		String regex = "^(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$";
		if(key.equals("1234")){
			if(from.equals("Shop") && to.equals("Wholesaler")){
				if(isbn.matches(regex)){
					JSONObject book = new JSONObject();
					try{
					book.put("isbn", isbn);
					book.put("stock", quantite + 5);
					} catch(Exception e){
						e.printStackTrace();
					}
					response = Response.status(200).type(MediaType.APPLICATION_JSON).entity(book.toString()).build();
				}
				else{
					try {
						error.put("message", "Bad request : invalid ISBN");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
					response = Response.status(400).type(MediaType.APPLICATION_JSON).entity(error.toString()).build();	
				}
			}
			else{
				try {
					error.put("message", "Bad request : invalid values for \"From\" and \"To\"");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response = Response.status(400).type(MediaType.APPLICATION_JSON).entity(error.toString()).build();				
			}						
		} else {
			try {
				error.put("message", "Invalid credentials");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
			response = Response.status(400).type(MediaType.APPLICATION_JSON).entity(error.toString()).build();
		}
		return response;			
	}
}