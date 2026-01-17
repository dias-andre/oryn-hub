ALTER TABLE users ADD COLUMN password_hash TEXT DEFAULT 'null_hash';

-- remove not null
ALTER TABLE users ALTER COLUMN discord_id SET DEFAULT 'null_id';
ALTER TABLE users DROP CONSTRAINT users_discord_id_key;