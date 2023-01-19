package ws.webchat.app.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatMessageTest {

    private ChatMessage chatMessage = new ChatMessage();


    @Test
    void getAndSetType() {
        chatMessage.setType(ChatMessage.MessageType.LEAVE);;
        Assert.assertEquals(ChatMessage.MessageType.LEAVE, chatMessage.getType());

    }



    @Test
    void getAndSetSender() {
        chatMessage.setSender("Boris");
        String expected = "Boris";
        Assert.assertEquals(expected, chatMessage.getSender());

    }

    @Test
    void getAndSetContent() {
        chatMessage.setContent("...content...");
        String expected = "...content...";
        Assert.assertEquals(expected, chatMessage.getContent());
    }
}