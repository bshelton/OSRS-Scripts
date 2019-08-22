package gdkiller.Tasks;

import gdkiller.utils.Areas;
import gdkiller.utils.SelfService;
import org.powerbot.script.Condition;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.*;

import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class TeleportToCorpBeast extends Task {


    SelfService helper = new SelfService(players.ctx);
    private String gamesRegex = ".*Games.*";
    private Pattern gamesPattern = Pattern.compile(gamesRegex);

    public TeleportToCorpBeast(ClientContext ctx){ super(ctx);}

    @Override
    public boolean activate() {
        return Areas.EDGEVILLE_BANK.getCentralTile().distanceTo(players.local()) < 5
                && wearingArmour()
                && SelfService.haveGamesNecklaceinInventory(players.ctx)
                && SelfService.haveFoodInInventory(players.ctx);
    }

    @Override
    public void execute(){

        players.ctx.game.tab(Game.Tab.INVENTORY);
        ItemQuery<Item> gamesQuery = players.ctx.inventory.select().name(gamesPattern);

        Item necklace = gamesQuery.poll();

        if (!players.ctx.chat.chatting() && players.local().animation() == -1){
            System.out.println("Not chatting");
            necklace.interact("Rub");
        }


        if (players.ctx.chat.chatting() && players.local().animation() == -1){
            System.out.println("Should select corp beast from chat.");

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    ChatOption c = players.ctx.chat.select().text("Corporeal Beast.").poll();
                    return c.select(true);
                }
            }, 250, 12);

        }
    }

    private boolean wearingArmour(){
        players.ctx.game.tab(Game.Tab.EQUIPMENT);

        if (players.ctx.equipment.itemAt(Equipment.Slot.TORSO).id() > 0
                && players.ctx.equipment.itemAt(Equipment.Slot.OFF_HAND).name().equals("Anti-dragon shield")
                && players.ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() > 0){

            return true;

        }

        return false;
    }

}
