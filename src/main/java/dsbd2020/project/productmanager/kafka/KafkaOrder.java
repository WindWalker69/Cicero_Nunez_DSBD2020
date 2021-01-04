package dsbd2020.project.productmanager.kafka;

import com.google.gson.Gson;
import dsbd2020.project.productmanager.data.ProductRepository;
import dsbd2020.project.productmanager.entities.Product;
import dsbd2020.project.productmanager.support.TopicOrderCompleted;
import dsbd2020.project.productmanager.support.ProductUpdateRequest;
import dsbd2020.project.productmanager.support.ProductUpdateResponse;

import dsbd2020.project.productmanager.support.TopicOrderValidation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class KafkaOrder {

    @Autowired
    ProductRepository repository;


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg, String topicName) {
        kafkaTemplate.send(topicName, msg);
    }

    @KafkaListener(topics = "${kafkaTopicOrders}", groupId = "${kafkaGroup}")
    public void listenProductTopic(String message) {
        if (message != null) {
            int status_code = 0;
            TopicOrderCompleted order_request = new Gson().fromJson(message, TopicOrderCompleted.class);
            ProductUpdateRequest updateRequest = order_request.getValue();
            Map<Integer, Integer> extraArgs = new HashMap<>();
            if (order_request.getKey().equals("order_completed")) {
                int quantityavailable = 0;
                double totalorder = 0;
                //definire una map per gli ordini che non possono essere aggiunti.
                for (Map.Entry<Integer, Integer> entry : updateRequest.getProducts().entrySet()) {
                    Optional<Product> product = repository.findById(entry.getKey());
                    if (product.isPresent()) {
                        Product p = product.get();
                        if (p.getQuantity() - entry.getValue() < 0) {
                            quantityavailable++;
                            //add della map del product_id con quantity cioè mettere la key in una nuova mappa
                            extraArgs.put(entry.getKey(), entry.getValue());

                        }
                        totalorder += (entry.getValue() * p.getPrice());
                    }else {
                      //prodotto non esiste
                        extraArgs.put(entry.getKey(), entry.getValue());
                        quantityavailable++;
                    }
                }

                if (totalorder == updateRequest.getTotal() && quantityavailable == 0) {
                    status_code = 0;
                    for (Map.Entry<Integer, Integer> entry : updateRequest.getProducts().entrySet()) {
                        Optional<Product> product = repository.findById(entry.getKey());
                        if (product.isPresent()) {
                            Product p = product.get();
                            p.setQuantity(p.getQuantity() - entry.getValue());
                            repository.save(p);
                        }
                    }
                } else {
                    //entrambe le verifiche non corrette
                    if (totalorder != updateRequest.getTotal() && quantityavailable != 0) {
                        status_code = -3;
                    } else {
                        if (quantityavailable > 0) {
                            //qualche prodotto non è disponibile
                            status_code = -1;
                        }
                        if (totalorder != updateRequest.getTotal()) {
                            //totale errato
                            status_code = -2;
                        }
                    }
                }

                //Preparo la order validation da trasmettere sui topic
                ProductUpdateResponse productupdateresponse = new ProductUpdateResponse().
                        setStatus(status_code).
                        setOrderId(updateRequest.getOrderId()).
                        setTimestamp(System.currentTimeMillis() / 1000L );

                if (status_code == -1) {
                    productupdateresponse.setExtraArgs(extraArgs);
                }

                TopicOrderValidation orderValidation = new TopicOrderValidation()
                    .setKey("order_validation")
                    .setValue(productupdateresponse);
                //produce il messaggio sui topic orders e notifications
                sendMessage(new Gson().toJson(orderValidation), "orderupdates");
                sendMessage(new Gson().toJson(orderValidation), "pushnotifications");
                //Se lo status code e' -2 o -3, il messaggio va inviato anche sul topic logging
                if (status_code == -2 || status_code == -3) {
                    sendMessage(new Gson().toJson(orderValidation), "logging");
                }
            }
       }
    }
}
