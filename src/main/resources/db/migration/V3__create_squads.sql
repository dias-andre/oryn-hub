CREATE TABLE squads (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    icon VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE squad_members (
    role VARCHAR(50),
    user_id UUID NOT NULL,
    squad_id UUID NOT NULL,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_squad_members PRIMARY KEY (user_id, squad_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY(squad_id) REFERENCES squads(id)
);

ALTER TABLE users DROP TABLE role;