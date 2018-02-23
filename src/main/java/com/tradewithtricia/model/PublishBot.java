package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.*;

import java.util.List;

public class PublishBot {
    private GetBotResult botToPublish;

    public PublishBot() {
        botToPublish = null;
    }

    public PublishBot(GetBotResult botToPublish) {
        this.botToPublish = botToPublish;
    }

    public void publishBot(AmazonLexModelBuilding modelBuildingClient) {
        // First verify bot uses published versions of intents
        verifyBotUsesPublishedIntent(modelBuildingClient);
        // Publish the bot
        CreateBotVersionRequest createBotVersionRequest = new CreateBotVersionRequest();
        createBotVersionRequest.setName(this.botToPublish.getName());
        //don't really need to set checksum since aws publishes latest version anyway, but should specify just to make sure
        //we're publishing the right version
        createBotVersionRequest.setChecksum(this.botToPublish.getChecksum());
        CreateBotVersionResult createBotVersionResult = modelBuildingClient.createBotVersion(createBotVersionRequest);

        // Create an alias that points to this most recent published version
        //TODO: change to update alias, creating a new one right now
        CreateBotAlias createBotAlias = new CreateBotAlias(createBotVersionResult.getName(), "stableTricia",
                createBotVersionResult.getVersion(),
                null, "Alias for latest stable version of Tricia");
        createBotAlias.putBotAlias(modelBuildingClient);
    }

    private void verifyBotUsesPublishedIntent(AmazonLexModelBuilding modelBuildingClient) {
        List<Intent> botIntents = this.botToPublish.getIntents();
        for (int i = 0; i < botIntents.size(); i++) {
            if (botIntents.get(i).getIntentVersion().equals("$LATEST")) {
                // bot doesn't use published version of an intent, need to publish first
                // get the specific intent to publish
                GetIntentRequest getIntentRequest = new GetIntentRequest();
                getIntentRequest.setName(botIntents.get(i).getIntentName());
                getIntentRequest.setVersion(botIntents.get(i).getIntentVersion());
                GetIntentResult getIntentResult = modelBuildingClient.getIntent(getIntentRequest);
                PublishIntent pI = new PublishIntent(getIntentResult);
                pI.publishIntent(modelBuildingClient);
            }
        }
    }

    public GetBotResult getBotToPublish() {
        return botToPublish;
    }

    public void setBotToPublish(GetBotResult botToPublish) {
        this.botToPublish = botToPublish;
    }
}