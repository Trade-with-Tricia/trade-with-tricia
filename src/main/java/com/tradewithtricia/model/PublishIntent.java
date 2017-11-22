package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.*;

import java.util.List;

public class PublishIntent {
    private GetIntentResult intentToPublish;

    public PublishIntent(GetIntentResult intentToPublish) {
        this.intentToPublish = intentToPublish;
    }

    public void publishIntent(AmazonLexModelBuilding modelBuildingClient) {
        // first verify that the bot does not use the $LATEST version of a slot
        verifyBotUsesPublishedSlot(modelBuildingClient);
        // Publish the intent
        CreateIntentVersionRequest createIntentVersionRequest = new CreateIntentVersionRequest();
        createIntentVersionRequest.setName(this.intentToPublish.getName());
        //don't really need to set checksum since aws publishes latest version anyway, but should specify just to make sure
        //we're publishing the right version
        createIntentVersionRequest.setChecksum(this.intentToPublish.getChecksum());
        CreateIntentVersionResult createIntentVersionResult = modelBuildingClient.createIntentVersion(createIntentVersionRequest);
    }

    private void verifyBotUsesPublishedSlot(AmazonLexModelBuilding modelBuildingClient) {
        List<Slot> botSlots = this.intentToPublish.getSlots();
        for (int i = 0; i < botSlots.size(); i++) {
            if (botSlots.get(i).getSlotTypeVersion().equals("$LATEST")) {
                // bot doesn't use published version of a slot, need to publish first
                // get specific slot to publish
                GetSlotTypeRequest getSlotTypeRequest = new GetSlotTypeRequest();
                getSlotTypeRequest.setName(botSlots.get(i).getName());
                getSlotTypeRequest.setVersion(botSlots.get(i).getSlotTypeVersion());
                GetSlotTypeResult getSlotTypeResult = modelBuildingClient.getSlotType(getSlotTypeRequest);
                PublishSlotType pST = new PublishSlotType(getSlotTypeResult);
                pST.publishSlotType(modelBuildingClient);
            }
        }
    }

    public GetIntentResult getIntentToPublish() {
        return intentToPublish;
    }

    public void setIntentToPublish(GetIntentResult intentToPublish) {
        this.intentToPublish = intentToPublish;
    }
}
