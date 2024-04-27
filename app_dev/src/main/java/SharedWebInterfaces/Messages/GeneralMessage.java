package SharedWebInterfaces.Messages;

import java.io.Serializable;

public interface GeneralMessage extends Serializable {
    void execute(GeneralAPI_Interface api);

}
