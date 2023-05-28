package com.enset.bank.digitalbank;

import com.enset.bank.digitalbank.entities.AccountOperation;
import com.enset.bank.digitalbank.entities.BankAccount;
import com.enset.bank.digitalbank.entities.Customer;
import com.enset.bank.digitalbank.exceptions.BalanceNotSufficientException;
import com.enset.bank.digitalbank.exceptions.BankAccountNotFoundException;
import com.enset.bank.digitalbank.exceptions.CustomerNotFoundException;
import com.enset.bank.digitalbank.service.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankApplication  {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankApplication.class, args);

	}

	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService){

		return args -> {
			Stream.of("Alpha","Aissatou","Marco","Aissatou").forEach(name->{
						Customer customer= new Customer();
						customer.setName(name);
						customer.setEmail(name.toLowerCase()+"@gmail.com");
						bankAccountService.saveCustomer(customer);
					}

			);
			bankAccountService.listCustomers().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*9000,9000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random()*12000,5.5, customer.getId() );
					List<BankAccount> bankAccounts= bankAccountService.bankAccountlist();

					for (BankAccount bankAccount:bankAccounts)

					 {
						System.out.println(bankAccount.toString());


						/*for (int i = 0; i < 10; i++) {
							try {
								bankAccountService.credit(bankAccount.getId(),1000+Math.random()*12000,"Operation Credit Effectuèe" );
							} catch (BankAccountNotFoundException e) {
								throw new RuntimeException(e);
							}
						}
						for (int i = 0; i < 10; i++) {
							try {
								bankAccountService.debit(bankAccount.getId(),1000+Math.random()*9000,"Operation Debit Effectuèe" );
							} catch (BankAccountNotFoundException e) {
								throw new RuntimeException(e);
							} catch (BalanceNotSufficientException e) {
								throw new RuntimeException(e);
							}
						}*/
					}


				} catch (CustomerNotFoundException e) {
					throw new RuntimeException(e);
				}
				//(double initialBalance, double overdraft, Long customerId,double overDraft)
			});
		};
	}

}
