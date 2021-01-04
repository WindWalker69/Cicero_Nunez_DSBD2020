package dsbd2020.project.productmanager.exception;

import com.google.gson.Gson;
import dsbd2020.project.productmanager.messageKafka.HttpErrorMessage;
import dsbd2020.project.productmanager.messageKafka.HttpErrorMessageValue;

import org.apache.catalina.connector.Response;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionController extends ResponseEntityExceptionHandler {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafkaTopicLogging}")
    private String topicName;

    @Value("${spring.application.name}")
    private String serviceName;

    public void sendMessage(String msg, String topicName) {
        kafkaTemplate.send(topicName, msg);
    }

    /*@ExceptionHandler(value = {
            ProductNotFoundException.class, CategoryNotFoundException.class, NoDataFoundException.class })
    protected ResponseEntity<Object> objectNotFoundExceptionHandler(
            RuntimeException ex, WebRequest request, HttpServletRequest requestHTTP){

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
            RuntimeException ex, WebRequest request, HttpServletRequest requestHTTP){

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
    }*/

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> exceptionHandler(
            Exception ex, WebRequest webRequest, HttpServletRequest httpRequest){

        ResponseEntity<Object> responseEntity;

        String sourceIp = httpRequest.getRemoteAddr();
        String request = httpRequest.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE)
                + " " + httpRequest.getMethod();
        String error;

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis() / 1000L);
        body.put("message", ex.getMessage());

        if(ex instanceof ProductNotFoundException ||
                ex instanceof CategoryNotFoundException ||
                ex instanceof NoDataFoundException) {
            error = String.valueOf(Response.SC_NOT_FOUND);
            responseEntity = new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
        else if(ex instanceof WrongRequestParameterException ||
                ex instanceof WrongRequestBodyException ||
                ex instanceof MethodArgumentNotValidException){
            error = String.valueOf(Response.SC_BAD_REQUEST);
            responseEntity = new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
        else {
            error = ExceptionUtils.getStackTrace(ex); //ex.getStackTrace().toString(); //String.valueOf(Response.SC_INTERNAL_SERVER_ERROR);
            responseEntity = new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpErrorMessageValue httpErrorMessageValue = new HttpErrorMessageValue()
                .setTimestamp(System.currentTimeMillis() / 1000L)
                .setSourceIp(sourceIp)
                .setService(serviceName)
                .setRequest(request)
                .setError(error);

        HttpErrorMessage httpErrorMessage = new HttpErrorMessage()
                .setKey("http_errors")
                .setValue(httpErrorMessageValue);

        sendMessage(new Gson().toJson(httpErrorMessage), topicName);

        return responseEntity;
    }

    @Override //Override obbligatorio se si usa @Valid
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return exceptionHandler(ex, request, getCurrentHttpRequest());
    }

    //METODI PRIVATI
    private HttpServletRequest getCurrentHttpRequest(){
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            return request;
        }
        return null;
    }
}
