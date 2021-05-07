
CREATE OR REPLACE STREAM "DESTINATION_SQL_STREAM" (
   "animal" varchar(40),
   number_of_animals double);

CREATE OR REPLACE PUMP "STREAM_PUMP" AS INSERT INTO "DESTINATION_SQL_STREAM"
SELECT STREAM "animal", number_of_animals
FROM (
    SELECT STREAM
        "animal",
        count("animal") OVER W1 as number_of_animals
    FROM "SOURCE_SQL_STREAM_001"
    WINDOW W1 AS (PARTITION BY "animal" RANGE INTERVAL '2' MINUTE PRECEDING)
)
WHERE ABS(number_of_animals) > 1;