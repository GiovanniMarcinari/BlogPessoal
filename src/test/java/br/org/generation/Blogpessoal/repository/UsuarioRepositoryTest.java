package br.org.generation.Blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.org.generation.Blogpessoal.model.Usuario;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		usuarioRepository.save(new Usuario(0L,"João da Silva","joao@email.com.br","123658585521"));
		
		usuarioRepository.save(new Usuario(0L,"Manuela da Silva","manuela@email.com.br","1234585565"));
		
		usuarioRepository.save(new Usuario(0L,"Adriana da Silva","adriana@email.com.br","asd12345656"));
		
		usuarioRepository.save(new Usuario(0L,"Paulo Antunes","paulo@email.com.br","1234568plo"));
		
		usuarioRepository.save(new Usuario(0L,"Joana Sanches","joana@email.com.br","1234585jj"));
		
		usuarioRepository.save(new Usuario(0L,"Ricardo Pereira","ricardo@email.com.br","1234585r596"));
	}
	
	@Test
	@DisplayName("✌ Retorna 1 Usuário")
	public void deveRetornarUmUsuario() {
		
		Optional <Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");		//ele é do tipo optional porque pode trazer algo ou pode nao trazer nada
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));	//mesma coisa que um é verdade, que o usuario joao@... existe?
	}
	
	@Test
	@DisplayName("✌ Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuarios.size());
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
		
	}
}