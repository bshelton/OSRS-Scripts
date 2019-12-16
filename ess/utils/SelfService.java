package scripts.ess.utils;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Player;

//Need to make use of later.
public class SelfService {

    public SelfService(){}

    public static boolean idling(ClientContext ctx) {
        Player self = ctx.players.local();
        return self.animation() == -1
                && !self.healthBarVisible()
                && !self.inMotion();
    }
}
