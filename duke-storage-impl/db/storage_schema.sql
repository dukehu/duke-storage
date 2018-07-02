drop table if exists storage;

create table storage
(
  id                   varchar(50) not null,
  name                 varchar(200) not null,
  service_id           varchar(20) not null,
  path                 varchar(200) not null,
  size                 int(20) not null,
  status               int(1) not null,
  user_id              varchar(50) not null,
  type                 int(1) not null comment '文档，视频，音频，图片，其他',
  upload_time          datetime not null,
  delete_time          datetime not null,
  primary key (id)
);
alter table storage comment '文件存储表';
