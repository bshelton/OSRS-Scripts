package LavaCrafter.Tasks;

import LavaCrafter.LavaCrafter;
import LavaCrafter.Tasks.Task;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Player;
import org.powerbot.script.rt4.Players;

import java.util.concurrent.Callable;

import static LavaCrafter.utils.Areas.AT_FIRE_ALTAR;
import static LavaCrafter.utils.Areas.INSIDE_ALTAR;

public class TradeCharacter extends Task {

    private Trade trade;

    public TradeCharacter(ClientContext ctx){
        super(ctx);
        trade = new Trade(ctx);

    }

    @Override
    public boolean activate() {
        return AT_FIRE_ALTAR.getCentralTile().distanceTo(players.local()) < 60;
    }

    @Override
    public void execute(){
        Players localPlayers = new Players(trade.ctx);
        for (final Player player : localPlayers){

            if (player.name().equals(LavaCrafter.crafterUsername)){

                trade.tradeWith(player);
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return trade.opened();
                    }
                }, 100, 100);


                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return trade.hasPlayerAccepted();
                    }
                },100, 100);
                System.out.println("Other player accepted trade on first window");
                trade.accept();


                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return trade.hasPlayerAcceptedSecond() && trade.secondOpened();
                    }
                },100, 100);
                trade.accept();

            }

        }

    }
}
