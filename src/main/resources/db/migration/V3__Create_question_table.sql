create table question
(
	id int,
	title varchar(50),
	description TEXT,
	gmt_create BIGINT,
	gmt_modify BIGINT,
	creator int,
	comment_count int default 0,
	view_count int default 0,
	like_count int default 0,
	tag varchar(256),
	column_11 int
);

