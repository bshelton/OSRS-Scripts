package gdkiller.Tasks;

import gdkiller.utils.SelfService;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;

public class Loot extends Task{

    private final int DRAGON_BONES = 536;
    private final int[] PICKUP_ITEMS = {536, 1753, 995};

    public Loot(ClientContext ctx){super(ctx);}

    @Override
    public boolean activate(){
        GroundItem itemToPickup = players.ctx.groundItems.select().id(PICKUP_ITEMS).nearest().poll();
        return SelfService.idling(players.ctx)
                && itemsOnGround()
                && !players.ctx.inventory.isFull();
    }

    @Override
    public void execute(){
        GroundItem itemToPickup = players.ctx.groundItems.select().id(PICKUP_ITEMS).nearest().poll();
        itemToPickup.interact("Take");
    }

    private boolean itemsOnGround(){
        GroundItem itemToPickup = players.ctx.groundItems.select().id(PICKUP_ITEMS).nearest().poll();
        return itemToPickup.valid() && itemToPickup.inViewport();
    }
}
