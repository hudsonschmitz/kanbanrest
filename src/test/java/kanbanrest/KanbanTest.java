package kanbanrest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import kanbanrest.repository.TestRepository;
import kanbanrest.request.KanbanRequest;
import kanbanrest.response.KanbanResponse;
import kanbanrest.response.ResponseError;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class KanbanTest {

  @BeforeAll
  static void setupDatabase() {
    TestRepository.init();
  }

  @AfterAll
  static void excluiItensDatabase() { TestRepository.destroy(); }

  @Test
  void deveRetornarKanbanCompleto() {
    KanbanResponse respostaEsperada = montaResponseEsperada();
    Response response = given().contentType(ContentType.JSON)
                               .when()
                               .get("/kanban/" + TestRepository.kanban.getId());
    KanbanResponse responseBody = response.body().as(KanbanResponse.class);

    response.then().statusCode(200);
    Assertions.assertEquals(responseBody, respostaEsperada);
  }

  @Test
  void deveRetornarVazio() {
    String idKanban = "0";
    Response response = given().contentType(ContentType.JSON)
            .when()
            .get("/kanban/" + idKanban);
    ResponseError responseError = response.body().as(ResponseError.class);

    response.then().statusCode(404);
    Assertions.assertEquals(responseError, new ResponseError("Nenhum kanban encontrado com o id: " + idKanban));
  }

  @Test
  void nomeKanbanDeveSerObrigatorio() {
    KanbanRequest kanbanRequest = new KanbanRequest();
    Response response = given().contentType(ContentType.JSON)
                                .body(kanbanRequest)
                                .when()
                                .post("/kanban");

    ResponseError responseError = response.body().as(ResponseError.class);

    response.then().statusCode(400);
    Assertions.assertNotNull(responseError);
    Assertions.assertTrue(StringUtils.isNotBlank(responseError.getMessage()));
  }

  @Test
  void kanbanDeveSerCriadoAtivo() {
    KanbanRequest kanbanRequest = new KanbanRequest();
    kanbanRequest.setNome("Kanban Test");
    Response response = given().contentType(ContentType.JSON)
            .body(kanbanRequest)
            .when()
            .post("/kanban");


    response.then().statusCode(201);
    KanbanResponse kanbanResponse = response.body().as(KanbanResponse.class);
    Assertions.assertTrue(kanbanResponse.getAtivo());
  }

  private KanbanResponse montaResponseEsperada() {
    KanbanResponse respostaEsperada = TestRepository.kanbanToResponse();
    respostaEsperada.setColunas(TestRepository.colunasToResponse());
    return respostaEsperada;
  }

}
