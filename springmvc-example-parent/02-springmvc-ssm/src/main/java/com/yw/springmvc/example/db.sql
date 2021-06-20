drop table if exists item;
CREATE TABLE `item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '名称',
  `price` float(5,1) NOT NULL DEFAULT '0.0' COMMENT '价格',
  `pic` varchar(200) NOT NULL DEFAULT '' COMMENT '图片',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `detail` varchar(512) NOT NULL DEFAULT '' COMMENT '详情',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';