package com.example.inventorymanger.view.swing;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import com.example.inventorymanager.view.swing.InventorySwingView;
import com.example.inventorymanger.model.Item;

import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(GUITestRunner.class)
public class ItemSwingViewTest extends AssertJSwingJUnitTestCase{

	private FrameFixture window;
	private InventorySwingView inventorySwingView;
	
	@Override
	protected void onSetUp() throws Exception {
		// TODO Auto-generated method stub
		GuiActionRunner.execute(()->{
			inventorySwingView = new InventorySwingView();
			return inventorySwingView;
		});
		window = new FrameFixture(robot(),inventorySwingView);
		window.show();
		
		
	}


	
	@Test @GUITest
	public void testInitialState() {
	    // Ensure buttons are initially disabled
	    window.button("btnAdd").requireDisabled();
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
		GuiActionRunner.execute(() ->
		inventorySwingView.getListItemModel().addElement(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop")));
		
		window.list("itemList").selectItem(0);
		JButtonFixture deleteButton =
		window.button(JButtonMatcher.withText("Delete Selected"));
		deleteButton.requireEnabled();
		window.list("itemList").clearSelection();
		deleteButton.requireDisabled();
	}
	
	@Test
	public void testsDisplayItemsShouldAddItemDescriptionsToTheList() {
		Item item1 = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		Item item2 = new Item("2", "Laptop", 12, 599.99, "gaming laptop");
		GuiActionRunner.execute(() ->
		inventorySwingView.displayItems(Arrays.asList(item1, item2))
				);
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(item1.toString(), item2.toString());
	}

	@Test
	public void testShowErrorMessageShouldShowTheMessageInTheErrorLabel() {
		Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		GuiActionRunner.execute(
			() -> inventorySwingView.showErrorMessage("error message", item)
		);
		window.label("errorMessageLabel")
			.requireText("error message: " + item);
	}
	
	@Test
	public void testItemAddedShouldAddTheItemToTheListAndResetTheErrorLabel() {
		Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		GuiActionRunner.execute(
				() ->
				inventorySwingView.addItem(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"))
				);
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(item.toString());
		window.label("errorMessageLabel").requireText(" ");
	}
	
	@Test
	public void testItemUpdatedShouldUpdateTheItemInTheListAndResetTheErrorLabel() {
	    Item updatedItem = new Item("1", "Laptop", 15, 899.99, "Updated laptop");
	    
	    GuiActionRunner.execute(() -> inventorySwingView.addItem(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop")));
	    
	
	    GuiActionRunner.execute(() -> inventorySwingView.updateItem(updatedItem));
	    
	    String[] listContents = window.list().contents();
	    assertThat(listContents).containsExactly(updatedItem.toString());
	    
	    window.label("errorMessageLabel").requireText(" ");
	}


	@Test
	public void testItemRemovedShouldRemoveTheItemFromTheListAndResetTheErrorLabel() {
		// setup
		Item item1 = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
		Item item2 = new Item("2", "Laptop", 12, 599.99, "gaming laptop");
		GuiActionRunner.execute(
			() -> {
				DefaultListModel<Item> listItemModel = inventorySwingView.getListItemModel();
				listItemModel.addElement(item1);
				listItemModel.addElement(item2);
			}
		);
		// execute
		GuiActionRunner.execute(
			() ->
			inventorySwingView.deleteItem(new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop"))
		);
		// verify
		String[] listContents = window.list().contents();
		assertThat(listContents).containsExactly(item2.toString());
		window.label("errorMessageLabel").requireText(" ");
	}

	
}
