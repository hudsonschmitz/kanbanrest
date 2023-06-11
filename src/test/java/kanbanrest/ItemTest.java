package kanbanrest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import kanbanrest.repository.TestRepository;
import kanbanrest.request.ItemRequest;
import kanbanrest.response.ResponseError;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ItemTest {

  /*
   * Item deve estar vinculado a uma coluna.
   *
   */

  @BeforeAll
  static void setupDatabase() {
    TestRepository.init();
  }

  @AfterAll
  static void excluiItensDatabase() { TestRepository.destroy(); }


  @Test
  void itemSoVaiPraFrente() {
    long idKanban = TestRepository.kanban.getId();
    long idItemSegundaColuna = TestRepository.itens.get(5).getId();
    long idPrimeiraColuna = TestRepository.colunas.get(0).getId();
    Response response = given().contentType(ContentType.JSON)
            .when()
            .put("/kanban/" + idKanban + "/item/" + idItemSegundaColuna + "?idColunaDestino=" + idPrimeiraColuna);

    validaErro(400, response);
  }

  @Test
  void itemDeveEstarVinculadoAColuna() {
    ItemRequest itemRequest = new ItemRequest("Item importante.", Arrays.asList("#Teste", "#Item", "#Importante"), null);
    long idKanban = TestRepository.kanban.getId();
    Response response = given().contentType(ContentType.JSON)
            .body(itemRequest)
            .when()
            .post("/kanban/" + idKanban + "/item");

    validaErro(400, response);

  }

  void validaErro(int statusCode, Response response) {
    response.then().statusCode(statusCode);
    ResponseError responseError = response.body().as(ResponseError.class);
    Assertions.assertTrue(Objects.nonNull(responseError));
  }

}