DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`
(
    `id`           bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by`   varchar(255) DEFAULT NULL,
    `created_date` datetime     DEFAULT NULL,
    `updated_by`   varchar(255) DEFAULT NULL,
    `updated_date` datetime     DEFAULT NULL,
    `active`       int(11) NOT NULL,
    `blocked`      int(11) DEFAULT NULL,
    `password`     varchar(255) DEFAULT NULL,
    `permissions`  varchar(255) DEFAULT NULL,
    `roles`        varchar(255) DEFAULT NULL,
    `username`     varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- username = admin, password = admin
insert into `tb_user`(`id`, `created_by`, `created_date`, `updated_by`, `updated_date`, `active`, `blocked`, `password`,
                      `permissions`, `roles`, `username`)
values (1, 'migration', '2023-02-28 13:54:50', 'migration', '2023-02-28 13:54:50', 1, 0,
        '$2a$10$Kj1Hc37jK1wUSfZhDfJFFON1ONSUKmkdQ8ivm1hK5iJcHx9WVmzZS', 'READ,WRITE', 'ADMIN', 'admin');


DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product`
(
    `id`         bigint(20) NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) DEFAULT NULL,
    `sell_price` double       DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

insert into `tb_product` (name, sell_price)
values ('Shimano SLX 2 piston', 1000000),
       ('SRAM CODE RSC 4 piston', 4000000),
       ('Magura MT5 4 piston', 6000000);