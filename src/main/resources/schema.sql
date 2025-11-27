CREATE TABLE IF NOT EXISTS publisher (
                                         publisherId SERIAL PRIMARY KEY,
                                         publisherName VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS author (
                                      authorId SERIAL PRIMARY KEY,
                                      authorName VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS book (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255),
                                    imageUrl VARCHAR(255),
                                    publisherId INT,
                                    FOREIGN KEY (publisherId) REFERENCES publisher(publisherId)
);

CREATE TABLE IF NOT EXISTS book_author (
                                           bookId INT,
                                           authorId INT,
                                           PRIMARY KEY (bookId, authorId),
                                           FOREIGN KEY (bookId) REFERENCES book(id),
                                           FOREIGN KEY (authorId) REFERENCES author(authorId)
);
