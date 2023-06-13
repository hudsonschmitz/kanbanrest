package kanbanrest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import kanbanrest.repository.TestRepository;
import kanbanrest.request.ColunaRequest;
import kanbanrest.response.ColunaResponse;
import kanbanrest.response.KanbanResponse;
import kanbanrest.response.ResponseError;
import kanbanrest.service.ColunaService;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.junit.platform.commons.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ColunaTest {

  private final ColunaService colunaService;

  public ColunaTest(ColunaService colunaService) {
    this.colunaService = colunaService;
  }


  @BeforeAll
  static void setupDatabase() {
    TestRepository.init();
  }

  @AfterAll
  static void excluiItensDatabase() { TestRepository.destroy(); }


  @Test
  @Order(1)
  void deveRetornarColunaPorId() {
    Response response = given().contentType(ContentType.JSON)
            .when()
            .get("/coluna/" + TestRepository.kanban.getId() + "/" + TestRepository.colunas.get(1).getId());

    ColunaResponse colunaResponse = response.body().as(ColunaResponse.class);

    response.then().statusCode(200);
    Assertions.assertEquals(colunaResponse, colunaService.modelToResponse(TestRepository.colunas.get(1)));
  }

  @Test
  @Order(2)
  void colunasDevemFicarEmOrdemAposAlteracao() {
    ColunaRequest colunaRequest = new ColunaRequest(1, TestRepository.colunas.get(2).getNome());
    Response responseColuna = given().contentType(ContentType.JSON)
            .body(colunaRequest)
            .when()
            .patch("/coluna/" + TestRepository.kanban.getId() + "/" + TestRepository.colunas.get(2).getId());

    Response responseKanban = given().contentType(ContentType.JSON)
            .when()
            .get("/kanban/" + TestRepository.kanban.getId());
    KanbanResponse responseBody = responseKanban.body().as(KanbanResponse.class);

    responseColuna.then().statusCode(200);

    List<Integer> stages = responseBody.getColunas().stream().map(ColunaResponse::getStage).sorted().collect(Collectors.toList());

    for(int i = 0; i < stages.size(); i++) {
      if(stages.get(i) != (i + 1)) {
        Assertions.fail("Stage das colunas do kanban estão fora da ordem.");
      }
    }
  }

  @Test
  @Order(3)
  void colunaNaoPodeTerStageZero() {
    ColunaRequest colunaRequest = new ColunaRequest(0, "Coluna teste");
    Response responseColuna = given().contentType(ContentType.JSON)
            .body(colunaRequest)
            .when()
            .post("/coluna/" + TestRepository.kanban.getId());

    responseColuna.then().statusCode(HttpStatus.SC_BAD_REQUEST);

    ResponseError responseError = responseColuna.body().as(ResponseError.class);
    Assertions.assertTrue(StringUtils.isNotBlank(responseError.getMessage()));
  }

  @Test
  @Order(4)
  void stageColunaTemQueEstarEmOrdem() {
    ColunaRequest colunaRequest = new ColunaRequest(10, "Coluna teste");
    Response responseColuna = given().contentType(ContentType.JSON)
            .body(colunaRequest)
            .when()
            .post("/coluna/" + TestRepository.kanban.getId());

    responseColuna.then().statusCode(HttpStatus.SC_BAD_REQUEST);

    ResponseError responseError = responseColuna.body().as(ResponseError.class);
    Assertions.assertTrue(StringUtils.isNotBlank(responseError.getMessage()));
  }

}
