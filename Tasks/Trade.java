package LavaCrafter.Tasks;

import org.powerbot.script.rt4.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Player TradeCharacter Interface.
 *
 */
public class Trade extends ItemQuery<Item> {

    public static final String WAIT_TEXT = "Other player has accepted.";
    public static final int
            WIDGET_SECOND = 334,
            ACCEPT_SECOND = 25,
            DECLINE_SECOND = 26,
            WAITING_FOR_SECOND = 4,

    WIDGET = 335,
            CLOSE_BUTTON = 14,
            ACCEPT_FIRST = 12,
            DECLINE_FIRST = 15,
            OUR_INVENTORY = 26,
            THEIR_INVENTORY = 29,
            WAITING_FOR_PLAYER = 30,
            DEPOSIT_INVENTORY = 40,
            DEPOSIT_POUCH = 41,
            OTHER_VALUE = 47,

    MONEY_WIDGET = 1469,
            MONEY_COMPONENT = 2;

    public Trade(ClientContext ctx) {
        super(ctx);
    }

    /**
     * Trades with the specified {@link Player}.
     *
     * @param player The player to trade with.
     * @return {@code true} if the player has been interacted with, otherwise,
     * {@code false}.
     */
    public boolean tradeWith(Player player) {
        return !opened() && player.valid() &&
                player.interact("Trade with", player.name());
    }

    /**
     * Determines whether or not the TradeCharacter interface is opened.
     *
     * @return {@code true} if opened, {@code false} otherwise.
     */
    public boolean opened() {
        return firstOpened() || secondOpened();
    }

    /**
     * Accepts the trade, whether it be on the first or second trading screen.
     * If on the first screen, it will require to be invoked once more in order
     * to accept on the second trading screen.
     *
     * @return {@code true} if the accept button has been properly clicked,
     * otherwise, {@code false}.
     */
    public boolean accept() {

        System.out.println("Is first window opened? " + firstOpened());
        System.out.println("Is the second window opened? " + secondOpened());

        return opened() && click(firstOpened() ? ACCEPT_FIRST : ACCEPT_SECOND);
    }

    //public boolean acceptSecond() { return opened() && click(secondOpened() ? ACCEPT_FIRST : ACCEPT_SECOND); }

    /**
     * Declines the trade, whether it be on the first or second trading screen.
     *
     * @return {@code} true if the decline button has been properly clicked,
     * otherwise, {@code false}.
     */
    public boolean decline() {
        return opened() && click(firstOpened() ? DECLINE_FIRST:DECLINE_SECOND);
    }

    /**
     * Offers the item up for trade.
     *
     * @param item The {@link Item} instance.
     * @return {@code true} if the item has been successfully offered,
     * otherwise, {@code false}.
     */
    public boolean offer(Item item) {
        return firstOpened() && item.valid() && item.interact("Offer");
    }


    /**
     * Offers our inventory in the trading screen.
     *
     * @return {@code true} if the inventory button has been properly clicked,
     * otherwise, {@code false}.
     */
    public boolean offerInventory() {
        return firstOpened() && ctx.widgets.component(WIDGET,
                DEPOSIT_INVENTORY).click();
    }

    /**
     * Determines if the other player has accepted.
     *
     * @return {@code true} if the player has accepted, otherwise,
     * {@code false}.
     */
    public boolean hasPlayerAccepted() {
        return opened() && getWidget().component(firstOpened()
                ? WAITING_FOR_PLAYER : WAITING_FOR_SECOND)
                .text().equals(WAIT_TEXT);
    }

    public boolean hasPlayerAcceptedSecond() {
        System.out.println(getWidget().component(WAITING_FOR_SECOND).text().equals(WAIT_TEXT));

        return opened() && getWidget().component(WAITING_FOR_SECOND).text().equals(WAIT_TEXT);
    }

    /**
     * Gets the market value of the other player's offer on the first trading
     * screen.
     *
     * @return The Market Value of the other player's items. Only works on the
     * first trading screen, otherwise, will return {@code 0}.
     */
    public int marketValue() {
        int value = 0;
        if(!firstOpened())
            return value;
        try {
            String s = ctx.widgets.component(WIDGET, OTHER_VALUE).text()
                    .split(" ")[0];
            value = Integer.parseInt(s.replace(",", ""));
        } catch(Exception e) {

        }
        return value;
    }

    @Override
    public Item nil() {
        Component c = ctx.widgets.component(-1,-1);
        return new Item(ctx,c, -1, -1);
    }

    @Override
    protected List<Item> get() {
        List<Item> list = new ArrayList<Item>();
        if(!firstOpened())
            return list;
        Component[] inv = ctx.widgets.component(WIDGET, THEIR_INVENTORY)
                .components();
        for(int i = 0; i < 28; i++) {
            if(inv[i].itemId() < 0)
                continue;
            list.add(new Item(ctx, inv[i], ((int) inv[i].itemStackSize()), i));
        }
        return list;
    }

    private boolean click( int component) {
        return getWidget().component(component).click();
    }

    private boolean firstOpened() {
        return ctx.widgets.component(WIDGET, ACCEPT_FIRST).visible();
    }

    public boolean secondOpened() {
        return ctx.widgets.component(WIDGET_SECOND, ACCEPT_SECOND).visible();
    }

    private Widget getWidget() {
        return ctx.widgets.widget(secondOpened() ? WIDGET_SECOND : WIDGET);
    }



}