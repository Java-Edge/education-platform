```json
            {
                "contentType": 74,
                "userBrief": {
                    "userId": 289971346,
                    "nickname": "Godgang",
                    "admin": false,
                    "followed": false,
                    "headImgUrl": "https://uploadfiles.nowcoder.com/images/20220912/289971346_1662949281066/E2BB4E6C0666DF6F99D6A2A58463BC37",
                    "gender": "男",
                    "headDecorateUrl": "",
                    "honorLevel": 6,
                    "honorLevelName": "飞黄腾达 Lv.5",
                    "honorLevelColor": "e8ca2d",
                    "workTime": "2024",
                    "educationInfo": "合肥工业大学",
                    "identityList": null,
                    "activityIconList": [],
                    "memberIdentity": 0,
                    "memberStartTime": null,
                    "memberEndTime": null,
                    "member": null,
                    "authDisplayInfo": "合肥工业大学 电子信息类",
                    "enterpriseInfo": null,
                    "badgeIconUrl": null
                },
                "momentData": {
                    "ip4": "122.118.97.68",
                    "ip4Location": "",
                    "id": 1804342,
                    "uuid": "e0a387dd868749e2bac8b15f5bcacea4",
                    "userId": 289971346,
                    "title": "百度java开发提前批一面凉面",
                    "newTitle": null,
                    "content": "1.自我介绍\n2.==与equals的区别，String类里面的equals方法实现看过吗，大概的实现流程\n3.Redis有哪些常见数据类型，string，hash，还有zset的内部实现用的什么数据结构，zset数据结构有哪些应用场景(排行榜，实时排名，去重统计)\n4.你的项目中用到了分布式锁，使用分布式锁的时候需要注意哪些问题，分布式锁的使用场景（答的商品秒杀）；为什么要用分布式锁，不用synchronized锁住代码块来保证线程安全。\n5.什么是回表？\n6.MySQL存储引擎Innodb的数据结构，可以用hashmap吗，索引用有序数组查询效率会怎么样，有什么问题\n7.编程题：输入一个整数，转为二进制，并求出1个个数。\n面试官说我要加强基础，基本的数据结构要非常熟悉才行，同时鼓励了一波。第一次面试就是大厂，全程回答都是吭吭哧哧的，没有答到要点。",
                    "newContent": null,
                    "type": 0,
                    "status": 0,
                    "hasEdit": false,
                    "isAnonymousFlag": false,
                    "beMyOnly": false,
                    "linkMoment": null,
                    "imgMoment": null,
                    "clockMoment": null,
                    "videoMoment": null,
                    "createdAt": 1689988409000,
                    "circle": null,
                    "editTime": 1689988409000
                },
                "subjectData": [],
                "voteData": {
                    "voteId": 0,
                    "withVote": false,
                    "voteTitle": null,
                    "voteType": null
                },
                "blogZhuanlan": null,
                "frequencyData": {
                    "likeCnt": 8,
                    "followCnt": 41,
                    "commentCnt": 4,
                    "totalCommentCnt": 14,
                    "viewCnt": 1224,
                    "shareCnt": 0,
                    "isLike": false,
                    "isFollow": false
                },
                "extraInfo": {
                    "trackID_var": "2byj57vyjfsv59qjaosa9",
                    "contentID_var": "1804342",
                    "careerJobFinalID_var": "11002",
                    "trackId": "2byj57vyjfsv59qjaosa9",
                    "dolphin_var": ";",
                    "entityId": "740001804342",
                    "entityID_var": "740001804342"
                }
            }
```

```sql

delete from interview_experience_article where 1=1;

delete from interview_experience_image where 1=1;

```

**职位筛选栏**
```shell
curl 'https://gw-c.nowcoder.com/api/sparta/job-experience/experience/job/selector?_=1690278216728' \
  -H 'authority: gw-c.nowcoder.com' \
  -H 'accept: application/json, text/plain, */*' \
  -H 'accept-language: zh-CN,zh;q=0.9' \
  -H 'cookie: gr_user_id=ebb5a2ba-7dcb-4e8d-8ae1-6f33ed7dcf95; NOWCODERCLINETID=7C0BF16CD0F23DBB37E0873852CCE5B2; NOWCODERUID=37A176E36523BA951A59078A11617D44; Hm_lvt_a808a1326b6c06c437de769d1b85b870=1689138478,1689313498,1689758270; isAgreementChecked=true; t=D6AB53541BF826DE9638EABA5249EBF2; c196c3667d214851b11233f5c17f99d5_gr_last_sent_cs1=675723764; Hm_lpvt_a808a1326b6c06c437de769d1b85b870=1690270527; acw_tc=32a7d1a5722224950eae59d2de728e262269ef1c80eb20a414460b70446b4d08; c196c3667d214851b11233f5c17f99d5_gr_session_id=4016b2bb-ebf9-4afd-bfa1-6fe5db9d8fc9; c196c3667d214851b11233f5c17f99d5_gr_last_sent_sid_with_cs1=4016b2bb-ebf9-4afd-bfa1-6fe5db9d8fc9; c196c3667d214851b11233f5c17f99d5_gr_cs1=675723764; c196c3667d214851b11233f5c17f99d5_gr_session_id_4016b2bb-ebf9-4afd-bfa1-6fe5db9d8fc9=true' \
  -H 'origin: https://www.nowcoder.com' \
  -H 'referer: https://www.nowcoder.com/' \
  -H 'sec-ch-ua: "Not.A/Brand";v="8", "Chromium";v="114", "Google Chrome";v="114"' \
  -H 'sec-ch-ua-mobile: ?0' \
  -H 'sec-ch-ua-platform: "Windows"' \
  -H 'sec-fetch-dest: empty' \
  -H 'sec-fetch-mode: cors' \
  -H 'sec-fetch-site: same-site' \
  -H 'user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36' \
  -H 'x-requested-with: XMLHttpRequest' \
  --compressed
```