import java.util.List;

import com.google.protobuf.ByteString;


public class GLIExample implements GLI {

	@Override
	public JOutPiqi.callback_return handle_action(String CommandName,
			List<ByteString> Args, ByteString State) {
		System.out.println("handle_action [" + CommandName + "] with [" + Args + "], state: ["+State.toStringUtf8() + "]");
		return demo().build();
	}

	@Override
	public JOutPiqi.callback_return handle_timer(ByteString identifier, int delta,
			ByteString State) {
		System.out.println("handle_timer [" + identifier.toStringUtf8() + "], delta [" + delta + "], State: [" + State.toStringUtf8() + "]");
		return demo().build();
	}

	@Override
	public JOutPiqi.callback_return handle_user_join(String UserId, ByteString State) {
		System.out.println("user_join [" + UserId + "]. State: [" + State.toStringUtf8() + "]");
		return demo().build();
	}

	@Override
	public JOutPiqi.callback_return handle_user_leave(String UserId, ByteString State) {
		System.out.println("user_leave [" + UserId + "]. State: [" + State.toStringUtf8() + "]");
		return demo().build();
	}

	@Override
	public JOutPiqi.callback_return init(List<String> Players) {
		System.out.println("init, players: [" + Players + "]");
		return demo().addTimers(JOutPiqi.gl_timer.newBuilder()
				.setDurationInMs(60*1000)
				.setTickDurationInMs(5*1000)
				.setTimerIdentifier(ByteString.copyFromUtf8("ticker"))).build();
	}

	private JOutPiqi.callback_return.Builder demo() {
		return JOutPiqi.callback_return.newBuilder()
		.addCommands(JOutPiqi.gl_command.newBuilder().setCont(true))
		.addMessages(JOutPiqi.gl_message.newBuilder()
				.setRecipient(
						JOutPiqi.gl_recipient.newBuilder().setAll(true))
						.setCommandName("hi"))
				.setNewState(ByteString.copyFromUtf8("newState"));

	}
}
