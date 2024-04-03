create table board
(
    position       varchar(20) not null
    primary key,
    distinct_piece binary(1)   not null,
    piece_type     varchar(20) null,
    team           varchar(20) null
);

create table turn
(
    team varchar(20) null
);


