CREATE TABLE account(
id TEXT NOT NULL PRIMARY KEY,
pwd TEXT NOT NULL
);

CREATE TABLE map_pc(
id TEXT NOT NULL,
pc TEXT NOT NULL PRIMARY KEY,
alias TEXT NOT NULL,
CONSTRAINT map_pc_fk_id FOREIGN KEY(id)
REFERENCES account(id));

CREATE TABLE map_android(
id TEXT NOT NULL,
token TEXT NOT NULL,
PRIMARY KEY(id, token),
CONSTRAINT map_android_fk_id FOREIGN KEY(id)
REFERENCES account(id));

CREATE TABLE auth_factor(
id TEXT NOT NULL PRIMARY KEY,
factor INTEGER NOT NULL DEFAULT 1,
CONSTRAINT map_factor_fk_id FOREIGN KEY(id)
REFERENCES account(id));

CREATE TABLE init_factor(
id TEXT NOT NULL PRIMARY KEY,
usb INTEGER NOT NULL DEFAULT 0 CHECK(usb=0 or usb=1),
pattern INTEGER NOT NULL DEFAULT 0 CHECK(pattern=0 or pattern=1),
finger_print INTEGER NOT NULL DEFAULT 0 CHECK(finger_print=0 or finger_print=1),
face INTEGER NOT NULL DEFAULT 0 CHECK(face=0 or face=1),
CONSTRAINT init_factor_fk_id FOREIGN KEY(id)
REFERENCES account(id));

CREATE TABLE auth_usb(
id TEXT NOT NULL,
usb_id TEXT NOT NULL PRIMARY KEY,
CONSTRAINT auth_usb_fk_id FOREIGN KEY(id)
REFERENCES account(id));

CREATE TABLE auth_pattern(
id TEXT NOT NULL PRIMARY KEY,
pattern TEXT NOT NULL,
CONSTRAINT auth_pattern_fk_id FOREIGN KEY(id)
REFERENCES account(id));

CREATE TABLE auth_face(
id TEXT NOT NULL PRIMARY KEY,
hash TEXT NOT NULL,
CONSTRAINT auth_face_fk_id FOREIGN KEY(id)
REFERENCES account(id));

CREATE TABLE auth_otp(
id TEXT NOT NULL PRIMARY KEY,
otp TEXT NOT NULL,
CONSTRAINT auth_otp_fk_id FOREIGN KEY(id)
REFERENCES account(id));