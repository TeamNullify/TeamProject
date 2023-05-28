package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class will make a new object that will allow the GUI to pull information
 * Will do calculations for the total, store the name, store the budget, and all of the item objects
 * @author Nathaniel Mann
 * @version v0.1
 *
 */

public class BudgetList {
	
	/**A new list that will hold all of the items*/
	private List<Item> BudgetList = new ArrayList<Item>();
	
	/**The total of all of the items combined*/
	private BigDecimal myTotal = BigDecimal.ZERO;
	
	/**The name of the budget planner*/
	private String myName;
	
	/**The budget from the user input also put in bigDecimal form to allow for money to be calculated properly*/
	private BigDecimal myBudget;
	
	/**A constructor*/
	public BudgetList() {

	}
	
	/**
	 * The method will add the item to the list that we have made to use later on 
	 * @param theItem The item that the user has made put in the budget planner to be used later on
	 */
	public void add(final Item theItem) {
		BudgetList.add(theItem);
	}
	
	/**
	 * 
	 * @param theItemList Takes an item list then will calculate the budget
	 */
	public void calculateTotal(List<Item> theItemList) {
		myTotal = BigDecimal.ZERO;
		final Iterator<Item> itr = theItemList.iterator();
		while(itr.hasNext()) {
			final Item theItem = itr.next();
			
			//Get's the value to be added to the total value 
			final BigDecimal theQuantity = BigDecimal.valueOf(theItem.getQuantity());
			final BigDecimal thePrice = theItem.getPrice().multiply(theQuantity);
			
			myTotal.add(thePrice);
		}
	}
	
	/**
	 * Allows the user to change the name if wanted
	 * @param theName Sets the name of the budget planner
	 */
	public void setName(String theName) {
		myName = theName;
	}
	
	/**
	 * Allow the user to change their budget if wanted
	 * @param theBudget of the budget planner
	 */
	public void setBudget(BigDecimal theBudget) {
		myBudget = theBudget;
	}
	
	/**
	 * Returns the budget - total = money left
	 * @return The money left from the items that they are planning to buy to display the amount of money left
	 */
	public BigDecimal getBudgetMinusTotal() {
		return myBudget.subtract(myTotal);
	}
	
	/**
	 * Returns if their budget left is positive or not
	 * @return
	 */
	public boolean isPositive() {
		return myBudget.subtract(myTotal).compareTo(BigDecimal.ZERO) > -1;
	}
	
	/**
	 * 
	 * @return The name of the budget
	 */
	public String getName() {
		return myName;
	}
	
	/**
	 * 
	 * @return The total of the budget planner
	 */
	public BigDecimal getTotal() {
		return myTotal;
	}
	
	/**
	 * 
	 * @return theBudget of the planner
	 */
	public BigDecimal getBudget() {
		return myBudget;
	}
	
	public boolean isBudgetEmpty() {
		return myBudget.equals(null);
	}
	
	
	/**
	 * Clears the item list
	 */
	public void clear() {
		BudgetList.clear();
	}
	
}
