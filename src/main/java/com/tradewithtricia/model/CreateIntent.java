package com.tradewithtricia.model;

/*
    This class is responsible for making the putIntent API calls after building each intent
 */

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.*;

import java.util.ArrayList;
import java.util.Collection;

public class CreateIntent {
    private String intentName;
    private String checksum;
    private Statement conclusionStatement;
    private Prompt confirmationPrompt;
    private String description;
    private CodeHook dialogCodeHook;
    private FollowUpPrompt followUpPrompt;
    private FulfillmentActivity fulfillmentActivity;
    private String parentIntentSignature;
    private Statement rejectionStatement;
    private Collection<String> sampleUtterances;
    private Collection<Slot> slots;
    private AmazonLexModelBuilding lexModelBuildingClient;

    public CreateIntent(String intentName, String checksum, Statement conclusionStatement,
                        Prompt confirmationPrompt, String description, CodeHook dialogCodeHook,
                        FollowUpPrompt followUpPrompt, FulfillmentActivity fulfillmentActivity,
                        String parentIntentSignature, Statement rejectionStatement,
                        Collection<String> sampleUtterances, Collection<Slot> slots,
                        AmazonLexModelBuilding lexModelBuildingClient) {
        this.intentName = intentName;
        this.checksum = checksum;
        this.conclusionStatement = conclusionStatement;
        this.confirmationPrompt = confirmationPrompt;
        this.description = description;
        this.dialogCodeHook = dialogCodeHook;
        this.followUpPrompt = followUpPrompt;
        this.fulfillmentActivity = fulfillmentActivity;
        this.parentIntentSignature = parentIntentSignature;
        this.rejectionStatement = rejectionStatement;
        this.sampleUtterances = sampleUtterances;
        this.slots = slots;
        this.lexModelBuildingClient = lexModelBuildingClient;
    }

    public CreateIntent() {}

    public void putIntent() {
        PutIntentRequest putIntentRequest = new PutIntentRequest();
        putIntentRequest.setName(this.intentName);
        putIntentRequest.setChecksum(this.checksum);
        putIntentRequest.setConclusionStatement(this.conclusionStatement);
        putIntentRequest.setConfirmationPrompt(this.confirmationPrompt);
        putIntentRequest.setDescription(this.description);
        putIntentRequest.setDialogCodeHook(this.dialogCodeHook);
        putIntentRequest.setFollowUpPrompt(this.followUpPrompt);
        putIntentRequest.setFulfillmentActivity(this.fulfillmentActivity);
        putIntentRequest.setParentIntentSignature(this.parentIntentSignature);
        putIntentRequest.setRejectionStatement(this.rejectionStatement);
        putIntentRequest.setSampleUtterances(this.sampleUtterances);
        putIntentRequest.setSlots(this.slots);
        PutIntentResult putIntentResult = this.lexModelBuildingClient.putIntent(putIntentRequest);
    }

    public void createBuyIntent(boolean updateIntent) {
        this.intentName = "BuyBook";

        if (updateIntent) this.retrieveChecksum(this.intentName, "$LATEST");
        else this.checksum = null;

        this.conclusionStatement = null;

        //Setup confirmation prompt
        this.confirmationPrompt = new Prompt().withMaxAttempts(2)
                .withMessages(new Message().withContentType(ContentType.PlainText)
                    .withContent("Is this the correct ISBN number: {ISBN}"));
        //Setup rejection message if the user says no to confirmation prompt
        this.rejectionStatement = new Statement().withMessages(new Message().withContentType(ContentType.PlainText)
                .withContent("Please re-enter the ISBN number in this format: \'ISBN: (ISBN # here)\'"));


        this.description = "Intent to handle user wanting to buy a book from Tricia";

        //Use this if we need to do any lambda initialization work
//        CodeHook codeHook = new CodeHook();
//        codeHook.setMessageVersion("1.0");
//        codeHook.setUri(null);
//        this.dialogCodeHook = codeHook;
        this.dialogCodeHook = null;

        //Setup followUpPrompt
        Prompt followUpPrompt = new Prompt().withMaxAttempts(2).withMessages(new Message()
                .withContentType(ContentType.PlainText)
                    .withContent("Is there anything else I can help you with today?"));
        Statement rejectionStatement = new Statement().withMessages(new Message()
                .withContentType(ContentType.PlainText)
                    .withContent("Okay, thanks for using Trade with Tricia. I hope I was able to help you with what you needed today."));
        this.followUpPrompt = new FollowUpPrompt().withPrompt(followUpPrompt)
                .withRejectionStatement(rejectionStatement);

        this.fulfillmentActivity = new FulfillmentActivity().withType(FulfillmentActivityType.CodeHook)
                .withCodeHook(new CodeHook().withMessageVersion("1.0")
                    .withUri("arn:aws:lambda:us-east-1:140857943657:function:AddBookBuyerSprint3"));//example uri

        this.parentIntentSignature = null;

        //Set sampleUtterances
        Collection<String> sampleUtterances = new ArrayList<String>();
        sampleUtterances.add("Hey Tricia I would like to {Buy} a book");
        sampleUtterances.add("Hey Tricia I would like to {Buy} a book with ISBN {ISBN}");
        sampleUtterances.add("I would like to {Buy} a book with ISBN number {ISBN}");
        sampleUtterances.add("I'm trying to {Buy} a book");
        sampleUtterances.add("I'm looking for a book to {Buy}");
        sampleUtterances.add("I'm {Buy} for a book");
        this.sampleUtterances = sampleUtterances;

        this.slots = this.getBuyIntentSlots();

        this.putIntent();
    }

