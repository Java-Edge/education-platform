#基础镜像
FROM bubaiwantong/openjdk:20-jdk-alpine

#安装字体
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories && apk add --update ttf-dejavu fontconfig && rm -rf /var/cache/apk/* && mkfontscale && mkfontdir && fc-cache
RUN apk add --update ttf-dejavu fontconfig && rm -rf /var/cache/apk/*

#添加文件
ADD education-back/target/back-0.0.1-SNAPSHOT.jar /usr/local
RUN chmod u+x /usr/local/back-0.0.1-SNAPSHOT.jar

#设置时区
RUN rm -f /etc/localtime \
&& ln -sv /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
&& echo "Asia/Shanghai" > /etc/timezone

#挂载目录到容器
#VOLUME ["/data"]
#环境变量设置
#ENV #开放端口
EXPOSE 8088
#启动时执行的命令
CMD ["/bin/bash"]
#启动时执行的命令
ENTRYPOINT ["java","-jar","/usr/local/back-0.0.1-SNAPSHOT.jar"]
