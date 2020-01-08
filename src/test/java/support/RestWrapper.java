package support;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RestWrapper {

    private String baseUrl = "https://skryabin.com/recruit/api/v1/";
    private static String loginToken;
    private static Map<String, Object> lastPosition;
    private static Map<String, Object> lastCandidate;
    private static JsonPath metadata;

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String JSON = "application/json";
    public static final String AUTH = "Authorization";

    public static Map<String, Object> getLastPosition() {
        return lastPosition;
    }

    public static Map<String, Object> getLastCandidate() {
        return lastCandidate;
    }

    public void login(Map<String, String> credentials) {

        // prepare
        RequestSpecification request = RestAssured
                .given()
                .log().all()
                .baseUri(baseUrl)
                .header(CONTENT_TYPE, JSON)
                .body(credentials);

        // execute
        Response response = request
                .when()
                .post("login");

        // verify and extract
        Map<String, Object> result = response
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("");

        loginToken = "Bearer " + result.get("token");
        initMetadata();
    }

    public Map<String, Object> createPosition(Map<String, String> position) {

        String dateOpen = position.get("dateOpen");
        String isoDateOpen = new SimpleDateFormat("yyyy-MM-dd").format(new Date(dateOpen));
        position.put("dateOpen", isoDateOpen);

        String title = position.get("title");
        String uniqueTitle = title + new SimpleDateFormat("-yyyy-MM-dd-HH-mm-sss").format(new Date());
        position.put("title", uniqueTitle);

        // prepare
        RequestSpecification request = RestAssured
                .given()
                .log().all()
                .baseUri(baseUrl)
                .header(CONTENT_TYPE, JSON)
                .header(AUTH, loginToken)
                .body(position);

        // execute
        Response response = request
                .when()
                .post("positions");

        // verify and extract
        Map<String, Object> result = response
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getMap("");

        lastPosition = result;
        assertMetadata(result, "positions");
        return result;
    }

    public List<Map<String, Object>> getPositions() {
        // prepare
        RequestSpecification request = RestAssured
                .given()
                .baseUri(baseUrl)
                .log().all();

        // execute
        Response response = request
                .when()
                .get("positions");

        // verify and extract
        List<Map<String, Object>> result = response
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("");

        for (Map<String, Object> item : result) {
            assertMetadata(item, "positions");
        }
        return result;
    }

    public Map<String, Object> updatePosition(Map<String, String> fields, Object id) {
        Map<String, Object> result = RestAssured
                .given()
                .log().all()
                .baseUri(baseUrl)
                .header(CONTENT_TYPE, JSON)
                .header(AUTH, loginToken)
                .body(fields)
                .when()
                .put("positions/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("");

        for(String key : result.keySet()) {
            lastPosition.put(key, result.get(key));
        }
        assertMetadata(result, "positions");
        return result;
    }

    public Map<String, Object> getPositionById(Object id) {
        Map<String, Object> result = RestAssured
                .given()
                .baseUri(baseUrl)
                .log().all()
                .when()
                .get("positions/" + id)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getMap("");

        assertMetadata(result, "positions");
        return result;
    }

    public void deletePositionById(Object id) {
        RestAssured
                .given()
                .baseUri(baseUrl)
                .log().all()
                .when()
                .header(AUTH, loginToken)
                .when()
                .delete("positions/" + id)
                .then()
                .statusCode(204);
    }


    public Map<String, Object> createCandidate(Map<String, String> candidate) {

        candidate.put("email", TestContext.addTimeStampToEmail(candidate.get("email")));

        Map<String, Object> result = RestAssured
                .given()
                .log().all()
                .baseUri(baseUrl)
                .header(CONTENT_TYPE, JSON)
                .header(AUTH, loginToken)
                .body(candidate)
                .when()
                .post("candidates")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getMap("");

        lastCandidate = result;
        assertMetadata(result, "candidates");
        return result;
    }

    public void addResume(File resume, Object candidateId) {
        RestAssured
                .given()
                .log().all()
                .baseUri(baseUrl)
                .header(AUTH, loginToken)
                .multiPart("resume", resume)
                .when()
                .post("candidates/" + candidateId + "/resume")
                .then()
                .log().all()
                .statusCode(201);
    }

    public ExtractableResponse<Response> getResume(Object candidateId) {
        return RestAssured
                .given()
                .log().all()
                .baseUri(baseUrl)
                .header(AUTH, loginToken)
                .when()
                .get("candidates/" + candidateId + "/resume")
                .then()
                .log().headers()
                .extract();
    }

    private void initMetadata() {
        JsonPath result = RestAssured
                .given()
                .baseUri(baseUrl)
                .log().all()
                .when()
                .get("swagger.json")
                .then()
                .log().headers()
                .statusCode(200)
                .extract()
                .jsonPath();

        metadata = result;
    }

    private static void assertMetadata(Map<String, Object> actual, String type) {
        for (String key : actual.keySet()) {
            if (actual.get(key) != null) {
                String actualType = actual.get(key).getClass().toString();
                actualType = actualType.substring(actualType.lastIndexOf(".") + 1);
                String expectedType = metadata.getString("definitions." + type + ".properties." + key + ".type");
                assertThat(actualType).isEqualToIgnoringCase(expectedType);
            }
        }
    }


}
