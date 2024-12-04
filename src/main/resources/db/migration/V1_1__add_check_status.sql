ALTER TABLE dbo.task
    ADD CHECK ( status in ('CREATED', 'DO_WORK', 'COMPLETED'));