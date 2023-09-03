CREATE TABLE page (
    id BIGINT  AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(1024),
    content VARCHAR(1024),
    parent_id BIGINT ,
    FOREIGN KEY (parent_id) REFERENCES page(id)
);
