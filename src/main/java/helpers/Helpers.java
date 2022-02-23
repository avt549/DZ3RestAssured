package helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.io.IOException;
import java.util.HashMap;

/**
 * Класс с вспомогательными методами
 */
public class Helpers {
        /**
         * Метод для инициализации базовых параметров API
         */

        /**
         * Метод для формирования заголовка и тела запроса
         *
         * @param msgBody тело сообщения в виде строки
         * @return получившийся запрос
         */
        public static RequestSpecification buildRequest(String msgBody) {
            RequestSpecification request = RestAssured
                    .given()

//                    .preemptive().basic(System.getProperty("basicLogin"), System.getProperty("basicPassword"));
            .header("Content-Type", "application/json")
            .body(msgBody);
            return request;
        }

        /**
         * Метод для формирования заголовка и тела запроса
         *
         * @return получившийся запрос
         */
        public static RequestSpecification buildRequest() {
            RequestSpecification request = RestAssured
                    .given();
            request.header("Content-Type", "application/json");
            return request;
        }

        /**
         * Метод для формирования json-строки из объекта
         *
         * @param object объект, который будем превращать в json
         * @param <T>    тип объекта
         * @return объект в виде json-строки
         */
        public static <T> String buildJSONFromObject(T object) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String jsonString;
            try {
                jsonString = gson.toJson(object);
            } catch (JsonSyntaxException ex) {
                ex.printStackTrace();
                jsonString = "";
            }
            return jsonString;
        }

        /**
         * Метод для выполнения POST запроса
         *
         * @param request  объект типа RequestSpecification с сформированным запросом
         * @param resource ресурс API, к которому выполняем запрос
         * @return ответ типа Response от сервера
         */
        public static Response executePostRequest(RequestSpecification request, String resource) {
            return request
                    .when().log().all()
                    .post(resource)
                    .then().log().all().extract().response();
        }

        /**
         * Метод для выполнения GET запроса
         *
         * @param request  объект типа RequestSpecification с сформированным запросом
         * @param resource ресурс API, к которому выполняем запрос
         * @return ответ типа Response от сервера
         */
        public static Response executeGetRequest(RequestSpecification request, String resource) {
            return request
                    .when().log().all()
                    .get(resource)
                    .then().log().all().extract().response();
        }




}
