package dio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dio.model.Pessoa;
import dio.model.Telefone;

@RestController
@RequestMapping("/api/0.1/pessoa")
public class PessoaController {

	@Autowired
	PessoaRepository pr;
	@Autowired
	TelefoneRepository tr;

	@GetMapping
	public ResponseEntity<List<Pessoa>> getPessoas() {
		List<Pessoa> pessoas = pr.findAll();
		for(Pessoa p : pessoas) {
			p.setTelefone(tr.findByFkcpf(p.getCpf()));
		}
		if(pessoas.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} else 
			return new ResponseEntity<>(pessoas,HttpStatus.OK);
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<Pessoa> getPessoaById(@PathVariable("cpf") Long cpf) {
		Optional<Pessoa> pessoaOpt = pr.findById(cpf);
		if (pessoaOpt.isPresent()) {
			Pessoa p = pessoaOpt.get();
			p.setTelefone(tr.findByFkcpf(p.getCpf()));
			return new ResponseEntity<>(pr.findById(cpf).get(),HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/")
	public ResponseEntity<Pessoa> createPessoas(@RequestBody @Validated Pessoa p) {
		return new ResponseEntity<>(pr.save(p), HttpStatus.CREATED);
	}
	@PutMapping("/{cpf}")
	public ResponseEntity<Pessoa> setPessoa(@PathVariable("cpf") Long cpf, @RequestBody @Validated Pessoa p) {
		Optional<Pessoa> pessoaOpt = pr.findById(cpf);
		if (pessoaOpt.isPresent()) {
			Pessoa pessoa = pessoaOpt.get();
			pessoa.setCpf(p.getCpf());
			pessoa.setNome(p.getNome());
			pessoa.setNascimento(p.getNascimento());
			pessoa.setEndereco(p.getEndereco());
			pessoa.setEmail(p.getEmail());
			pessoa.setTelefone(p.getTelefone());
			return new ResponseEntity<>(pr.save(pessoa), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{cpf}")
	public ResponseEntity<HttpStatus> deletePessoa(@PathVariable("cpf") Long cpf) {
		Optional<Pessoa> pessoaOpt = pr.findById(cpf);
		if (pessoaOpt.isPresent()) {
			Pessoa pessoa = pessoaOpt.get();
			List<Telefone> telefones = tr.findByFkcpf(pessoa.getCpf());
			for(Telefone t : telefones) {
				tr.deleteById(t.getId());
			}
			pr.deleteById(cpf);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
