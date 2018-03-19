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

public class TradeIntentUtteranceTest {
    private AmazonLexRuntime lexRuntimeClient = AmazonLexRuntimeClientBuilder.defaultClient();

    @Before
    @AfterEach
    public void endConversation() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("Goodbye");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
    }

    @Test
    public void tradeIntentPath1Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("Hey Tricia I want to trade a book");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("TradeBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("TradePurpose", textResult.getSlotToElicit());
        assertEquals("Hi, would this be a book you have or a book that you need?", textResult.getMessage());

        textRequest.setInputText("I have this book");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("OwnedISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you have?", textResult.getMessage());

        textRequest.setInputText("1234567890");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("DesiredISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you need?", textResult.getMessage());

        textRequest.setInputText("0987654321");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    @Test
    public void tradeIntentPath2Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("Hey Tricia I would like to trade a book");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("TradeBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("TradePurpose", textResult.getSlotToElicit());
        assertEquals("Hi, would this be a book you have or a book that you need?", textResult.getMessage());

        textRequest.setInputText("I need this book");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("DesiredISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you need?", textResult.getMessage());

        textRequest.setInputText("1234567890");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("OwnedISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you have?", textResult.getMessage());

        textRequest.setInputText("0987654321");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    @Test
    public void tradeIntentPath3Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("I have a book that I want to trade");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("TradeBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("OwnedISBN", textResult.getSlotToElicit());
        assertEquals("Hi, what is the ISBN number of the book you have?", textResult.getMessage());

        textRequest.setInputText("1234567890");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("DesiredISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you need", textResult.getMessage());

        textRequest.setInputText("0987654321");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    @Test
    public void tradeIntentPath4Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("I want to trade for a book that I need");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("TradeBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("DesiredISBN", textResult.getSlotToElicit());
        assertEquals("Hi, what is the ISBN number of the book you need?", textResult.getMessage());

        textRequest.setInputText("1234567890");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("OwnedISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you have?", textResult.getMessage());

        textRequest.setInputText("0987654321");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    @Test
    public void tradeIntentPath5Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("I want to trade a book with ISBN 1234567890");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("TradeBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("TradePurpose", textResult.getSlotToElicit());
        assertEquals("Hi, would this be a book you have or a book that you need?", textResult.getMessage());

        textRequest.setInputText("I have this book");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("DesiredISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you need?", textResult.getMessage());

        textRequest.setInputText("0987654321");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    @Test
    public void tradeIntentPath6Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("I want to trade a book with ISBN 1234567890");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("TradeBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("TradePurpose", textResult.getSlotToElicit());
        assertEquals("Hi, would this be a book you have or a book that you need?", textResult.getMessage());

        textRequest.setInputText("I need this book");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("OwnedISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you have?", textResult.getMessage());

        textRequest.setInputText("0987654321");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    @Test
    public void tradeIntentPath7Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("I have a book with ISBN 1234567890 that I want to trade");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("TradeBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("DesiredISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you need?", textResult.getMessage());

        textRequest.setInputText("0987654321");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    @Test
    public void tradeIntentPath8Success() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("I need a book with ISBN 1234567890 that I want to trade for");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("TradeBook", textResult.getIntentName());
        assertEquals(DialogState.ElicitSlot.toString(), textResult.getDialogState());
        assertEquals("OwnedISBN", textResult.getSlotToElicit());
        assertEquals("What is the ISBN number of the book you have?", textResult.getMessage());

        textRequest.setInputText("0987654321");
        textResult = lexRuntimeClient.postText(textRequest);
        assertEquals(DialogState.ReadyForFulfillment.toString(), textResult.getDialogState());
    }

    //If figure out a way to do it all in one message, add tests for that. Currently cannot determine which ISBN is
    //desired/owned if multiple are given.

}
