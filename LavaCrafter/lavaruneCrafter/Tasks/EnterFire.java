package LavaCrafter.lavaruneCrafter.Tasks;
import LavaCrafter.lavaruneCrafter.utils.Items;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;

import static ess.utils.Areas.OUTSIDE_RUINS;

public class EnterFire extends Task {

    public EnterFire(ClientContext ctx) {
        super(ctx);
    }

    public boolean activate() {
        return !players.ctx.inventory.select().id(Items.PURE_ESS_ID).isEmpty() && OUTSIDE_RUINS.getCentralTile().distanceTo(players.local()) < 20;
    }

    public void execute() {

        GameObject g = players.ctx.objects.select().id(Items.FIRE_RUIN_OBJECT_ID).poll();

        if (!g.inViewport()){
            camera.turnTo(g);
        }else{
            g.interact("Enter");
        }
    }
}