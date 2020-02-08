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
INSERT INTO "user" VALUES ('kdokic1','Kanita','Đokić','kdokic1@etf.unsa.ba','Uquahlahying18523');
COMMIT;
