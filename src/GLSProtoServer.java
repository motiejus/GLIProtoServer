
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

    private ServerSocket socket;
    private Socket sock;
    private GLIService service;

	/**
	 * Starts the server and prints out the state of incomming GLSResponseProto objects
	 */
	public static void main (String [] argv) throws Exception {
        new GLSProtoServer().loop();
    }

    public void loop(){
		socket = new ServerSocket(PORT);
        service = new GLIService(this); 

		while (true) {

            JInPiqi.request req = read_message();

			JOutPiqi.response response = null;
			GLI inst = new GLIExample(service);

			if (req.hasInit()) {
				response = inst.init(req.getInit().getUseridListList());
			} else if (req.hasHandleAction()) {
				JInPiqi.handle_action a = req.getHandleAction();
				response = inst.handle_action(a.getUserid(), a.getCommandName(), a.getCommandArgsList(), a.getState());
			} else if (req.hasHandleUserJoin()) {
				JInPiqi.handle_user_join j = req.getHandleUserJoin();
				response = inst.handle_user_join(j.getUserid(), j.getState());
			} else if (req.hasHandleUserLeave()) {
				JInPiqi.handle_user_leave j = req.getHandleUserLeave();
				response = inst.handle_user_leave(j.getUserid(), j.getState());
			} else if (req.hasHandleTimer()) {
				JInPiqi.handle_timer j = req.getHandleTimer();
				response = inst.handle_timer(j.getId(), j.getDelta(), j.getState());
			} else if (req.hasHandleTimerComplete()) {
                JInPiqi.handle_timer_complete j = req.getHandleTimerComplete();
                response = inst.handle_timer_complete(j.getId(), j.getDelta(), j.getState());
            } else {
				System.out.println("Unknown command " + req);
			}

            write_message(response);
			
			os.flush();
			os.close();
			
			sock.close();
		}
	}

    public void send_system_request(JSystemPiqi.system_request request) {
        if (request != null) {
            write_message(JOutPiqi.response.getBuilder().setSystem(request).build());
            JInPiqi.request response = read_message();

            if(response.hasSystem()){
                JInPiqi.system_response system_response = packet.getSystem();
                if (!system_response.hasOk()) {
                    throw new Exception("Received error response: message: " 
                        + system_response.getError().getMessage() + ", code: " 
                        + system_response.getError().getCode()
                    );
                }
            }else{
                //for debugging
                throw new Exception("Unexpected response received from system");
            }
        }
    }

    public void write_message(Object r){        
        OutputStream os = sock.getOutputStream();

        if (r != null) {
                byte[] ret = r.toByteArray();
                java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate(ret.length + 4);
                bb.putInt(ret.length & 0x7fffff);
                bb.put(ret);
                os.write(bb.array());
            }
    }

    public void read_message(){
        System.out.println ("Waiting for connections on port " + PORT);

        //This waits for an incomming message
        sock = ss.accept();
        InputStream is = sock.getInputStream();        

        byte[] lb = new byte[4];
        int length = 0;
        //Gets the incoming data and recreates the proto object
        
        is.read(lb, 0, 4);
        length = lb[0] << 24 | lb[1] << 16 | lb[2] << 8 | lb[3] << 0;

        byte[] in = new byte[length];
        is.read(in, 0, length);
        return JInPiqi.request.parseFrom(in);
    }
}
