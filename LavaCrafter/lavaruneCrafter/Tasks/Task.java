package LavaCrafter.lavaruneCrafter.Tasks;
import org.powerbot.script.rt4.ClientContext;

public abstract class Task extends ClientContext {

    protected final int[] PORTALID = {14846, 14841, 14844};
    protected final int[] ESSENCE = {7936, 1436};
    protected final int[] RUINSID = {14399, 14409, 14405};
    protected final int[] craftAltar = {14902,14900,14897};

    public Task(ClientContext ctx){
        super(ctx);
    }

    public abstract boolean activate();
    public abstract void execute();
}