    public void createSellIntent(boolean updateIntent) {
        this.intentName = "SellBook";

        if (updateIntent) this.retrieveChecksum(this.intentName, "$LATEST");
        else this.checksum = null;

        this.conclusionStatement = null;

        //Setup confirmation prompt
        this.confirmationPrompt = new Prompt().withMaxAttempts(2)
                .withMessages(new Message().withContentType(ContentType.PlainText)
                        .withContent("Is this the correct ISBN number: {ISBN}"));
        //Setup rejection message if the user says no to confirmation prompt
        this.rejectionStatement = new Statement().withMessages(new Message().withContentType(ContentType.PlainText)
                .withContent("Please re-enter the ISBN number in this format: \'ISBN: (ISBN # here)\'"));


        this.description = "Intent to handle user wanting to sell a book from Tricia";

        //Use this if we need to do any lambda initialization work
//        CodeHook codeHook = new CodeHook();
//        codeHook.setMessageVersion("1.0");
//        codeHook.setUri(null);
//        this.dialogCodeHook = codeHook;
        this.dialogCodeHook = null;

        //Setup followUpPrompt
        Prompt followUpPrompt = new Prompt().withMaxAttempts(2).withMessages(new Message()
                .withContentType(ContentType.PlainText)
                .withContent("Is there anything else I can help you with today?"));
        Statement rejectionStatement = new Statement().withMessages(new Message()
                .withContentType(ContentType.PlainText)
                .withContent("Okay, thanks for using Trade with Tricia. I hope I was able to help you with what you needed today."));
        this.followUpPrompt = new FollowUpPrompt().withPrompt(followUpPrompt)
                .withRejectionStatement(rejectionStatement);

        //TODO: attach lambda function to sell intent when complete
        this.fulfillmentActivity = new FulfillmentActivity().withType(FulfillmentActivityType.CodeHook)
                .withCodeHook(new CodeHook().withMessageVersion("1.0")
                    .withUri("arn:aws:lambda:us-east-1:140857943657:function:AddBook-ToSell"));

        this.parentIntentSignature = null;

        //Set sampleUtterances
        Collection<String> sampleUtterances = new ArrayList<String>();
        sampleUtterances.add("Hey Tricia I would like to {Sell} a book");
        sampleUtterances.add("Hey Tricia I would like to {Sell} a book with ISBN {ISBN}");
        sampleUtterances.add("I would like to {Sell} a book with ISBN number {ISBN}");
        sampleUtterances.add("I'm trying to {Sell} a book");
        sampleUtterances.add("I'm looking to {Sell} a book");
        this.sampleUtterances = sampleUtterances;

        this.slots = this.getSellIntentSlots();

        this.putIntent();
    }

    public void createEndConversationIntent(boolean updateIntent) {
        this.intentName = "EndConversation";

        if (updateIntent) this.retrieveChecksum(this.intentName, "$LATEST");
        else this.checksum = null;

        this.conclusionStatement = null;
        this.confirmationPrompt = null;
        this.description = "Intent to handle a user wanting to end the conversation";
        this.dialogCodeHook = null;
        this.followUpPrompt = null;
        this.fulfillmentActivity = new FulfillmentActivity().withType(FulfillmentActivityType.ReturnIntent);
        this.parentIntentSignature = null;
        this.rejectionStatement = null;

        this.slots = null;

        //Set sampleUtterances
        Collection<String> sampleUtterances = new ArrayList<String>();
        sampleUtterances.add("Goodbye");
        sampleUtterances.add("I don't need anything");
        sampleUtterances.add("Bye Tricia");
        sampleUtterances.add("Bye");
        this.sampleUtterances = sampleUtterances;

        this.putIntent();
    }

