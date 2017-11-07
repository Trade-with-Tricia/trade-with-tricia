package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.*;

import java.util.ArrayList;
import java.util.Collection;

public class CreateBot {
    //Required params
    private String botName;
    private String botChecksum;
    private boolean childDirected;
    private Locale locale;


    public CreateBot() {
        this.botName = "Tricia";
        this.childDirected = false;
        this.locale = Locale.EnUS;
    }

    public CreateBot(String botName, String botChecksum, boolean childDirected, Locale locale) {
        this.botName = botName;
        this.botChecksum = botChecksum;
        this.childDirected = childDirected;
        this.locale = locale;
    }

    public PutBotResult putBot(AmazonLexModelBuilding modelBuildingClient) {
        Prompt clarificationPrompt = new Prompt();
        clarificationPrompt.setMaxAttempts(3);
        Message clarificationMessage = new Message();
        clarificationMessage.setContentType("PlainText");
        clarificationMessage.setContent("Can you please say that again?");
        Collection<Message> listOfClarificationMessages = new ArrayList<Message>();
        listOfClarificationMessages.add(clarificationMessage);
        clarificationPrompt.setMessages(listOfClarificationMessages);

        Statement abortStatement = new Statement();
        Collection<Message> listOfAbortStatements = new ArrayList<Message>();
        Message abortMessage = new Message();
        abortMessage.setContentType("PlainText");
        abortMessage.setContent("I couldn't understand. Bye!");
        listOfAbortStatements.add(abortMessage);
        abortStatement.setMessages(listOfAbortStatements);

        //Commented out code should be used when we are not publishing a version
//        CreateIntent dummyIntent = new CreateIntent();
//        PutIntentResult dummyIntentResult = dummyIntent.putIntent(modelBuildingClient);

//        Collection<Intent> listOfIntents = new ArrayList<Intent>();
//        Intent actualDummyIntent = new Intent();
//        actualDummyIntent.setIntentName(dummyIntentResult.getName());
//        actualDummyIntent.setIntentVersion(dummyIntentResult.getVersion());
//        listOfIntents.add(actualDummyIntent);

        //Need to use this chunk of code when publishing because to publish a bot you need to used a published version of the intent
        GetIntentRequest getIntentRequest = new GetIntentRequest();
        getIntentRequest.setName("AddBookToISBN");
        getIntentRequest.setVersion("1");
        GetIntentResult getIntentResult = modelBuildingClient.getIntent(getIntentRequest);

        Collection<Intent> listOfIntents = new ArrayList<Intent>();
        Intent actualDummyIntent = new Intent();
        actualDummyIntent.setIntentName(getIntentRequest.getName());
        actualDummyIntent.setIntentVersion(getIntentRequest.getVersion());
        listOfIntents.add(actualDummyIntent);


        if (!validateRequiredParams()) {
            return null;
        }
        PutBotRequest putBotRequest = new PutBotRequest();
        putBotRequest.setName(this.botName);
        putBotRequest.setChecksum(this.botChecksum);
        putBotRequest.setChildDirected(this.childDirected);
        putBotRequest.setLocale(this.locale);
        putBotRequest.setDescription("Hello there");
        putBotRequest.setClarificationPrompt(clarificationPrompt);
        putBotRequest.setAbortStatement(abortStatement);
        putBotRequest.setIntents(listOfIntents);
        return modelBuildingClient.putBot(putBotRequest);
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

    public void setBotChecksum(String botChecksum) {
        this.botChecksum = botChecksum;
    }
}
