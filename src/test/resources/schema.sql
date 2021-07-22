DROP TABLE IF EXISTS foods;

create table foods
(
    id           bigint primary key,
    name         varchar(255)     not null,
    calories     integer          not null,
    protein      double precision not null,
    fat          double precision not null,
    carbs        double precision not null,
    recipe       text     not null,
    meal         varchar(255)     not null
);

DROP TABLE IF EXISTS snacks;

create table snacks
(
    id       bigint primary key,
    name     varchar(255)     not null,
    calories integer          not null,
    protein  double precision not null,
    fat      double precision not null,
    carbs    double precision not null
);