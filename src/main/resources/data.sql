-- 루트 페이지 생성
INSERT INTO page (title, content, parent_id) VALUES ('A', 'A', NULL);

-- 999개의 자식 페이지 생성
INSERT INTO page (title, content, parent_id)
SELECT 'A' || ROWNUM, 'A' || ROWNUM, CASE WHEN ROWNUM = 1 THEN NULL ELSE ROWNUM END
FROM SYSTEM_RANGE(1, 9999);