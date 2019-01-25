package model;

public class Loan {
	public Loan()
	{

	}
	public double computePayment(double principal, double interest, double period, 
			double fixedInterest, int graceChecked, double graceInterest, double gracePeriod) throws Exception
	{
		double monthlyPayment;
		double totalInterest = interest + fixedInterest;
		monthlyPayment = (((totalInterest/100) / 12) * principal) / 
				(1 - (Math.pow(1 + ((totalInterest/100) / 12), -period)));
		if(graceChecked == 1) {
			monthlyPayment = monthlyPayment + (graceInterest/gracePeriod); 
		}
		if (principal < 0 || interest < 0 || period < 0 || fixedInterest < 0 || graceInterest < 0 || gracePeriod < 0) {
			throw new IllegalArgumentException();

		}
		return monthlyPayment;

	}
	public double computeGraceInterest(double principal, double gracePeriod, double interest, double fixedInterest, int graceChecked, double period) throws Exception 
	{
		double graceInterest;
		double totalInterest = interest + fixedInterest;
		if (graceChecked == 1)
		{
			graceInterest = principal * ((totalInterest/100) / 12) * gracePeriod;
		}
		else 
		{
			graceInterest = 0;
		}
		
		if (principal < 0 || interest < 0 || period < 0 || fixedInterest < 0 || gracePeriod < 0) {
			throw new IllegalArgumentException();

		}
		return graceInterest;
	}
}
