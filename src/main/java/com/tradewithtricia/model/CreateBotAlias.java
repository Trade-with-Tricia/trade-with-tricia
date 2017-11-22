package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.PutBotAliasRequest;
import com.amazonaws.services.lexmodelbuilding.model.PutBotAliasResult;

public class CreateBotAlias {
    private String botName;
    private String aliasName;
    private String botVersion;
    private String botAliasChecksum;
    private String botAliasDescription;

    public CreateBotAlias(String botName, String aliasName, String botVersion, String botAliasChecksum, String botAliasDescription) {
        this.botName = botName;
        this.aliasName = aliasName;
        this.botVersion = botVersion;
        this.botAliasChecksum = botAliasChecksum;
        this.botAliasDescription = botAliasDescription;
    }

    public void putBotAlias(AmazonLexModelBuilding modelBuildingClient) {
        PutBotAliasRequest putBotAliasRequest = new PutBotAliasRequest();
        putBotAliasRequest.setBotName(this.botName);
        putBotAliasRequest.setName(this.aliasName);
        putBotAliasRequest.setBotVersion(this.botVersion);
        //putBotAliasRequest.setChecksum(this.botAliasChecksum);//Specify checksum only when updating
        putBotAliasRequest.setDescription(this.botAliasDescription);
        PutBotAliasResult putBotAliasResult = modelBuildingClient.putBotAlias(putBotAliasRequest);
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getBotVersion() {
        return botVersion;
    }

    public void setBotVersion(String botVersion) {
        this.botVersion = botVersion;
    }

    public String getBotAliasChecksum() {
        return botAliasChecksum;
    }

    public void setBotAliasChecksum(String botAliasChecksum) {
        this.botAliasChecksum = botAliasChecksum;
    }

    public String getBotDescription() {
        return botAliasDescription;
    }

    public void setBotDescription(String botDescription) {
        this.botAliasDescription = botDescription;
    }
}
