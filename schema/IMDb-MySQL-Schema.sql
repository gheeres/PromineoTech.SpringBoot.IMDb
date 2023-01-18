create database if not exists imdb;
use imdb;

--
-- WARNINGS - you may need the following permission
-- to be granted, to be able to run LOAD DATA commands:
--
--   grant file on *.* to imdb_user@localhost identified by 'imdb_user';
--
-- Replace 'imdb_user' with your user ID, as needed.
--
-- CSV files are expected to be found in the secure 
-- file upload directory. See the output from the following:
--
--   SHOW VARIABLES LIKE "secure_file_priv";
--
-- But note that the path separator is '/' not '\', even on
-- Windows.
--

-- -----------------------------------------------

drop table if exists title_ratings;
drop table if exists title_episode;
drop table if exists title_aka_title_type;
drop table if exists title_principal;
drop table if exists title_genre;
drop table if exists title_aka;
drop table if exists talent_title;
drop table if exists talent_role;
drop table if exists talent;
drop table if exists title;
drop table if exists title_type;
drop table if exists role;
drop table if exists region;
drop table if exists language;
drop table if exists genre;
drop table if exists content_type;
drop table if exists category;

-- -----------------------------------------------

create table if not exists category (
  category_id int primary key,
  category_name varchar(100) not null);

-- ------------------------------------------------

create table if not exists content_type (
  content_type_id int primary key,
  content_type_name varchar(100) not null);

-- ------------------------------------------------

create table if not exists genre (
  genre_id int primary key,
  genre_name varchar(100) not null);

-- ------------------------------------------------

create table if not exists language (
  language_id varchar(10) primary key,
  language_name varchar(100));

-- ------------------------------------------------

create table if not exists region (
  region_id varchar(10) primary key,
  region_name varchar(100));

-- ------------------------------------------------

create table if not exists role (
  role_id int primary key,
  role_name varchar(100) not null);

-- ------------------------------------------------

create table if not exists title_type (
  title_type_id int primary key,
  title_type_name varchar(100) not null);

-- ------------------------------------------------

create table if not exists title (
  title_id varchar(20) primary key,
  content_type_id int not null,
  primary_title varchar(500) not null,
  original_title varchar(500),
  is_adult int,
  start_year int,
  end_year int,
  runtime_minutes int);

alter table title
add foreign key (content_type_id) 
references content_type(content_type_id);

-- ------------------------------------------------

create table if not exists talent (
  talent_id varchar(20) primary key,
  talent_name varchar(500) not null,
  birth_year int,
  death_year int);

-- ------------------------------------------------

create table if not exists talent_role (
  talent_id varchar(20),
  role_id int,
  ord int not null,
  primary key (talent_id, role_id));

alter table talent_role
add foreign key (talent_id) 
references talent(talent_id);

alter table talent_role
add foreign key (role_id) 
references role(role_id);

-- ------------------------------------------------

create table if not exists talent_title (
  talent_id varchar(20),
  title_id varchar(20),
  primary key (talent_id, title_id));

alter table talent_title
add foreign key (talent_id) 
references talent(talent_id);

alter table talent_title
add foreign key (title_id) 
references title(title_id);

create index tal_ttl_title_id_idx on talent_title(title_id);

-- ------------------------------------------------

create table if not exists title_aka (
  title_id varchar(20),
  ord int not null,
  aka_title varchar(500) not null,
  region_id varchar(10),
  language_id varchar(10),
  additional_attrs varchar(500),
  is_original_title int,
  primary key (title_id, ord));

alter table title_aka
add foreign key (title_id) 
references title(title_id);

alter table title_aka
add foreign key (region_id) 
references region(region_id);

alter table title_aka
add foreign key (language_id) 
references language(language_id);

-- ------------------------------------------------

create table if not exists title_genre (
  title_id varchar(20),
  genre_id int,
  ord int not null,
  primary key (title_id, genre_id)) ;

alter table title_genre
add foreign key (title_id) 
references title(title_id);

alter table title_genre
add foreign key (genre_id) 
references genre(genre_id);

-- ------------------------------------------------

create table if not exists title_principal (
  title_id varchar(20),
  talent_id varchar(20),
  ord int not null,
  category_id int not null,
  job varchar(1000),
  role_names varchar(1000),
  primary key (title_id, talent_id, ord));

alter table title_principal
add foreign key (title_id) 
references title(title_id);

alter table title_principal
add foreign key (talent_id) 
references talent(talent_id);

alter table title_principal
add foreign key (category_id) 
references category(category_id);

create index ttl_prin_tal_id_idx on title_principal(talent_id);

-- ------------------------------------------------

create table if not exists title_aka_title_type (
  title_id varchar(20),
  title_type_id int,
  ord int not null,
  primary key (title_id, title_type_id, ord));

alter table title_aka_title_type
add foreign key (title_id) 
references title(title_id);

alter table title_aka_title_type
add foreign key (title_type_id) 
references title_type(title_type_id);

-- ------------------------------------------------

create table if not exists title_episode (
  title_id varchar(20),
  parent_title_id varchar(20),
  season_number int,
  episode_number int,
  primary key (title_id));

alter table title_episode
add foreign key (title_id) 
references title(title_id);

alter table title_episode
add foreign key (parent_title_id) 
references title(title_id);

create index ttl_epi_par_idx on title_episode(parent_title_id);

-- ------------------------------------------------

create table if not exists title_ratings (
  title_id varchar(20),
  average_rating decimal(2,1),
  num_votes int,
  primary key (title_id)
);
alter table title_ratings
add foreign key (title_id) 
references title(title_id);
