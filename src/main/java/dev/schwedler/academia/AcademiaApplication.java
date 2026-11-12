package dev.schwedler.academia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication junta 3 anotacoes:
//  - @Configuration: a classe pode declarar beans;
//  - @EnableAutoConfiguration: o Spring Boot configura sozinho o que encontra no classpath
//    (datasource, JPA, Flyway, Jackson, servidor web...);
//  - @ComponentScan: procura @Service, @RestController, @Repository etc. neste pacote e nos subpacotes.
// Por isso esta classe fica no pacote RAIZ (dev.schwedler.academia): tudo abaixo dela e encontrado.
@SpringBootApplication
public class AcademiaApplication {

	// Ponto de entrada do programa. SpringApplication.run sobe o contexto do Spring
	// e inicia o servidor web embutido (Tomcat), que fica escutando as requisicoes HTTP.
	public static void main(String[] args) {
		SpringApplication.run(AcademiaApplication.class, args);
	}

}
