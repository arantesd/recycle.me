package com.recycleme.recycleme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.recycleme.recycleme.model.Avaliacao;
import com.recycleme.recycleme.repository.AvaliacaoRepository;

@RestController
@RequestMapping("api/v1/recycleMe/avaliacao")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AvaliacaoController {

	@Autowired
	private AvaliacaoRepository repository;

	@GetMapping
	public ResponseEntity<List<Avaliacao>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Avaliacao> GetById(@PathVariable Long id) {
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp)).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/avaliacao/{avaliacao}")
	public ResponseEntity<List<Avaliacao>> GetByAvaliacao(@PathVariable Long avaliacao) {
		return ResponseEntity.ok(repository.findAllByAvaliacao(avaliacao));
	}

	@GetMapping("/classificacao")
	public ResponseEntity<List<Avaliacao>> GetByClassificacao() {
		return ResponseEntity.ok(repository.findAllBylCassificacao());
	}

	@PostMapping
	public ResponseEntity<Avaliacao> post(@RequestBody Avaliacao avaliacao) {
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(avaliacao));
	}

	@PutMapping("/{avaliacaoId}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Avaliacao avaliacao) {

		Optional<Avaliacao> avaliacaoAtual = repository.findById(id);

		if (avaliacaoAtual != null) {
			avaliacaoAtual = repository.save(avaliacaoAtual);
			return ResponseEntity.ok(avaliacaoAtual);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		repository.deleteById(id);
	}
}