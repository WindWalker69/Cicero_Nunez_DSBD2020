package dsbd2020.project.productmanager.exception;

import com.google.gson.Gson;
import dsbd2020.project.productmanager.messageKafka.HttpErrorMessage;
import dsbd2020.project.productmanager.messageKafka.HttpErrorMessageValue;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;



@ControllerAdvice
public class RestExceptionController extends ResponseEntityExceptionHandler {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafkaTopicLogging}")
    String topicName;


    public void sendMessage(String msg, String topicName) {
        kafkaTemplate.send(topicName, msg);
    }

    @ExceptionHandler(value = {
            ProductNotFoundException.class, CategoryNotFoundException.class, NoDataFoundException.class })
    protected ResponseEntity<Object> objectNotFoundExceptionHandler(
            RuntimeException ex, HttpServletRequest requestHTTP){

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis() / 1000L);
        body.put("message", ex.getMessage());


        HttpErrorMessageValue httpErrorMessageValue = new HttpErrorMessageValue()
                .setTimestamp(System.currentTimeMillis() / 1000L)
                .setSourceIp(requestHTTP.getRemoteAddr())
                .setService("productmanager")
                .setRequest(requestHTTP.getRequestURL()+" "+requestHTTP.getMethod())
                .setError(String.valueOf((Response.SC_NOT_FOUND)));

        HttpErrorMessage httpErrorMessage = new HttpErrorMessage()
                .setKey("http_errors")
                .setValue(httpErrorMessageValue);

        sendMessage(new Gson().toJson(httpErrorMessage), topicName);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            WrongRequestParameterException.class, WrongRequestBodyException.class })
    protected ResponseEntity<Object> badRequestExceptionHandler(
            RuntimeException ex,  HttpServletRequest requestHTTP){

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis() / 1000L);
        body.put("message", ex.getMessage());

        HttpErrorMessageValue httpErrorMessageValue = new HttpErrorMessageValue()
                .setTimestamp(System.currentTimeMillis() / 1000L)
                .setSourceIp(requestHTTP.getRemoteAddr())
                .setService("productmanager")
                .setRequest(requestHTTP.getRequestURL()+requestHTTP.getMethod())
                .setError(String.valueOf((Response.SC_BAD_REQUEST)));


        HttpErrorMessage httpErrorMessage = new HttpErrorMessage()
                .setKey("http_errors")
                .setValue(httpErrorMessageValue);

        sendMessage(new Gson().toJson(httpErrorMessage), topicName);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
