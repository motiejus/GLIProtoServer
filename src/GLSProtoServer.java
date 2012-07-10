
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Waits for incoming GLSResponseProto obects. When one arrives, recreates it from 
 * the inputStream and prints out its state 
 * @author kevinparkings
 *
 */
public class GLSProtoServer {

	private static final int PORT = 1234;

	/**
	 * Starts the server and prints out the state of incomming GLSResponseProto objects
	 */
	public static void main (String [] argv) throws Exception {

		ServerSocket ss = new ServerSocket(PORT);

		while (true) {

			System.out.println ("Waiting for connections on port " + PORT);

			//This waits for an incomming message
			Socket sock = ss.accept();

			byte[] lb = new byte[4];
			int length = 0;
			//Gets the incoming data and recreates the proto object
			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();
			is.read(lb, 0, 4);
			length = lb[0] << 24 | lb[1] << 16 | lb[2] << 8 | lb[3] << 0;

			byte[] in = new byte[length];
			is.read(in, 0, length);
			JInPiqi.command req = JInPiqi.command.parseFrom(in);

			JOutPiqi.callback_return r = null;
			GLI inst = new GLIExample();
			if (req.hasInit()) {
				r =	inst.init(req.getInit().getUseridListList());
			} else if (req.hasHandleAction()) {
				JInPiqi.handle_action a = req.getHandleAction();
				r = inst.handle_action(a.getCommandName(), a.getCommandArgsList(), a.getState());
			} else if (req.hasHandleUserJoin()) {
				JInPiqi.handle_user_join j = req.getHandleUserJoin();
				r = inst.handle_user_join(j.getUserid(), j.getState());
			} else if (req.hasHandleUserLeave()) {
				JInPiqi.handle_user_leave j = req.getHandleUserLeave();
				r = inst.handle_user_leave(j.getUserid(), j.getState());
			} else if (req.hasHandleTimer()) {
				JInPiqi.handle_timer j = req.getHandleTimer();
				r = inst.handle_timer(j.getIdentifier(), j.getDelta(), j.getState());
			} else {
				System.out.println("Unknown command " + req);
			}
			
			if (r != null) {
				byte[] ret = r.toByteArray();
				java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(ret.length + 4);
				bb.putInt(ret.length & 0x7fffff);
				bb.put(ret);
				os.write(bb.array());
			}
			os.flush();
			os.close();
			
			sock.close();
		}
	}
}
