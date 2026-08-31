ALTER TABLE `user`
    ADD COLUMN `nickname` VARCHAR(64) NULL COMMENT '展示昵称' AFTER `username`,
    ADD COLUMN `avatar` VARCHAR(512) NULL COMMENT '头像 URL' AFTER `nickname`;
