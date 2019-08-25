package gdkiller.Tasks;


import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.ClientAccessor;

public abstract class Task extends ClientAccessor<ClientContext>{

    public Task(ClientContext ctx){
        super(ctx);
    }

    public abstract boolean activate();
    public abstract void execute();
}
