package gdkiller.Tasks;

import gdkiller.utils.SelfService;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GroundItem;
import org.powerbot.script.rt4.Item;
import org.powerbot.script.rt4.ItemQuery;

import java.util.Random;

public class Loot extends Task{

    private final int DRAGON_BONES = 536;
    private final int[] PICKUP_ITEMS = {536, 1753, 995};

    public Loot(ClientContext ctx){super(ctx);}

    @Override
    public boolean activate(){
        return itemsOnGround();
    }

    @Override
    public void execute(){

        GroundItem itemToPickup = ctx.groundItems.select().id(PICKUP_ITEMS).nearest().poll();

        if (SelfService.haveFoodInInventory(ctx)){
            if (ctx.combat.healthPercent() < 60){
                SelfService.getFood(ctx).interact("Eat");
            }

            if (ctx.inventory.isFull() && SelfService.haveFoodInInventory(ctx)){
                //Need to clear up inventory
                SelfService.getFood(ctx).interact("Eat");

                Condition.sleep(new Random().nextInt(1500));

                itemToPickup.interact("Take");
            } else{
                itemToPickup.interact("Take");
            }
        }

    }

    private boolean itemsOnGround(){
        GroundItem itemToPickup = ctx.groundItems.select().id(PICKUP_ITEMS).nearest().poll();
        return itemToPickup.valid() && itemToPickup.inViewport();
    }
}
