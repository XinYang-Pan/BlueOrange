-- TBL_PERSON
DROP TABLE TBL_PERSON;
CREATE TABLE TBL_PERSON (
	ID bigint NOT NULL PRIMARY KEY, -- Auto added for HasId Po
	NAME varchar(255) NULL,
	AGE int NOT NULL,
	SEX varchar(6) NULL, -- gender
	CREATE_ID bigint NULL, -- Auto added for Traceable Po
	UPDATE_ID bigint NULL, -- Auto added for Traceable Po
	CREATE_TIME date NULL, -- Auto added for Traceable Po
	UPDATE_TIME date NULL, -- Auto added for Traceable Po
	ACTIVE_FLAG boolean NULL -- Auto added for Traceable Po
);
