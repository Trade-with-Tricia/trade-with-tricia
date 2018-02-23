package com.tradewithtricia;

import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClientBuilder;
import com.amazonaws.services.lexmodelbuilding.model.*;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;
import com.tradewithtricia.model.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;

import java.util.Scanner;

import static org.apache.http.util.TextUtils.isEmpty;

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


//        AmazonLexModelBuilding lexModelBuildingClient = null;
//        try {
//            AWSCredentials awsCreds = new BasicAWSCredentials("AKIAI2ZEPBLONY6G7WAA",//IAM user's ACCESS_KEY
//                    "h752FhbffxY7u0Ie8HCLNSFN5rv/bUTm9aR25+AC");//IAM user's SECRET_KEY
//            lexModelBuildingClient = AmazonLexModelBuildingClientBuilder.standard()
//                    .withRegion(Regions.US_EAST_1)
//                    .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
//                    .build();

//        //Create all slots
            CreateSlot triciaSlots = new CreateSlot();
            triciaSlots.setLexModelBuildingClient(lexModelBuildingClient);
            triciaSlots.createBuyTypeSlot(true);
            triciaSlots.createSellTypeSlot(true);
            triciaSlots.createDeleteTypeSlot(true);


            //System.
            //Create all intents
            CreateIntent triciaIntents = new CreateIntent();
            triciaIntents.setLexModelBuildingClient(lexModelBuildingClient);
            triciaIntents.createBuyIntent(true);
            triciaIntents.createSellerDeleteBookIntent(true);
            triciaIntents.createEndConversationIntent(true);
            triciaIntents.createSellIntent(true);
            triciaIntents.createFirstTimeUserIntent(true);

            //Update our bot using the checksum retrieved
            CreateBot tricia = new CreateBot();
            tricia.setLexModelBuildingClient(lexModelBuildingClient);
            tricia.createTricia(true);

            GetBotRequest getBotRequest = new GetBotRequest().withName("Tricia").withVersionOrAlias("$LATEST");
            GetBotResult getBotResult = lexModelBuildingClient.getBot(getBotRequest);
        }


//         //Bot Publishing Process
//        PublishBot publishTricia = new PublishBot(getBotResult);
//        publishTricia.publishBot(lexModelBuildingClient);

//        CreateBotAlias createBotAlias = new CreateBotAlias("Tricia", "dev", "$LATEST",
//                null, "Alias for dev version of Tricia");
//        createBotAlias.putBotAlias(lexModelBuildingClient);
//
//         //Test bot from console
//        PostTextRequest textRequest = new PostTextRequest();
//        textRequest.setBotName("Tricia");
//        textRequest.setBotAlias("dev");
//        textRequest.setUserId("testUser");
//        Scanner scanner = new Scanner(System.in);
//        while(true) {
//            String requestText = scanner.nextLine().trim();
//            if(isEmpty(requestText))
//                break;
//
//            textRequest.setInputText(requestText);
//            PostTextResult textResult = lexRuntimeClient.postText(textRequest);
//            if(textResult.getDialogState().startsWith("Elicit"))
//                System.out.println(textResult.getMessage());
//            else if (textResult.getDialogState().equals("ConfirmIntent"))
//                System.out.println(textResult.getIntentName());
//            else if(textResult.getDialogState().equals("ReadyForFulfillment"))
//                System.out.println(String.format("%s: %s", textResult.getIntentName(), textResult.getSlots()));
//            else
//                System.out.println(textResult.toString());
//
//        }
//        System.out.println("Bye.");



}