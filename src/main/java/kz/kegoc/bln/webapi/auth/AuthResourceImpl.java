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
import org.apache.commons.lang3.StringUtils;


@RequestScoped
@Path("/auth")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class AuthResourceImpl {
	
	@POST
	public Response auth(AuthDataDto authData) {
		boolean auth = false;

		if (StringUtils.isEmpty(authData.getUserName()))
			return buildResponse(false, "Введите имя пользователя");

		if (StringUtils.isEmpty(authData.getPassword()))
			return buildResponse(false, "Введите пароль");

		if (!authData.getPassword().equals("123456"))
			return buildResponse(false, "Имя пользователя или пароль указаны неверно");

		Long userId = authService.auth(authData.getUserName(), authData.getPassword());
		if (userId<=0)
			return buildResponse(false, "Пользователь не зарегистрирован");

		return buildResponse(true, "success");
	}

	private Response buildResponse(boolean auth, String msg) {
		Response resp = null;
		if (auth)
			resp = Response.ok("{ \"success\": true" + "}").build();
		else {
			String template = "{ \"success\": false, \"message\": {\"errMsg\": \"@msg\"}" + "}";
			template = template.replace("@msg", msg);
			resp = Response.ok(template).build();
		}
		return resp;
	}

	@Inject
	private AuthService authService;
}
