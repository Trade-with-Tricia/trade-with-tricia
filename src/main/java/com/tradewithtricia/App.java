package com.tradewithtricia;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClient;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClientBuilder;
import com.amazonaws.services.lexmodelbuilding.model.CreateBotVersionRequest;
import com.amazonaws.services.lexmodelbuilding.model.CreateBotVersionResult;
import com.amazonaws.services.lexmodelbuilding.model.PutBotResult;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        AmazonLexRuntime lexRuntimeClient = AmazonLexRuntimeClientBuilder.defaultClient();
        AmazonLexModelBuilding lexModelBuildingClient = AmazonLexModelBuildingClientBuilder.defaultClient();
        CreateBot createBot = new CreateBot(lexModelBuildingClient);
        PutBotResult tricia = createBot.getTricia();
        System.out.println(tricia.getName());



//        CreateBotVersionRequest createBotVersionRequest = new CreateBotVersionRequest();
//        createBotVersionRequest.setName(tricia.botName);
//        CreateBotVersionResult createBotVersionResult = lexModelBuildingClient.createBotVersion(createBotVersionRequest);
//        PostTextRequest textRequest = new PostTextRequest();
//        textRequest.setBotName("Tricia");
//        textRequest.setBotAlias("triciaLatest");
//        textRequest.setUserId("testing");
//        textRequest.setInputText("hey tricia");
//        PostTextResult textResult = client.postText(textRequest);
//        System.out.println(textResult.getDialogState());
//        System.out.println(textResult.getMessage());
//        System.out.println(textResult.getIntentName());

    }
}
