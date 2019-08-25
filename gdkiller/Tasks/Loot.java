package gdkiller.Tasks;

import gdkiller.utils.Items;
import gdkiller.utils.SelfService;
import org.powerbot.script.Condition;
import org.powerbot.script.rt4.*;

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

        GroundItem itemToPickup = ctx.groundItems.select(5).id(Items.PICKUP_ITEMS).nearest().poll();

        if (SelfService.haveFoodInInventory(ctx)){
            if (ctx.combat.healthPercent() < 60){
                SelfService.getFood(ctx).interact("Eat");
            }

            if (ctx.inventory.isFull() && SelfService.haveFoodInInventory(ctx)){
                //Need to clear up inventory
                SelfService.getFood(ctx).interact("Eat");
                Condition.sleep(new Random().nextInt(1500));
                //itemToPickup.interact("Take");
                itemToPickup.interact( "Take");
            } else{
                itemToPickup.interact( "Take");

            }
        }
    }

    private boolean itemsOnGround(){
        BasicQuery<GroundItem> groundItems = ctx.groundItems.select().id(Items.PICKUP_ITEMS);

        if (groundItems.isEmpty()){
            return false;
        } else return true;
    }


}
