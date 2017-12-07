package com.tradewithtricia;

import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.services.lexruntime.model.DialogState;
import com.amazonaws.services.lexruntime.model.PostTextRequest;
import com.amazonaws.services.lexruntime.model.PostTextResult;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class VerifyCorrectResponseTest {
    AmazonLexRuntime lexRuntimeClient = AmazonLexRuntimeClientBuilder.defaultClient();
    // Test bot from console
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

    @Test
    public void buyIntentSampleUtterance1() {
        PostTextRequest textRequest = new PostTextRequest().withBotName("Tricia").withBotAlias("dev").withUserId("testUser")
                .withInputText("123456 Hey Tricia I would like to purchase a book with ISBN 12345");
        PostTextResult textResult = lexRuntimeClient.postText(textRequest);
        assertEquals("BuyBook", textResult.getIntentName());
//        assertEquals("Is this the correct ISBN number: 12345", textResult2.getMessage());
        assertEquals(DialogState.ConfirmIntent.toString(), textResult.getDialogState());

        textRequest.withInputText("123456 Hey Tricia I would like to buy a book");
        PostTextResult textResult2 = lexRuntimeClient.postText(textRequest);
        assertEquals("BuyBook", textResult2.getIntentName());
        assertEquals("What is the ISBN number of the book you want to buy?", textResult2.getMessage());
        assertEquals(DialogState.ElicitSlot.toString(), textResult2.getDialogState());
        assertEquals("ISBN", textResult2.getSlotToElicit());


    }

}
