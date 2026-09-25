CREATE TABLE IF NOT EXISTS equipamentos (
    idEquipamentos SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    quantidade INT NOT NULL DEFAULT 0,
    fabricante VARCHAR(80) NOT NULL,
    peso FLOAT NOT NULL,
    descricao TEXT,
    status VARCHAR(20) DEFAULT 'DISPONIVEL' CHECK (status IN ('DISPONIVEL', 'MANUTENCAO', 'INDISPONIVEL')),
    tipo_pelotao VARCHAR(20) NOT NULL CHECK (tipo_pelotao IN ('selva', 'tanque', 'jeep', 'paraquedista')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);