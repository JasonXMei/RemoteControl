package com.jason.common.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTUtil {
	
	public static String createToken(String password, Integer userId) {
		// 头部信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");
		// 加密算法
		Algorithm algorithm = Algorithm.HMAC256(password);
		
		Date expireDate = DateUtil.getAfterDate(new Date(), 0, 0, 0, 24, 0, 0);// 2小时过期

		String token = JWT.create().withHeader(map) //头部信息 Header
				.withClaim("userId", userId)
				.withExpiresAt(expireDate)	//签名过期的时间
				.sign(algorithm);	//签名 Signature
		return token;
	}
	
	public static int decodeToken(String token) {
		return JWT.decode(token).getClaims().get("userId").asInt();
	}

	public static boolean verifyToken(String token, String password) {
		Algorithm algorithm = Algorithm.HMAC256(password);
		JWTVerifier verifier = JWT.require(algorithm).build(); 
		verifier.verify(token);
		return true;
	}
}
