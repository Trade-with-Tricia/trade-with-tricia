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

public class FirstTimeUserIntentUtteranceTest {
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
                .withInputText("This is a first time user");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("FirstTimeUser", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("UserFirstName", textResult.getSlotToElicit());
        assertEquals("Hi! I see that you are a first time user of Trade with Tricia. If you accept " +
                "our T&C here at www.dummyurl.com, please respond with your full name in this format " +
                "\'My name is (first) (last)\'. Otherwise, do not worry about replying.", textResult.getMessage());
    }

    @Test
    public void sellIntentSampleUtterance2Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("My name is Nate Camacho");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("FirstTimeUser", textResult.getIntentName());
        assertEquals(DialogState.ConfirmIntent.toString(), textResult.getDialogState());
        assertEquals("Just to confirm, is Nate Camacho your name?", textResult.getMessage());
    }

}

