package kz.kegoc.bln.webapi.auth;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;

import kz.kegoc.bln.entity.auth.dto.AuthDataDto;
import kz.kegoc.bln.service.auth.AuthService;


@RequestScoped
@Path("/auth")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class AuthResourceImpl {
	
	@POST
	public Response auth(AuthDataDto authData) {
		Long userId = authService.auth(authData.getUserName(), authData.getPassword());
		
		Response resp = null;
		if (userId>0)
			resp = Response.ok("{ \"success\": true" + "}").build();
		else
			resp = Response.ok("{ \"success\": false, \"message\": {\"errMsg\": \"Invalid user name or password\"}" + "}").build();
			
		return resp;		
	}
	
	@Inject private AuthService authService;
}
