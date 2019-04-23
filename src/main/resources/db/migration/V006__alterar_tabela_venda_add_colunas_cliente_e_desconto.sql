ALTER TABLE venda ADD COLUMN IF NOT EXISTS desconto numeric(10,2) DEFAULT 0;
ALTER TABLE venda ADD COLUMN IF NOT EXISTS cliente_id bigint;

ALTER TABLE venda DROP CONSTRAINT IF EXISTS cliente_id_fk;
ALTER TABLE venda ADD CONSTRAINT cliente_id_fk FOREIGN KEY(cliente_id) REFERENCES usuario(id);
