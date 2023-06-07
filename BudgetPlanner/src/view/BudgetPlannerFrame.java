package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;



import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.BudgetList;
import model.Item;

/**
 * A GUI class that will take the input from the user to make items and other things
 * Then it will display all of the information to the user in the GUI
 * @author Nathaniel Mann
 * @version v0.2
 *
 */

public class BudgetPlannerFrame extends JFrame {

	
	private JScrollPane jScroll = new JScrollPane();
	
	private JPanel jPanel = new JPanel(new FlowLayout());
	
	private JPanel southJPanel = new JPanel(new FlowLayout());
	
	private static final long serialVersionUID = -5920717045729015756L;
	
	/**Is used to get the frame of the users computer and set it to a default size*/
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();
	
	/**The screen size of the users computer*/
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();
	
	/**Creates a new list that will hold the items the user creates*/
	private static List<Item> ITEM_LIST = new ArrayList<>();
	
	/**Will make a new object of the new BudgetList to do calculations and hold other information*/
	private final BudgetList myItems;
	
	/**The budget that the user will input to be sent into the */
	private double myBudget;
	
	
	private String myBudgetName;
	
	private JButton totalButton;
	
	private JButton budgetLeft;
	
	/**
	 * A constructor that will start the GUI 
	 */
	public BudgetPlannerFrame(){
		super();
		
		this.myItems = new BudgetList();
		
		myBudget = 0;
		
		myBudgetName = "";
		
		totalButton = new JButton("$0.00");
		
		budgetLeft = new JButton("");
		
		startUp();
	}
	
	/**
	 * A start up that will run all of the necessary methods and display the necessary information to the user
	 */
	protected void startUp() {
		
		setTitle("Budget Planner Team Nullify");
		setVisible(true);
		setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
				SCREEN_SIZE.height / 2 - getHeight() / 2);
		
		setName();		
		if(myBudgetName == "") {
			return;
		}
		setBudget();
		if(myBudget == 0) {
			return;
		}
		
		jScroll.getViewport().add(jPanel);
		jScroll.setVisible(true);
		jScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(jScroll, BorderLayout.CENTER);
		
		southJPanel.setVisible(true);
		budgetLeft = new JButton(numberFormat(myItems.getBudget()));
		southJPanel.add(budgetLeft);
		add(southJPanel, BorderLayout.SOUTH);
		

		final JPanel thePanel = new JPanel(new GridLayout());
		final JButton budgetButton = new JButton("Budget: " + numberFormat(myItems.getBudget()));
		final JButton nameButton = new JButton(myItems.getName());
		final JButton addItemButton = new JButton("Add item...");
		thePanel.add(nameButton);
		thePanel.add(budgetButton);
		thePanel.add(addItemButton);
		
		nameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
						setName();
						nameButton.setText(myItems.getName());
				}
			});
		
		budgetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
					setBudget();
					budgetButton.setText("Budget: " + numberFormat(myItems.getBudget()));
			}	
		});
		
		addItemButton.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(final ActionEvent theEvent) {
				addItem();
			}
		});
		
		add(thePanel, BorderLayout.NORTH);
		
		setSize(SCREEN_SIZE.width / 2, SCREEN_SIZE.height / 2);
		
		setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
				SCREEN_SIZE.height / 2 - getHeight() / 2);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Sets the name of the budget planner to display to the user
	 * Can be used to either start the budget planner name or edit the name
	 */
	private void setName() {
		
		String nameString = null;
			nameString = 
					   JOptionPane.showInputDialog(null, "Please input the name of the budget");
			
			if((nameString == null || 
					   (nameString != null && ("".equals(nameString)))) 
					    && myBudgetName != ""){
							return;
					    
					} else if((nameString == null || 
							  (nameString != null && ("".equals(nameString))))
							   && myBudgetName == ""){

						dispose();
					    return;
					}	
		myBudgetName = nameString;
		myItems.setName(nameString);
	}
	
	/**
	 * Sets the budget from the user input 
	 */
	private void setBudget() {
		boolean flag = false;
		
		while(flag != true) {

			String budgetValue = JOptionPane.showInputDialog(null, 
					"Please enter your budget");
			if((budgetValue == null || 
			   (budgetValue != null && ("".equals(budgetValue)))) 
			    && myBudget != 0){
			    return;
			} else if((budgetValue == null || 
					  (budgetValue != null && ("".equals(budgetValue))))
					   && myBudget == 0){
				dispose();
			    return;
			}
			try {
				myBudget = Double.parseDouble(budgetValue);
				
				myItems.setBudget(BigDecimal.valueOf(myBudget));
				showBudgetLeft();
				flag = true;
			} catch (final NumberFormatException e) {
				if (budgetValue == null) { 
					break;
				}
				JOptionPane.showMessageDialog(null,
						"Please enter a numerical value",
						"Input error!", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Makes a new item
	 */
	private void addItem() {
		
		
		JFrame itemInfo = new JFrame("Add Item details");
		itemInfo.setVisible(true);
		JPanel itemPanel = new JPanel();
		itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
		
		itemInfo.setResizable(false);
		
		

		
		JTextField thePriceInstruction = new JTextField("Price of the item");
		JTextField theNameInstruction = new JTextField("Name of the item");
		JTextField theQuantityInstruction = new JTextField
				("Number of items you are buying");
		thePriceInstruction.setEditable(false);
		thePriceInstruction.setFont(thePriceInstruction.getFont().deriveFont(20f));
		thePriceInstruction.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		thePriceInstruction.setHorizontalAlignment(JTextField.CENTER);
		theNameInstruction.setEditable(false);
		theNameInstruction.setFont(thePriceInstruction.getFont().deriveFont(20f));
		theNameInstruction.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		theNameInstruction.setHorizontalAlignment(JTextField.CENTER);
		

		theQuantityInstruction.setEditable(false);
		theQuantityInstruction.setFont(thePriceInstruction.getFont().deriveFont(20f));
		theQuantityInstruction.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		theQuantityInstruction.setHorizontalAlignment(JTextField.CENTER);

		
		JTextField thePrice = new JTextField(40);
		
		thePrice.setFont(thePriceInstruction.getFont().deriveFont(20f));
		thePrice.setHorizontalAlignment(JTextField.CENTER);
	    JTextField theName = new JTextField(40);
	    theName.setHorizontalAlignment(JTextField.CENTER);
	    theName.setFont(thePriceInstruction.getFont().deriveFont(20f));

	    JTextField theQuantity = new JTextField(40);
	    theQuantity.setFont(thePriceInstruction.getFont().deriveFont(20f));
	    theQuantity.setHorizontalAlignment(JTextField.CENTER);

	    
	    JButton save = new JButton("Save");
	    JButton cancel = new JButton("Cancel");
	    cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				itemInfo.dispose();
				}
			});
	    
	    save.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(final ActionEvent theEvent) {
	    		String price = thePrice.getText();
	    		
	    		String itemName = theName.getText();
	    		String quantity = theQuantity.getText();
	    		BigDecimal itemPrice = null;
	    		int itemQuantity = 0;
	    		

	    		try{
	    			itemPrice = new BigDecimal(Double.parseDouble(price));
	    			itemQuantity = Integer.parseInt(quantity);

	    			if(itemPrice == null|| itemName.isEmpty() || itemQuantity == 0) {
	   					JOptionPane.showMessageDialog(null,
	   							"Please fill in all of the blanks", 
	   							"Input Error", JOptionPane.ERROR_MESSAGE);
	    			    
	    				
	    			} else if (itemPrice.compareTo(BigDecimal.ZERO) <= 0 
	    					   || itemQuantity <= 0){
	    				JOptionPane.showMessageDialog(null,
	   							"Please input a positive value", 
	   							"Input Error", JOptionPane.ERROR_MESSAGE);
	    				
	    			} else if (itemPrice.compareTo(myItems.calculateTotal(ITEM_LIST)) == 1){
	    				JOptionPane.showMessageDialog(null, 
	    						"This item will put you over your budget, "
	    						+ "please enter a different item or change your budget",
	    						"Input Error!", JOptionPane.ERROR_MESSAGE);
	    			} else {
	    				Item theItem = new Item(itemName, itemPrice, itemQuantity);
	   					ITEM_LIST.add(theItem); 
	    				//flag = true;
	   					itemToPanel(theItem);
	   					showBudgetLeft();
	    				itemInfo.dispose();
	    			}
	   			} catch(final NumberFormatException e) {
	   				if (itemPrice == null || itemQuantity == 0) { 
	   					JOptionPane.showMessageDialog(null,
	    						"Please enter a numerical value",
		    					"Input error!", JOptionPane.ERROR_MESSAGE);
	   				}	    				
	    		}
	    	}
	    });
	    
	    
	    JPanel thePanel = new JPanel();
	    thePanel.setLayout(new FlowLayout());
	    thePanel.add(cancel);
	    thePanel.add(save);
	    itemInfo.add(thePanel, BorderLayout.SOUTH);

		itemPanel.add(thePriceInstruction);
		
		itemPanel.add(thePrice);
		
		itemPanel.add(theNameInstruction);
		itemPanel.add(theName);
		
		itemPanel.add(theQuantityInstruction);
		itemPanel.add(theQuantity);
		
		itemInfo.add(itemPanel);

		itemInfo.setLocation(SCREEN_SIZE.width / 2 - getWidth() / 5,
				SCREEN_SIZE.height / 2 - getHeight() / 5);
		
		itemInfo.pack();
		
		itemInfo.setLocationRelativeTo(null);
		
		itemInfo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		

	}
	
	private void itemToPanel(Item theItem) {
		JButton button = new JButton(theItem.toString());
		button.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(final ActionEvent theEvent) {
				addEditedItem(theItem, button);
			}
		});
		jPanel.add(button);
		jPanel.revalidate();
		jPanel.repaint();
	}
	
	private Item addEditedItem(Item theItem, JButton theButton) {
		
		
		JFrame itemInfo = new JFrame("Add Item details");
		
		itemInfo.setVisible(true);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//Sets the window to a certain size to not be edited and keep everything in line
		itemInfo.setResizable(false);
		
		
		itemInfo.setLocation(SCREEN_SIZE.width / 2 - getWidth() / 5,
				SCREEN_SIZE.height / 2 - getHeight() / 5);
		
		JTextField thePriceInstruction = new JTextField("Price of the item");
		JTextField theNameInstruction = new JTextField("Name of the item");
		JTextField theQuantityInstruction = new JTextField
				("Number of items you are buying");
		thePriceInstruction.setEditable(false);
		thePriceInstruction.setFont(thePriceInstruction.getFont().deriveFont(20f));
		thePriceInstruction.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		thePriceInstruction.setHorizontalAlignment(JTextField.CENTER);
		theNameInstruction.setEditable(false);
		theNameInstruction.setFont(thePriceInstruction.getFont().deriveFont(20f));
		theNameInstruction.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		theNameInstruction.setHorizontalAlignment(JTextField.CENTER);
		

		theQuantityInstruction.setEditable(false);
		theQuantityInstruction.setFont(thePriceInstruction.getFont().deriveFont(20f));
		theQuantityInstruction.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		theQuantityInstruction.setHorizontalAlignment(JTextField.CENTER);
		
		//Makes new text fields the user can add to 
		JTextField thePrice = new JTextField(theItem.getPrice().toString());
		thePrice.setFont(thePrice.getFont().deriveFont(20f));
		thePrice.setColumns(40);
		thePrice.setHorizontalAlignment(JTextField.CENTER);
	    JTextField theName = new JTextField(theItem.getName());
	    theName.setFont(theName.getFont().deriveFont(20f));
	    theName.setColumns(40);
	    theName.setHorizontalAlignment(JTextField.CENTER);
	    JTextField theQuantity = new JTextField(String.valueOf(theItem.getQuantity()));
	    theQuantity.setFont(theQuantity.getFont().deriveFont(20f));
	    theQuantity.setHorizontalAlignment(JTextField.CENTER);
	    theQuantity.setColumns(40);
	    
	    //Cancels the item creation and closes the GUI
	    JButton save = new JButton("Save");
	    JButton cancel = new JButton("Cancel");
	    JButton delete = new JButton("Delete");
	    cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				itemInfo.dispose();
				}
			});
	    
	    delete.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent theEvent) {
			int response = JOptionPane.showConfirmDialog(null, 
					"Are you sure you want to delete this item?", "Please confirm", 
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			if(response == JOptionPane.OK_OPTION) {
				JOptionPane.showMessageDialog(null, "The item has been deleted");
				myItems.addBackToBudget(theItem.getPrice());
				
				ITEM_LIST.remove(theItem);
				showBudgetLeft();
				jPanel.remove(theButton);
				jPanel.revalidate();
				jPanel.repaint();
				
			}
			itemInfo.dispose();
			}
		});
	    	

	    
	    
	    //Puts all of the text fields and buttons into proper areas
	    JPanel thePanel = new JPanel();
	    thePanel.setLayout(new FlowLayout());
	    thePanel.add(cancel);
	    thePanel.add(save);
	    thePanel.add(delete);
	    itemInfo.add(thePanel, BorderLayout.SOUTH);

	    
		panel.add(thePriceInstruction);
		panel.add(thePrice);
		
		panel.add(theNameInstruction);
		panel.add(theName);
		
		panel.add(theQuantityInstruction);
		panel.add(theQuantity);

		//This action listener will save the items info into the item class and add it in to the item list from above
		
		save.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(final ActionEvent theEvent) {
	    		String price = thePrice.getText();
	    		
	    		String itemName = theName.getText();
	    		String quantity = theQuantity.getText();
	    		BigDecimal itemPrice = null;
	    		int itemQuantity = 0;
	    		

	    		try{
	    			itemPrice = new BigDecimal(Double.parseDouble(price));
	    			itemQuantity = Integer.parseInt(quantity);

	    			if(itemPrice == null|| itemName.isEmpty() || itemQuantity == 0) {
	   					JOptionPane.showMessageDialog(null,
	   							"Please fill in all of the blanks", 
	   							"Input Error", JOptionPane.ERROR_MESSAGE);
	    			    
	    				
	    			} else if (itemPrice.compareTo(BigDecimal.ZERO) <= 0 
	    					   || itemQuantity <= 0){
	    				JOptionPane.showMessageDialog(null,
	   							"Please input a positive value", 
	   							"Input Error", JOptionPane.ERROR_MESSAGE);
	    				
	    			} else if (itemPrice.compareTo(myItems.calculateTotal(ITEM_LIST)) == 1) {
		    				JOptionPane.showMessageDialog(null, 
		    						"This item will put you over your budget, "
		    						+ "please enter a different item or change your budget",
		    						"Input Error!", JOptionPane.ERROR_MESSAGE);
	    			} else {
	    				//flag = true;
	    				theItem.setName(itemName);
	    				theItem.setQuantity(itemQuantity);
	    				theItem.setPrice(itemPrice);
	    				theButton.setText(theItem.toString());
	    				showBudgetLeft();
	    				itemInfo.dispose();
	    			}
	   			} catch(final NumberFormatException e) {
	   				if (itemPrice == null || itemQuantity == 0) { 
	   					JOptionPane.showMessageDialog(null,
	    						"Please enter a numerical value",
		    					"Input error!", JOptionPane.ERROR_MESSAGE);
	   				}
	    				
	    		}
	    			
	    		//}
	    	}
	    });
		
		panel.revalidate();
		panel.repaint();
		
		itemInfo.add(panel);
		itemInfo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		itemInfo.pack();
		
		itemInfo.setLocationRelativeTo(null);
		
		return theItem;
	}
	
	private String numberFormat(final BigDecimal thePrice) {
        final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        return nf.format(thePrice);
    }
	
	private void showBudgetLeft() {
		budgetLeft.setText("Budget Left: " + numberFormat(myItems.calculateTotal(ITEM_LIST)));
		southJPanel.revalidate();
		southJPanel.repaint();
	}
	
}
