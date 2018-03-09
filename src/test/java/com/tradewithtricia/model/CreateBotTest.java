package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClientBuilder;
import com.amazonaws.services.lexmodelbuilding.model.GetBotRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetBotResult;
import com.amazonaws.services.lexmodelbuilding.model.Intent;
import com.amazonaws.services.lexmodelbuilding.model.Locale;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class CreateBotTest {
    private AmazonLexModelBuilding lexModelBuildingClient = AmazonLexModelBuildingClientBuilder.defaultClient();

    @Test
    public void createTriciaHasCorrectParams() {
        CreateBot tricia = new CreateBot();
        tricia.setLexModelBuildingClient(lexModelBuildingClient);
        String botChecksum = this.retrieveChecksum();
        tricia.createTricia(true);
        assertEquals("Tricia", tricia.getBotName());
        assertEquals(botChecksum, tricia.getBotChecksum());
        assertEquals("Tricia is a bot to help students at UP buy, sell, and trade textbooks", tricia.getDescription());
        assertFalse(tricia.isChildDirected());
        assertEquals(Locale.EnUS, tricia.getLocale());
        assertEquals("SAVE", tricia.getProcessBehavior());
        assertEquals(7200, tricia.getIdleSessionTTLInSeconds());
        assertEquals("I am so sorry that I cant understand your requests right now. Maybe I can help you " +
                "another time.", tricia.getAbortStatement().getMessages().get(0).getContent());
        assertEquals("Can you please say that again?", tricia.getClarificationPrompt().getMessages().get(0).getContent());
        ArrayList<Intent> intents = new ArrayList<Intent>(tricia.getIntents());
        assertEquals("BuyBook", intents.get(0).getIntentName());
        assertEquals("SellBook", intents.get(1).getIntentName());
        assertEquals("EndConversation", intents.get(2).getIntentName());
        assertEquals("FirstTimeUser", intents.get(3).getIntentName());

    }

    private String retrieveChecksum() {
        //Get the bot we want to update
        GetBotRequest getBotRequest = new GetBotRequest().withName("Tricia").withVersionOrAlias("$LATEST");
        GetBotResult getBotResult = lexModelBuildingClient.getBot(getBotRequest);
        return getBotResult.getChecksum();
    }

}
