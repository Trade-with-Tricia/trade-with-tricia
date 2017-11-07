package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.CreateBotVersionRequest;
import com.amazonaws.services.lexmodelbuilding.model.CreateBotVersionResult;

public class PublishBot {
    private String botName;
    private String botChecksum;

    public PublishBot() {
        this.botName = "Tricia";
        this.botChecksum = "";
    }

    public PublishBot(String botName, String botChecksum) {
        this.botName = botName;
        this.botChecksum = botChecksum;
    }

    public void publishBot(AmazonLexModelBuilding modelBuildingClient) {
        CreateBotVersionRequest createBotVersionRequest = new CreateBotVersionRequest();
        createBotVersionRequest.setName(this.botName);
//        createBotVersionRequest.setChecksum(this.botChecksum);//Don't really need to set this since createBotVersion already publishes latest version
        CreateBotVersionResult createBotVersionResult = modelBuildingClient.createBotVersion(createBotVersionRequest);
        CreateBotAlias createBotAlias = new CreateBotAlias(createBotVersionResult.getName(),"stableTricia",
                createBotVersionResult.getVersion(), null, "Alias for latest stable version of Tricia");
        createBotAlias.putBotAlias(modelBuildingClient);
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getBotChecksum() {
        return botChecksum;
    }

    public void setBotChecksum(String botChecksum) {
        this.botChecksum = botChecksum;
    }
}
