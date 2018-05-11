package advprog.billboardTheHot100.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler

public class BillboardTheHot100ControllerStub extends BillboardTheHot100Controller{
	@EventMapping
	public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event){
		String str = "This is a test message";
		TextMessage msg = new TextMessage(str);
		return msg;
	}
}
