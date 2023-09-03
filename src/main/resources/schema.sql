CREATE TABLE page (
    id BIGINT  AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(16383),
    content VARCHAR(16383),
    parent_id BIGINT ,
    FOREIGN KEY (parent_id) REFERENCES page(id)
);
