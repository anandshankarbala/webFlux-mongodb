package com.wiredbraincoffee.productapiannotation;

import com.wiredbraincoffee.productapiannotation.model.Product;
import com.wiredbraincoffee.productapiannotation.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ProductApiAnnotationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApiAnnotationApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ReactiveMongoOperations operations, ProductRepository repository){
		return args -> {
			Flux<Product> productFlux = Flux.just(
					new Product(null,"Chai",2.99),
					new Product(null,"Poha",7),
					new Product(null,"Dosa",10)
					).flatMap(repository::save);

			productFlux.thenMany(repository.findAll())
					.subscribe(System.out::println);

//			operations.collectionExists(Product.class)
//					.flatMap(exists -> exists ? operations.dropCollection(Product.class) : Mono.just(exists))
//					.thenMany(v -> operations.createCollection(Product.class))
//					.thenMany(productFlux)
//					.thenMany(repository.findAll())
//					.subscribe(product -> {
//						System.out.println("product is "+product);
//					});
		};
	}

}
