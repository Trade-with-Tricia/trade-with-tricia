package com.tradewithtricia;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class App {
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "AC8e82966026ad06984e2438fd3257730c";
    public static final String AUTH_TOKEN = "c0ef4708432641607a7d95e74bede0e4";
    public static final String AVAYA = "+12088632997";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber("+19712010322"),
                new PhoneNumber("+15034448774"),
                "This is the ship that made the Kessel Run in fourteen parsecs?").create();

        System.out.println(message.getSid());
    }
}