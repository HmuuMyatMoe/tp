package trackr.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import trackr.commons.exceptions.IllegalValueException;
import trackr.model.commons.Deadline;
import trackr.model.commons.Name;
import trackr.model.menu.ItemCost;
import trackr.model.menu.ItemName;
import trackr.model.menu.ItemPrice;
import trackr.model.menu.MenuItem;

/**
 * Jackson-friendly version of {@link MenuItem}.
 */
class JsonAdaptedMenuItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Menu Item's %s field is missing!";

    private final String itemName;
    private final String itemCost;
    private final String itemPrice;

    /**
     * Constructs a {@code JsonAdaptedMenuItem} with the given menu item details.
     */
    @JsonCreator
    public JsonAdaptedMenuItem(@JsonProperty("itemName") String itemName,
                               @JsonProperty("itemCost") String itemCost,
                               @JsonProperty("itemPrice") String itemPrice) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemPrice = itemPrice;
    }

    /**
     * Converts a given {@code MenuItem} into this class for Jackson use.
     */
    public JsonAdaptedMenuItem(MenuItem source) {
        itemName = source.getItemName().getName();
        itemCost = source.getItemCost().toJsonString();
        itemPrice = source.getItemPrice().toJsonString();
    }

    /**
     * Converts this Jackson-friendly adapted menu item object into the model's {@code MenuItem} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted menu item.
     */
    public MenuItem toModelType() throws IllegalValueException {
        if (itemName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemName.class.getSimpleName()));
        }
        if (!ItemName.isValidName(itemName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final ItemName modelItemName = new ItemName(itemName);

        if (itemCost == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemCost.class.getSimpleName()));
        }
        if (!ItemCost.isValidCost(itemCost)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }
        final ItemCost modelItemCost = new ItemCost(itemCost);

        if (itemPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ItemPrice.class.getSimpleName()));
        }
        if (!ItemPrice.isValidPrice(itemPrice)) {
            throw new IllegalValueException(ItemPrice.MESSAGE_CONSTRAINTS);
        }
        final ItemPrice modelItemPrice = new ItemPrice(itemPrice);

        return new MenuItem(modelItemName, modelItemPrice, modelItemCost);
    }

}
