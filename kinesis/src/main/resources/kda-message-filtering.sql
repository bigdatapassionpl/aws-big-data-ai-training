
CREATE OR REPLACE STREAM "DESTINATION_SQL_STREAM" (
   "animal" varchar(40),
   avg_change double);
CREATE OR REPLACE PUMP "STREAM_PUMP" AS INSERT INTO "DESTINATION_SQL_STREAM"
SELECT STREAM "animal", avg_change
FROM (
    SELECT STREAM
        "animal",
        count("number") OVER W1 as avg_change
    FROM "SOURCE_SQL_STREAM_001"
    WINDOW W1 AS (PARTITION BY "animal" RANGE INTERVAL '60' SECOND PRECEDING)
)
WHERE ABS(avg_change) > 1;