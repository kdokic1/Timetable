BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "subject" (
	"subject_name"	TEXT,
	"user"	TEXT,
	"teacher"	TEXT,
	"classroom"	TEXT,
	PRIMARY KEY("subject_name"),
	FOREIGN KEY("user") REFERENCES "user"("username")
);
CREATE TABLE IF NOT EXISTS "user" (
	"username"	TEXT,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("username")
);
CREATE TABLE IF NOT EXISTS "timetable" (
    "timetable_name" TEXT,
    "user" TEXT,
    "include_saturday" boolean,
    PRIMARY KEY ("user","timetable_name"),
    FOREIGN KEY("user") REFERENCES "user"("username")
);
CREATE TABLE IF NOT EXISTS "timetable_field" (
    "timetable" TEXT,
    "user" TEXT,
    "day" TEXT,
    "ordinal_number" INTEGER,
    "subject" TEXT,
    "starts" time,
    "ends" time,
    FOREIGN KEY ("timetable") REFERENCES "timetable"("timetable_name"),
    FOREIGN KEY ("user") REFERENCES "user"("username"),
    FOREIGN KEY ("subject") REFERENCES "subject"("subject_name")
);
INSERT INTO "user" VALUES ('kdokic1','Kanita','Đokić','kdokic1@etf.unsa.ba','Uquahlahying18523');
INSERT INTO "subject" VALUES ('math','kdokic1','Muslija Omerović','classroom');
INSERT INTO "timetable" VALUES ('novi','kdokic1','0');
INSERT INTO "timetable_field" VALUES ('novi','kdokic1','mon','2','math','11','12');
COMMIT;
