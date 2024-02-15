CREATE TABLE IF NOT EXISTS song_metadata (
    id SERIAL PRIMARY KEY,
    name text,
    artist text,
    album text,
    length text,
    resource_id INTEGER,
    year text
);