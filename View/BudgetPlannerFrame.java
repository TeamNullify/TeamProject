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
import javax.swing.JTextField;

import model.BudgetList;
import model.Item;

/**
 * A GUI class that will take the input from the user to make items and other things
 * Then it will display all of the information to the user in the GUI
 * @author Nathaniel Mann
 * @version v0.1
 *
 */

public class BudgetPlannerFrame extends JFrame {


	private static final long serialVersionUID = -5920717045729015756L;

	/**Is used to get the frame of the users computer and set it to a default size*/
	private static final Toolkit KIT = Toolkit.getDefaultToolkit();

	/**The screen size of the users computer*/
	private static final Dimension SCREEN_SIZE = KIT.getScreenSize();

	/**Creates a new list that will hold the items the user creates*/
	private static List<Item> ITEM_LIST = new ArrayList<>();

	/**Will make a new object of the new BudgetList to do calculations and hold other information*/
	private final BudgetList myItems;

	private WindowFrame window;

	/**
	 * A constructor that will start the GUI
	 */
	public BudgetPlannerFrame(){
		super();
		this.myItems = new BudgetList();
		startUp();
		this.window = window;
	}

	/**
	 * A start up that will run all of the necessary methods and display the necessary information to the user
	 */
	public void startUp() {
		setTitle("Budget Planner Team Nullify");
		setVisible(true);

		setLocation(SCREEN_SIZE.width / 2 - getWidth() / 2,
				SCREEN_SIZE.height / 2 - getHeight() / 2);
		setName();

		setBudget();

		System.out.println(myItems.getName());

		System.out.println(myItems.getBudget());

		final JPanel thePanel = new JPanel(new GridLayout());
		final JButton budgetButton = new JButton("Budget: " + numberFormat(myItems.getBudget()));
		final JButton nameButton = new JButton(myItems.getName());
		final JButton addItemButton = new JButton("Add item...");
		thePanel.add(nameButton);
		JButton backButton = new JButton("Back");

		backButton.addActionListener(e -> {
            this.setVisible(false);
            window.showWindow();
        });
		thePanel.add(backButton);
		nameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {

					int response = JOptionPane.showConfirmDialog(null,
							"Would you like to change the name of this budget planner?");
					if (response == JOptionPane.YES_OPTION) {
						setName();
						nameButton.setText(myItems.getName());
					}
				}
			});

		budgetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				int response = JOptionPane.showConfirmDialog(null,
						"Would you like to change your budget?");

				if(response == JOptionPane.YES_OPTION) {
					setBudget();
					budgetButton.setText("Budget: " + numberFormat(myItems.getBudget()));
				}
			}
		});

		addItemButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				addItem();
			}
		});

		thePanel.add(budgetButton);
		thePanel.add(addItemButton);
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

		//Biggest issue is if the user hits cancel it will cause an error ask someone how to fix this ?
		String nameString = "";
		//while(nameString.isEmpty()) {
			nameString =
					   JOptionPane.showInputDialog(null, "Enter a name for this budget",
							   						     "");

		//}


		myItems.setName(nameString);

	}

	/**
	 * Sets the budget from the user input
	 */
	private void setBudget() {

		//still having the issue of what happens if the user hits cancel dont know what to do

		double budget = 0;

		boolean flag = false;

		while(flag != true) {
			final String budgetValue = JOptionPane.showInputDialog(null, "Please enter your budget");


			try {

				budget = Double.parseDouble(budgetValue);

				myItems.setBudget(BigDecimal.valueOf(budget));
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


	private void addItem() {
		JFrame itemInfo = new JFrame("Add Item details");
		itemInfo.setSize(SCREEN_SIZE.width / 5, SCREEN_SIZE.height / 5);
		itemInfo.setResizable(false);
		itemInfo.setLocation(SCREEN_SIZE.width / 2 - itemInfo.getWidth() / 2,
				SCREEN_SIZE.height / 2 - itemInfo.getHeight() / 2);

		JTextField thePriceInstruction = new JTextField("Please enter the price of the item");
		JTextField theNameInstruction = new JTextField("Please enter the name of the item");
		JTextField theQuantityInstruction = new JTextField("Please enter the quantity of this item");
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

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent theEvent) {
				String price = thePrice.getText();
				String itemName = theName.getText();
				String quantity = theQuantity.getText();

				BigDecimal itemPrice = null;
				int itemQuantity = 0;

				try {
					itemPrice = new BigDecimal(price);
					itemQuantity = Integer.parseInt(quantity);

					if (itemPrice.compareTo(BigDecimal.ZERO) <= 0 || itemQuantity <= 0 || itemName.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Please fill in all the fields with valid values",
								"Input Error", JOptionPane.ERROR_MESSAGE);
					} else {
						Item theItem = new Item(itemName, itemPrice, itemQuantity);
						ITEM_LIST.add(theItem);
						itemInfo.dispose();
					}
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please enter valid numerical values",
							"Input Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		itemInfo.add(panel);
		itemInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		itemInfo.setVisible(true);
	}

	private String numberFormat(final BigDecimal thePrice) {
        final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
        return nf.format(thePrice);
    }
}
