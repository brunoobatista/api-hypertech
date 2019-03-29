CREATE TABLE IF NOT EXISTS venda
(
    id BIGSERIAL,
    data_venda date NOT NULL,
    valor numeric(10,2) NOT NULL,
    observacao text,
    usuario_id bigint NOT NULL,
    CONSTRAINT venda_pkey PRIMARY KEY (id),
    CONSTRAINT venda_id_usuario_fkey FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
);


-- Table: public.venda_produtos

-- DROP TABLE public.venda_produtos;

CREATE TABLE IF NOT EXISTS venda_produtos
(
    venda_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    quantidade integer NOT NULL DEFAULT 0,
    CONSTRAINT venda_produtos_pkey PRIMARY KEY (venda_id, produto_id),
    CONSTRAINT venda_produtos_id_produto_fkey FOREIGN KEY (produto_id)
        REFERENCES public.produto (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT venda_produtos_id_venda_fkey FOREIGN KEY (venda_id)
        REFERENCES public.venda (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)