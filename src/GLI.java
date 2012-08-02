import java.util.List;

import com.google.protobuf.ByteString;

public interface GLI {
	public JOutPiqi.response init(List<String> players);
    public JOutPiqi.response handle_action(String user_id, String command_name, List<ByteString> args, ByteString state);
	public JOutPiqi.response handle_user_join(String user_id, ByteString state);
	public JOutPiqi.response handle_user_leave(String user_id, ByteString state);
	public JOutPiqi.response handle_timer(String id, int delta, ByteString state);
    public JOutPiqi.response handle_timer_complete(String id, int delta, ByteString state);
}
