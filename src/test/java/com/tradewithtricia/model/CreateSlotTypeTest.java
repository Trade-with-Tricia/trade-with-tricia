package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClientBuilder;
import com.amazonaws.services.lexmodelbuilding.model.EnumerationValue;
import com.amazonaws.services.lexmodelbuilding.model.GetSlotTypeRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetSlotTypeResult;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class CreateSlotTypeTest {
    private AmazonLexModelBuilding lexModelBuildingClient = AmazonLexModelBuildingClientBuilder.defaultClient();

    @Test
    public void createBuyTypeSlotHasCorrectParams() {
        CreateSlotType buySlot = new CreateSlotType();
        buySlot.setLexModelBuildingClient(lexModelBuildingClient);
        String checksum = retrieveChecksum("Buy");
        buySlot.createBuyTypeSlot(true);
        assertEquals("Buy", buySlot.getSlotName());
        assertEquals(checksum, buySlot.getChecksum());
        assertEquals("Slot type to recognize buy and synonyms of buy", buySlot.getDescription());
        ArrayList<EnumerationValue> enumerationValues = new ArrayList<EnumerationValue>(buySlot.getEnumerationValues());
        assertEquals("buy", enumerationValues.get(0).getValue());
        assertEquals("purchase", enumerationValues.get(0).getSynonyms().get(0));
        assertEquals("find", enumerationValues.get(0).getSynonyms().get(1));
        assertEquals("looking", enumerationValues.get(0).getSynonyms().get(2));
        assertEquals("TOP_RESOLUTION", buySlot.getValueSelectionStrategy());
    }

    private String retrieveChecksum(String slotName) {
        GetSlotTypeRequest getSlotTypeRequest = new GetSlotTypeRequest().withName(slotName)
                .withVersion("$LATEST");
        GetSlotTypeResult getSlotTypeResult = this.lexModelBuildingClient.getSlotType(getSlotTypeRequest);
        return getSlotTypeResult.getChecksum();
    }
}
