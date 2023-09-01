CREATE TABLE page (
    id BIGINT  AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    content VARCHAR(255),
    parent_id BIGINT ,
    FOREIGN KEY (parent_id) REFERENCES page(id)
);
