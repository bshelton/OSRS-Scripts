package gdkiller.Tasks;

import gdkiller.GreenDragonKiller;
import gdkiller.utils.Areas;
import gdkiller.GreenDragonKiller.*;

import org.powerbot.script.Filter;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.ItemQuery;
import org.powerbot.script.rt4.Npc;
import org.powerbot.script.Condition;


import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class AttackDrags extends Task{

    private final int[] DRAGON_IDS = {260, 261, 262, 263, 264};

    private String foodRegex = ".*"+GreenDragonKiller.foodSelection+".*";
    private Pattern foodPattern = Pattern.compile(foodRegex);

    public AttackDrags(ClientContext ctx) {super(ctx);}

    @Override
    public boolean activate(){

        return  Areas.GREEN_DRAGS_LVL24.getCentralTile().distanceTo(players.local()) < 75
                && canAttackDrag()
                && hasFoodInInventory();
    }

    @Override
    public void execute(){

        if (getDrag().animation() != 92 ) {// not dying
            getDrag().interact("Attack");
        }


        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return players.ctx.players.local().healthBarVisible();
            }
        }, 250, 12);
    }

    private boolean hasFoodInInventory(){
        ItemQuery<Item> foodQuery = players.ctx.inventory.select().name(foodPattern);
        return foodQuery.isEmpty();
    }

    private Npc getDrag(){
        Npc dragToAttack = players.ctx.npcs.select().id(DRAGON_IDS).select(new Filter<Npc>() {
            @Override
            public boolean accept(Npc npc) {
                return !npc.interacting().valid();
            }

            @Override
            public boolean test(Npc npc){return true;}
        }).nearest().poll();

        return dragToAttack;
    }

    private boolean canAttackDrag(){
        return getDrag().valid();
    }
}
