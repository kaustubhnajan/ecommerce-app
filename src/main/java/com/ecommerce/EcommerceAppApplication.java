package com.ecommerce;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceAppApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws ResourceNotFoundException {
		User customer = new User("user1", "user1@gmail.com","User1_first_name",
				"User1_last_name","1234567890", "Pune", User.UserType.CUSTOMER);
		userRepository.save(customer);

		User seller = new User("seller1", "seller1@gmail.com","Seller1_first_name",
				"Seller1_last_name","9876543211", "Pune", User.UserType.SELLER);
		userRepository.save(seller);
	}
}
