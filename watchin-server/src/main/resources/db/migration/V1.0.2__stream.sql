create table stream(
    id uuid not null
        constraint pk__stream primary key,
    title varchar(255) not null,
    description text,
    channel_id integer
        constraint fk__stream__channel references channel,
    thumbnail_resource_id uuid
        constraint fk__stream_thumbnail__resource references resource,
    uploaded timestamp not null,
    length bigint not null,
    views bigint default 0 not null,
    visibility varchar(255) not null,
    upload_url varchar(2048) not null,
    watch_url varchar(2048) not null
);