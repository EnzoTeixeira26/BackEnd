CREATE TABLE IF NOT EXISTS atividades (
    id SERIAL PRIMARY KEY,
    usuario_id INT,
    acao VARCHAR(255) NOT NULL,
    descricao TEXT,
    tipo VARCHAR(20) DEFAULT 'consulta' CHECK (tipo IN ('login', 'consulta', 'cadastro', 'edicao', 'exclusao', 'movimentacao')),
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(idUsuarios) ON DELETE SET NULL
);