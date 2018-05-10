package com.jsonpath.examples;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import com.walmart.base.WalmartBase;

public class SearchJsonPathExample extends WalmartBase {

	static final String APIKEY = "nprqvdpf647zfcqmevjbuu9h";

	@Test
	public void extractNumberOfItems() {
		assertThat(given().queryParam("query", "ipod").queryParam("apiKey", APIKEY).queryParam("format", "json").when()
				.get("/search").then().extract().path("numItems")).as("check the number of items").isEqualTo(10);
	}

	@Test
	public void extractQueryValue() {
		assertThat(given().queryParam("query", "ipod").queryParam("apiKey", APIKEY).queryParam("format", "json").when()
				.get("/search").then().extract().path("query")).as("check the query value").isEqualTo("ipod");
	}

	@Test
	public void extractProductNameOfSpecificItem() {
		assertThat(given().queryParam("query", "ipod").queryParam("apiKey", APIKEY).queryParam("format", "json").when()
				.get("/search").then().extract().path("items[0].name")).as("check the product name of specific item")
						.isEqualTo("Apple iPod touch 128GB");
	}

	@Test
	public void extractGiftItemsKeyValues() {
		HashMap<String, Boolean> giftOptions = given().queryParam("query", "ipod").queryParam("apiKey", APIKEY)
				.queryParam("format", "json").when().get("/search").then().extract().path("items[0].giftOptions");

		assertThat(giftOptions).as("check the gift item key value")
				.extracting("allowGiftWrap", "allowGiftMessage", "allowGiftReceipt")
				.containsExactly(false, false, false);
	}

	@Test
	public void extractSizeofItemsList() {
		assertThat(given().queryParam("query", "ipod").queryParam("apiKey", APIKEY).queryParam("format", "json").when()
				.get("/search").then().extract().path("items.size()")).as("check the item size").isEqualTo(10);
	}

	@Test
	public void extractNameOfAllItems() {
		List<String> names = given().queryParam("query", "ipod").queryParam("apiKey", APIKEY)
				.queryParam("format", "json").when().get("/search").then().extract().path("items.name");

		Iterator<String> namesIterator = names.iterator();
		while (namesIterator.hasNext()) {
			System.out.println(namesIterator.next());
		}
	}

	@Test
	public void extractAllValuesOfSpecificItem() {
		List<HashMap<String, Object>> values = given().queryParam("query", "ipod").queryParam("apiKey", APIKEY)
				.queryParam("format", "json").when().get("/search").then().extract()
				.path("items.findAll{it.name=='Apple iPod touch 32GB'}");

		/**
		 * Notes: In Maven Dependecies, if you open json-path-<version>.jar you will
		 * find ConfigurableJsonSlurper.class, you will lot of groovy imports, which
		 * means, rest assured is implemented using groovy library. So in the above json
		 * path code snippet findAll is a groovy method and {it.name=='Apple iPod touch
		 * 32GB'} is a closure. In rest-assured json path java doc -
		 * http://static.javadoc.io/io.rest-assured/json-path/3.0.2/io/restassured/path/json/JsonPath.html
		 * you will see it follows Groovy GPath syntax, similar to XPath. GPath can be
		 * used for XML and JSON. Link to Groovy Collection Methods -
		 * http://docs.groovy-lang.org/2.4.3/html/groovy-jdk/java/util/Collection.html
		 */

		Iterator<HashMap<String, Object>> valueIterator = values.iterator();
		while (valueIterator.hasNext()) {
			System.out.println(valueIterator.next());
		}
	}

	@Test
	public void extractSalePriceOfSpecificItem() {
		List<Float> values = given().queryParam("query", "ipod").queryParam("apiKey", APIKEY)
				.queryParam("format", "json").when().get("/search").then().extract()
				.path("items.findAll{it.name=='Apple iPod touch 32GB'}.salePrice");

		Iterator<Float> valueIterator = values.iterator();
		while (valueIterator.hasNext()) {
			System.out.println(valueIterator.next());
		}
	}

	@Test
	public void extractNameOfItemsWithSalePriceLessThan200() {
		List<String> values = given().queryParam("query", "ipod").queryParam("apiKey", APIKEY)
				.queryParam("format", "json").when().get("/search").then().extract()
				.path("items.findAll{it.salePrice<200}.name");

		Iterator<String> valueIterator = values.iterator();
		while (valueIterator.hasNext()) {
			System.out.println(valueIterator.next());
		}
	}

	@Test
	public void extractMSRPValueOfItemsWhoseNameStartsWith_Ref() {
		List<Float> values = given().queryParam("query", "ipod").queryParam("apiKey", APIKEY)
				.queryParam("format", "json").when().get("/search").then().extract()
				.path("items.findAll{it.name==~/Ref.*/}.msrp");

		Iterator<Float> valueIterator = values.iterator();
		while (valueIterator.hasNext()) {
			System.out.println(valueIterator.next());
		}
	}

	@Test
	public void extractSalePriceOfItemsWhoseNameEndsWith_ed() {
		List<Float> values = given().queryParam("query", "ipod").queryParam("apiKey", APIKEY)
				.queryParam("format", "json").when().get("/search").then().extract()
				.path("items.findAll{it.name==~/.*ed/}.salePrice");

		Iterator<Float> valueIterator = values.iterator();
		while (valueIterator.hasNext()) {
			System.out.println(valueIterator.next());
		}
	}
}
