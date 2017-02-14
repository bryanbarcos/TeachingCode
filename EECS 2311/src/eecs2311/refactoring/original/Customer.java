package eecs2311.refactoring.original;

import java.util.*;

public class Customer {
	private String _name;
	private Vector<Rental> _rentals = new Vector<Rental>();
	
	public Customer (String name) {
		_name = name;
	}
	
	public void addRental (Rental arg) {
		_rentals.addElement(arg);
	}
	
	public String getName() {
		return _name;
	}
	
	public String statement() {
		double totalAmount = 0;
		int frequentRenterPoints = 0;
		Enumeration rentals = _rentals.elements();
		String result = "Rental Record for " + getName() + "\n";
		while (rentals.hasMoreElements()){
			double thisAmount = 0;
			Rental each = (Rental) rentals.nextElement();
			
			//Determine amounts for each line
			switch (each.getMovie().getPriceCode()) {
					case Movie.REGULAR:
						thisAmount += 2;
						if (each.getDaysRented() > 2)
							thisAmount += (each.getDaysRented() - 2) * 1.5;
						break;
					case Movie.NEW_RELEASE:
						thisAmount += each.getDaysRented() * 3;
						break;
					case Movie.CHILDRENS:
						thisAmount += 1.5;
						if (each.getDaysRented() > 3)
							thisAmount += (each.getDaysRented() - 3) * 1.5;
						break;
			}
			//Add frequent renter points
			frequentRenterPoints ++;
			//Add bonus for a two day new release rental
			if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE) &&
				 each.getDaysRented() > 1) frequentRenterPoints ++;
			
			//Show figures for this rental
			result += "  " + each.getMovie().getTitle() + " " + String.valueOf(thisAmount) + "\n";
			totalAmount += thisAmount;
		}
		
		//Add footer lines
		result += "Amount owed is " + String.valueOf(totalAmount) + "\n";
		result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
		return result;
	}
}