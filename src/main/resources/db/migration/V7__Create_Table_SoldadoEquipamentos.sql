CREATE TABLE IF NOT EXISTS soldados_equipamentos (
    idSoldadoEquipamento SERIAL PRIMARY KEY,
    soldado_id INT NOT NULL,
    equipamento_id INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    data_atribuicao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observacao TEXT,
    usuario_id INT,
    FOREIGN KEY (soldado_id) REFERENCES soldados(idSoldados) ON DELETE CASCADE,
    FOREIGN KEY (equipamento_id) REFERENCES equipamentos(idEquipamentos) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(idUsuarios) ON DELETE SET NULL
);