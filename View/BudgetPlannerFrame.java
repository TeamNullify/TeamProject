package View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

	private double myBudget;

	private String myBudgetName;

	private JButton totalButton;

	private JButton budgetLeft;

	private WindowFrame window;

	/**
	 * A constructor that will start the GUI
	 */
	public BudgetPlannerFrame(WindowFrame window){
		super();

		this.myItems = new BudgetList();

		myBudget = 0;

		myBudgetName = "";

		totalButton = new JButton("$0.00");

		startUp();

		this.window = window;
	}

	/**
	 * A start up that will run all of the necessary methods and display the necessary information to the user
	 */
	protected void startUp() {
		setTitle("Budget Planner Team Nullify");
		setVisible(true);

		setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
				SCREEN_SIZE.height / 2 - getHeight() / 2);


		//run on startup to get user input on the name and budget
		//has an issue of not knowing what to do on cancel
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

		southJPanel.add(totalButton);
		budgetLeft = new JButton(numberFormat(myItems.getBudget()));

		southJPanel.setVisible(true);

		southJPanel.add(budgetLeft);
        //System.out.println(myItems.getName());
        //System.out.println(myItems.getBudget());
		//Will make new buttons that will then later attach listeners to put on the north area of the JFrame
		//If the user clicks on the button, it will ask if the user would like to change the values of what they input from before
		final JPanel thePanel = new JPanel(new GridLayout());
		final JButton budgetButton = new JButton("Budget: " + numberFormat(myItems.getBudget()));
		final JButton nameButton = new JButton(myItems.getName());
		final JButton addItemButton = new JButton("Add item...");
		JButton backButton = new JButton("Back");
		backButton.addActionListener(e -> {
            this.setVisible(false);
            window.showWindow();
        });
		thePanel.add(backButton);
		thePanel.add(nameButton);
		nameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {

					int response = JOptionPane.showConfirmDialog(null,
							"Set the name of the budget planner");
					if (response == JOptionPane.OK_OPTION) {
						setName();
						nameButton.setText(myItems.getName());
					}
				}
			});

		budgetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				int response = JOptionPane.showConfirmDialog(null,
						"Set your budget for this project");
				if(response == JOptionPane.OK_OPTION) {
					setBudget();
					budgetButton.setText("Budget: " + numberFormat(myItems.getBudget()));
				}
			}
		});

		//Will start a new GUI defined in a method below to make new items to add to the class
		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				addItem();
			}
		});
		thePanel.add(budgetButton);
		thePanel.add(addItemButton);
		add(thePanel, BorderLayout.NORTH);

		add(southJPanel, BorderLayout.SOUTH);

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
		//Biggest issue is if the user hits cancel it will cause an error ask someone how to fix this ?
		String nameString = null;
		//while(nameString.isEmpty()) {
			nameString =
					   JOptionPane.showInputDialog(null, "Please input the name of the budget");

			if((nameString == null ||
					   (nameString != null && ("".equals(nameString))))
					    && myBudgetName != ""){
							return;

					} else if((nameString == null ||
							  (nameString != null && ("".equals(nameString))))
							   && myBudgetName == ""){
						System.out.println("here?");
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
		//still having the issue of what happens if the user hits cancel dont know what to do
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
		itemInfo.setSize(SCREEN_SIZE.width / 5, SCREEN_SIZE.height / 5);
		itemInfo.setResizable(false);
		itemInfo.setLocation(SCREEN_SIZE.width / 2 - itemInfo.getWidth() / 2,
				SCREEN_SIZE.height / 2 - itemInfo.getHeight() / 2);
		//Makes instruction that cannot be changed by the user that will tell them what goes in which box
		//Makes sure that they cant be chagned
		JTextField thePriceInstruction = new JTextField("enter the price");
		JTextField theNameInstruction = new JTextField("enter the name");
		JTextField theQuantityInstruction = new JTextField("quantity");
		thePriceInstruction.setEditable(false);
		theNameInstruction.setEditable(false);
		theQuantityInstruction.setEditable(false);
		JTextField thePrice = new JTextField(40);
		JTextField theName = new JTextField(40);
		JTextField theQuantity = new JTextField(40);

		JButton save = new JButton("Save");
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				itemInfo.dispose();
			}
		});

		JPanel thePanel = new JPanel();
		thePanel.setLayout(new FlowLayout());
		thePanel.add(cancel);
		thePanel.add(save);
		itemInfo.add(thePanel, BorderLayout.SOUTH);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
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
	    		boolean flag = false;
	    		BigDecimal itemPrice = null;
	    		int itemQuantity = 0;
	    		try{
	    			itemPrice = new BigDecimal(Double.parseDouble(price));
	    			itemQuantity = Integer.parseInt(quantity);

	    			if(itemPrice == null|| itemName.isEmpty() || itemQuantity == 0) {
	    				//System.out.println("it is indeed null");
	   					JOptionPane.showMessageDialog(null,
	   							"Please fill in all of the blanks",
	   							"Input Error", JOptionPane.ERROR_MESSAGE);
	    			} else if (itemPrice.compareTo(BigDecimal.ZERO) <= 0
	    					   || itemQuantity <= 0){
	    				JOptionPane.showMessageDialog(null,
	   							"Please input a positive value",
	   							"Input Error", JOptionPane.ERROR_MESSAGE);
	    			} else {
	    				Item theItem = new Item(itemName, itemPrice, itemQuantity);
	   					ITEM_LIST.add(theItem);
	    				//flag = true;
	   					itemToPanel(theItem);
	   					showTotal();
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
		itemInfo.add(panel);
		itemInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		itemInfo.setVisible(true);
	}

	private void itemToPanel(Item theItem) {
		JButton button = new JButton(theItem.toString());
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				Item editedItem = addEditedItem(theItem, button);
//				button.setText(editedItem.toString());
//				button.revalidate();
//				button.repaint();
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

		//Sets the window to a certain size to not be edited and keep everything in line
		itemInfo.setResizable(false);

		itemInfo.setSize(SCREEN_SIZE.width / 5, SCREEN_SIZE.height / 5);

		itemInfo.setLocation(SCREEN_SIZE.width / 2 - getWidth() / 5,
				SCREEN_SIZE.height / 2 - getHeight() / 5);

		//Makes instruction that cannot be changed by the user that will tell them what goes in which box
		//Makes sure that they cant be chagned
		JTextField thePriceInstruction = new JTextField("enter the price");
		JTextField theNameInstruction = new JTextField("enter the name");
		JTextField theQuantityInstruction = new JTextField
				("Quantity");
//		JTextField thePriorityInstruction = new JTextField
//				("On a scale of one to 10 how important is this item?");
		thePriceInstruction.setEditable(false);
		theNameInstruction.setEditable(false);
		theQuantityInstruction.setEditable(false);

		//Makes new text fields the user can add to
		JTextField thePrice = new JTextField(theItem.getPrice().toString());
		thePrice.setColumns(40);
	    JTextField theName = new JTextField(theItem.getName());
	    theName.setColumns(40);
	    JTextField theQuantity = new JTextField(String.valueOf(theItem.getQuantity()));
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
				ITEM_LIST.remove(theItem);
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
	    		boolean flag = false;
	    		BigDecimal itemPrice = null;
	    		int itemQuantity = 0;
	    		try{
	    			itemPrice = new BigDecimal(Double.parseDouble(price));
	    			itemQuantity = Integer.parseInt(quantity);

	    			if(itemPrice == null|| itemName.isEmpty() || itemQuantity == 0) {
	    				//System.out.println("it is indeed null");
	   					JOptionPane.showMessageDialog(null,
	   							"Please fill in all of the blanks",
	   							"Input Error", JOptionPane.ERROR_MESSAGE);
					} else if (itemPrice.compareTo(BigDecimal.ZERO) <= 0
	    					   || itemQuantity <= 0){
	    				JOptionPane.showMessageDialog(null,
	   							"Please input a positive value",
	   							"Input Error", JOptionPane.ERROR_MESSAGE);
	    			} else {
	    				//flag = true;
	    				theItem.setName(itemName);
	    				theItem.setQuantity(itemQuantity);
	    				theItem.setPrice(itemPrice);
	    				theButton.setText(theItem.toString());
	    				showTotal();
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
		System.out.println(ITEM_LIST);
		return theItem;
	}

	private String numberFormat(final Object object) {
        final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        return nf.format(object);
    }

	private void showBudgetLeft() {
		budgetLeft.setText("Budget Left: " + numberFormat(myItems.getBudgetMinusTotal()));
		southJPanel.revalidate();
		southJPanel.repaint();
	}

	private void showTotal() {
		// totalButton.setText("Total: " + numberFormat(myItems.calculateTotal(ITEM_LIST)));
		// southJPanel.revalidate();
		// southJPanel.repaint();
	}

}
