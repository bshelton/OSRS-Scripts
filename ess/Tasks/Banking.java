package scripts.ess.Tasks;

import org.powerbot.script.rt4.*;
import org.powerbot.script.rt4.Equipment.*;
import scripts.ess.EssRunner;
import scripts.ess.utils.DuelingRing;

import java.util.regex.Pattern;

import static scripts.ess.utils.Items.*;
import static scripts.ess.utils.Areas.*;

public class Banking extends scripts.ess.Tasks.Task {


    public Banking(ClientContext ctx){
        super(ctx);
    }

    @Override
    public boolean activate() {
        return CASTLE_WARS_AREA.getCentralTile().distanceTo(players.local()) < 15;
    }

    @Override
    public void execute() {

        switch(EssRunner.runeToCraft) {

            case "Fire Rune":

                DuelingRing duelingRing = new DuelingRing();

                if (duelingRing.oneCharge(players.ctx)){
                    players.ctx.game.tab(Game.Tab.EQUIPMENT);
                    equipment.itemAt(Slot.RING).interact("Remove");
                    bank.open();
                    bank.depositInventory();
                    bank.close();
                }

                if (! duelingRing.wearingRing(players.ctx)) {
                    if (!bank.inViewport())
                        camera.turnTo(bank.nearest());

                    if (!bank.opened()) {
                        bank.open();
                        bank.depositInventory();

                        // Find Ring in Bank and Withdraw
                        for (int ring: duelingRing.rings){
                            for (Item item : bank.select().id(ring)){
                                if (item.id() == ring) {
                                    bank.withdraw(item.id(), Bank.Amount.ONE);
                                }
                            }
                        }
                        bank.close();

                        players.ctx.game.tab(Game.Tab.INVENTORY);
                        //Wear the ring
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

            default:
                System.out.println("default");
        }
    }

//    private boolean wearingRing(){
//
//        players.ctx.game.tab(Game.Tab.EQUIPMENT);
//
//        String dueling = "";
//
//        int i = 0;
//        while (!players.local().ctx.equipment.itemAt(Slot.RING).name().equals(dueling)){
//            i++;
//            dueling = "Ring of dueling(" + i + ")";
//
//            if (players.local().ctx.equipment.itemAt(Slot.RING).name().equals(dueling)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private boolean oneCharge(){
//        players.ctx.game.tab(Game.Tab.EQUIPMENT);
//        return players.local().ctx.equipment.itemAt(Slot.RING).name().equals("Ring of dueling(1)");
//    }


}