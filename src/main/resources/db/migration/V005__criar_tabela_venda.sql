CREATE TABLE IF NOT EXISTS vendas
(
    id BIGSERIAL,
    data_venda timestamp NOT NULL,
    valor numeric(10,2) NOT NULL,
    desconto numeric(10,2) DEFAULT 0,
    observacao text,
    usuario_id bigint NOT NULL,
    cliente_id bigint,
    status VARCHAR(50),
    CONSTRAINT venda_pkey PRIMARY KEY (id),
    CONSTRAINT venda_id_usuario_fkey FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id)
);


CREATE TABLE IF NOT EXISTS vendas_produtos
(
    venda_id bigint NOT NULL,
    produto_id bigint NOT NULL,
    quantidade integer NOT NULL DEFAULT 0,
    valor NUMERIC(10,2) NOT NULL,
    CONSTRAINT venda_produtos_pkey PRIMARY KEY (venda_id, produto_id),
    CONSTRAINT venda_produtos_id_produto_fkey FOREIGN KEY (produto_id)
        REFERENCES produtos (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT venda_produtos_id_venda_fkey FOREIGN KEY (venda_id)
        REFERENCES vendas (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)