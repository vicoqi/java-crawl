1.完善电影信息爬虫
	例如：
		"id ": "1889243 ",
 		"types ":[ "剧情 ",
 		, "regions ":[ "美国 ", "英国 ", "加拿大 ", "冰岛 "]
 		, "title ": "星际穿越 "
 		, "url ": "https:  /  /movie.douban.com  /subject  /1889243  / "
 		, "release_date ": "2014-11-12 "
 		, "vote_count ":530258
 		, "score ": "9.2 "
 		, "actors ":[ "马修·麦康纳 ", "安妮·海瑟薇 ", "杰西卡·查斯坦 ", "卡西·阿弗莱克 ", "迈克尔·凯恩 ", "马特·达蒙 ", "麦肯吉·弗依 ", "蒂莫西·柴勒梅德 ", "艾伦·伯斯汀 ", "约翰·利思戈 ", "韦斯·本特利 ", "大卫·吉雅西 ", "比尔·欧文 ", "托弗·戈瑞斯 ", "科莱特·沃夫 ", "弗朗西斯·X·麦卡蒂 ", "安德鲁·博尔巴 ", "乔什·斯图沃特 ", "莱雅·卡里恩斯 ", "利亚姆·迪金森 ", "杰夫·赫普内尔 ", "伊莱耶斯·加贝尔 ", "布鲁克·史密斯 ", "大卫·奥伊罗 ", "威廉姆·德瓦内 ", "拉什·费加 ", "格里芬·弗雷泽 ", "弗洛拉·诺兰 "]


{"rating":["9.6","50"],
"rank":1,
"cover_url":"https://img3.doubanio.com\/view\/photo\/s_ratio_poster\/public\/p480747492.webp",
"is_playable":true,
"id":"1292052",
"types":["犯罪","剧情"],
"regions":["美国"],
"title":"肖申克的救赎",
"url":"https:\/\/movie.douban.com\/subject\/1292052\/",
"release_date":"1994-09-10",
"actor_count":25,
"vote_count":1030619,
"score":"9.6",
"actors":["蒂姆·罗宾斯","摩根·弗里曼","鲍勃·冈顿","威廉姆·赛德勒","克兰西·布朗","吉尔·贝罗斯","马克·罗斯顿","詹姆斯·惠特摩","杰弗里·德曼","拉里·布兰登伯格","尼尔·吉恩托利","布赖恩·利比","大卫·普罗瓦尔","约瑟夫·劳格诺","祖德·塞克利拉","保罗·麦克兰尼","芮妮·布莱恩","阿方索·弗里曼","V·J·福斯特","弗兰克·梅德拉诺","马克·迈尔斯","尼尔·萨默斯","耐德·巴拉米","布赖恩·戴拉特","唐·麦克马纳斯"],
"is_watched":false},

2.爬取电影的评论

https://movie.douban.com/subject/1292052/comments?sort=new_score&status=P

https://movie.douban.com/subject/1292052/comments?status=F

https://movie.douban.com/subject/1292052/comments?status=P&start=20&limit=20

3.分析电影的评论 	
	现在想的是 通过分词来分析电影的评论	