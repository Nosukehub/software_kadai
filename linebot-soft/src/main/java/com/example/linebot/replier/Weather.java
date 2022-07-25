package com.example.linebot.replier;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class Weather implements Replier {

    private final MessageEvent<TextMessageContent> event;

    public Weather(MessageEvent<TextMessageContent> event){
        this.event = event;
    }

    @Override
    public Message reply() {
//        TextMessageContent tmc = event.getMessage();
//        String text = tmc.getText();

        RestTemplateBuilder templateBuilder= new RestTemplateBuilder();
        RestTemplate restTemplate = templateBuilder.build();

        String url = "http://127.0.0.1:5000/weather";

        try{
            String result = restTemplate.getForObject(url, String.class);
            return new TextMessage(Objects.requireNonNull(result));//引数がnullの時、例外処理に飛ぶ
        }catch (RestClientException e){
            return new TextMessage(Objects.requireNonNull(e.getMessage()));
        }
    }
}
