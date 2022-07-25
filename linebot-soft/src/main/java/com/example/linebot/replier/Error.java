package com.example.linebot.replier;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

public class Error implements Replier {

    @Override
    public Message reply() {
        return new TextMessage("その時間に電車はありません");
    }

}
