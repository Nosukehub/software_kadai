package com.example.linebot;

import com.example.linebot.replier.*;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.sql.Date;
import java.sql.Timestamp;
//



@LineMessageHandler
public class Callback {

    private static final Logger log = LoggerFactory.getLogger(Callback.class);
    // private Object Error;

    // フォローイベントに対応する
    @EventMapping
    public Message handleFollow(FollowEvent event) {
        // 実際はこのタイミングでフォロワーのユーザIDをデータベースにに格納しておくなど
        Follow follow = new Follow(event);
        return follow.reply();
    }


    //文章で話しかけられたとき（テキストメッセージのイベント）に対応する
    @EventMapping
    public Message handleMessage(MessageEvent<TextMessageContent> event) {
        TextMessageContent tmc = event.getMessage();
        String text = tmc.getText();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //ここはつかうクラスをすべてインスタンス化
        Summary cov = new Summary(event);
        Weather weather = new Weather(event);
        Intent intent = Intent.whichIntent(text);
        Parrot parrot = new Parrot(event);
//      return cov.reply();

        switch (intent) {
//            case "やあ":
//                Greet greet = new Greet();
//                return greet.reply();
//
//            case"ハロー":
//                Hello hello = new Hello(event);
//                return hello.reply();

//            case "今日の感染者数":
//                Summary covid = new Summary(event);
//                return covid.reply();

            case SUMMARY -> {
                return cov.reply();
            }
            case WEATHER -> {
                return weather.reply();
            }
            case UNKNOWN -> {
                return parrot.reply();
            }
//            default:
//                Parrot parrot = new Parrot(event);
//                return parrot.reply();
//            }
        }
        return parrot.reply();
    }
}

