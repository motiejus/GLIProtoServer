public class GLSRequestHandler {

	private GLSProtoServer server;

	public GLSRequestHandler(GLSProtoServer server) {
		this.server = server;
	}

	public JSystemPiqi.system_response buy_item_request(String userId, int shopItemId, int amount) {
		JSystemPiqi.system_response response = null;

		JSystemPiqi.system_request request =
				JSystemPiqi.system_request.newBuilder().setBuyItem(
						JSystemPiqi.buy_item_request.newBuilder()
						.setUserid(userId)
						.setShopItemId(shopItemId)
						.setAmount(amount).build()
						).build();
		try{
			response = server.send_system_request(request);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}

	public JSystemPiqi.system_response grant_coins_request(String userId, int coins) {
		JSystemPiqi.system_response response = null;

		JSystemPiqi.system_request request =
				JSystemPiqi.system_request.newBuilder().setGrantCoins(
						JSystemPiqi.grant_coins_request.newBuilder()
						.setUserid(userId)
						.setCoins(coins).build()
						).build();
		try{
			response = server.send_system_request(request);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return response;
	}

}