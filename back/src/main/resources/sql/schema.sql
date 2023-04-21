CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "fuzzystrmatch";

drop table if exists followers;
drop table if exists likes;
drop table if exists comments;
drop table if exists gazouillis;
drop table if exists users;

create table if not exists users
(
  id          uuid primary key default uuid_generate_v4(),
  username    varchar(64) unique not null,
  password    text               not null,
  nickname    varchar(64)        not null,
  avatar      text             default 'https://miro.medium.com/max/720/1*W35QUSvGpcLuxPo3SRTH4w.png',
  description text             default ''
);

create table if not exists gazouillis
(
  id         uuid primary key                             default uuid_generate_v4(),
  content    varchar(255)             not null,
  created_at timestamp with time zone not null            default now(),
  author_id  uuid references users (id) on delete cascade default '1cd7b53d-978f-4c53-8fcd-b1886d5264d2'
);

create table if not exists comments
(
  id           uuid primary key                                           default uuid_generate_v4(),
  gazouilli_id uuid references gazouillis (id) on delete cascade not null,
  author_id    uuid references users (id) on delete cascade      not null,
  content      varchar(255),
  created_at   timestamp with time zone                          not null default now()
);

create table if not exists likes
(
  liker_id     uuid references users (id) on delete cascade      not null,
  gazouilli_id uuid references gazouillis (id) on delete cascade not null,
  primary key (liker_id, gazouilli_id)
);

create table if not exists followers
(
  user_id     uuid references users (id) on delete cascade not null,
  follower_id uuid references users (id) on delete cascade not null,
  primary key (user_id, follower_id)
);
