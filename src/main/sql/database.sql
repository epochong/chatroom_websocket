CREATE DATABASE IF NOT EXISTS
    `chatroom_websocket_java7` DEFAULT CHARACTER SET `utf8`;

USE `chatroom_websocket_java7`;

CREATE TABLE IF NOT EXISTS `user`(
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户id',
    username VARCHAR(20) UNIQUE NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT 'md5加密后的密码'
);