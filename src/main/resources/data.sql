DROP TABLE IF EXISTS NEWS CASCADE;
DROP TABLE IF EXISTS COMMENT CASCADE;
CREATE TABLE IF NOT EXISTS NEWS
(
    id    bigserial
        constraint news_pk
            primary key,
    title varchar,
    text  varchar,
    date  timestamp with time zone
);

CREATE TABLE IF NOT EXISTS COMMENT
(
    id       bigserial
        constraint comment_pk
            primary key,
    date     timestamp with time zone,
    text     varchar,
    username varchar,
    news_id  bigint not null
        constraint news_id
            references news
            on update cascade on delete cascade
);

INSERT INTO NEWS (id, title, text, date)
VALUES (1, 'news-1', 'able', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (2, 'news-2', 'text for news-2', '2003-04-12 04:06:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (3, 'news-3', 'text for news-3', '2003-04-12 11:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (4, 'news-4', 'fora', '2003-04-12 12:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (5, 'news-5', 'text for news-5', '2003-04-12 13:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (6, 'news-6', 'text for news-6', '2003-04-12 14:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (7, 'news-7', 'text for news-7', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (8, 'news-8', 'text for news-8', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (9, 'news-9', 'text for news-9', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (10, 'news-10', 'text for news-10', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (11, 'news-11', 'text for news-11', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (12, 'news-12', 'text for news-12', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (13, 'news-13', 'text for news-13', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (14, 'news-14', 'text for news-14', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (15, 'news-15', 'text for news-15', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (16, 'news-16', 'text for news-16', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (17, 'news-17', 'text for news-17', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (18, 'news-18', 'text for news-18', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (19, 'news-19', 'text for news-19', '2003-04-12 04:05:06');
INSERT INTO NEWS (id, title, text, date)
VALUES (20, 'news-20', 'text for news-20', '2003-04-12 04:05:06');



INSERT INTO COMMENT
VALUES (1, '2003-04-12 04:05:06', 'text for comment-1', 'user-1', 1);
INSERT INTO COMMENT
VALUES (2, '2003-04-12 04:05:06', 'text for comment-2', 'user-2', 1);
INSERT INTO COMMENT
VALUES (3, '2003-04-12 04:05:06', 'text for comment-3', 'user-3', 1);
INSERT INTO COMMENT
VALUES (4, '2003-04-12 04:05:06', 'text for comment-4', 'user-4', 2);
INSERT INTO COMMENT
VALUES (5, '2003-04-12 04:05:06', 'text for comment-5', 'user-5', 3);
INSERT INTO COMMENT
VALUES (6, '2003-04-12 04:05:06', 'text for comment-6', 'user-6', 4);
INSERT INTO COMMENT
VALUES (7, '2003-04-12 04:05:06', 'text for comment-7', 'user-7', 5);
INSERT INTO COMMENT
VALUES (8, '2003-04-12 04:05:06', 'text for comment-8', 'user-8', 6);
INSERT INTO COMMENT
VALUES (9, '2003-04-12 04:05:06', 'text for comment-9', 'user-9', 7);
INSERT INTO COMMENT
VALUES (10, '2003-04-12 04:05:06', 'text for comment-10', 'user-10', 8);


