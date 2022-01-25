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

import dio.model.Telefone;

@RestController
@RequestMapping("/api/0.1/telefone")
public class TelefoneController {

	@Autowired
	TelefoneRepository ms;

	@GetMapping
	public ResponseEntity<List<Telefone>> getTelefone() {
		List<Telefone> telefone = ms.findAll();
		if(telefone.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} else 
			return new ResponseEntity<>(ms.findAll(),HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Telefone> getTelefoneById(@PathVariable("id") Long id) {
		return new ResponseEntity<>(ms.findById(id).get(),HttpStatus.OK);
	}

	@PostMapping("/")
	public ResponseEntity<Telefone> createTelefone(@RequestBody @Validated Telefone p) {
		return new ResponseEntity<>(ms.save(p), HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Telefone> setTelefone(@PathVariable("id") Long id, @RequestBody @Validated Telefone p) {
		Optional<Telefone> telefoneOpt = ms.findById(id);
		if (telefoneOpt.isPresent()) {
			Telefone telefone = telefoneOpt.get();
			telefone.setId(p.getId());
			telefone.setDdi(p.getDdi());
			telefone.setDdd(p.getDdd());
			telefone.setNumero(p.getNumero());
			
			return new ResponseEntity<>(ms.save(telefone), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteTelefone(@PathVariable("id") Long id) {
		Optional<Telefone> telefoneOpt = ms.findById(id);
		if (telefoneOpt.isPresent()) {
			ms.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
