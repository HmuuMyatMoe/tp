package trackr.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackr.logic.commands.CommandTestUtil.assertCommandFailure;
import static trackr.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackr.logic.commands.CommandTestUtil.showTaskAtIndex;
import static trackr.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static trackr.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static trackr.testutil.TypicalOrders.getTypicalOrderList;
import static trackr.testutil.TypicalSuppliers.getTypicalSupplierList;
import static trackr.testutil.TypicalTasks.getTypicalTaskList;

import org.junit.jupiter.api.Test;

import trackr.commons.core.Messages;
import trackr.commons.core.index.Index;
import trackr.model.Model;
import trackr.model.ModelManager;
import trackr.model.UserPrefs;
import trackr.model.order.Order;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteOrderCommand}.
 */
public class DeleteOrderCommandTest {
    private Model model = new ModelManager(getTypicalSupplierList(), getTypicalTaskList(),
            getTypicalOrderList(), new UserPrefs());
    
    @Test
    public void execute_validIndexUnfilteredOrderList_success() {
        Order taskToDelete = model.getFilteredOrderList().get(INDEX_FIRST_OBJECT.getZeroBased());
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(INDEX_FIRST_OBJECT);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS, taskToDelete);

        ModelManager expectedModel = new ModelManager(model.getSupplierList(), model.getTaskList(),
                model.getOrderList(), new UserPrefs());

        expectedModel.deleteOrder(taskToDelete);

        assertCommandSuccess(deleteOrderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredTaskList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_ORDER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredTaskList_success() {
        showTaskAtIndex(model, INDEX_FIRST_OBJECT);

        Order taskToDelete = model.getFilteredOrderList().get(INDEX_FIRST_OBJECT.getZeroBased());
        DeleteOrderCommand deleteTaskCommand = new DeleteOrderCommand(INDEX_FIRST_OBJECT);

        String expectedMessage = String.format(DeleteOrderCommand.MESSAGE_DELETE_ORDER_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getSupplierList(), model.getTaskList(),
                model.getOrderList(), new UserPrefs());
        expectedModel.deleteOrder(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(deleteTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredTaskList_throwsCommandException() {
        showTaskAtIndex(model, INDEX_FIRST_OBJECT);

        Index outOfBoundIndex = INDEX_SECOND_OBJECT;
        // ensures that outOfBoundIndex is still in bounds of task list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getOrderList().getOrderList().size());

        DeleteOrderCommand deleteOrderCommand = new DeleteOrderCommand(outOfBoundIndex);

        assertCommandFailure(deleteOrderCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteOrderCommand deleteOrderFirstCommand = new DeleteOrderCommand(INDEX_FIRST_OBJECT);
        DeleteOrderCommand deleteOrderSecondCommand = new DeleteOrderCommand(INDEX_SECOND_OBJECT);

        // same object -> returns true
        assertTrue(deleteOrderFirstCommand.equals(deleteOrderFirstCommand));

        // same values -> returns true
        DeleteOrderCommand deleteOrderCommandFirstCopy = new DeleteOrderCommand(INDEX_FIRST_OBJECT);
        assertTrue(deleteOrderFirstCommand.equals(deleteOrderCommandFirstCopy));

        // different types -> returns false
        assertFalse(deleteOrderFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteOrderFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(deleteOrderFirstCommand.equals(deleteOrderSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered task list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assertTrue(model.getFilteredTaskList().isEmpty());
    }
}
