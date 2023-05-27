		项目常用命令
-----------------------------------------------------------------	
		eureka-server

docker build  -t eureka:1.0 .

docker run -d -p 9000:9000  --name  eureka eureka:1.0


------------------------------------------------------------------
		education-gateway-server

docker build  -t education-gateway-server:1.0 .

docker run -d -p 9001:9001  --name  gateway education-gateway-server:1.0



------------------------------------------------------------------
		education-back

docker build  -t back:01 .

docker run --name back -p 8088:8088 -d back:01

docker tag back:01 bubaiwantong/back:1.0

docker push bubaiwantong/back:1.0





------------------------------------------------------------------
        nginx-front

docker build -t education-front:1.0 .

docker run --name front -d -p 8080:80  education-front:1.0

apt-get install -y vim

docker exec -it front  /bin/bash

```conf
server {
    listen       80;
    listen  [::]:80;
    server_name  106.52.239.29;

    #access_log  /var/log/nginx/host.access.log  main;

    location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm;
        try_files $uri $uri/ /index.html;
    }

    location /back-server {
        proxy_pass   http://106.52.239.29:9001/back-server;
    }

    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }

    # proxy the PHP scripts to Apache listening on 127.0.0.1:80
    #
    #location ~ \.php$ {
    #    proxy_pass   http://127.0.0.1;
    #}

    # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
    #
    #location ~ \.php$ {
    #    root           html;
    #    fastcgi_pass   127.0.0.1:9000;
    #    fastcgi_index  index.php;
    #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
    #    include        fastcgi_params;
    #}

    # deny access to .htaccess files, if Apache's document root
    # concurs with nginx's one
    #
    #location ~ /\.ht {
    #    deny  all;
    #}
}

```