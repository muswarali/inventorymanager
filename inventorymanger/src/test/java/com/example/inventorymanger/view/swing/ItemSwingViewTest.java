package com.example.inventorymanger.view.swing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultListModel;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.awaitility.Awaitility;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.inventorymanager.view.swing.InventorySwingView;
import com.example.inventorymanger.controller.ItemController;
import com.example.inventorymanger.model.Item;


@RunWith(GUITestRunner.class)
public class ItemSwingViewTest extends AssertJSwingJUnitTestCase{

	private FrameFixture window;
	private InventorySwingView inventorySwingView;
	
	
	@Mock
	private ItemController itemController;

	private AutoCloseable closeable;

	
	@Override
	protected void onSetUp() throws Exception {
		// TODO Auto-generated method stub
		closeable = MockitoAnnotations.openMocks(this);
		GuiActionRunner.execute(()->{
			inventorySwingView = new InventorySwingView();
			inventorySwingView.setItemController(itemController);
			return inventorySwingView;
		});
		window = new FrameFixture(robot(),inventorySwingView);
		window.show();
		
		
	}

	@Override
	protected void onTearDown() throws Exception {
		closeable.close();
	}

	
	@Test @GUITest
	public void testInitialState() {
	    // Ensure buttons are initially disabled
	    window.button("btnAdd").requireDisabled();
	    window.button("btnCancel").requireDisabled();
	    window.button("btnDeleteSelected").requireDisabled();
	    window.button("btnUpdateSelected").requireDisabled();

	    // Ensure all text fields are empty
	    window.textBox("idTextBox").requireEmpty();
	    window.textBox("nameTextBox").requireEmpty();
	    window.textBox("quantityTextBox").requireEmpty();
	    window.textBox("priceTextBox").requireEmpty();
	    window.textBox("descriptionTextBox").requireEmpty();
	}
	
	@Test @GUITest
	public void testEnableAddButtonOnValidInput() {
	    // Enter valid inputs
	    window.textBox("idTextBox").enterText("1");
	    window.textBox("nameTextBox").enterText("Laptop");
	    window.textBox("quantityTextBox").enterText("10");
	    window.textBox("priceTextBox").enterText("999.99");
	    window.textBox("descriptionTextBox").enterText("Gaming Laptop");

	    // Verify the Add button is now enabled
	    window.button("btnAdd").requireEnabled();
	}
	
	@Test @GUITest
	public void testWhenAnyInputIsEmptyAddButtonShouldBeDisabled()
	{
		JTextComponentFixture idTextBox =  window.textBox("idTextBox");
		JTextComponentFixture nameTextBox =  window.textBox("nameTextBox");
		JTextComponentFixture quantiyTextBox =  window.textBox("quantityTextBox");
		JTextComponentFixture priceTextBox =  window.textBox("priceTextBox");
		JTextComponentFixture descriptionTextBox =  window.textBox("descriptionTextBox");

		//when id is empty
		idTextBox.enterText(" ");
		nameTextBox.enterText("Laptop");
		quantiyTextBox.enterText("10");
		priceTextBox.enterText("999.99");
		descriptionTextBox.enterText("Gaming Laptop");
		window.button(JButtonMatcher.withText("Add Item")).requireDisabled();
		
		//reseting the inputs
		idTextBox.setText("");
		nameTextBox.setText("");
		quantiyTextBox.setText("");
		priceTextBox.setText("");
		descriptionTextBox.setText("");
		
		//when Name is empty
		idTextBox.enterText("1");
		nameTextBox.enterText(" ");
		quantiyTextBox.enterText("10");
		priceTextBox.enterText("999.99");
		descriptionTextBox.enterText("Gaming Laptop");
		window.button(JButtonMatcher.withText("Add Item")).requireDisabled();
		
		
		//reseting the inputs
		idTextBox.setText("");
		nameTextBox.setText("");
		quantiyTextBox.setText("");
		priceTextBox.setText("");
		descriptionTextBox.setText("");
		
		//when Quantity is empty
		idTextBox.enterText("1");
		nameTextBox.enterText("Laptop");
		quantiyTextBox.enterText(" ");
		priceTextBox.enterText("999.99");
		descriptionTextBox.enterText("Gaming Laptop");
		window.button(JButtonMatcher.withText("Add Item")).requireDisabled();
		
		
		//reseting the inputs
		idTextBox.setText("");
		nameTextBox.setText("");
		quantiyTextBox.setText("");
		priceTextBox.setText("");
		descriptionTextBox.setText("");
		
		
		//when Price is empty
		idTextBox.enterText("1");
		nameTextBox.enterText("Laptop");
		quantiyTextBox.enterText("10");
		priceTextBox.enterText(" ");
		descriptionTextBox.enterText("Gaming Laptop");
		window.button(JButtonMatcher.withText("Add Item")).requireDisabled();
		
		
		//reseting the inputs
		idTextBox.setText("");
		nameTextBox.setText("");
		quantiyTextBox.setText("");
		priceTextBox.setText("");
		descriptionTextBox.setText("");
		
		
		//when Description is empty
		idTextBox.enterText("1");
		nameTextBox.enterText("Laptop");
		quantiyTextBox.enterText("10");
		priceTextBox.enterText("999.99");
		descriptionTextBox.enterText(" ");
		window.button(JButtonMatcher.withText("Add Item")).requireDisabled();
		
	}
	
