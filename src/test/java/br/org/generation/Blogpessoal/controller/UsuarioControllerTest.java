package br.org.generation.Blogpessoal.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.org.generation.Blogpessoal.model.Usuario;
import br.org.generation.Blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Test
	@Order(1)
	@DisplayName("Cadastar um Usuário")
	public void deveCriarUmUsuario() {		//faz a checagem para ver se o usuário existe
		

		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L, 
				"Paulo Antunes","paulo@email.com.br","1234568p"));

		ResponseEntity<Usuario> resposta = testRestTemplate
			.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getNome(), resposta.getBody().getNome());
		assertEquals(requisicao.getBody().getUsuario(), resposta.getBody().getUsuario());
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir duplicação do Usuário")
	public void naoDeveDuplicarUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L,"Manuela da Silva","manuela@email.com.br","1234585"));
		
		HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(new Usuario(0L,"Manuela da Silva","manuela@email.com.br","1234585"));
		
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, requisicao, Usuario.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode()); //ele vai fazer uma checagem pra ver se o que eu passei existe
	
	}
	
	@Test
	@Order(3)
	@DisplayName("Listar todos os Usuário")
	public void deveMostrarTodosUsuario() {
		
		usuarioService.cadastrarUsuario(new Usuario(0L,"Joana Sanches","joana@email.com.br","1234585jj"));
		
		usuarioService.cadastrarUsuario(new Usuario(0L,"Ricardo Pereira","ricardo@email.com.br","1234585r"));
		
		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/all", HttpMethod.GET, null, String.class);

			assertEquals(HttpStatus.OK, resposta.getStatusCode());
	
	}
	
	@Test
	@Order(4)
	@DisplayName("Atualizar um Usuário")
	public void deveAtualizarUmUsuario() {
		
		Optional<Usuario> usuarioCreate = usuarioService.cadastrarUsuario(new Usuario(0L, 
				"Ricardo Pereira","ricardo@email.com.br","1234585r"));

			Usuario usuarioUpdate = new Usuario(usuarioCreate.get().getId(), 
					"Ricardo Pereira","ricardo_pereira@email.com.br","1234585r");
			
			HttpEntity<Usuario> requisicao = new HttpEntity<Usuario>(usuarioUpdate);

			ResponseEntity<Usuario> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/usuarios/atualizar", HttpMethod.PUT, requisicao, Usuario.class);

			assertEquals(HttpStatus.OK, resposta.getStatusCode());
			assertEquals(usuarioUpdate.getNome(), resposta.getBody().getNome());
			assertEquals(usuarioUpdate.getUsuario(), resposta.getBody().getUsuario()); 
	
	}
	
	
}