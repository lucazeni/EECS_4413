package model;

public class Loan {
	public String errorMessage;
	public boolean errorFlag = false;
	public Loan() {

	}

	public double computePayment(double principal, double interest, double period, double fixedInterest,
			int graceChecked, double graceInterest, double gracePeriod) throws Exception {
		double monthlyPayment;
		double totalInterest = interest + fixedInterest;
		monthlyPayment = (((totalInterest / 100) / 12) * principal)
				/ (1 - (Math.pow(1 + ((totalInterest / 100) / 12), -period)));
		if (graceChecked == 1) {
			monthlyPayment = monthlyPayment + (graceInterest / gracePeriod);
		}
		errorFlag = false;
			if (principal < 0)
			{
				errorMessage = "Principal must be greater than 0!";
				errorFlag = true;
				throw new Exception();
			}
			else if (period < 0)
			{
				errorMessage = "Period must be greater than 0!";
				errorFlag = true;
				throw new Exception();
			}
			else if (interest < 0)
			{
				errorFlag = true;
				errorMessage = "Interest must be greater than 0!";
				throw new Exception();
			}
			else
			{
				errorMessage = "";
			}
		return monthlyPayment;

	}

	public double computeGraceInterest(double principal, double gracePeriod, double interest, double fixedInterest,
			int graceChecked, double period) throws Exception {
		double graceInterest;
		double totalInterest = interest + fixedInterest;
		if (graceChecked == 1) {
			graceInterest = principal * ((totalInterest / 100) / 12) * gracePeriod;
		} else {
			graceInterest = 0;
		}
		return graceInterest;
	}
}
