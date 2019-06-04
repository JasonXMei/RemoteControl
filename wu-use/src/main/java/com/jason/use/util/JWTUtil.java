package com.jason.use.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;

public class JWTUtil {
	public static int decodeToken(String token) {
		Claim userClaim = JWT.decode(token).getClaims().get("userId");
		if(userClaim != null && userClaim.asInt() != null){
			return userClaim.asInt();
		}
		return 0;
	}
}
