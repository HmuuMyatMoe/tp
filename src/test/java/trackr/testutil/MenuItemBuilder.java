package trackr.testutil;

import trackr.model.menu.ItemCost;
import trackr.model.menu.ItemName;
import trackr.model.menu.ItemPrice;
import trackr.model.menu.MenuItem;

/**
 * A utility class to help with building Task objects.
 */
public class MenuItemBuilder {

    public static final String DEFAULT_ITEM_NAME = "Chocolate cookies";
    public static final String DEFAULT_ITEM_COST = "0";
    public static final String DEFAULT_ITEM_PRICE = "5";

    private ItemName itemName;
    private ItemCost itemCost;
    private ItemPrice itemPrice;

    /**
     * Creates a {@code TaskBuilder} with the default details.
     */
    public MenuItemBuilder() {
        itemName = new ItemName(DEFAULT_ITEM_NAME);
        itemCost = new ItemCost(DEFAULT_ITEM_COST);
        itemPrice = new ItemPrice(DEFAULT_ITEM_PRICE);
    }

    /**
     * Initializes the TaskBuilder with the data of {@code taskToCopy}.
     */
    public MenuItemBuilder(MenuItem menuItemToCopy) {
        itemName = menuItemToCopy.getItemName();
        itemCost = menuItemToCopy.getItemCost();
        itemPrice = menuItemToCopy.getItemPrice();
    }

    /**
     * Sets the {@code TaskName} of the {@code Task} that we are building.
     */
    public MenuItemBuilder withItemName(String itemName) {
        this.itemName = new ItemName(itemName);
        return this;
    }

    /**
     * Sets the {@code TaskDeadline} of the {@code Task} that we are building.
     */
    public MenuItemBuilder withItemCost(String itemCost) {
        this.itemCost = new ItemCost(itemCost);
        return this;
    }

    /**
     * Sets the {@code TaskStatus} of the {@code Task} that we are building.
     */
    public MenuItemBuilder withItemPrice(String itemPrice) {
        this.itemPrice = new ItemPrice(itemPrice);
        return this;
    }

    public MenuItem build() {
        return new MenuItem(itemName, itemPrice, itemCost);
    }

}
