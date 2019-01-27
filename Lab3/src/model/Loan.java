package model;

public class Loan {
	
	public Loan() {

	}

	public double computePayment(String principal, String interest, String period, String fixedInterest,
			int graceChecked, double graceInterest, String gracePeriod) throws Exception {
		double monthlyPayment;
		double totalInterest = Double.parseDouble(interest) + Double.parseDouble(fixedInterest);
		monthlyPayment = (((totalInterest / 100) / 12) * Double.parseDouble(principal)
				/ (1 - (Math.pow(1 + ((totalInterest / 100) / 12), -Double.parseDouble(period)))));
		if (graceChecked == 1) {
			monthlyPayment = monthlyPayment + (graceInterest / Double.parseDouble(gracePeriod));
		}
		if(Double.parseDouble(principal) < 0 || Double.parseDouble(interest) < 0 || Double.parseDouble(period) < 0)
		{
			throw new Exception();
		}
		return monthlyPayment;

	}

	public double computeGraceInterest(String principal, String gracePeriod, String interest, String fixedInterest,
			int graceChecked, String period){
		double graceInterest;
		double totalInterest = Double.parseDouble(interest) + Double.parseDouble(fixedInterest);
		if (graceChecked == 1) {
			graceInterest = Double.parseDouble(principal) * ((totalInterest / 100) / 12)
					* Double.parseDouble(gracePeriod);
		} else {
			graceInterest = 0;
		}
		return graceInterest;
	}

}
