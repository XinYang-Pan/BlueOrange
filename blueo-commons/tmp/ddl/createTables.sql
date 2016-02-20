CREATE TABLE TBL_PERSON (
	ID bigint NOT NULL, -- Auto added for HasId Po
	NAME varchar(255) NULL,
	AGE int NULL,
	SEX varchar(6) NULL, -- gender
	CREATE_ID long NULL, -- Auto added for Traceable Po
	UPDATE_ID long NULL, -- Auto added for Traceable Po
	CREATE_TIME date NULL, -- Auto added for Traceable Po
	UPDATE_TIME date NULL, -- Auto added for Traceable Po
	DEL_FLAG boolean NULL -- Auto added for Traceable Po
)

