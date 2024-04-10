CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    author VARCHAR(255),
    publication_year timestamp without time zone,
    isbn VARCHAR(255)
);