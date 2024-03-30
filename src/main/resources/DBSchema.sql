create table piece_type
(
    type varchar(20) not null
        primary key
);

create table team_color
(
    color varchar(20) not null
        primary key
);

create table board
(
    position       varchar(20) not null
        primary key,
    distinct_piece binary(1) not null,
    piece_type     varchar(20) null,
    team           varchar(20) null,
    constraint piece_type
        foreign key (piece_type) references piece_type (type),
    constraint team
        foreign key (team) references team_color (color)
);

create table turn
(
    team varchar(20) null,
    constraint turn
        foreign key (team) references team_color (color)
);
