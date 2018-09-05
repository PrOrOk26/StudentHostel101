import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.LoggingMXBean;

public class ReceivedChatMessagesNotifyClientsRunnable implements Runnable{

	private Message messagesToSend;
	private Logger log;
	private Map<User,PrintWriter> myConnectionsWritersMap;
	
	public ReceivedChatMessagesNotifyClientsRunnable(Message messagesToSend) {
		try
		{
		this.messagesToSend=messagesToSend;
		
		myConnectionsWritersMap=new HashMap<>();
		
		log=Logger.getLogger(LoggingMXBean.class.getName());
		
		Map<User,Socket> sockets=CustomServer.getListeningForReceivedMessagesSocketMap();
		
		
		for(Map.Entry<User,Socket> entry: sockets.entrySet())
		{
			myConnectionsWritersMap.put(entry.getKey(),new PrintWriter(entry.getValue().getOutputStream()));
		}
		
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		
		List<Message> toSend=new ArrayList<>();
		toSend.add(messagesToSend);
		String responce=XMLMessagesCreator.createReceiveAllRecentChatMessagesXML(toSend);
		
		log.info("Result of XMLCreator for recent messages:\n");
		log.info(responce);
		
		for(Map.Entry<User, PrintWriter> entry:myConnectionsWritersMap.entrySet())
		{
			if(!entry.getKey().getLogin().equals(messagesToSend.getSender().getLogin()))
			{
		    entry.getValue().println(responce);
		    entry.getValue().flush();
			}
		
		}
	}

}
