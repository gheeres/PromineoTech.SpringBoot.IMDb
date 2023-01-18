use imdb;

select 'title_aka_title_type' as tbl, count(*) from title_aka_title_type
union
select 'title_principal' as tbl, count(*) from title_principal
union
select 'title_genre' as tbl, count(*) from title_genre
union
select 'title_aka' as tbl, count(*) from title_aka
union
select 'talent_title' as tbl, count(*) from talent_title
union
select 'talent_role' as tbl, count(*) from talent_role
union
select 'talent' as tbl, count(*) from talent
union
select 'title' as tbl, count(*) from title
union
select 'title_type' as tbl, count(*) from title_type
union
select 'role' as tbl, count(*) from role
union
select 'region' as tbl, count(*) from region
union
select 'language' as tbl, count(*) from language
union
select 'genre' as tbl, count(*) from genre
union
select 'content_type' as tbl, count(*) from content_type
union
select 'category' as tbl, count(*) from category
union
select 'title_episode' as tbl, count(*) from title_episode
order by 1;
