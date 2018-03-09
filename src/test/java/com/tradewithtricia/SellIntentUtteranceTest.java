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

public class SellIntentUtteranceTest {
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
                .withInputText("Hey Tricia I would like to sell a book");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("SellBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("ISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you want to sell?", textResult.getMessage());
    }

    @Test
    public void sellIntentSampleUtterance2Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("Hey Tricia I would like to sell a book with ISBN 12345");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("SellBook", textResult.getIntentName());
        assertEquals(DialogState.ConfirmIntent.toString(), textResult.getDialogState());
        assertEquals("Is this the correct ISBN number: 12345", textResult.getMessage());
    }

    @Test
    public void sellIntentSampleUtterance3Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("I'm trying to unload a book");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("SellBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("ISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you want to sell?", textResult.getMessage());
    }

    @Test
    public void sellIntentSampleUtterance4Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("I'm looking to unload a book");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("SellBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("ISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you want to sell?", textResult.getMessage());
    }

    @Test
    public void sellIntentSampleUtterance5Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("I would like to sell a book with ISBN 12345");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("SellBook", textResult.getIntentName());
        assertEquals(DialogState.ConfirmIntent.toString(), textResult.getDialogState());
        assertEquals("Is this the correct ISBN number: 12345", textResult.getMessage());
    }

}

