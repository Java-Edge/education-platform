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