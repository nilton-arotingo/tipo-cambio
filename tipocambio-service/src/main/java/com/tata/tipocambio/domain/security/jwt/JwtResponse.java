package com.tata.tipocambio.domain.security.jwt;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter @NoArgsConstructor
public class JwtResponse {

	private String message;
	private Status status;
	private String exceptionType;
	private String jwt;
	private Jws<Claims> jws;

	public enum Status {
		SUCCESS, ERROR
	}

	public JwtResponse(String jwt) {
		this.jwt = jwt;
		this.status = Status.SUCCESS;
	}

	public JwtResponse(Jws<Claims> jws) {
		this.jws = jws;
		this.status = Status.SUCCESS;
	}

	public static JwtResponse error(String message) {
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setStatus(Status.ERROR);
		jwtResponse.setMessage(message);
		return jwtResponse;
	}

}
