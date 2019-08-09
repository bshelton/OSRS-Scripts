package LavaCrafter;

import LavaCrafter.Tasks.*;


import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;


import java.util.ArrayList;
import java.awt.*;


@Script.Manifest(
        name = "Lava Rune Crafter",
        description = "Script that makes Lava runes.",
        properties = "client = 4;"
)

public class LavaCrafter extends PollingScript<ClientContext>{

    private ArrayList<Task> tasks;
    public static String crafterUsername;
    public static boolean haveRunStart;

    public LavaCrafter(){
        tasks = new ArrayList<Task>();
    }

    @Override
    public void start(){
        System.out.println("Script Starting");

        tasks.add(new Banking(ctx));
        tasks.add(new TeleportToKharid(ctx));
        tasks.add(new WalkToAltar(ctx));
        tasks.add(new EnterFire(ctx));
        tasks.add(new MakeRunes(ctx));
        tasks.add(new TeleportToCastleWars(ctx));
    }

    @Override
    public void poll(){

        for (Task task : tasks) {
            if (task.activate())
                task.execute();
        }

    }

    @Override
    public void stop(){
        System.out.println("Script Stopped");
    }

}
