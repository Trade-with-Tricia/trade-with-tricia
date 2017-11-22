package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.*;

import java.util.ArrayList;
import java.util.Collection;

public class CreateIntent {

    private String intentName;

    public String getIntentName() {return intentName;}

    public CreateIntent() {
        this.intentName = "AddBookToISBN";
    }

    public PutIntentResult putIntent(AmazonLexModelBuilding modelBuildingClient) {
        //TODO: add try catch in case intent not found
        GetIntentRequest getIntentRequest = new GetIntentRequest();
        getIntentRequest.setName(this.intentName);
        getIntentRequest.setVersion("1");
        GetIntentResult getIntentResult = modelBuildingClient.getIntent(getIntentRequest);


        CreateSlot ISBNslot = new CreateSlot("BookISBN", "Required");
        Collection<Slot> listOfSlots = new ArrayList<Slot>();
        listOfSlots.add(ISBNslot.createISBNSlot(modelBuildingClient));


        FulfillmentActivity intentFulfillmentActivity = new FulfillmentActivity();
        CodeHook addBooktoDatabase = new CodeHook();
        addBooktoDatabase.setUri("arn:aws:lambda:us-east-1:140857943657:function:TestFunction");
        addBooktoDatabase.setMessageVersion("1.0");
        intentFulfillmentActivity.setType("CodeHook");
        intentFulfillmentActivity.setCodeHook(addBooktoDatabase);

        Collection<String> intentUtterances = new ArrayList<String>();
        intentUtterances.add("Hey Tricia I would like to add a book to the database");

        PutIntentRequest putIntentRequest = new PutIntentRequest();
        putIntentRequest.setName(this.intentName);
        putIntentRequest.setSampleUtterances(intentUtterances);
        putIntentRequest.setChecksum(getIntentResult.getChecksum());
        putIntentRequest.setSlots(listOfSlots);
        putIntentRequest.setFulfillmentActivity(intentFulfillmentActivity);

        return modelBuildingClient.putIntent(putIntentRequest);
    }
}