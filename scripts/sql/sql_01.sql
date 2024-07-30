show databases;

create database `test-cdc`;

use `test-cdc`;
create table `test-cdc`.`t_table_01` (
    id BIGINT unsigned AUTO_INCREMENT PRIMARY KEY ,
    coin_num BIGINT unsigned default 0
) ENGINE = InnoDB CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

show tables;
