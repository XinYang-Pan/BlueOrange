-- TBL_PERSON
CREATE TABLE TBL_PERSON (
	ID bigint NOT NULL PRIMARY KEY, -- Auto added for HasId Po
	NAME varchar(255) NOT NULL,
	AGE int NOT NULL,
	CREATE_ID bigint NULL, -- Auto added for Traceable Po
	UPDATE_ID bigint NULL, -- Auto added for Traceable Po
	CREATE_TIME date NULL, -- Auto added for Traceable Po
	UPDATE_TIME date NULL, -- Auto added for Traceable Po
	DEL_FLAG boolean NULL -- Auto added for Traceable Po
);
