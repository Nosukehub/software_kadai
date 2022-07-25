package com.example.linebot.replier;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

public class Summary implements Replier {

    private final MessageEvent<TextMessageContent> event;

    public Summary(MessageEvent<TextMessageContent> event){
        this.event = event;
    }

    @Override
    public Message reply() {
        //34行目の処理で、URLの後に文字を加えるため、24,25行目の処理が必要
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        // 改行を削除（要約で改行の入っている文章も取り扱いたいため,文字の種類によって\n,\rちがう

        text = text.replace("\n", "");
        text = text.replace("\r", "");

        //URLを使って何か処理を行うときにこのライブラリが必要
        RestTemplateBuilder templateBuilder= new RestTemplateBuilder();
        RestTemplate restTemplate = templateBuilder.build();

        String url = String.format("http://127.0.0.1:5000/summary?doc=%s", text);

        try{
            String[] results = restTemplate.getForObject(url, String[].class);
            //str型の配列resultsに、指定したURLから値を取得してそれを格納
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i< Objects.requireNonNull(results).length; i++) {
                stringBuilder.append(results[i]);//stringBuilder配列にresultsを加えておく
            }
            return new TextMessage(stringBuilder.toString());//最後にtextmessageを返す
        }catch (RestClientException e){
            return new TextMessage(Objects.requireNonNull(e.getMessage()));
        }
    }
}