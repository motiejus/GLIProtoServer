import java.util.List;

import com.google.protobuf.ByteString;

public interface GLI {
	public JOutPiqi.callback_return init(List<String> Players);
	public JOutPiqi.callback_return handle_action(String CommandName, List<ByteString> Args, ByteString State);
	public JOutPiqi.callback_return handle_user_join(String UserId, ByteString State);
	public JOutPiqi.callback_return handle_user_leave(String UserId, ByteString State); 
	public JOutPiqi.callback_return handle_timer(ByteString identifier, int delta, ByteString State);
}
