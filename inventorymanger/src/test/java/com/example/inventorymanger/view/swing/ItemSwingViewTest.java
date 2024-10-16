package com.example.inventorymanger.view.swing;

import static org.junit.Assert.*;

import javax.swing.JLabel;

import org.assertj.swing.annotation.GUITest;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.runner.GUITestRunner;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import com.example.inventorymanager.view.swing.InventorySwingView;

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
	public void testAddItemAction() {
	    // Enter item details
	    window.textBox("idTextBox").enterText("1");
	    window.textBox("nameTextBox").enterText("Laptop");
	    window.textBox("quantityTextBox").enterText("10");
	    window.textBox("priceTextBox").enterText("999.99");
	    window.textBox("descriptionTextBox").enterText("Gaming Laptop");

	    // Click the Add Item button
	    window.button("btnAdd").click();

	    // Verify item appears in the list and error message is empty
	    window.list("itemList").requireItemCount(1);
	    window.label("errorMessageLabel").requireText("");
	}

	@Test @GUITest
	public void testDeleteSelectedItem() {
	    // Enter and add an item
	    window.textBox("idTextBox").enterText("1");
	    window.textBox("nameTextBox").enterText("Laptop");
	    window.textBox("quantityTextBox").enterText("10");
	    window.textBox("priceTextBox").enterText("999.99");
	    window.textBox("descriptionTextBox").enterText("Gaming Laptop");
	    window.button("btnAdd").click();

	    // Select the item in the list
	    window.list("itemList").selectItem(0);

	    // Verify that Delete Selected button is enabled and click it
	    window.button("btnDeleteSelected").requireEnabled().click();

	    // Verify the item list is now empty
	    window.list("itemList").requireItemCount(0);
	}
	
	@Test @GUITest
	public void testErrorMessageDisplay() {
	    // Leave name field empty and try to add
	    window.textBox("idTextBox").enterText("1");
	    window.textBox("quantityTextBox").enterText("10");
	    window.textBox("priceTextBox").enterText("999.99");
	    window.textBox("descriptionTextBox").enterText("Gaming Laptop");

	    // Add Item should remain disabled due to missing required fields
	    window.button("btnAdd").requireDisabled();

	    // Check if error message displays correctly
	    window.label("errorMessageLabel").requireText("Name field cannot be empty");
	}




}
