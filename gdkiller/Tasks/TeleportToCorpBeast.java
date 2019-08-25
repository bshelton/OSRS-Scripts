package gdkiller.Tasks;

import gdkiller.utils.Areas;
import gdkiller.utils.SelfService;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.*;

import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class TeleportToCorpBeast extends Task {


    SelfService helper = new SelfService(ctx);
    private String gamesRegex = ".*Games.*";
    private Pattern gamesPattern = Pattern.compile(gamesRegex);

    public TeleportToCorpBeast(ClientContext ctx){ super(ctx);}

    @Override
    public boolean activate() {
        return Areas.EDGEVILLE_BANK.getCentralTile().distanceTo(ctx.players.local()) < 4
                && wearingArmour()
                && SelfService.haveGamesNecklaceinInventory(ctx)
                && SelfService.haveFoodInInventory(ctx);
    }

    @Override
    public void execute(){


        if (ctx.game.tab(Game.Tab.INVENTORY)){
            ctx.game.tab(Game.Tab.INVENTORY);

        }


        ItemQuery<Item> gamesQuery = ctx.inventory.select().name(gamesPattern);

        Item necklace = gamesQuery.poll();

        if (!ctx.chat.chatting() && ctx.players.local().animation() == -1){
            necklace.interact("Rub");
        }


        if (ctx.chat.chatting() && ctx.players.local().animation() == -1){
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    ChatOption c = ctx.chat.select().text("Corporeal Beast.").poll();
                    return c.select(true);
                }
            }, 250, 12);

        }
    }

    private boolean wearingArmour(){
        ctx.game.tab(Game.Tab.EQUIPMENT);

        if (ctx.equipment.itemAt(Equipment.Slot.TORSO).id() > 0
                && ctx.equipment.itemAt(Equipment.Slot.OFF_HAND).name().equals("Anti-dragon shield")
                && ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() > 0){

            return true;
        }
        return false;
    }

}
