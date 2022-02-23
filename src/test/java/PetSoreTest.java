import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.val;
import models.AddNewPet.AddNewPet;
import models.AddNewPet.Category;
import models.AddNewPet.TagsItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static helpers.Helpers.*;
import static org.hamcrest.Matchers.equalTo;


public class PetSoreTest {
    String url = "https://petstore.swagger.io/v2/pet";

    /*
    Проверка добавления нового питномца
    1) Заролняем все необходимые поля валидными данные
    2) Выплняем Post запрос
    3) Проверяем стаутс код (200)
    4) Проверям значения поля id (1)
    5) Проверям значения поля name (PettyPat)
    6) Проеверям json схему
     */
    @Test
    public void CreatePetTest(){
        val category =Category.builder()
                .name("dog")
                .id(1)
                .build();

        val tag = TagsItem.builder()
                .name("Super dog")
                .id(1)
                .build();
        val tag2 = TagsItem.builder()
                .name("My dog")
                .id(2)
                .build();

        AddNewPet addNewPet = AddNewPet.builder()
                .photoUrls(List.of("http://surl.li/agbgt"))
                .name("PettyPat")
                .id(1)
                .category(category)
                .tags(List.of(tag,tag2))
                .status("status").build();

        RequestSpecification request = buildRequest(buildJSONFromObject(addNewPet)) ;
        Response response =
                executePostRequest(request, url);

                        response.then()
                        .log().all()
                        .statusCode(200)
                        .body("id", equalTo(1))
                        .body("name", equalTo("PettyPat"))
                        .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/AddNewPet.json"));
    }
    /*
        Проверка добавления нового питномца
    1) Заролняем все необходимые кроме поля name
    2) Выплняем Post запрос
    3) Проверяем стаутс код (405)
    4) Проверям значения поля code (405)
    5) Проверям значения поля type (Invalid input)
    6) Проверям значения поля message (Invalid input)
     */
    @Test
    public void CreatePetWithoutName(){

        val category =Category.builder()
                .name("dog")
                .id(1)
                .build();

        val tag = TagsItem.builder()
                .name("PettyPet")
                .id(1)
                .build();

        val tag2 = TagsItem.builder()
                .name("Rex")
                .id(2)
                .build();


        AddNewPet addNewPet = AddNewPet.builder()
                .photoUrls(List.of(""))
                .name("")
                .id(1)
                .category(category)
                .tags(List.of(tag,tag2))
                .status("status").build();

        RequestSpecification request = buildRequest(buildJSONFromObject(addNewPet)) ;
        Response response =
                executePostRequest(request, url);

        response.then()
                .log().all()
                .statusCode(405)
                .body("code", equalTo(405))
                .body("type", equalTo("Invalid input"))
                .body("message", equalTo("Invalid input"));
    }
    /*
    Проверка получение данных о питомце по id
    1) Делаем Get запрос в url указываем валидный id
    2) Проверяем статутс код (200)
    3) Проверяем name(asd)
    4) Проверяем json схему
     */
    @Test
    public void GetPetByName(){
        RequestSpecification request = buildRequest();
        Response response =
                executeGetRequest(request, url+"/1");

        response.then()
                .log().all()
                .statusCode(200)
                .body("name", equalTo("asd"))
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/AddNewPet.json"));
    }
    /*
      Проверка получение данных о питомце по несуществующему id
    1) Делаем Get запрос в url указываем невалидный id
    2) Проверяем статутс код (404)
    3) Проверяем code(1)
    4) Проверяем type(error)
    5) Проверяем message(Pet not found)
       */
    @Test
    public void GetPetByNameNotFound(){
        RequestSpecification request = buildRequest();
        Response response =
                executeGetRequest(request, url+"/122");

        response.then()
                .log().all()
                .statusCode(404)
                .body("code", equalTo(1))
                .body("type", equalTo("error"))
                .body("message", equalTo("Pet not found"));
    }
}
