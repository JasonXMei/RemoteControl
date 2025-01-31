package com.jason.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	
	/**
	 * 检查header jwt传参是否正确
	 * @param token 标准格式(Bearer jwt_token)
	 * @return
	 */
	public static String checkAndHandleSessionToken(String token){
		if(!StringUtils.isEmpty(token)){
			int index = token.indexOf("Bearer ");
			if(index != -1){
				index += 7;
				return token.substring(index);
			}
		}
		return null;
	}

	/**
	 * 检查request url jwt传参是否正确
	 * @param requestURL http://localhost:8082/user/token/{token}/
	 * @return
	 */
	public static String checkAndHandleURLToken(String requestURL){
		String token = requestURL.substring(requestURL.indexOf("token") + 6, requestURL.length() - 1);
		return token;
	}
	
	public static int decodeToken(String token) {
		Claim userClaim = JWT.decode(token).getClaims().get("userId");
		if(userClaim != null && userClaim.asInt() != null){
			return userClaim.asInt();
		}
		return 0;
	}

	public static void verifyToken(String token, String password) {
		Algorithm algorithm = Algorithm.HMAC256(password);
		JWTVerifier verifier = JWT.require(algorithm).build(); 
		verifier.verify(token);
	}
}
