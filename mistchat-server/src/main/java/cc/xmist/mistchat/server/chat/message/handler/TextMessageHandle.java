package cc.xmist.mistchat.server.chat.message.handler;

import cc.xmist.mistchat.server.chat.entity.Message;
import cc.xmist.mistchat.server.chat.entity.MessageExtra;
import cc.xmist.mistchat.server.chat.message.AbstractMsgHandler;
import cc.xmist.mistchat.server.chat.message.extra.TextMsgExtra;
import cc.xmist.mistchat.server.common.enums.MessageType;
import org.springframework.stereotype.Component;

@Component
public class TextMessageHandle extends AbstractMsgHandler<TextMsgExtra> {
    @Override
    protected MessageType getMsgType() {
        return MessageType.TEXT;
    }

    @Override
    protected Message customSaveMsg(Message message, TextMsgExtra extra) {
        MessageExtra messageExtra = MessageExtra.builder().build();
        message.setExtra(messageExtra);
        return message;
    }

    @Override
    public void recall() {

    }
}
