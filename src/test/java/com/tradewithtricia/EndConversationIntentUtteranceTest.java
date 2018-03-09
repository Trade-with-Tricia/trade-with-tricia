package com.tradewithtricia;

import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.lexruntime.model.DialogState;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;

public class EndConversationIntentUtteranceTest {
    private AmazonLexRuntime lexRuntimeClient = AmazonLexRuntimeClientBuilder.defaultClient();

    @Before
    @AfterEach
    public void endConversation() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("Goodbye");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
    }

    @Test
    public void sellIntentSampleUtterance1Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("Goodbye");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("EndConversation", textResult.getIntentName());
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    @Test
    public void sellIntentSampleUtterance2Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("Bye");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("EndConversation", textResult.getIntentName());
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    @Test
    public void sellIntentSampleUtterance3Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("Bye Tricia");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("EndConversation", textResult.getIntentName());
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    @Test
    public void sellIntentSampleUtterance4Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("I don't need anything");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("EndConversation", textResult.getIntentName());
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

}
