package advprog.billboardTropical.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler
public class BillboardTropicalControllerStub extends BillboardTropicalController{
	@EventMapping
	public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event){
		String str = "This is a test message";
		TextMessage msg = new TextMessage(str);
		return msg;
	}
}