    public void createFirstTimeUserIntent(boolean updateIntent) {
        this.intentName = "FirstTimeUser";

        if (updateIntent) this.retrieveChecksum(this.intentName, "$LATEST");
        else this.checksum = null;

        this.conclusionStatement = null;

        this.confirmationPrompt = new Prompt().withMaxAttempts(2)
                .withMessages(new Message().withContentType(ContentType.PlainText)
                    .withContent("Just to confirm, is {UserFirstName} {UserLastName} your name?"));
        //Setup rejection message if the user says no to confirmation prompt
        this.rejectionStatement = new Statement().withMessages(new Message().withContentType(ContentType.PlainText)
            .withContent("Please re-enter your name in this format \'My name is (first) (last)\'"));

        this.description = "Intent to handle a first time user of TWT";

        this.dialogCodeHook = null;

        //Setup followUpPrompt
        this.followUpPrompt = new FollowUpPrompt().withPrompt(new Prompt().withMaxAttempts(2)
                .withMessages(new Message().withContentType(ContentType.PlainText)
                    .withContent("Thanks {UserFirstName}! What can I help you with today?")))
                    .withRejectionStatement(new Statement().withMessages(new Message()
                        .withContentType(ContentType.PlainText)
                        .withContent("Okay, thanks for using Trade with Tricia. I hope I was able to help you with what you needed today.")));

        //TODO: Need Lambda function to store user's name in db
        this.fulfillmentActivity = new FulfillmentActivity().withType(FulfillmentActivityType.CodeHook)
                .withCodeHook(new CodeHook().withMessageVersion("1.0")
                    .withUri("arn:aws:lambda:us-east-1:140857943657:function:SaveUsersName"));//example uri
        
        //this.fulfillmentActivity = new FulfillmentActivity().withType(FulfillmentActivityType.ReturnIntent);

        this.parentIntentSignature = null;

        Collection<String> sampleUtterances = new ArrayList<String>();
        sampleUtterances.add("This is a first time user");
        sampleUtterances.add("My name is {UserFirstName} {UserLastName}");
        this.sampleUtterances = sampleUtterances;

        this.slots = this.getFirstTimeUserSlots();

        this.putIntent();

    }


    private void retrieveChecksum(String intentName, String version) {
        GetIntentRequest getIntentRequest = new GetIntentRequest().withName(intentName)
                .withVersion(version);
        GetIntentResult getIntentResult = this.lexModelBuildingClient.getIntent(getIntentRequest);
        this.checksum = getIntentResult.getChecksum();
    }

    private Collection<Slot> getBuyIntentSlots() {
        Collection<Slot> buyIntentSlots = new ArrayList<Slot>();
        Slot ISBN = new Slot().withName("ISBN").withDescription("ISBN of a book")
                .withPriority(3).withSlotConstraint(SlotConstraint.Required)
                .withSampleUtterances("The ISBN of my book is {ISBN}")
                .withSlotType("AMAZON.NUMBER").withValueElicitationPrompt(new Prompt().withMaxAttempts(2)
                    .withMessages(new Message().withContentType(ContentType.PlainText)
                        .withContent("What is the ISBN number of the book you want to buy?")));

        Slot buySlot = new Slot().withName("Buy").withDescription("Buy along with synonyms")
                .withPriority(2).withSlotConstraint(SlotConstraint.Required)
                .withSlotType("Buy").withSlotTypeVersion("$LATEST")
                .withValueElicitationPrompt(new Prompt().withMaxAttempts(1)
                        .withMessages(new Message().withContentType(ContentType.PlainText)
                                .withContent("What would you like to do today?")));

        buyIntentSlots.add(ISBN);
        buyIntentSlots.add(buySlot);
        return buyIntentSlots;
    }

