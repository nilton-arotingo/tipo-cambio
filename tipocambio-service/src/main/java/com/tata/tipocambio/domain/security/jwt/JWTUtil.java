package com.tata.tipocambio.domain.security.jwt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTUtil {

	@Value("${jwt.segundosValidezToken}")
	private Integer segundosValidezToken;
	
	@Value("${jwt.secretManager}")
	private String secretManager;

	public JwtResponse crearJWT(Map<String, Object> claims) {
		String jws = null;
		try {
			jws = Jwts.builder()
					.setClaims(claims)
					.setExpiration(Date.from(LocalDateTime.now().plusSeconds(segundosValidezToken).atZone(ZoneId.systemDefault()).toInstant()))
					.signWith(SignatureAlgorithm.HS256, getHS256SecretBytes())
					.compact();
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			log.error("Error al generar el jwt", e);
		}
		return new JwtResponse(jws);
	}
	
	public JwtResponse crearJWT(Map<String, Object> claims, int segundosExpiracion) {
		try {
			return new JwtResponse(Jwts.builder().setClaims(claims)
					.setExpiration(Date.from(LocalDateTime.now().plusSeconds(segundosValidezToken).atZone(ZoneId.systemDefault()).toInstant()))
					.signWith(SignatureAlgorithm.HS256, getHS256SecretBytes()).compact());
		} catch (Exception e) {
			log.error("crearJWT - Error: {}", e);
			return JwtResponse.error(e.getMessage());
		}
	}

	public byte[] getHS256SecretBytes() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		return TextCodec.BASE64.decode(secretManager);
	}
	
	

	private SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
        @Override
        public byte[] resolveSigningKeyBytes(@SuppressWarnings("rawtypes") JwsHeader header, Claims claims) {
        	byte[] byteArray = null;
        	try {
        		byteArray = TextCodec.BASE64.decode(secretManager);
			} catch (Exception e) {
				e.getMessage();
			}
			return byteArray;
		}
	};
	
	public boolean esValidoTokenJWT(String tokenJWT) {
		try {
			Jws<Claims>  jwsClaims = Jwts.parser()
		        .setSigningKeyResolver(signingKeyResolver)
		        .parseClaimsJws(tokenJWT);
		    
		   Date expiracion = jwsClaims.getBody().getExpiration();
		   if(expiracion.before(new Date())){
			   return false;
		   }
		} catch(SignatureException | ExpiredJwtException se){
			return false;
		}
		return true;
	}

	public String getStringAttribute(String tokenJWT, String attributeName) {
		return getStringClaim(tokenJWT, attributeName);
	}
	
	public Integer getIntegerAttribute(String tokenJWT, String attributeName) {
		return getIntegerClaim(tokenJWT, attributeName);
	}
	
	private Integer getIntegerClaim(String tokenJWT, String claimName) {
		Integer value = null;
		try {
			Jws<Claims>  jwsClaims = Jwts.parser()
					.setSigningKeyResolver(signingKeyResolver)
					.parseClaimsJws(tokenJWT);
			value = jwsClaims.getBody().get(claimName, Integer.class);
		}catch(SignatureException se){
			se.getMessage();
		}
		return value;
	}
	
	private String getStringClaim(String tokenJWT, String claimName) {
		String value = null;
		try {
			Jws<Claims>  jwsClaims = Jwts.parser()
					.setSigningKeyResolver(signingKeyResolver)
					.parseClaimsJws(tokenJWT);
			value = jwsClaims.getBody().get(claimName, String.class);
		}catch(SignatureException se){
			se.getMessage();
		}
		return value;
	}

}
