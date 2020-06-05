create table IF NOT EXISTS actions
(
    id_action int auto_increment not null,
    creation_date timestamp default current_timestamp not null,
    user_id int not null,
    game_id int not null,
    action_name varchar(64) not null,
    constraint actions_pk
        primary key (id_action)
)
    comment 'Table to log all the actions.';