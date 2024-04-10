CREATE TABLE borrow (
    id SERIAL PRIMARY KEY,
    book_id INTEGER,
    patron_id INTEGER,
    borrowed_at timestamp without time zone,
    returned_at timestamp without time zone,
    CONSTRAINT book_id_borrows_fk FOREIGN KEY (book_id) REFERENCES books(id),
    CONSTRAINT patron_id_borrows_fk FOREIGN KEY (patron_id) REFERENCES patrons(id),
    CONSTRAINT unique_book_patron_pair UNIQUE (book_id, patron_id)
);
