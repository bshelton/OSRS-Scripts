package scripts.gdkiller.Tasks;


import scripts.gdkiller.utils.Areas;
import scripts.gdkiller.utils.Items;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;
import org.powerbot.script.rt4.GameObject;
import org.powerbot.script.rt4.ChatOption;

public class ExitCorpCave extends Task {

    public ExitCorpCave(ClientContext ctx){super(ctx);}

    @Override
    public boolean activate(){return Areas.CORP_BEAST.getCentralTile().distanceTo(ctx.players.local()) < 25;}

    @Override
    public void execute(){
        ctx.game.tab(Game.Tab.ATTACK);
        ctx.combat.autoRetaliate(false);

        GameObject g = ctx.objects.select().id(Items.CAVE_EXIT).poll();

        if (!g.inViewport()){
            ctx.camera.turnTo(g);
        }

        ChatOption c = ctx.chat.select().text("Yes.").poll();
        if (ctx.players.local().animation() == -1 && !ctx.chat.chatting()){
            g.interact("Exit");

        }
        c.select(true);



    }

}
