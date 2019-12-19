package scripts.ess.Tasks;

import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Equipment.*;
import scripts.ess.EssRunner;
import scripts.ess.utils.FireRune;
import scripts.ess.utils.Items;
import scripts.ess.utils.SelfService;

import java.util.regex.Pattern;

import static scripts.ess.utils.Items.*;
import static scripts.ess.utils.Areas.*;

public class Banking extends scripts.ess.Tasks.Task {


    public Banking(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        return CASTLE_WARS_AREA.getCentralTile().distanceTo(ctx.players.local()) < 15;
    }

    @Override
    public void execute() {

        Bank bank = ctx.bank;

        switch (EssRunner.runeToCraft) {

            case "Fire Rune":
                if (!bank.inViewport())
                    ctx.camera.turnTo(bank.nearest());

                if (SelfService.wearingItem(ctx, FIRE_TIARA)){
                    if (!bank.opened()){
                        bank.open();
                        bank.depositInventory();
                        bank.withdraw(FIRE_TIARA, Bank.Amount.ONE);
                    }
                }

                if (SelfService.oneCharge(ctx)) {
                    ctx.game.tab(Game.Tab.EQUIPMENT);
                    ctx.equipment.itemAt(Slot.RING).interact("Remove");
                    bank.open();
                    bank.depositInventory();
                    bank.close();
                }

                if (!SelfService.wearingDuelingRing(ctx)) {
                    ItemQuery<Item> duelingRings = ctx.inventory.select().id(rings);
                    Item ring = duelingRings.poll();

                    if (!bank.opened()) {
                        bank.open();
                        bank.depositInventory();
                        bank.withdraw(ring.id(), Bank.Amount.ONE);
                        bank.close();

                        ctx.game.tab(Game.Tab.INVENTORY);
                        //Wear the ring
                        ring.interact("Wear");

                        ctx.game.tab(Game.Tab.EQUIPMENT);

                        bank.open();
                        bank.withdraw(PURE_ESS_ID, Bank.Amount.ALL);
                        bank.close();
                    }
                } else {
                    if (!bank.opened()) {
                        bank.open();
                        bank.depositInventory();
                        bank.withdraw(PURE_ESS_ID, Bank.Amount.ALL);
                        bank.close();
                    }
                    bank.close();
                }
            default:
                System.out.println("default");
        }
    }
}