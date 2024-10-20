
package com.example.inventorymanager.view.swing;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import com.example.inventorymanger.model.Item;
import com.example.inventorymanger.view.InventoryView;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InventorySwingView extends JFrame implements InventoryView {

	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtID;
	private JTextField txtQauntity;
	private JTextField txtPrice;
	private JTextField txtDescription;
	private JButton btnDeleteSelected;
	private JButton btnUpdateSelected;
	private JButton btnAdd;

	private JLabel lblErrorMessage;

	private JList<Item> listItems;
	private DefaultListModel<Item> listItemModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventorySwingView frame = new InventorySwingView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DefaultListModel<Item> getListItemModel() {
		return listItemModel;
	}

	/**
	 * Create the frame.
	 */
	public InventorySwingView() {
		setMinimumSize(new Dimension(900, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Inventory Manager");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 45, 0, 770, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 173, 0, 23, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblNewLabel_1 = new JLabel("id");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 0;
		getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

		txtID = new JTextField();

		txtID.setName("idTextBox");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 0;
		getContentPane().add(txtID, gbc_textField_1);
		txtID.setColumns(10);

		JLabel lblNewLabel = new JLabel("Name");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);

		txtName = new JTextField();
		txtName.setName("nameTextBox");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		getContentPane().add(txtName, gbc_textField);
		txtName.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Quantity");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 2;
		getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

		txtQauntity = new JTextField();
		txtQauntity.setName("quantityTextBox");
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 2;
		getContentPane().add(txtQauntity, gbc_textField_2);
		txtQauntity.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Price");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 3;
		getContentPane().add(lblNewLabel_3, gbc_lblNewLabel_3);

		txtPrice = new JTextField();
		txtPrice.setName("priceTextBox");
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 3;
		getContentPane().add(txtPrice, gbc_textField_3);
		txtPrice.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Description");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 4;
		getContentPane().add(lblNewLabel_4, gbc_lblNewLabel_4);

		txtDescription = new JTextField();
		txtDescription.setName("descriptionTextBox");
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 2;
		gbc_textField_4.gridy = 4;
		getContentPane().add(txtDescription, gbc_textField_4);
		txtDescription.setColumns(10);

		btnAdd = new JButton("Add Item");
		btnAdd.setEnabled(false);
		btnAdd.setName("btnAdd");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridwidth = 3;
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 5;
		getContentPane().add(btnAdd, gbc_btnNewButton_2);

		btnDeleteSelected = new JButton("Delete Selected");
		btnDeleteSelected.setEnabled(false);
		btnDeleteSelected.setName("btnDeleteSelected");
		GridBagConstraints gbc_btnDeleteSelected = new GridBagConstraints();
		gbc_btnDeleteSelected.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeleteSelected.gridx = 2;
		gbc_btnDeleteSelected.gridy = 7;
		getContentPane().add(btnDeleteSelected, gbc_btnDeleteSelected);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 6;
		getContentPane().add(scrollPane, gbc_scrollPane);

		listItemModel = new DefaultListModel<Item>();
		listItems = new JList<>(listItemModel);
		listItems.addListSelectionListener(e -> btnDeleteSelected.setEnabled(listItems.getSelectedIndex() != -1));
		scrollPane.setViewportView(listItems);
		listItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listItems.setName("itemList");

		btnUpdateSelected = new JButton("Update Selected");
		btnUpdateSelected.setName("btnUpdateSelected");
		btnUpdateSelected.setEnabled(false);
		GridBagConstraints gbc_btnUpdateSelected;
		gbc_btnUpdateSelected = new GridBagConstraints();
		gbc_btnUpdateSelected.insets = new Insets(0, 0, 5, 5);
		gbc_btnUpdateSelected.gridx = 1;
		gbc_btnUpdateSelected.gridy = 7;
		getContentPane().add(btnUpdateSelected, gbc_btnUpdateSelected);

		lblErrorMessage = new JLabel("");
		lblErrorMessage.setName("errorMessageLabel");
		GridBagConstraints gbc_lblErrorMessage = new GridBagConstraints();
		gbc_lblErrorMessage.gridwidth = 3;
		gbc_lblErrorMessage.insets = new Insets(0, 0, 0, 5);
		gbc_lblErrorMessage.gridx = 0;
		gbc_lblErrorMessage.gridy = 8;
		getContentPane().add(lblErrorMessage, gbc_lblErrorMessage);

		KeyAdapter btnAddenabler = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				btnAdd.setEnabled(!txtID.getText().trim().isEmpty() && !txtName.getText().trim().isEmpty()
						&& !txtDescription.getText().trim().isEmpty() && !txtPrice.getText().trim().isEmpty()
						&& !txtQauntity.getText().trim().isEmpty());
			}
		};

		txtID.addKeyListener(btnAddenabler);
		txtName.addKeyListener(btnAddenabler);
		txtDescription.addKeyListener(btnAddenabler);
		txtPrice.addKeyListener(btnAddenabler);
		txtQauntity.addKeyListener(btnAddenabler);

	}

	@Override
	public void displayItems(List<Item> items) {
		// TODO Auto-generated method stub
		items.stream().forEach(listItemModel::addElement);

	}

	@Override
	public void addItem(Item item) {
		// TODO Auto-generated method stub
		listItemModel.addElement(item);
		resetErrorLabel();
	}

	@Override
	public void deleteItem(Item item) {
		// TODO Auto-generated method stub
		listItemModel.removeElement(item);
		resetErrorLabel();
	}

	@Override
	public void updateItem(Item item) {
		// TODO Auto-generated method stub
		for (int i = 0; i < listItemModel.size(); i++) {
			if (listItemModel.get(i).getId().equals(item.getId())) {
				listItemModel.set(i, item);
				break;
			}
		}
		resetErrorLabel();
	}

	@Override
	public void showErrorMessage(String message, Item item) {
		// TODO Auto-generated method stub
		lblErrorMessage.setText(message + ": " + item);

	}

	private void resetErrorLabel() {
		lblErrorMessage.setText(" ");
	}

}
