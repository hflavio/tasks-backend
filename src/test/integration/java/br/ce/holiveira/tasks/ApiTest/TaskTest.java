package br.ce.holiveira.tasks.ApiTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class TaskTest {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarHelloWorld() {
        RestAssured.given()
                .when()
                .get()
                .then().log().all()
                .statusCode(200)
        ;
    }

    @Test
    public void deveRetornarTarefas() {
        RestAssured.given()
                .when()
                .get("/todo")
                .then()
                .statusCode(200);
    }

    @Test
    public void deveAdicionarTarefasComSucesso() {
        RestAssured.given()
                .body("{\"task\": \"Test via API\", \"dueDate\": \"2021-07-17\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void deveAdicionarTarefasInvalida() {
        RestAssured.given()
                .body("{\"task\": \"Test via API\", \"dueDate\": \"2010-12-30\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"))
        ;
    }

}

