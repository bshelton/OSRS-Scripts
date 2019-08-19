package ess.Tasks;

import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Equipment.*;

import java.util.regex.Pattern;

import static ess.utils.Items.*;
import static ess.utils.Areas.*;

public class Banking extends Task {


    public Banking(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        return CASTLE_WARS_AREA.getCentralTile().distanceTo(players.local()) < 15;
    }

    @Override
    public void execute() {

        if (oneCharge()){
            players.ctx.game.tab(Game.Tab.EQUIPMENT);
            equipment.itemAt(Slot.RING).interact("Remove");
            bank.open();
            bank.depositInventory();
            bank.close();

        }

        if (!wearingRing()) {
            if (!bank.inViewport())
                camera.turnTo(bank.nearest());

            if (!bank.opened()) {
                bank.open();
                bank.depositInventory();

                String dueling = "";
                for (int i = 1; i <= 8; i++) {
                    dueling = "Ring of dueling(" + i + ")";

                    for (Item item : bank.select().name(dueling)) {
                        if (item.name().equals(dueling)){
                            bank.withdraw(item.id(), Bank.Amount.ONE);
                        }
                    }

                }
                bank.close();
                players.ctx.game.tab(Game.Tab.INVENTORY);
                for (Item item: players.ctx.inventory.items()) {

                    if (item.name().contains("dueling")){
                        item.interact("Wear");
                    }
                }

                players.ctx.game.tab(Game.Tab.EQUIPMENT);

                bank.open();
                bank.withdraw(PURE_ESS_ID, Bank.Amount.ALL);
            }

        } else {
            if (!bank.inViewport())
                camera.turnTo(bank.nearest());
            if (!bank.opened()) {
                bank.open();
                bank.depositInventory();
                bank.withdraw(PURE_ESS_ID, Bank.Amount.ALL);
                bank.close();
            }

            bank.close();
        }
        bank.close();

    }

    private boolean wearingRing(){

        players.ctx.game.tab(Game.Tab.EQUIPMENT);

        String dueling = "";

        int i = 0;
        while (!players.local().ctx.equipment.itemAt(Slot.RING).name().equals(dueling)){
            i++;
            dueling = "Ring of dueling(" + i + ")";

            if (players.local().ctx.equipment.itemAt(Slot.RING).name().equals(dueling)) {
                System.out.println("Wearing the ring");
                return true;
            }
        }

        return false;
    }

    private boolean oneCharge(){
        players.ctx.game.tab(Game.Tab.EQUIPMENT);
        return players.local().ctx.equipment.itemAt(Slot.RING).name().equals("Ring of dueling(1)");
    }


}