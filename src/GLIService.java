public class GLIService {

    private GLSProtoServer server;

    public GLIService(GLSProtoServer server) {
        this.server = server;
    }
    
    public void buy_item_request(String userId, int shopItemId, int amount){
        JSystemPiqi.system_request request = 
            JSystemPiqi.system_request.newBuilder().setBuyItem(
                JSystemPiqi.buy_item_request.newBuilder()
                    .setUserid(userId)
                    .setShopItemId(shopItemId)
                    .setAmount(amount).build()
            ).build();
        server.send_system_request(request);
    }

    public void grant_coins_request(String userId, int coins){
        JSystemPiqi.system_request request = 
            JSystemPiqi.system_request.newBuilder().setGrantCoins(
                JSystemPiqi.grant_coins_request.newBuilder().setUserid(userId)
                                                         .setCoins(coins).build()
            ).build();
        server.send_system_request(request);
    }

}