DO
$$
    BEGIN
        IF EXISTS(SELECT
                  FROM information_schema.tables
                  WHERE table_schema = 'public'
                    AND table_name = 'users')
        THEN
            INSERT INTO users (is_active, is_queue, "order", role, tg_id, username)
            VALUES
                (true, false, 0, 'ADMIN', 5781421477, '@daniltribushko26');
        END IF;
    END;
$$