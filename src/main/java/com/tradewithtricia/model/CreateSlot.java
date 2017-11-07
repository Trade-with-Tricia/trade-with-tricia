package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreateSlot {

    String slotName;
    String slotConstraint;

    public CreateSlot(String name, String givenSlotConstraint) {
        this.slotName = name;
        this.slotConstraint = givenSlotConstraint;
    }

    private BuiltinSlotTypeMetadata getBuiltInSlot(String signature, AmazonLexModelBuilding client){
        GetBuiltinSlotTypesRequest builtinSlotTypesRequest = new GetBuiltinSlotTypesRequest();
        builtinSlotTypesRequest.setMaxResults(1);
        builtinSlotTypesRequest.setSignatureContains(signature);
        GetBuiltinSlotTypesResult builtinSlotTypesResult = client.getBuiltinSlotTypes(builtinSlotTypesRequest);
        return builtinSlotTypesResult.getSlotTypes().get(0);
    }

    public Slot createISBNSlot(AmazonLexModelBuilding client){
        Collection<String> sampleUtterances = new ArrayList<String>();
        sampleUtterances.add("The ISBN of my book is {" + this.slotName + "}");

        Prompt ISBNprompts = new Prompt();
        List<Message> ISBNpromptsMessages = new ArrayList<Message>();
        Message prompt1Message = new Message();
        prompt1Message.setContent("What is the ISBN of the book?");
        prompt1Message.setContentType("PlainText");
        ISBNpromptsMessages.add(prompt1Message);
        ISBNprompts.setMessages(ISBNpromptsMessages);
        ISBNprompts.setMaxAttempts(2);

        Slot ISBNslot = new Slot();
        ISBNslot.setName(this.slotName);
        ISBNslot.setSlotConstraint(this.slotConstraint);
        BuiltinSlotTypeMetadata builtinSlot = getBuiltInSlot("AMAZON.NUMBER", client);
        ISBNslot.setSlotType(builtinSlot.getSignature());
        ISBNslot.setDescription("ISBN of book.");
        ISBNslot.setPriority(1);
        ISBNslot.setSampleUtterances(sampleUtterances);
        ISBNslot.setValueElicitationPrompt(ISBNprompts);

        return ISBNslot;


    }


}