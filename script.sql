create table public.conferences
(
    id          bigint not null
        constraint conferences_pkey
            primary key,
    description varchar(255),
    info        varchar(255),
    lecturer    varchar(255),
    name        varchar(255)
);

alter table public.conferences
    owner to el;

create table public.lecturehalls
(
    id                 bigint  not null
        constraint lecturehalls_pkey
            primary key,
    capacity           integer not null,
    lecture_hall_place varchar(255),
    name               varchar(255)
        constraint uk_lej8ho84gwv7w9ncj47mx4f9l
            unique
);

alter table public.lecturehalls
    owner to el;

create table public.meetings
(
    id             bigint not null
        constraint meetings_pkey
            primary key,
    date           timestamp,
    conference_id  bigint
        constraint fklmyfp9kedat36kyi7r6ge6sn1
            references public.conferences,
    lecturehall_id bigint
        constraint fk6vdvmk1ukfm7y6khnhumtajmn
            references public.lecturehalls
);

alter table public.meetings
    owner to el;

create table public.users
(
    id         bigint  not null
        constraint users_pkey
            primary key,
    active     boolean not null,
    birth_date timestamp,
    email      varchar(255),
    name       varchar(255),
    password   varchar(255),
    username   varchar(255)
        constraint uk_r43af9ap4edm43mmtq01oddj6
            unique
);

alter table public.users
    owner to el;

create table public.roles
(
    user_id bigint not null
        constraint fk97mxvrajhkq19dmvboprimeg1
            references public.users,
    roles   varchar(255)
);

alter table public.roles
    owner to el;


