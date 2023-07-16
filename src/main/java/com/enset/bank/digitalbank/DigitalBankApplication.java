package com.enset.bank.digitalbank;

import com.enset.bank.digitalbank.dao.AccountOperationRepository;
import com.enset.bank.digitalbank.dao.BankAccountReposittory;
import com.enset.bank.digitalbank.dao.CustomerRepository;
import com.enset.bank.digitalbank.dtos.BankAccountDto;
import com.enset.bank.digitalbank.dtos.CurrentAccountDto;
import com.enset.bank.digitalbank.dtos.CustomerDto;
import com.enset.bank.digitalbank.dtos.SavingAccountDto;
import com.enset.bank.digitalbank.entities.*;
import com.enset.bank.digitalbank.enums.AccountStatus;
import com.enset.bank.digitalbank.enums.OperationType;
import com.enset.bank.digitalbank.exceptions.BalanceNotSufficientException;
import com.enset.bank.digitalbank.exceptions.BankAccountNotFoundException;
import com.enset.bank.digitalbank.exceptions.CustomerNotFoundException;
import com.enset.bank.digitalbank.service.BankAccountService;
import com.enset.bank.digitalbank.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankApplication  {

	public static void main(String[] args) {
		SpringApplication.run(DigitalBankApplication.class, args);

	}

	//@Bean
	/*CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountReposittory bankAccountReposittory,
							AccountOperationRepository accountOperationRepository
	){
		return args -> {
			Stream.of("Mamadou","Aliou","saliou").forEach(c->{
				Customer customer= new Customer();
				customer.setName(c);
				customer.setEmail(c.toLowerCase()+"@gmail.com");
				customerRepository.save(customer);
			});
			customerRepository.findAll().forEach(customer -> {
				System.out.println(customer.toString());
				CurrentAccount currentAccount= new CurrentAccount();
				currentAccount.setOverdraft(9000);
				currentAccount.setBalance(Math.random()*9000);
				currentAccount.setCustomer(customer);
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setCurrency("euro");
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCreatedAt(new Date());
				bankAccountReposittory.save(currentAccount);

				SavingAccount savingAccount= new SavingAccount();
				savingAccount.setInterestRate(5.5);
				savingAccount.setBalance(Math.random()*15000);
				savingAccount.setCustomer(customer);
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setCurrency("euro");
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCreatedAt(new Date());
				bankAccountReposittory.save(savingAccount);
			});
			bankAccountReposittory.findAll().forEach(bankAccount -> {
				System.out.println(bankAccount.toString());
				for (int i = 0; i < 15; i++) {
					AccountOperation operation= new AccountOperation();
					operation.setAmount(Math.random()*5000);
					operation.setDate(new Date());
					operation.setAccount(bankAccount);
					operation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
					accountOperationRepository.save(operation);
				}
			});
		};
	}

	//@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService){

		return args -> {
			for (int i = 0; i < 10; i++) {
				System.out.println("=======================================>"+Math.round(Math.random()*9000));
			}
			Stream.of("Mamadou","Aliou","saliou","Alpha","Aissatou","Marco","Aissatou","Amedeo","paola","Lara","Mazzocco","Cecilia").forEach(name->{
						CustomerDto customer= new CustomerDto();
						customer.setName(name);
						customer.setEmail(name.toLowerCase()+"@gmail.com");
						//bankAccountService.saveCustomer(customer);
					}

			);
			bankAccountService.listCustomers().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*9000,9000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random()*12000,5.5, customer.getId() );
					List<BankAccountDto> bankAccounts= bankAccountService.bankAccountlist();

					for (BankAccountDto bankAccount:bankAccounts) {
						System.out.println(bankAccount.toString());


						/*for (int i = 0; i < 10; i++) {
							try {
								bankAccountService.credit(bankAccount.getId(),10000+Math.random()*120000,"Operation Credit Effectuèe" );
								//	bankAccountService.debit(bankAccount.getId(),100+Math.random()*9000,"Operation Debit Effectuèe" );
							} catch (BankAccountNotFoundException e) {
								throw new RuntimeException(e);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
						}

					}


				} catch (CustomerNotFoundException e) {
					throw new RuntimeException(e);
				}
				//(double initialBalance, double overdraft, Long customerId,double overDraft)
			});
		};
	}*/
	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService, CustomerService customerService){
		return args -> {
			Stream.of("Elenora","Guilia","Sam","Mamadou","Aliou","saliou","Alpha","Marco","Aissatou","Amedeo","paola","Lara","Mazzocco","Cecilia").forEach(name->{
				CustomerDto customer=new CustomerDto();
				customer.setName(name);
				customer.setEmail(name.toLowerCase()+"@gmail.com");
				customerService.saveCustomer(customer);
			});
			customerService.listCustomers().forEach(customer->{
				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());

				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});
			List<BankAccountDto> bankAccounts = bankAccountService.bankAccountList();
			for (BankAccountDto bankAccount:bankAccounts){
				for (int i = 0; i <10 ; i++) {
					String accountId;
					if(bankAccount instanceof SavingAccountDto){
						accountId=((SavingAccountDto) bankAccount).getId();
					} else{
						accountId=((CurrentAccountDto) bankAccount).getId();
					}
					bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
					bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
				}
			}
		};
	}
			//	customerService.listCustomers().forEach(customerDto -> System.out.println(customerDto.toString()));
			/*customerService.listCustomers().forEach(customer->{
				try {
					bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getId());

					bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());

				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}
			});
			/*List<BankAccountDto> bankAccounts = bankAccountService.bankAccountlist();
			for (BankAccountDto bankAccount:bankAccounts){
				for (int i = 0; i <10 ; i++) {
					String accountId;
					if(bankAccount instanceof SavingAccountDto){
						accountId=((SavingAccountDto) bankAccount).getId();
					} else{
						accountId=((CurrentAccountDto) bankAccount).getId();
					}
					bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
					bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
				}
			}*/
		};
