package dio.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import dio.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}
