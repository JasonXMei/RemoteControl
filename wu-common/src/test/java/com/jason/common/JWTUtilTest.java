package com.jason.common;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.jason.common.util.JWTUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JWTUtilTest {
	
	private static String jwtToken;
	private static int userId = 1;
	private static String password = "123456";
	
	@Test
	public void aCreateToken(){
		jwtToken = "Bearer " +JWTUtil.createToken(password, userId);
	}
	
	@Test
	public void bCheckToken(){
		System.out.println("handle header before:" +jwtToken);
		jwtToken = JWTUtil.checkAndHandleSessionToken(jwtToken);
		System.out.println("handle header after:" +jwtToken);
	}
	
	@Test
	public void bDecodeToken(){
		Assert.assertEquals(userId, JWTUtil.decodeToken(jwtToken));
	}
	
	@Test
	public void cVerifyToken(){
		JWTUtil.verifyToken(jwtToken, password);
	}

	@Test
	public void eCreateToken(){
		jwtToken = "http://localhost:8082/user/token/" + JWTUtil.createToken(password, userId) + "/";
	}

	@Test
	public void fCheckToken(){
		System.out.println("handle url before:" +jwtToken);
		jwtToken = JWTUtil.checkAndHandleURLToken(jwtToken);
		System.out.println("handle url after:" +jwtToken);
	}

	@Test
	public void fDecodeToken(){
		Assert.assertEquals(userId, JWTUtil.decodeToken(jwtToken));
	}

	@Test
	public void gVerifyToken(){
		JWTUtil.verifyToken(jwtToken, password);
	}
}
