package aps.view;

import java.util.ArrayList;
import java.util.List;

import org.jsmpp.examples.gateway.Gateway;
import org.smslib.AGateway.Protocols;
import org.smslib.InboundMessage;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

public class MessageAp {
	SerialModemGateway gateway;
	public void initGateway(){
		List<InboundMessage> msglist;
		 try{

			//initializing gateway.  I have used COM23 as my modem
			 // port. Make sure you insert your one.
			    gateway = new SerialModemGateway("modem.com16", "COM16", 115200, "Huawei", "E392");
			// Set the modem protocol to PDU (alternative is// TEXT).
			//PDU is the default, anyway...
			gateway.setProtocol(Protocols.PDU);

			//to get incoming messages.
			gateway.setInbound(true);

			// if you want to send messages
			gateway.setOutbound(true);

			// if you have set a sim pin,you should let SMSlib to 
			//know the pin
		//	gateway.setSimPin("0000");

			//attaching gateway obj to service obj.
			//you can add  more than one gateway to the service //object also
			Service.getInstance().addGateway(gateway);

			//starting the service
			Service.getInstance().startService();

			  }catch(Exception ex){
			       System.out.println(ex);
			   }
	}
	 public void readMessages () throws Exception{
     // Define a list which will hold the read messages.
     List<InboundMessage> msgList;
     msgList = new ArrayList<InboundMessage>();
     //service is setting to get unread messages
     Service.getInstance().readMessages(msgList, MessageClasses.UNREAD);

     for (InboundMessage msg : msgList){
     //display the message
     System.out.println(msg.getText());
     //display the sender’s phone no                            
    System.out.println(msg.getOriginator());
     }
	 }
/*	 public static void main(String args[]) {


		         MessageAp rm = new MessageAp();
		         rm.initGateway();

		        new Thread(new Runnable() {
		@Override
		            public void run() {
		                try {
		                    while(true){
		                    
		                     rm.readMessages();
		                    System.out.println("thread");
		                    Thread.sleep(1000);
		                    }
		                } catch (Exception ex) {
		                    System.out.println(ex);
		                }
		            }
		       }).start();

		        }*/
}
