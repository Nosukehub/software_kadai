package com.example.linebot.replier;

import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

public class DialogAnswer implements Replier {

    private PostbackEvent event;

    public DialogAnswer(PostbackEvent event) {
        this.event = event;
    }

    @Override
    public Message reply() {
        String actionLabel = this.event.getPostbackContent().getData();
        switch (actionLabel) {
            case "CY":
                return new TextMessage("新札幌からのる");
            case "CN":
                return new TextMessage("南千歳からのる");

        }
        return new TextMessage("?");
    }
}