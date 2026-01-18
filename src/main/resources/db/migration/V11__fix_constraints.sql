ALTER TABLE squad_members DROP CONSTRAINT squad_members_user_id_fkey;
ALTER TABLE squad_members DROP CONSTRAINT squad_members_squad_id_fkey;

ALTER TABLE squad_members ADD CONSTRAINT squad_members_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL;
ALTER TABLE squad_members ADD CONSTRAINT squad_members_squad_id_fkey FOREIGN KEY (squad_id) REFERENCES squads(id) ON DELETE CASCADE;

-- giveaways

ALTER TABLE giveaways DROP CONSTRAINT giveaways_squad_id_fkey;
ALTER TABLE giveaways ADD CONSTRAINT giveaways_squad_id_fkey FOREIGN KEY (squad_id) REFERENCES squads(id) ON DELETE CASCADE;

-- proofs

ALTER TABLE proofs DROP CONSTRAINT proofs_giveaway_id_fkey;
ALTER TABLE proofs ADD CONSTRAINT proofs_giveaway_id_fkey FOREIGN KEY (giveaway_id) REFERENCES giveaways(id) ON DELETE SET NULL;