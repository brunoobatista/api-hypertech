package com.rancotech.tendtudo.exceptionhandler;

import com.rancotech.tendtudo.service.exception.ProdutoInexistenteException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ProdutoExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({ ProdutoInexistenteException.class })
    public ResponseEntity<Object> handleProdutoInexistenteException(Exception ex, WebRequest request) {
        String mensagemUsuario = messageSource.getMessage("produto.inexistente", null, LocaleContextHolder.getLocale());
        return this.montaMensagem(ex, request, mensagemUsuario);
    }

    private ResponseEntity<Object> montaMensagem(Exception ex, WebRequest request, String mensagemUsuario) {
        String mensagemDesenvolvedor = ExceptionUtils.getRootCauseMessage(ex);
        List<TendtudoExceptionHandler.Erro> erros = Arrays.asList(new TendtudoExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<TendtudoExceptionHandler.Erro> criarListaErros(BindingResult bindingResult) {
        List<TendtudoExceptionHandler.Erro> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String mensagemDesenvolvedor = fieldError.toString();
            erros.add(new TendtudoExceptionHandler.Erro(mensagemUsuario, mensagemDesenvolvedor));
        }

        return erros;
    }

    public static class Erro {

        private String mensagemUsuario;
        private String mensagemDesenvolvedor;

        public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
            this.mensagemUsuario = mensagemUsuario;
            this.mensagemDesenvolvedor = mensagemDesenvolvedor;
        }

        public String getMensagemUsuario() {
            return mensagemUsuario;
        }

        public String getMensagemDesenvolvedor() {
            return mensagemDesenvolvedor;
        }

    }
}
