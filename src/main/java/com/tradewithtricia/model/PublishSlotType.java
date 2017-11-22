package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.CreateSlotTypeVersionRequest;
import com.amazonaws.services.lexmodelbuilding.model.CreateSlotTypeVersionResult;
import com.amazonaws.services.lexmodelbuilding.model.GetSlotTypeResult;

public class PublishSlotType {
    private GetSlotTypeResult slotTypeToPublish;


    public PublishSlotType(GetSlotTypeResult getSlotTypeResult) {
        this.slotTypeToPublish = getSlotTypeResult;
    }

    public void publishSlotType(AmazonLexModelBuilding modelBuildingClient) {
        // Publish slot type
        CreateSlotTypeVersionRequest createSlotTypeVersionRequest = new CreateSlotTypeVersionRequest();
        createSlotTypeVersionRequest.setName(this.slotTypeToPublish.getName());
        //don't really need to set checksum since aws publishes latest version anyway, but should specify just to make sure
        //we're publishing the right version
        createSlotTypeVersionRequest.setChecksum(this.slotTypeToPublish.getChecksum());
        CreateSlotTypeVersionResult createSlotTypeVersionResult = modelBuildingClient.createSlotTypeVersion(createSlotTypeVersionRequest);
    }

    public GetSlotTypeResult getSlotTypeToPublish() {
        return slotTypeToPublish;
    }

    public void setSlotTypeToPublish(GetSlotTypeResult slotTypeToPublish) {
        this.slotTypeToPublish = slotTypeToPublish;
    }
}
