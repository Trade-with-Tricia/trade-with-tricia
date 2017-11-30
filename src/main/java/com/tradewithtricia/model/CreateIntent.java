package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        //TODO: attach lambda function to buy intent when complete
//        this.fulfillmentActivity = new FulfillmentActivity().withType(FulfillmentActivityType.CodeHook)
//                .withCodeHook(new CodeHook().withMessageVersion("1.0")
//                    .withUri("arn:aws:lambda:us-east-1:140857943657:function:TestFunction"));//example uri
        //Just return intent for now
        this.fulfillmentActivity = new FulfillmentActivity().withType(FulfillmentActivityType.ReturnIntent);

        this.parentIntentSignature = null;

        //Set sampleUtterances
        Collection<String> sampleUtterances = new ArrayList<String>();
        sampleUtterances.add("{UserPhoneNumber} Hey Tricia I would like to {Buy} a book");
        sampleUtterances.add("{UserPhoneNumber} Hey Tricia I would like to {Buy} a book with ISBN {ISBN}");
        sampleUtterances.add("{UserPhoneNumber} I would like to {Buy} a book with ISBN number {ISBN}");
        sampleUtterances.add("{UserPhoneNumber} I'm trying to {Buy} a book");
        sampleUtterances.add("{UserPhoneNumber} I'm looking for a book to {Buy}");
        sampleUtterances.add("{UserPhoneNumber} I'm looking for a book");
        this.sampleUtterances = sampleUtterances;

        this.slots = this.getBuyIntentSlots();

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
        this.sampleUtterances = sampleUtterances;

        this.putIntent();
    }

    public void createGreetingIntent(boolean updateIntent) {
        this.intentName = "Greeting";

    }

    private void retrieveChecksum(String intentName, String version) {
        GetIntentRequest getIntentRequest = new GetIntentRequest().withName(intentName)
                .withVersion(version);
        GetIntentResult getIntentResult = this.lexModelBuildingClient.getIntent(getIntentRequest);
        this.checksum = getIntentResult.getChecksum();
    }

    private Collection<Slot> getBuyIntentSlots() {
        Collection<Slot> buyIntentSlots = new ArrayList<Slot>();
        Slot userPhoneNumber = new Slot().withName("UserPhoneNumber").withDescription("Phone Number of User")
                .withPriority(1).withSlotConstraint(SlotConstraint.Required)
                .withSlotType("AMAZON.NUMBER");
        Slot ISBN = new Slot().withName("ISBN").withDescription("ISBN of a book")
                .withPriority(3).withSlotConstraint(SlotConstraint.Required)
                .withSampleUtterances("The ISBN of my book is {ISBN}")
                .withSlotType("AMAZON.NUMBER").withValueElicitationPrompt(new Prompt().withMaxAttempts(2)
                    .withMessages(new Message().withContentType(ContentType.PlainText)
                        .withContent("What is the ISBN number of the book you want to buy?")));
        Slot buySlot = new Slot().withName("Buy").withDescription("Buy along with synonyms")
                .withPriority(2).withSlotConstraint(SlotConstraint.Required)
                .withSlotType("Buy").withSlotTypeVersion("$LATEST");

        buyIntentSlots.add(userPhoneNumber);
        buyIntentSlots.add(ISBN);
        buyIntentSlots.add(buySlot);
        return buyIntentSlots;
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