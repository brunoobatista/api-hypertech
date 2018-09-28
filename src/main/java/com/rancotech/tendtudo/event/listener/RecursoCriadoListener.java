package com.rancotech.tendtudo.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import com.rancotech.tendtudo.event.RecursoCriadoEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

    @Override
    public void onApplicationEvent(RecursoCriadoEvent recrusoCriadoEvent) {
        HttpServletResponse response = recrusoCriadoEvent.getResponse();
        Long id = recrusoCriadoEvent.getId();

        adicionarHeaderLocation(response, id);

    }

    private void adicionarHeaderLocation(HttpServletResponse response, Long id) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(id).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }

}
