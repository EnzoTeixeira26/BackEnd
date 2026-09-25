CREATE TABLE IF NOT EXISTS movimentacoes_insumos (
    idMovimentacoes SERIAL PRIMARY KEY,
    insumo_id INT NOT NULL,
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('ENTRADA', 'SAIDA')),
    quantidade INT NOT NULL,
    observacao TEXT,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT,
    FOREIGN KEY (insumo_id) REFERENCES insumos(idInsumos) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(idUsuarios) ON DELETE SET NULL
);