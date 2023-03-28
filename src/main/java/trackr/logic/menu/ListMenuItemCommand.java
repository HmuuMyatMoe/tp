package trackr.logic.menu;

import trackr.logic.commands.ListItemCommand;
import trackr.model.ModelEnum;
import trackr.model.menu.MenuItem;

/**
 * Lists all items in the menu to the user.
 */
public class ListMenuItemCommand extends ListItemCommand<MenuItem> {

    public static final String COMMAND_WORD = "list_menu";
    public static final String COMMAND_WORD_SHORTCUT = "list_m";

    public ListMenuItemCommand() {
        super(ModelEnum.MENUITEM);
    }
}
