package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.*;

import java.util.ArrayList;
import java.util.Collection;

public class CreateBot {
    private String botName;
    private String botChecksum;
    private Statement abortStatement;
    private boolean childDirected;
    private Prompt clarificationPrompt;
    private String description;
    private int idleSessionTTLInSeconds;
    private Collection<Intent> intents;
    private Locale locale;
    private String processBehavior;
    private AmazonLexModelBuilding lexModelBuildingClient;

    public CreateBot() {}

    public CreateBot(String botName, String botChecksum, Statement abortStatement,
                     boolean childDirected, Prompt clarificationPrompt, String description,
                     int idleSessionTTLInSeconds, Collection<Intent> intents, Locale locale,
                     String processBehavior, AmazonLexModelBuilding lexModelBuildingClient) {
        this.botName = botName;
        this.botChecksum = botChecksum;
        this.abortStatement = abortStatement;
        this.childDirected = childDirected;
        this.clarificationPrompt = clarificationPrompt;
        this.description = description;
        this.idleSessionTTLInSeconds = idleSessionTTLInSeconds;
        this.intents = intents;
        this.locale = locale;
        this.processBehavior = processBehavior;
        this.lexModelBuildingClient = lexModelBuildingClient;
    }

    public CreateBot(String botName, String botChecksum, boolean childDirected, Locale locale) {
        this.botName = botName;
        this.botChecksum = botChecksum;
        this.childDirected = childDirected;
        this.locale = locale;
    }

    public void putBot() {
        if (!validateRequiredParams()) {
            return;
        }
        PutBotRequest putBotRequest = new PutBotRequest();
        putBotRequest.setName(this.botName);
        // To update our bot, make sure to set updateMode to true
        putBotRequest.setChecksum(this.botChecksum);
        putBotRequest.setAbortStatement(this.abortStatement);
        putBotRequest.setClarificationPrompt(this.clarificationPrompt);
        putBotRequest.setChildDirected(this.childDirected);
        putBotRequest.setLocale(this.locale);
        putBotRequest.setDescription(this.description);
        putBotRequest.setIdleSessionTTLInSeconds(this.idleSessionTTLInSeconds);
        putBotRequest.setIntents(this.intents);
        putBotRequest.setProcessBehavior(this.processBehavior);
        PutBotResult putBotResult = this.lexModelBuildingClient.putBot(putBotRequest);
    }

    public void createTricia(boolean updateBot) {
        this.botName = "Tricia";
        if (updateBot) this.retrieveChecksum(this.botName, "$LATEST");
        else this.botChecksum = null;

        this.description = "Tricia is a bot to help students at UP buy, sell, and trade textbooks";
        this.childDirected = false;
        this.locale = Locale.EnUS;
        this.processBehavior = "SAVE"; //can set to SAVE or BUILD
        this.idleSessionTTLInSeconds = 7200; // 2 hour conversation timeout

        this.abortStatement = new Statement().withMessages(new Message().withContentType(ContentType.PlainText)
            .withContent("I am so sorry that I cant understand your requests right now. Maybe I can help you another time."));

        this.clarificationPrompt = new Prompt().withMaxAttempts(3).withMessages(new Message()
                .withContentType(ContentType.PlainText)
                .withContent("Can you please say that again?"));

        this.intents = getAllTriciaIntents();

        this.putBot();
    }

    private void retrieveChecksum(String botName, String version) {
        //Get the bot we want to update
        GetBotRequest getBotRequest = new GetBotRequest().withName(botName).withVersionOrAlias(version);
        GetBotResult getBotResult = this.lexModelBuildingClient.getBot(getBotRequest);
        this.botChecksum = getBotResult.getChecksum();
        System.out.println("Checksum: " + this.botChecksum);
    }

    /*
    *   Get all intents associated with Tricia. If you create a new intent, need to get it here.
    *
    *
    */
    public Collection<Intent> getAllTriciaIntents() {
        Collection<Intent> triciaIntents = new ArrayList<Intent>();

        GetIntentRequest getBuyIntentRequest = new GetIntentRequest().withName("BuyBook").withVersion("$LATEST");
        GetIntentResult getButIntentResult = this.lexModelBuildingClient.getIntent(getBuyIntentRequest);
        triciaIntents.add(new Intent().withIntentName(getButIntentResult.getName())
                .withIntentVersion(getButIntentResult.getVersion()));

        GetIntentRequest getEndConversationIntentRequest = new GetIntentRequest().withName("EndConversation")
                .withVersion("$LATEST");
        GetIntentResult getEndConversationIntentResult = this.lexModelBuildingClient.getIntent(getEndConversationIntentRequest);
        triciaIntents.add(new Intent().withIntentName(getEndConversationIntentResult.getName())
                .withIntentVersion(getEndConversationIntentResult.getVersion()));

        return triciaIntents;
    }


    private boolean validateRequiredParams() {

        if (this.botName != null || this.locale != null) {
            if (this.botName.length() >= 2 && !this.childDirected && this.locale == Locale.EnUS) {
                return true;
            }
        }
        //these are bad params and we do not want to create a bot with these params
        //TODO: we may want to log these errors or define an exception class to account for these types of exceptions
        return false;
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

    public Statement getAbortStatement() {
        return abortStatement;
    }

    public void setAbortStatement(Statement abortStatement) {
        this.abortStatement = abortStatement;
    }

    public boolean isChildDirected() {
        return childDirected;
    }

    public void setChildDirected(boolean childDirected) {
        this.childDirected = childDirected;
    }

    public Prompt getClarificationPrompt() {
        return clarificationPrompt;
    }

    public void setClarificationPrompt(Prompt clarificationPrompt) {
        this.clarificationPrompt = clarificationPrompt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdleSessionTTLInSeconds() {
        return idleSessionTTLInSeconds;
    }

    public void setIdleSessionTTLInSeconds(int idleSessionTTLInSeconds) {
        this.idleSessionTTLInSeconds = idleSessionTTLInSeconds;
    }

    public Collection<Intent> getIntents() {
        return intents;
    }

    public void setIntents(Collection<Intent> intents) {
        this.intents = intents;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getProcessBehavior() {
        return processBehavior;
    }

    public void setProcessBehavior(String processBehavior) {
        this.processBehavior = processBehavior;
    }

    public AmazonLexModelBuilding getLexModelBuildingClient() {
        return lexModelBuildingClient;
    }

    public void setLexModelBuildingClient(AmazonLexModelBuilding lexModelBuildingClient) {
        this.lexModelBuildingClient = lexModelBuildingClient;
    }
}
