package payment.services;

import java.util.Calendar;
import java.util.Date;

import entities.Contract;
import entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinepaymentservice;

    public ContractService( OnlinePaymentService onlinepaymentservice) {
    	this.onlinepaymentservice = onlinepaymentservice;
    }
	
	public void processContract( Contract contract, int months) {
		double basicQuota = contract.getTotalValue() / months;
		for(int i =1; i<=months; i++) {
			double updateQuota= basicQuota + onlinepaymentservice.interest(basicQuota, i);
	        // nesse momento está updateQuonta = 202	
			double fullQuota= updateQuota + onlinepaymentservice.paymentFee(updateQuota);
		    // fullQuota = 206.04
			Date dueDate = addMonths(contract.getDate(), i); // AQUI VAI INSTANCIAR UMA DATA PARA CADA VALOR DE CONTRATO, COM A VARIAVEL (i) por causa do FOR
			contract.getInstallments().add(new Installment(dueDate, fullQuota));
		}	
	}
	
	private Date addMonths(Date date , int n) {
		Calendar calendar = Calendar.getInstance(); // ESSA FUNÇÃO CALENDAR SERVE PARA ADICIONAR OU RETIRAR DATAS
	    calendar.setTime(date);
	    calendar.add(Calendar.MONTH, n);
	    return calendar.getTime();
	}
	
	
}
