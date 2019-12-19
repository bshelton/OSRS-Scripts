package scripts.ess.Tasks;

import scripts.ess.EssRunner;
import org.powerbot.script.Condition;

import org.powerbot.script.rt4.Player;
import org.powerbot.script.rt4.Players;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.Callable;

import static scripts.ess.utils.Areas.*;

public class TradeCharacter extends Task {

    private Trade trade;

    public TradeCharacter(ClientContext ctx){
        super(ctx);
        trade = new Trade(ctx);

    }

    @Override
    public boolean activate() {
        System.out.println("trading activate");
        return AT_FIRE_ALTAR.getCentralTile().distanceTo(ctx.players.local()) < 70;
    }

    @Override
    public void execute(){
        System.out.println("we tradin boys");
        Players localPlayers = new Players(trade.ctx);
        for (final Player player : localPlayers){

            if (player.name().equals(EssRunner.crafterUsername)){
                System.out.println("Found player");
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
