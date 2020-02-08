BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "user" (
	"username"	TEXT,
	"first_name"	TEXT,
	"last_name"	TEXT,
	"email"	TEXT,
	"password"	TEXT,
	PRIMARY KEY("username")
);
insert into user (username,first_name,last_name,email,password) values ('Kani','Kans','Kanci','kan@etf.unsa.ba','pass');
COMMIT;
