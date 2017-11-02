package com.tradewithtricia;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.Locale;
import com.amazonaws.services.lexmodelbuilding.model.PutBotRequest;
import com.amazonaws.services.lexmodelbuilding.model.PutBotResult;

public class CreateBot {
    private String botName;
    private PutBotResult tricia;



    public CreateBot(AmazonLexModelBuilding client) {
        botName = "TriciaTwo";
        tricia = putBot(client);
    }

    private PutBotResult putBot(AmazonLexModelBuilding modelBuildingClient) {
        PutBotRequest putBotRequest = new PutBotRequest();
        putBotRequest.setName(botName);
        putBotRequest.setChildDirected(false);
        putBotRequest.setLocale(Locale.EnUS);
        PutBotResult putBotResult = modelBuildingClient.putBot(putBotRequest);
        return putBotResult;
    }

    public String getBotName() {
        return botName;
    }

    public PutBotResult getTricia() {
        return tricia;
    }
}
