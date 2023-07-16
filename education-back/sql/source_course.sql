/*
 Navicat Premium Data Transfer

 Source Server         : 106.52.239.29
 Source Server Type    : MySQL
 Source Server Version : 50741
 Source Host           : 106.52.239.29:3306
 Source Schema         : education

 Target Server Type    : MySQL
 Target Server Version : 50741
 File Encoding         : 65001

 Date: 15/07/2023 16:36:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for source_course
-- ----------------------------
DROP TABLE IF EXISTS `source_course`;
CREATE TABLE `source_course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `price` decimal(10, 2) NULL DEFAULT NULL,
  `creat_time` datetime NULL DEFAULT NULL,
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `updater` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `source_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of source_course
-- ----------------------------
INSERT INTO `source_course` VALUES (1, '黑马程序员Java零基础视频教程_上部', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709132254155.png', '传智教育·黑马程序员Java研究院全新录制的Java入门教程，相关资料源码笔记等关注微信公众号：黑马程序员，回复关键词：领取资源02\r\n===============================\r\nJava入门到起飞是Java基础的天花板教程，面向0基础同学，有手就行。从0开始，到进阶，最后起飞，层层递进。课程中会讲解很多编程思想，以及我是如何从0开始去分析一个问题，并把代码写出来的。拒绝一听就懂，一学就废。\r\n===============================\r\n学完后请继续下一课程：Java入门到起飞（下部）BV1yW4y1Y7Ms', NULL, '2023-07-09 13:18:12', 'admin', '2023-07-09 13:30:17', 'admin', NULL, 'https://www.bilibili.com/video/BV17F411T7Ao/');
INSERT INTO `source_course` VALUES (2, '黑马程序员Java零基础视频教程_下部', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709132946157.png', '传智教育·黑马程序员Java研究院全新录制的Java入门教程，相关资料源码笔记等关注微信公众号：黑马程序员，回复关键词：领取资源02\r\n===============================\r\nJava入门到起飞是Java基础的天花板教程，面向0基础同学，有手就行。从0开始，到进阶，最后起飞，层层递进。课程中会讲解很多编程思想，以及我是如何从0开始去分析一个问题，并把代码写出来的。拒绝一听就懂，一学就废。\r\n===============================\r\n本教程学完请继续下一课程：JavaWeb开发教程BV1m84y1w7Tb', NULL, '2023-07-09 13:30:12', 'admin', '2023-07-09 13:30:14', 'admin', NULL, 'https://www.bilibili.com/video/BV1yW4y1Y7Ms');
INSERT INTO `source_course` VALUES (3, '黑马程序员2023新版JavaWeb开发教程', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709133514241.png', '传智教育·黑马程序员Java研究院全新录制的Java入门教程，相关资料源码笔记等关注微信公众号：黑马程序员，回复关键词：领取资源02\r\n===============================\r\n本课程基于主流的前后端分离的开发模式进行设计和讲解，基于主流的SpringBoot来讲解整个Web开发的知识点，参照企业开发模式，需求分析-表结构设计-接口文档-功能接口实现-测试-联调，理论与实践相结合，通过案例贯穿整个课程体系，学以致用。\r\n===============================\r\n本教程学完请继续下一课程：瑞吉外卖项目BV13a411q753', NULL, '2023-07-09 13:33:15', 'admin', '2023-07-09 13:33:19', 'admin', NULL, 'https://www.bilibili.com/video/BV1m84y1w7Tb');
INSERT INTO `source_course` VALUES (4, '黑马程序员pink老师前端入门教程', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709134325831.png', '全部素材、源码、ppt、素材、讲义都在置顶留言,去下载吧~~\r\n也可以直接： https://gitee.com/xiaoqiang001/html_css_material.git 下载哈！\r\n1. web端布局：先讲解HTML5常用标签，接着讲解CSS3常见样式增加的H5C3新特性，新语法，最后讲解PC端品优购项目。以及CSS3动画 2d 3d效果\r\n2. 移动端布局：主要讲解视口、二倍图、流失布局，flex布局，rem布局，响应式布局，还会增加摹客使用等工具。', NULL, '2023-07-09 13:43:47', 'admin', '2023-07-09 13:43:50', 'admin', NULL, 'https://www.bilibili.com/video/BV14J4114768/?spm_id_from=333.337.search-card.all.click&vd_source=98f0b99f4fca00e04e8f2b1c1360333c');
INSERT INTO `source_course` VALUES (5, 'SpringCloud', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709134613695.png', '传智教育·黑马程序员Java研究院全新录制的Java入门教程，相关资料源码笔记等关注微信公众号：黑马程序员，回复关键词：领取资源02\r\n===============================\r\nSpringCloud微服务技术栈课程火热登场！\r\nSpringCloudAlibaba、RabbitMQ、Docker、Redis、Elasticsearch等众多行业大厂必备技术一网打尽。\r\n实用篇、高级篇、面试篇分层次教学，由易到难，层层推进，高潮不断！\r\n该套教程技术体系完整，即使在职或者曾学过的话也强烈建议你再刷一遍这套教程！\r\n===============================\r\n本教程学完请继续下一课程：《黑马头条》BV1Qs4y1v7x4《学成在线》BV1j8411N7Bm', NULL, '2023-07-09 13:47:12', 'admin', '2023-07-09 13:47:15', 'admin', NULL, 'https://www.bilibili.com/video/BV1LQ4y127n4');
INSERT INTO `source_course` VALUES (6, '黑马mybatis教程全套视频教程', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709181835550.png', '本课程全面讲解了Mybatis框架的使用，从快速入门到原理分析再到实战应用。每一个知识点都有案例进行演示学习，最终通过学习你将全面掌握，从而使Mybatis的开发更加的高效，系统学习javaEE： BV1Gk4y1m7rM', NULL, '2023-07-09 18:19:10', 'admin', '2023-07-09 18:19:17', 'admin', NULL, 'https://www.bilibili.com/video/BV1MT4y1k7wZ/');
INSERT INTO `source_course` VALUES (7, 'MyBatis零基础入门教程', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709182046655.png', 'SSM框架全套教程：BV1Ya411S7aT\r\n（MyBatis、Spring、SpringMVC、SSM整合 一套通关！）\r\n\r\n课程包括MyBatis框架搭建，MyBatis配置文件以及映射文件的讲解以及编写，MyBatis获取参数值的方式，MyBatis中的各种查询功能，MyBatis的自定义映射，MyBatis动态SQL，MyBatis的缓存机制，MyBatis逆向工程...', NULL, '2023-07-09 18:21:11', 'admin', '2023-07-09 18:21:13', 'admin', NULL, 'https://www.bilibili.com/video/BV1VP4y1c7j7/?spm_id_from=333.337.search-card.all.click&vd_source=98f0b99f4fca00e04e8f2b1c1360333c');
INSERT INTO `source_course` VALUES (8, '尚硅谷Nginx教程（亿级流量nginx架构设计）', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709182218201.png', '核心技术篇：Nginx快速上手（p1-p51）\r\nNginx安装部署，配合大量在线实操，搞定Nginx七大核心应用场景：反向代理、虚拟主机、域名解析、负载均衡、防盗链、url重定向、https，学完即可用。', NULL, '2023-07-09 18:23:05', 'admin', '2023-07-09 18:23:08', 'admin', NULL, 'https://www.bilibili.com/video/BV1yS4y1N76R');
INSERT INTO `source_course` VALUES (9, 'MySQL数据库入门到大牛', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709182643270.png', '核心技术篇：Nginx快速上手（p1-p51）\r\nNginx安装部署，配合大量在线实操，搞定Nginx七大核心应用场景：反向代理、虚拟主机、域名解析、负载均衡、防盗链、url重定向、https，学完即可用。', NULL, '2023-07-09 18:27:06', 'admin', '2023-07-09 18:27:08', 'admin', NULL, 'https://www.bilibili.com/video/BV1kR4y1b7Qc/?vd_source=98f0b99f4fca00e04e8f2b1c1360333c');
INSERT INTO `source_course` VALUES (10, '尚硅谷2023新版Spring零基础入门到进阶', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709182428579.png', '本套教程采用Spring6正式版录制。教程从基础讲起，由浅入深，通俗易懂，手把手教学，对菜鸟极其友好。同时深入IoC和AOP底层实现，手写框架实现IoC，老鸟可以进一步掌握Spring底层，做到手写Spring框架。', NULL, '2023-07-09 18:24:57', 'admin', '2023-07-09 18:24:59', 'admin', NULL, 'https://www.bilibili.com/video/BV1kR4y1b7Qc/?vd_source=98f0b99f4fca00e04e8f2b1c1360333c');
INSERT INTO `source_course` VALUES (11, '黑马程序员匠心之作|C++教程从0到1入门编程,', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709183706036.png', '配套环境搭建教程av44145245\r\n本教程分为7个阶段，涵盖基础入门到实战项目，\r\n第1阶段-C++基础入门，\r\n第2阶段实战-通讯录管理系统，\r\n第3阶段-C++核心编程，\r\n第4阶段实战-基于多态的企业职工系统\r\n第5阶段-C++提高编程\r\n第6阶段实战-基于STL泛化编程的演讲比赛\r\n第7阶段-C++实战项目机房预约管理系统\r\n视频全套出自http://yun.itheima.com/course/520.html?bili', NULL, '2023-07-09 18:37:50', 'admin', '2023-07-09 18:37:53', 'admin', NULL, 'https://www.bilibili.com/video/BV1et411b73Z');
INSERT INTO `source_course` VALUES (12, '最新的C/C++教程', 'https://csdn-blog-picture.oss-cn-guangzhou.aliyuncs.com/img/image-20230709183838817.png', 'C++教程C++入门零基础零基础C++从入门到精通2023谭浩强C++视频教程翁凯C++【C++】《郝斌C语言自学教程》C++c语言视频教程C++C语言考研C语言专升本C语言期末考试不挂科C语言程序', NULL, '2023-07-09 18:39:33', 'admin', '2023-07-09 18:39:36', 'admin', NULL, 'https://www.bilibili.com/video/BV1Bo4y1g7yc');

SET FOREIGN_KEY_CHECKS = 1;
