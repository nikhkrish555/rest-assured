package com.walmart.base;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

public class WalmartBase {
	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://api.walmartlabs.com";
		RestAssured.basePath = "/v1";
	}
}
