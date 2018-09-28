package com.rancotech.tendtudo.resource;

import java.util.List;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import com.rancotech.tendtudo.repository.filter.TipoFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rancotech.tendtudo.model.Tipo;
import com.rancotech.tendtudo.repository.TipoRepository;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/tipos")
public class TipoResource {

	@Autowired
	private TipoRepository tipoRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_TIPO') and #oauth2.hasScope('write')")
	public Page<Tipo> listar(TipoFilter tipoFilter, Pageable pageable) {
		return tipoRepository.filtrar(tipoFilter, pageable);
	}
	
	@PostMapping
	public ResponseEntity<Tipo> criar(@Valid @RequestBody Tipo tipo, HttpServletResponse response) {
		Tipo tipoSalvo = tipoRepository.save(tipo);

		publisher.publishEvent(new RecursoCriadoEvent(this, response, tipoSalvo.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(tipoSalvo);
	}

}
