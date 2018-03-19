package com.tradewithtricia.model;

/*
    This class is responsible for making the putSlotType API calls after building each slot type
 */

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CreateSlotType {

    private String slotName;
    private String checksum;
    private String description;
    private Collection<EnumerationValue> enumerationValues;
    private String valueSelectionStrategy;
    private AmazonLexModelBuilding lexModelBuildingClient;

    public CreateSlotType(String slotName, String checksum, String description,
                          Collection<EnumerationValue> enumerationValues, String valueSelectionStrategy,
                          AmazonLexModelBuilding lexModelBuildingClient ) {
        this.slotName = slotName;
        this.checksum = checksum;
        this.description = description;
        this.enumerationValues = enumerationValues;
        this.valueSelectionStrategy = valueSelectionStrategy;
        this.lexModelBuildingClient = lexModelBuildingClient;
    }


    public CreateSlotType() {}

    private BuiltinSlotTypeMetadata getBuiltInSlot(String signature, AmazonLexModelBuilding client){
        GetBuiltinSlotTypesRequest builtinSlotTypesRequest = new GetBuiltinSlotTypesRequest();
        builtinSlotTypesRequest.setMaxResults(1);
        builtinSlotTypesRequest.setSignatureContains(signature);
        GetBuiltinSlotTypesResult builtinSlotTypesResult = client.getBuiltinSlotTypes(builtinSlotTypesRequest);
        return builtinSlotTypesResult.getSlotTypes().get(0);
    }

    public void putSlotType() {
        PutSlotTypeRequest putSlotTypeRequest = new PutSlotTypeRequest();
        putSlotTypeRequest.setName(this.slotName);
        putSlotTypeRequest.setChecksum(this.checksum);
        putSlotTypeRequest.setDescription(this.description);
        putSlotTypeRequest.setEnumerationValues(this.enumerationValues);
        putSlotTypeRequest.setValueSelectionStrategy(this.valueSelectionStrategy);
        PutSlotTypeResult putSlotTypeResult = this.lexModelBuildingClient.putSlotType(putSlotTypeRequest);
    }

    public void createBuyTypeSlot(boolean updateSlotType) {
        this.slotName = "Buy";

        if (updateSlotType) this.retrieveChecksum(this.slotName, "$LATEST");
        else this.checksum = null;

        this.description = "Slot type to recognize buy and synonyms of buy";
        Collection<String> buySynonyms = new ArrayList<String>();
        buySynonyms.add("purchase");
        buySynonyms.add("find");
        buySynonyms.add("looking");
        Collection<EnumerationValue> enumerationValues = new ArrayList<EnumerationValue>();
        enumerationValues.add(new EnumerationValue().withValue("buy").withSynonyms(buySynonyms));
        this.enumerationValues = enumerationValues;
        this.valueSelectionStrategy = "TOP_RESOLUTION";
        this.putSlotType();
    }

    public void createSellTypeSlot(boolean updateSlotType) {
        this.slotName = "Sell";

        if (updateSlotType) this.retrieveChecksum(this.slotName, "$LATEST");
        else this.checksum = null;

        this.description = "Slot type to recognize sell and synonyms of sell";
        Collection<String> sellSynonyms = new ArrayList<String>();
        sellSynonyms.add("unload");
        
        Collection<EnumerationValue> enumerationValues = new ArrayList<EnumerationValue>();
        enumerationValues.add(new EnumerationValue().withValue("sell").withSynonyms(sellSynonyms));
        this.enumerationValues = enumerationValues;
        this.valueSelectionStrategy = "TOP_RESOLUTION";
        this.putSlotType();
    }

    public void createTradeTypeSlot(boolean updateSlotType) {
        this.slotName = "Trade";

        if (updateSlotType) this.retrieveChecksum(this.slotName, "$LATEST");
        else this.checksum = null;

        this.description = "Slot type to recognize trade and synonyms of trade";

        Collection<String> tradeSynonyms = new ArrayList<String>();
        tradeSynonyms.add("exchange");
        tradeSynonyms.add("barter");

        Collection<EnumerationValue> enumerationValues = new ArrayList<EnumerationValue>();
        enumerationValues.add(new EnumerationValue().withValue("trade").withSynonyms(tradeSynonyms));
        this.enumerationValues = enumerationValues;
        this.valueSelectionStrategy = "TOP_RESOLUTION";
        this.putSlotType();
    }

    public void createTradePurposeTypeSlot(boolean updateSlotType) {
        this.slotName = "TradePurpose";

        if (updateSlotType) this.retrieveChecksum(this.slotName, "$LATEST");
        else this.checksum = null;

        this.description = "Slot type to recognize the trade purpose and synonyms of that trade purpose";

        Collection<String> haveSynonyms = new ArrayList<String>();
        haveSynonyms.add("own");
        haveSynonyms.add("possess");

        Collection<String> needSynonyms = new ArrayList<String>();
        needSynonyms.add("require");

        Collection<EnumerationValue> enumerationValues = new ArrayList<EnumerationValue>();
        enumerationValues.add(new EnumerationValue().withValue("have").withSynonyms(haveSynonyms));
        enumerationValues.add(new EnumerationValue().withValue("need").withSynonyms(needSynonyms));
        this.enumerationValues = enumerationValues;
        this.valueSelectionStrategy = "TOP_RESOLUTION";
        this.putSlotType();
    }

    private void retrieveChecksum(String slotName, String version) {
        GetSlotTypeRequest getSlotTypeRequest = new GetSlotTypeRequest().withName(slotName)
                .withVersion(version);
        GetSlotTypeResult getSlotTypeResult = this.lexModelBuildingClient.getSlotType(getSlotTypeRequest);
        this.checksum = getSlotTypeResult.getChecksum();
    }

    public String getSlotName() {
        return slotName;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<EnumerationValue> getEnumerationValues() {
        return enumerationValues;
    }

    public void setEnumerationValues(Collection<EnumerationValue> enumerationValues) {
        this.enumerationValues = enumerationValues;
    }

    public String getValueSelectionStrategy() {
        return valueSelectionStrategy;
    }

    public void setValueSelectionStrategy(String valueSelectionStrategy) {
        this.valueSelectionStrategy = valueSelectionStrategy;
    }

    public AmazonLexModelBuilding getLexModelBuildingClient() {
        return lexModelBuildingClient;
    }

    public void setLexModelBuildingClient(AmazonLexModelBuilding lexModelBuildingClient) {
        this.lexModelBuildingClient = lexModelBuildingClient;
    }
}
