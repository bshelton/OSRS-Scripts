package gdkiller.Tasks;

import gdkiller.utils.Areas;
import gdkiller.utils.SelfService;
import org.powerbot.script.Filter;
import org.powerbot.script.rt4.*;

import java.util.regex.Pattern;

public class TeleportToCorpBeast extends Task {


    SelfService helper = new SelfService(players.ctx);
    private String gamesRegex = ".*Games.*";
    private Pattern gamesPattern = Pattern.compile(gamesRegex);

    public TeleportToCorpBeast(ClientContext ctx){ super(ctx);}

    @Override
    public boolean activate() {
        return Areas.EDGEVILLE.getCentralTile().distanceTo(players.local()) < 25
                //&& wearingArmour()
                &&helper.haveGamesNecklace();
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

        ChatOption c = players.ctx.chat.select().text("Corporeal Beast.").poll();
        if (players.ctx.chat.chatting() && players.local().animation() == -1){
            c.select(true);
        }
    }

    private boolean wearingArmour(){
        players.ctx.game.tab(Game.Tab.EQUIPMENT);

        if (players.ctx.equipment.itemAt(Equipment.Slot.TORSO).id() > 0
                && players.ctx.equipment.itemAt(Equipment.Slot.OFF_HAND).name().equals("Anti-dragon shield")
                && players.ctx.equipment.itemAt(Equipment.Slot.MAIN_HAND).id() > 0){

            System.out.println("Wearing torso and has a shield");
            System.out.println("Wielding something to attack with");
            return true;

        }

        return false;
    }

}
