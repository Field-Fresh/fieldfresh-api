CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
--
-- Name: base62_encode(bigint); Type: FUNCTION;
--
CREATE OR REPLACE FUNCTION base62_encode(long_number bigint) RETURNS text
    LANGUAGE plpgsql IMMUTABLE STRICT
    AS $$
/*
 * base62_encode()
 *
 * This function accepts a small or big number (base 10) and reduces its length into a string
 * that is URI-safe using the upper and lower case 26-letter English alphabet
 * as well as the numbers 0 - 9. The result is returned as text.
 *
 * You can find a handy explainer at https://helloacm.com/base62/
 *
 * Source: https://gist.github.com/david-sanabria/0d3ff67eb56d2750502aed4186d6a4a7
 *
 */
declare
  k_base     constant integer := 62;
  k_alphabet constant text[] := string_to_array('0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'::text, null);
  v_return_text text := '';
  v_remainder   integer;
  v_interim     bigint;
begin
  v_interim := abs(long_number);  -- Negative Numbers (sign) are ignored
  loop
    v_remainder := v_interim % k_base;
    v_interim := v_interim / k_base;
    v_return_text := ''|| k_alphabet[ (v_remainder + 1) ] || v_return_text;
    exit when v_interim <= 0;
  end loop;
  return v_return_text;
end;$$;
--
-- Name: base62_uuid(); Type: FUNCTION;
--
CREATE OR REPLACE FUNCTION base62_uuid() RETURNS text
    LANGUAGE plpgsql
    AS $$
/*
 * base62_uuid()
 *
 * Generate a UUID, but encode it in base62.
 *
 */
declare
  v_uuid_text text;
  v_uuid_lo   bigint;
  v_uuid_hi   bigint;
begin
  v_uuid_text := replace(cast (uuid_generate_v1mc() as text), '-', '');
  v_uuid_lo := ('x' || substring(v_uuid_text for 16))::bit(64)::bigint;
  v_uuid_hi := ('x' || substring(v_uuid_text from 17))::bit(64)::bigint;
  return lpad(base62_encode(v_uuid_lo), 11, '0') || lpad(base62_encode(v_uuid_hi), 11, '0');
end;$$;
--
-- Name: fieldfresh_id(text); Type: FUNCTION;
--
CREATE OR REPLACE FUNCTION fieldfresh_id(id_prefix text DEFAULT NULL::text) RETURNS text
    LANGUAGE plpgsql
    AS $$
/*
 * fieldfresh_id(id_prefix)
 *
 * Generate an ID for the purpose an fieldfresh primary key. Optionally,
 * with a prefix to allow for easy identification.
 *
 */
declare
  v_prefix text;
begin
  v_prefix := '';
  if id_prefix is not null then
    v_prefix := id_prefix || '_';
  end if;
  return v_prefix || base62_uuid();
end;$$;

CREATE TABLE users (
    id                  character(24) DEFAULT fieldfresh_id('u'::text) NOT NULL PRIMARY KEY,
    first_name varchar,
    last_name varchar,
    email varchar not null,
    phone varchar,
    cognito_sub varchar,
    verified bool,
    username varchar,
    created_at          timestamp,
    updated_at          timestamp
);
