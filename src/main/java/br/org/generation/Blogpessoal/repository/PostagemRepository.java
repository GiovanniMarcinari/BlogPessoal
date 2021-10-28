package br.org.generation.Blogpessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.Blogpessoal.model.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository< Postagem, Long> {

}
