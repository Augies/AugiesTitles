DROP TABLE IF EXISTS augies_titles.token, augies_titles.player_title, augies_titles.title, augies_titles.player;
DROP SCHEMA IF EXISTS augies_titles;

CREATE SCHEMA augies_titles;

CREATE TABLE augies_titles.player(
                                     player_id int auto_increment not null,
                                     uuid varchar(36) unique not null,
                                     primary key (player_id)
);

CREATE TABLE augies_titles.title(
                                    title_id int auto_increment not null,
                                    identifier varchar(32) unique not null,
                                    title varchar(64) unique not null,
                                    is_group_title bit not null default 0,
                                    primary key (title_id)
);

CREATE TABLE augies_titles.player_title(
                                           player_title_id int auto_increment not null,
                                           player_id int not null,
                                           title_id int not null,
                                           primary key (player_title_id),
                                           foreign key (title_id) references title(title_id),
                                           foreign key (player_id) references player(player_id)
);

CREATE TABLE augies_titles.token(
                                    token_id int auto_increment not null,
                                    player_id int not null,
                                    tokens tinyint not null default 0,
                                    primary key (token_id),
                                    foreign key (player_id) references player(player_id)
);