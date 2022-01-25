package dio.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dio.model.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
	List<Telefone> findByFkcpf(Long cpf);
}
