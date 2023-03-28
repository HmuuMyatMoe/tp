package trackr.logic.menu;

import trackr.logic.commands.ClearItemCommand;
import trackr.model.ModelEnum;
import trackr.model.menu.MenuItem;

/**
 * Clears the menu.
 */
public class ClearMenuItemCommand extends ClearItemCommand<MenuItem> {

    public static final String COMMAND_WORD = "clear_menu";
    public static final String COMMAND_WORD_SHORTCUT = "clear_m";

    public ClearMenuItemCommand() {
        super(ModelEnum.MENUITEM);
    }
}
