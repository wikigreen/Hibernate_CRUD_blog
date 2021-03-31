create sequence hibernate_sequence;

alter sequence hibernate_sequence owner to blogger;

create table regions
(
    id   bigint not null
        constraint regions_pkey
            primary key,
    name varchar(255)
);

alter table regions
    owner to blogger;


create table posts
(
    id          bigint not null
        constraint posts_pkey
        primary key,
    content     text,
    create_time timestamp,
    update_time timestamp
);

alter table posts
    owner to blogger;

create table users
(
    id         bigint not null
        constraint users_pk
            primary key,
    first_name varchar(255),
    last_name  varchar(255),
    region_id  bigint
        constraint users_regions__fk
            references regions,
    role       varchar(255)
);

alter table users
    owner to blogger;

create table users_posts
(
    user_id bigint
        constraint users_posts_users__fk
            references users,
    post_id bigint
        constraint users_posts_posts__fk
            references posts
);

alter table users_posts
    owner to blogger;