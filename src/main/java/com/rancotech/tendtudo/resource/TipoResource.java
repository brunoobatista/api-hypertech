package com.rancotech.tendtudo.resource;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import com.rancotech.tendtudo.model.Tipo;
import com.rancotech.tendtudo.repository.TipoRepository;
import com.rancotech.tendtudo.repository.filter.TipoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipos")
public class TipoResource {

	@Autowired
	private TipoRepository tipoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
    @PreAuthorize("hasAnyAuthority('READ_PRODUTO', 'FULL_PRODUTO')")
	public Page<Tipo> listar(TipoFilter tipoFilter, Pageable pageable) {
		return tipoRepository.filtrar(tipoFilter, pageable);
	}

	@GetMapping("/search/")
    @PreAuthorize("hasAnyAuthority('READ_PRODUTO', 'WRITE_PRODUTO', 'FULL_PRODUTO')")
	public List<Tipo> buscarTodos() {
		String valor = "";
		return tipoRepository.filtrarPorTipo(valor);
	}

	@GetMapping("/search/{valor}")
    @PreAuthorize("hasAnyAuthority('READ_PRODUTO', 'FULL_PRODUTO')")
	public List<Tipo> buscarTodosSearch(@PathVariable String valor) {
		return tipoRepository.filtrarPorTipo(valor);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyAuthority('WRITE_PRODUTO', 'FULL_PRODUTO')")
	public ResponseEntity<Tipo> criar(@Valid @RequestBody Tipo tipo, HttpServletResponse response) {
		Tipo tipoSalvo = tipoRepository.save(tipo);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, tipoSalvo.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(tipoSalvo);
	}

	@GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('READ_PRODUTO', 'FULL_PRODUTO')")
	public ResponseEntity<Tipo> buscarPorCodigo(@PathVariable Long id) {
		Optional<Tipo> tipo = tipoRepository.findById(id);
		return tipo.isPresent() ? ResponseEntity.ok(tipo.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyAuthority('WRITE_PRODUTO', 'FULL_PRODUTO')")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Object> remover(@PathVariable Long id) {
		tipoRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("ok");
	}


}