	@Test
	public void testDeleteButtonShouldBeEnabledOnlyWhenAItemIsSelected() {
	    GuiActionRunner.execute(
	        () -> 
	        inventorySwingView.getListItemModel().addElement(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"))
	    );
	    
	    window.list("itemList").selectItem(0);
	    JButtonFixture deleteButton = window.button(JButtonMatcher.withText("Delete Selected"));
	    deleteButton.requireEnabled();
	    window.list("itemList").clearSelection();
	    Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(deleteButton::requireDisabled);
	}

	@Test
	public void testsDisplayItemsShouldAddItemDescriptionsToTheList() {
	    Item item1 = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    Item item2 = new Item("2", "Laptop", 12, 599.99, "gaming laptop");
	    GuiActionRunner.execute(
	        () -> inventorySwingView.displayItems(Arrays.asList(item1, item2))
	    );
	    
	    Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
	        String[] listContents = window.list().contents();
	        assertThat(listContents).containsExactly(item1.toString(), item2.toString());
	    });
	}

	@Test
	public void testShowErrorMessageShouldShowTheMessageInTheErrorLabel() {
	    Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    inventorySwingView.showErrorMessage("error message", item);

	    Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
	        window.label("errorMessageLabel")
	            .requireText("error message: " + item);
	    });
	}

	@Test
	public void testItemAddedShouldAddTheItemToTheListAndResetTheErrorLabel() {
	    Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    inventorySwingView.addItem(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"));
	    Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
	        String[] listContents = window.list().contents();
	        assertThat(listContents).containsExactly(item.toString());
	        window.label("errorMessageLabel").requireText(" ");
	    });
	}

	@Test
	public void testItemUpdatedShouldUpdateTheItemInTheListAndResetTheErrorLabel() {
	    Item updatedItem = new Item("1", "Laptop", 15, 899.99, "Updated laptop");

	    inventorySwingView.addItem(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"));

	    inventorySwingView.updateItem(updatedItem);

	    Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
	        String[] listContents = window.list().contents();
	        assertThat(listContents).containsExactly(updatedItem.toString());
	        window.label("errorMessageLabel").requireText(" ");
	    });
	}

	@Test
	public void testItemRemovedShouldRemoveTheItemFromTheListAndResetTheErrorLabel() {
	    Item item1 = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    Item item2 = new Item("2", "Laptop", 12, 599.99, "gaming laptop");
	    GuiActionRunner.execute(
	        () -> {
	            DefaultListModel<Item> listItemModel = inventorySwingView.getListItemModel();
	            listItemModel.addElement(item1);
	            listItemModel.addElement(item2);
	        }
	    );
	    
	    GuiActionRunner.execute(
	        () -> inventorySwingView.deleteItem(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"))
	    );
	    
	    Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
	        String[] listContents = window.list().contents();
	        assertThat(listContents).containsExactly(item2.toString());
	        window.label("errorMessageLabel").requireText(" ");
	    });
	}

	@Test
	public void testAddButtonShouldDelegateToItemControllerNewItem() {
	    window.textBox("idTextBox").enterText("1");
	    window.textBox("nameTextBox").enterText("Laptop");
	    window.textBox("quantityTextBox").enterText("10");
	    window.textBox("priceTextBox").enterText("999.99");
	    window.textBox("descriptionTextBox").enterText("Gaming Laptop");
	    window.button(JButtonMatcher.withText("Add Item")).click();
	    Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
	        verify(itemController).addItem(new Item("1", "Laptop", 10, 999.99, "Gaming Laptop"));
	    });
	}

	@Test
	public void testAddButtonShouldBeDisabledWhenAllFieldsAreFilledButItemIsSelected() {
	    Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    GuiActionRunner.execute(() -> inventorySwingView.getListItemModel().addElement(item));
	    
	    window.list("itemList").selectItem(0);

	    window.textBox("idTextBox").requireText("1");
	    window.textBox("nameTextBox").enterText("Laptop");
	    window.textBox("quantityTextBox").enterText("10");
	    window.textBox("priceTextBox").enterText("999.99");
	    window.textBox("descriptionTextBox").enterText("Gaming Laptop");

	    // Verify that the Add button is disabled due to item selection
	    window.button(JButtonMatcher.withText("Add Item")).requireDisabled();
	}
	
	@Test
	public void testDeleteButtonShouldDelegateToItemControllerDeleteItem() {
	    Item item1 = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    Item item2 = new Item("2", "Laptop", 12, 599.99, "gaming laptop");
	    GuiActionRunner.execute(
	        () -> {
	            DefaultListModel<Item> listStudentsModel = inventorySwingView.getListItemModel();
	            listStudentsModel.addElement(item1);
	            listStudentsModel.addElement(item2);
	        }
	    );
	    window.list("itemList").selectItem(1);
	    window.button(JButtonMatcher.withText("Delete Selected")).click();
	    Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
	        verify(itemController).deleteItem(item2);
	    });
	}

	@Test
	public void testUpdateButtonShouldDelegateToItemControllerUpdateSelectedItem() {
	    Item originalItem1 = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    Item originalItem2 = new Item("2", "Laptop", 12, 599.99, "Gaming laptop");

	    GuiActionRunner.execute(() -> {
	        DefaultListModel<Item> listItemModel = inventorySwingView.getListItemModel();
	        listItemModel.addElement(originalItem1);
	        listItemModel.addElement(originalItem2);
	    });

	    window.list("itemList").selectItem(1);

	    window.textBox("idTextBox").requireText("2");
	    window.textBox("idTextBox").requireDisabled();

	    window.textBox("nameTextBox").setText("Updated Laptop");
	    window.textBox("quantityTextBox").setText("15");
	    window.textBox("priceTextBox").setText("699.99");
	    window.textBox("descriptionTextBox").setText("Updated gaming laptop");

	    window.button(JButtonMatcher.withText("Update Selected")).click();

	    Item updatedItem = new Item("2", "Updated Laptop", 15, 699.99, "Updated gaming laptop");
	    Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
	        verify(itemController).updateItem(updatedItem);
	    });
	}
	
	@Test
	public void testCancelButtonShouldClearSelectedItemAndResetFields() {
	    // Add an item to the list and simulate selection
	    Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    GuiActionRunner.execute(() -> inventorySwingView.getListItemModel().addElement(item));
	    
	    window.list("itemList").selectItem(0);
	    
	    window.textBox("idTextBox").requireText("1");
	    window.textBox("nameTextBox").requireText("Laptop");
	    window.textBox("quantityTextBox").requireText("10");
	    window.textBox("priceTextBox").requireText("999.99");
	    window.textBox("descriptionTextBox").requireText("High-end gaming laptop");
	    
	    window.button(JButtonMatcher.withText("Update Selected")).requireEnabled();
	    window.button(JButtonMatcher.withText("Delete Selected")).requireEnabled();
	    
	    window.button(JButtonMatcher.withText("Cancel")).click();
	    
	    window.textBox("idTextBox").requireText("");
	    window.textBox("nameTextBox").requireText("");
	    window.textBox("quantityTextBox").requireText("");
	    window.textBox("priceTextBox").requireText("");
	    window.textBox("descriptionTextBox").requireText("");
	    
	    window.button(JButtonMatcher.withText("Update Selected")).requireDisabled();
	    window.button(JButtonMatcher.withText("Delete Selected")).requireDisabled();
	    window.button(JButtonMatcher.withText("Add Item")).requireDisabled();
	    
	    assertThat(window.list("itemList").selection()).isEmpty();
	}

	@Test
	public void testAddButtonShouldBeDisabledWhenItemIsSelected() {
	    Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    GuiActionRunner.execute(() -> inventorySwingView.getListItemModel().addElement(item));

	    window.list("itemList").selectItem(0);
	    
	    window.button(JButtonMatcher.withText("Add Item")).requireDisabled();

	}
	
	@Test
	public void testAddButtonShouldBeEnabledWhenFieldsAreFilledAndNoItemIsSelected() {
	    // Ensure no item is selected
	    window.list("itemList").clearSelection();
	    
	    // Simulate key release event to populate all the fields
	    window.textBox("idTextBox").enterText("1");
	    window.textBox("nameTextBox").enterText("Laptop");
	    window.textBox("quantityTextBox").enterText("10");
	    window.textBox("priceTextBox").enterText("999.99");
	    window.textBox("descriptionTextBox").enterText("Gaming Laptop");

	    // Verify that the Add button is enabled when all fields are filled
	    window.button(JButtonMatcher.withText("Add Item")).requireEnabled();
	}
	
	@Test
	public void testItemUpdateWhenItemNotFound() {
	    Item nonExistentItem = new Item("2", "Tablet", 20, 299.99, "Updated tablet");

	    inventorySwingView.addItem(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"));

	    inventorySwingView.updateItem(nonExistentItem);

	    Awaitility.await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
	        String[] listContents = window.list().contents();
	        assertThat(listContents).containsExactly(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop").toString());
	    });

	    window.label("errorMessageLabel").requireText(" ");
	}



	
}