    private Collection<Slot> getSellIntentSlots() {
        Collection<Slot> sellIntentSlots = new ArrayList<Slot>();
        Slot ISBN = new Slot().withName("ISBN").withDescription("ISBN of a book")
                .withPriority(3).withSlotConstraint(SlotConstraint.Required)
                .withSampleUtterances("The ISBN of my book is {ISBN}")
                .withSlotType("AMAZON.NUMBER").withValueElicitationPrompt(new Prompt().withMaxAttempts(2)
                        .withMessages(new Message().withContentType(ContentType.PlainText)
                                .withContent("What is the ISBN number of the book you want to sell?")));
        Slot sellSlot = new Slot().withName("Sell").withDescription("Sell along with synonyms")
                .withPriority(2).withSlotConstraint(SlotConstraint.Required)
                .withSlotType("Sell").withSlotTypeVersion("$LATEST")
                .withValueElicitationPrompt(new Prompt().withMaxAttempts(1).withMessages(new Message().withContentType(ContentType.PlainText)
                        .withContent("What would you like to do today?")));;

        sellIntentSlots.add(ISBN);
        sellIntentSlots.add(sellSlot);
        return sellIntentSlots;
    }

    private Collection<Slot> getFirstTimeUserSlots() {
        Collection<Slot> firstTimeUserSlots = new ArrayList<Slot>();

        Slot userFirstName = new Slot().withName("UserFirstName").withDescription("First name of user")
                .withPriority(2).withSlotConstraint(SlotConstraint.Required)
                .withSlotType("AMAZON.US_FIRST_NAME")
                .withValueElicitationPrompt(new Prompt().withMaxAttempts(2)
                    .withMessages(new Message().withContentType(ContentType.PlainText)
                    .withContent("Hi! I see that you are a first time user of Trade with Tricia. If you accept " +
                            "our T&C here at www.dummyurl.com, please respond with your full name in this format " +
                            "\'My name is (first) (last)\'. Otherwise, do not worry about replying.")));

        Slot userLastName = new Slot().withName("UserLastName").withDescription("Last name of user")
                .withPriority(3).withSlotConstraint(SlotConstraint.Required)
                .withSlotType("AMAZON.US_LAST_NAME")
                .withValueElicitationPrompt(new Prompt().withMaxAttempts(1)
                        .withMessages(new Message().withContentType(ContentType.PlainText)
                        .withContent("Could you please re-enter your last name in the format \'Last name (last)\'?")));


        firstTimeUserSlots.add(userFirstName);
        firstTimeUserSlots.add(userLastName);
        return firstTimeUserSlots;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Statement getConclusionStatement() {
        return conclusionStatement;
    }

    public void setConclusionStatement(Statement conclusionStatement) {
        this.conclusionStatement = conclusionStatement;
    }

    public Prompt getConfirmationPrompt() {
        return confirmationPrompt;
    }

    public void setConfirmationPrompt(Prompt confirmationPrompt) {
        this.confirmationPrompt = confirmationPrompt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CodeHook getDialogCodeHook() {
        return dialogCodeHook;
    }

    public void setDialogCodeHook(CodeHook dialogCodeHook) {
        this.dialogCodeHook = dialogCodeHook;
    }

    public FollowUpPrompt getFollowUpPrompt() {
        return followUpPrompt;
    }

    public void setFollowUpPrompt(FollowUpPrompt followUpPrompt) {
        this.followUpPrompt = followUpPrompt;
    }

    public FulfillmentActivity getFulfillmentActivity() {
        return fulfillmentActivity;
    }

    public void setFulfillmentActivity(FulfillmentActivity fulfillmentActivity) {
        this.fulfillmentActivity = fulfillmentActivity;
    }

    public String getParentIntentSignature() {
        return parentIntentSignature;
    }

    public void setParentIntentSignature(String parentIntentSignature) {
        this.parentIntentSignature = parentIntentSignature;
    }

    public Statement getRejectionStatement() {
        return rejectionStatement;
    }

    public void setRejectionStatement(Statement rejectionStatement) {
        this.rejectionStatement = rejectionStatement;
    }

    public Collection<String> getSampleUtterances() {
        return sampleUtterances;
    }

    public void setSampleUtterances(Collection<String> sampleUtterances) {
        this.sampleUtterances = sampleUtterances;
    }

    public Collection<Slot> getSlots() {
        return slots;
    }

    public void setSlots(Collection<Slot> slots) {
        this.slots = slots;
    }

    public AmazonLexModelBuilding getLexModelBuildingClient() {
        return lexModelBuildingClient;
    }

    public void setLexModelBuildingClient(AmazonLexModelBuilding lexModelBuildingClient) {
        this.lexModelBuildingClient = lexModelBuildingClient;
    }

}