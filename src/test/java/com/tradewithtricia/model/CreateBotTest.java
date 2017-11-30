package com.tradewithtricia.model;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClientBuilder;
import com.amazonaws.services.lexmodelbuilding.model.Locale;
import com.amazonaws.services.lexmodelbuilding.model.PutBotResult;
import org.junit.Test;
import static org.junit.Assert.*;


public class CreateBotTest {
    public AmazonLexModelBuilding testClient = AmazonLexModelBuildingClientBuilder.defaultClient();
    public String botName;
    public String botChecksum;
    public boolean childDirected;
    public Locale locale;
    public CreateBot createBotObj;
    public PutBotResult testTricia;

//    @Test
//    public void putBotHappyPathTest() {
//        botName = "testTricia";
//        botChecksum = "$LATEST";
//        childDirected = false;
//        locale = Locale.EnUS;
//        createBotObj = new CreateBot(botName, botChecksum,childDirected, locale);
//        testTricia = createBotObj.putBot(testClient);
//        assertEquals("bot name", botName, testTricia.getName());
//        assertFalse("childDirected was true", testTricia.getChildDirected());
//        assertEquals("locale",locale, testTricia.getLocale());
//    }
//
//    @Test
//    public void putBotReturnsNullWhenBadParamsTest() {
//        createBotObj = new CreateBot(null, null, true, null);
//        testTricia = createBotObj.putBot(testClient);
//        assertNull("putBot was not null", testTricia);
//
//    }

}
