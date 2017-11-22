package com.tradewithtricia;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClientBuilder;
import com.amazonaws.services.lexmodelbuilding.model.*;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.tradewithtricia.model.CreateBot;
import com.tradewithtricia.model.PublishBot;

/**
 * Hello world!
 *
 */
public class TradeWithTricia
{
    public static void main(String[] args)
    {
        AmazonLexRuntime lexRuntimeClient = AmazonLexRuntimeClientBuilder.defaultClient();
        AmazonLexModelBuilding lexModelBuildingClient = AmazonLexModelBuildingClientBuilder.defaultClient();

        //Get the bot we want to update
        GetBotRequest getBotRequest = new GetBotRequest();
        getBotRequest.setName("Tricia");
        getBotRequest.setVersionOrAlias("$LATEST");
        GetBotResult getBotResult = lexModelBuildingClient.getBot(getBotRequest);

        //Update our bot using the checksum retrieved
        CreateBot createBot = new CreateBot();
        createBot.putBot(lexModelBuildingClient);

        // Bot Publishing Process
        //PublishBot publishTricia = new PublishBot(getBotResult);
        //publishTricia.publishBot(lexModelBuildingClient);




    }
}
