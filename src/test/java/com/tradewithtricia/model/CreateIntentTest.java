package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClientBuilder;
import com.amazonaws.services.lexmodelbuilding.model.FulfillmentActivityType;
import com.amazonaws.services.lexmodelbuilding.model.GetIntentRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetIntentResult;
import com.amazonaws.services.lexmodelbuilding.model.Slot;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CreateIntentTest {
    private AmazonLexModelBuilding lexModelBuildingClient = AmazonLexModelBuildingClientBuilder.defaultClient();

    @Test
    public void createBuyIntentHasCorrectParams() {
        CreateIntent buyIntent = new CreateIntent();
        buyIntent.setLexModelBuildingClient(lexModelBuildingClient);
        String buyChecksum = retrieveChecksum("BuyBook");
        buyIntent.createBuyIntent(true);
        assertEquals("BuyBook", buyIntent.getIntentName());
        assertEquals(buyChecksum, buyIntent.getChecksum());
        assertNull(buyIntent.getConclusionStatement());
        assertEquals("Is this the correct ISBN number: {ISBN}",
                buyIntent.getConfirmationPrompt().getMessages().get(0).getContent());
        assertEquals("Please re-enter the ISBN number in this format: \'ISBN: (ISBN # here)\'",
                buyIntent.getRejectionStatement().getMessages().get(0).getContent());
        assertEquals("Intent to handle user wanting to buy a book from Tricia", buyIntent.getDescription());
        assertNull(buyIntent.getDialogCodeHook());
        assertEquals("Is there anything else I can help you with today?", buyIntent.getFollowUpPrompt()
                .getPrompt().getMessages().get(0).getContent());
        assertEquals("Okay, thanks for using Trade with Tricia. I hope I was able to help you with what you needed today.",
                buyIntent.getFollowUpPrompt().getRejectionStatement().getMessages().get(0).getContent());
        assertEquals(FulfillmentActivityType.CodeHook.toString(), buyIntent.getFulfillmentActivity().getType());
        assertNull(buyIntent.getParentIntentSignature());
        ArrayList<String> utterances = new ArrayList<String>(buyIntent.getSampleUtterances());
        assertEquals("Hey Tricia I would like to {Buy} a book", utterances.get(0));
        assertEquals("Hey Tricia I would like to {Buy} a book with ISBN {ISBN}", utterances.get(1));
        assertEquals("I would like to {Buy} a book with ISBN number {ISBN}", utterances.get(2));
        assertEquals("I'm trying to {Buy} a book", utterances.get(3));
        assertEquals("I'm looking for a book to {Buy}", utterances.get(4));
        assertEquals("I'm {Buy} for a book", utterances.get(5));
        ArrayList<Slot> slots = new ArrayList<Slot>(buyIntent.getSlots());
        assertEquals("ISBN", slots.get(0).getName());
        assertEquals("Buy", slots.get(1).getName());

    }

    @Test
    public void createEndConversationIntentHasCorrectParams() {
        CreateIntent endConversationIntent = new CreateIntent();
        endConversationIntent.setLexModelBuildingClient(lexModelBuildingClient);
        String checksum = retrieveChecksum("EndConversation");
        endConversationIntent.createEndConversationIntent(true);
        assertEquals("EndConversation", endConversationIntent.getIntentName());
        assertEquals(checksum, endConversationIntent.getChecksum());
        assertNull(endConversationIntent.getConclusionStatement());
        assertNull(endConversationIntent.getConfirmationPrompt());
        assertEquals("Intent to handle a user wanting to end the conversation", endConversationIntent.getDescription());
        assertNull(endConversationIntent.getDialogCodeHook());
        assertNull(endConversationIntent.getFollowUpPrompt());
        assertEquals("ReturnIntent", endConversationIntent.getFulfillmentActivity().getType());
        assertNull(endConversationIntent.getParentIntentSignature());
        assertNull(endConversationIntent.getRejectionStatement());
        ArrayList<String> utterances = new ArrayList<String>(endConversationIntent.getSampleUtterances());
        assertEquals("Goodbye", utterances.get(0));
        assertEquals("I don't need anything", utterances.get(1));
        assertEquals("Bye Tricia", utterances.get(2));
        assertEquals("Bye", utterances.get(3));

    }

    @Test
    public void createFirstTimeUserIntentHasCorrectParams() {
        CreateIntent firstTimeUserIntent = new CreateIntent();
        firstTimeUserIntent.setLexModelBuildingClient(lexModelBuildingClient);
        String checksum = retrieveChecksum("FirstTimeUser");
        firstTimeUserIntent.createFirstTimeUserIntent(true);
        assertEquals("FirstTimeUser", firstTimeUserIntent.getIntentName());
        assertEquals(checksum, firstTimeUserIntent.getChecksum());
        assertNull(firstTimeUserIntent.getConclusionStatement());
        assertEquals("Just to confirm, is {UserFirstName} {UserLastName} your name?",
                firstTimeUserIntent.getConfirmationPrompt().getMessages().get(0).getContent());
        assertEquals("Please re-enter your name in this format \'My name is (first) (last)\'",
                firstTimeUserIntent.getRejectionStatement().getMessages().get(0).getContent());
        assertEquals("Intent to handle a first time user of TWT", firstTimeUserIntent.getDescription());
        assertNull(firstTimeUserIntent.getDialogCodeHook());
        assertEquals("Thanks {UserFirstName}! What can I help you with today?", firstTimeUserIntent.getFollowUpPrompt()
                .getPrompt().getMessages().get(0).getContent());
        assertEquals("Okay, thanks for using Trade with Tricia. I hope I was able to help you with what you needed today.",
                firstTimeUserIntent.getFollowUpPrompt().getRejectionStatement().getMessages().get(0).getContent());
        assertEquals("ReturnIntent", firstTimeUserIntent.getFulfillmentActivity().getType());
        assertNull(firstTimeUserIntent.getParentIntentSignature());
        ArrayList<String> utterances = new ArrayList<String>(firstTimeUserIntent.getSampleUtterances());
        assertEquals("This is a first time user", utterances.get(0));
        assertEquals("My name is {UserFirstName} {UserLastName}", utterances.get(1));
        ArrayList<Slot> slots = new ArrayList<Slot>(firstTimeUserIntent.getSlots());
        assertEquals("UserFirstName", slots.get(0).getName());
        assertEquals("UserLastName", slots.get(1).getName());
    }

    private String retrieveChecksum(String intentName) {
        GetIntentRequest getIntentRequest = new GetIntentRequest().withName(intentName)
                .withVersion("$LATEST");
        GetIntentResult getIntentResult = this.lexModelBuildingClient.getIntent(getIntentRequest);
        return getIntentResult.getChecksum();
    }
}
