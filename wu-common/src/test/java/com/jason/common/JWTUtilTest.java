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
		System.out.println("handle before:" +jwtToken);
		jwtToken = JWTUtil.checkAndHandleToken(jwtToken);
		System.out.println("handle after:" +jwtToken);
	}
	
	@Test
	public void bDecodeToken(){
		Assert.assertEquals(userId, JWTUtil.decodeToken(jwtToken));
	}
	
	@Test
	public void cVerifyToken(){
		Assert.assertTrue(JWTUtil.verifyToken(jwtToken, password));
	}
}
