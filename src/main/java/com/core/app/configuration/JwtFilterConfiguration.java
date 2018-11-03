package com.core.app.configuration;

import com.auth0.jwt.JWT;
import com.core.app.entities.Role;
import com.core.app.services.ShelterTokenService;
import com.core.app.utils.Helper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.core.app.constants.GeneralConstants.*;
import static com.core.app.constants.HttpConstants.*;


@Configuration
public class JwtFilterConfiguration extends GenericFilterBean {

	private final ShelterTokenService tokenService;


	@Autowired
			JwtFilterConfiguration(ShelterTokenService tokenService) {
		this.tokenService = tokenService;
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String token = Helper.getTokenFromHeader(request);
		boolean isMethodOptions = this.skipTokenCheckingForOptionsRequest(request, response);
		if (!isMethodOptions) {
			this.processNonOptionsRequest(request, response, chain, token);
		}
	}

	private void processNonOptionsRequest(HttpServletRequest request, HttpServletResponse response, FilterChain chain, String token) throws IOException, ServletException {
		if (this.isRouteAllowedWithoutToken(request)) {
			response.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(request, response);
		} else {
			if (token == null) {
				sendInvalidTokenResponse(response);
			} else {
				proceedRequestWithToken(chain, request, response, token);
			}
		}
	}

	private boolean skipTokenCheckingForOptionsRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getMethod().equalsIgnoreCase(OPTIONS)) {
			response.sendError(HttpServletResponse.SC_OK, SUCCESS);
			return true;
		}
		return false;
	}

	private void proceedRequestWithToken(FilterChain chain, HttpServletRequest request, HttpServletResponse response, String token) throws IOException, ServletException {
		boolean isTokenValid = this.tokenService.isTokenValid(token);
		if (isTokenValid) {
			if (isAccessibleForAdminOnly(request)) {
				handleAccessToAdminRoutes(chain, request, response, token);
			} else {
				ObjectId userId = this.tokenService.getShelterIdFromToken(token);
				request.setAttribute(USER_ID, userId);
				chain.doFilter(request, response);
				tokenService.updateTokenExpirationTime(token);
			}
		} else {
			sendInvalidTokenResponse(response);
		}
	}

	private void handleAccessToAdminRoutes(FilterChain chain, HttpServletRequest request, HttpServletResponse response, String token) throws IOException, ServletException {
		if (isAdmin(JWT.decode(token).getClaim(USER_ROLE).asString())) {
			ObjectId userId = this.tokenService.getShelterIdFromToken(token);
			request.setAttribute(USER_ID, userId);
			chain.doFilter(request, response);
			tokenService.updateTokenExpirationTime(token);
		} else {
			sendForbiddenResponse(response);
		}
	}

	private void sendInvalidTokenResponse(HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, INVALID_TOKEN);
	}

	private void sendForbiddenResponse(HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN, FORBIDDEN);
	}

	private boolean isRouteAllowedWithoutToken(HttpServletRequest request) {
		return (requestUriEquals(request, "/") ||
				requestUriContains(request, "swagger") ||
				requestUriContains(request, "api-docs") ||
				requestUriContains(request,"/images") ||
				requestUriContains(request,"/css") ||
				requestUriContains(request,"/fonts") ||
				requestUriContains(request, "/favicon") ||
				requestUriContains(request, "/v1/shelter/admins/login") ||
				requestUriContains(request, "/v1/shelter/admins/register") ||
				requestUriContains(request, "/v1/shelters") ||
				requestUriContains(request, "/v1/shelters/shelter") ||
                requestUriContains(request, "/v1/pets") ||
                requestUriContains(request, "/v1/test"));

	}

	private boolean isAccessibleForAdminOnly(HttpServletRequest request) {
		return requestUriContains(request, "/admin");
	}

	private boolean isAdmin(String role) {
		return role.equalsIgnoreCase(Role.ADMIN.name()) || role.equalsIgnoreCase(Role.SUPER_ADMIN.name());
	}

	private boolean requestUriContains(HttpServletRequest request, String route) {
		return request.getRequestURI().contains(route);
	}

	private boolean requestUriEquals(HttpServletRequest request, String route) {
		return request.getRequestURI().equals(route);
	}




}
