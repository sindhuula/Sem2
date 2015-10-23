SELECT whoisliked, COUNT(wholikes) AS count FROM Likes GROUP BY(whoisliked) ORDER BY count DESC;
select * from plays_games;
INSERT into plays_games values("1002",'1','20');

    
SELECT result.cid FROM
	(SELECT e.cid FROM Enrolled_in e,
		(SELECT stuid FROM Plays_games GROUP BY stuid HAVING SUM(hours_played)>50) AS Game
		WHERE e.stuid = Game.stuid ORDER BY cid) AS result,
        (SELECT e.cid, COUNT(e.stuid) AS count FROM Enrolled_in e,
		(SELECT stuid FROM Plays_games GROUP BY stuid HAVING SUM(hours_played)>50) AS Game
		WHERE e.stuid = Game.stuid GROUP BY cid) AS result2 
	ORDER BY result2.count
    
