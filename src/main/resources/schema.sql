DROP TABLE IF EXISTS PAGE;
CREATE TABLE PAGE
(
    page_id BIGINT AUTO_INCREMENT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    parent_page_id BIGINT,
    PRIMARY KEY (page_id),
    FOREIGN KEY (parent_page_id) REFERENCES PAGE(page_id)
);