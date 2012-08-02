 class SystemException extends Exception {
	 
	 public SystemException(int code, String message) {
		 super();
		 this.code = code;
		 this.message = message;
	 }
	 
	 public int getCode() { return this.code; }
	 public String getMessage() { return this.message; }
	 
	 private int code;
	 private String message;
 };

public class GLSRequestHandler {
	
	private GLSProtoServer server;

	public GLSRequestHandler(GLSProtoServer server) {
		this.server = server;
	}

	public void buy_item_request(String userId, int shopItemId, int amount) throws Exception {
		JSystemPiqi.system_request request =
				JSystemPiqi.system_request.newBuilder().setBuyItem(
						JSystemPiqi.buy_item_request.newBuilder()
						.setUserid(userId)
						.setShopItemId(shopItemId)
						.setAmount(amount).build()
						).build();
		server.send_system_request(request);
	}

	public void grant_coins_request(String userId, int coins) throws Exception {
		JSystemPiqi.system_request request =
				JSystemPiqi.system_request.newBuilder().setGrantCoins(
						JSystemPiqi.grant_coins_request.newBuilder()
						.setUserid(userId)
						.setCoins(coins).build()
						).build();
		server.send_system_request(request);
	}

}