package gdkiller.Tasks;


import gdkiller.utils.Areas;
import gdkiller.utils.Items;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.ChatOption;

public class ExitCorpCave extends Task {

    public ExitCorpCave(ClientContext ctx){super(ctx);}

    @Override
    public boolean activate(){return Areas.CORP_BEAST.getCentralTile().distanceTo(players.local()) < 25;}

    @Override
    public void execute(){

        GameObject g = players.ctx.objects.select().id(Items.CAVE_EXIT).poll();
        ChatOption c = players.ctx.chat.select().text("Yes.").poll();
        if (players.local().animation() == -1){
            g.interact("Exit");
            c.select(true);
        }



    }

}
