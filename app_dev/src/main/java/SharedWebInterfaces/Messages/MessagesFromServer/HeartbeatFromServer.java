package SharedWebInterfaces.Messages.MessagesFromServer;

import SharedWebInterfaces.SharedInterfaces.ViewAPI_Interface;

import java.util.HashMap;

public class HeartbeatFromServer  implements MessageFromServer{



    public HeartbeatFromServer() {}

    @Override
    public void execute(ViewAPI_Interface view) {
        view.heartbeatFromServer();
    }
}
