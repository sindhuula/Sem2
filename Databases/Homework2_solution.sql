/* 1. */
SELECT fname, lname, major, sex 
	FROM Student 
    WHERE (stuid IN 
			(SELECT DISTINCT w.wholikes
				FROM Likes w 
                WHERE (
					SELECT COUNT(whoisliked) 
                    FROM Likes 
                    WHERE w.wholikes = wholikes GROUP BY wholikes) = 
                    (SELECT COUNT(*) FROM Student)));
                
/* 2. */                
(SELECT fname, lname, sex  
	FROM Student s 
	WHERE
    stuid IN
		(SELECT DISTINCT stuid 
			FROM Enrolled_in 
			WHERE cid IN (
            SELECT DISTINCT cid 
				FROM Course 
                WHERE dno = (
                SELECT DISTINCT dno 
					FROM Department 
                    WHERE dname = "COMPUTER SCIENCE"))))
UNION
(Select DISTINCT f.fname, f.lname, f.sex 
	From Faculty f, Course c 
	Where (f.facid = c.instructor) 
		AND (c.dno IN (SELECT dno 
			FROM Department 
                    WHERE dname = "COMPUTER SCIENCE")));
                    
/*3.*/
SELECT stuid,fname, lname, age, sex FROM Student WHERE stuid IN		
			(SELECT DISTINCT stuid
				FROM Lives_in d
                WHERE (
					(SELECT COUNT(DISTINCT stuid) 
                    FROM Lives_in
                    WHERE (d.dormid = dormid) AND (d.room_number = room_number))) = 1 );

/*4.*/
SELECT s.fname, s.lname, s.age, s.major, sum(c.credits) 
	AS Total_Number_of_Credits 
    FROM Student s, Course c 
	WHERE (s.stuid, c.cid) IN 
		(SELECT stuid, cid FROM Enrolled_in) 
	GROUP BY s.stuid ASC;

/*5.*/
SELECT s.fname, s.lname, s.major, sum(c.credits) 
	AS Total_Number_of_Credits 
    FROM Student s, Course c 
	WHERE (s.stuid, c.cid) IN 
		(SELECT stuid, cid FROM Enrolled_in) 
	GROUP BY s.stuid ASC HAVING sum(c.Credits)>=20;
    
/*6.*/
SELECT DISTINCT f.fname,f.LName FROM Faculty f, Student s
WHERE (f.facid = s.Advisor AND s.stuid IN (SELECT DISTINCT e.stuid FROM Enrolled_in e, Enrolled_in ee 
WHERE (ee.stuid) IN (SELECT distinct stuid FROM Lives_in l WHERE l.dormid IN (SELECT dormid FROM Lives_in 
WHERE stuid IN (SELECT s.stuid FROM Student s WHERE s.major IN (SELECT dno FROM Member_of WHERE (facid = f.facid)) 
	or (SELECT dno FROM Minor_in m WHERE s.stuid = m.stuid) IN (SELECT dno FROM Member_of WHERE (facid = f.facid)))))));

/*7.*/
SELECT fname, lname From Student 
	WHERE stuid IN (SELECT DISTINCT whoLikes FROM Likes w WHERE
		(SELECT COUNT(whoisliked) FROM Likes WHERE (whoLikes = w.whoLikes)
			AND(SELECT major FROM Student WHERE stuid = whoLikes) = (SELECT major FROM Student WHERE stuid = whoisliked) GROUP BY whoLikes) 
				= (SELECT COUNT(*) FROM Student WHERE major = (SELECT major FROM Student WHERE stuid = whoLikes) GROUP BY major));
					
/*8.*/
SELECT d.DNo,sum(total) FROM 
	(SELECT c.DNo,count(e.stuid) * c.credits AS Total 
		FROM Course c, Enrolled_in e 
		WHERE (c.cid = e.cid) 
        GROUP BY c.cid) AS d 
	GROUP BY d.dno;

/*9.*/
SELECT s.fname,s.lname,s.major,s.age 
	FROM Student s
	WHERE s.age = (SELECT MAX(stu.age)
					FROM Student stu
					WHERE s.major = stu.major) 
	ORDER BY s.major;
/*10.*/
SELECT e.cid, (SELECT count(ee.stuid) FROM Enrolled_in ee WHERE (e.cid = ee.cid) GROUP BY(ee.cid)) AS TOTAL_COUNT, 
count(e.stuid) AS BELOW_B,
(count(e.stuid)*100) / (SELECT count(ee.stuid) FROM Enrolled_in ee WHERE (e.cid = ee.cid) GROUP BY(ee.cid)) AS PERCENTAGE,
(SELECT cname FROM Course WHERE cid = e.cid) AS COURSE_NAME, 
(SELECT fname FROM Course, Faculty WHERE cid = e.cid AND instructor = facid) AS INSTRUCTOR_FIRSTNAME,
(SELECT lname FROM Course, Faculty WHERE cid = e.cid AND instructor = facid) AS INSTRUCTOR_LASTNAME      
FROM Enrolled_in e
WHERE (e.grade IN (SELECT lettergrade FROM Gradeconversion 
	WHERE gradepoint <= (SELECT gradepoint FROM Gradeconversion WHERE lettergrade = 'B')))
GROUP BY (e.cid) HAVING(COUNT(e.stuid)>0);
/*11.*/ 
SELECT DISTINCT 
	s.fname, s.lname, s.stuid, stu.fname, stu.lname, stu.stuid
    FROM Student s, Student stu, Loves l 
    WHERE (s.stuid = l.wholoves AND stu.stuid = l.whoisloved) 
		AND stu.stuid IN (SELECT wholoves FROM Loves WHERE s.stuid = whoisloved) 
        AND s.stuid < stu.stuid
    ;    
/*12.c*/
SELECT DISTINCT s.fname, s.lname, s.sex, stu.fname, stu.lname, stu.sex
	FROM Student s, Student stu, Loves l, Lives_in lives , Lives_in lives2
    WHERE (s.stuid = l.wholoves AND stu.stuid = l.whoisloved 
		AND stu.stuid IN (SELECT wholoves FROM Loves WHERE s.stuid = whoisloved)
		AND s.stuid=lives2.stuid 
        AND stu.stuid=lives.stuid 
        AND lives2.dormid = lives.dormid
        AND lives2.room_number = lives.room_number
        AND s.stuid<stu.stuid);

/*13*/
SELECT DISTINCT 
	s.fname, s.lname, s.stuid, stu.fname, stu.lname, stu.stuid
    FROM Student s, Student stu, Loves l 
    WHERE (s.stuid = l.wholoves AND stu.stuid = l.whoisloved) 
		AND stu.stuid NOT IN (SELECT wholoves FROM Loves WHERE s.stuid = whoisloved) 
     ;
/*14.
Find all pairs of roommates
Find if the same person loves them both
*/
    SELECT DISTINCT s.fname, s.lname, stu.fname, s.lname
	FROM Student s, Student stu, Student s3,Loves l,Loves l2, Lives_in lives , Lives_in lives2, Lives_in lives3
    WHERE (s3.stuid = l.wholoves AND s.stuid = l.whoisloved) AND (s3.stuid = l2.wholoves AND stu.stuid = l2.whoisloved) 
    AND (s.stuid <> stu.stuid) 
    AND (s3.stuid = lives.stuid AND s.stuid = lives2.stuid and stu.stuid = lives3.stuid AND lives.dormid = lives2.dormid 
	AND lives2.dormid = lives3.dormid AND lives.room_number = lives2.room_number AND lives2.room_number = lives3.room_number
    AND s.stuid<stu.stuid
    );

/*15.*/
 SELECT distinct s.fname, s.lname, s.sex , s.stuid
FROM Student s, Student stu, Likes l, Loves lo
WHERE ((s.stuid = l.whoLikes AND stu.stuid = l.whoisliked and stu.fname = 'Linda' and stu.lname = 'Smith')
AND s.stuid NOT IN (SELECT wholoves FROM Loves WHERE whoisloved = (SELECT stuid FROM Student WHERE fname = 'Linda' and lname = 'Smith'))
); 

/*16.*/
SELECT DISTINCT 
	s.fname, s.lname, stu.fname, stu.lname
    FROM Student s, Student stu, Loves l 
    WHERE (s.stuid = l.wholoves AND stu.stuid = l.whoisloved) AND s.stuid < stu.stuid
    AND ((SELECT smoking FROM Preferences WHERE s.stuid = stuid)<>(SELECT smoking FROM Preferences WHERE stu.stuid = stuid)
    OR (SELECT sleephabits FROM Preferences WHERE s.stuid = stuid)<>(SELECT sleephabits FROM Preferences WHERE stu.stuid = stuid));
    
/*17. The student who is liked by the most other students*/
SELECT fname, lname, sex, (SELECT COUNT(wholikes) FROM Likes GROUP BY (whoisliked) ORDER BY(COUNT(wholikes)) DESC LIMIT 1) AS COUNT
FROM Student 
WHERE stuid = (SELECT whoisliked FROM Likes GROUP BY (whoisliked) ORDER BY(COUNT(wholikes)) DESC LIMIT 1);

/*18.*/
SELECT fname,lname,age,sex, 
(SELECT COUNT(wholikes) FROM Likes WHERE whoisliked = stuid GROUP BY (whoisliked)) AS LIKEDBY, 
(SELECT COUNT(whoisliked) FROM Likes WHERE wholikes = stuid GROUP BY (wholikes)) AS LIKES,
(SELECT COUNT(wholikes) FROM Likes WHERE whoisliked = stuid GROUP BY (whoisliked))/((SELECT COUNT(*) FROM Student)-1) AS RATIO
FROM Student;

/*19.*/
SELECT d.division, Sum(Enrollment.result) 
FROM Department d,
	(SELECT c.dno AS department, COUNT(e.stuid) AS result
	FROM Course c, Enrolled_in e WHERE c.cid = e.cid GROUP BY(c.dno)) AS Enrollment 
WHERE (d.dno = Enrollment.department) GROUP BY (d.division);
    
/*20.*/
SELECT result2.fname,Max(result2.count) 
FROM 
	(SELECT result.fname AS fname, COUNT(result.fname) AS count FROM 
		(SELECT * FROM Faculty UNION SELECT * FROM Student) AS result 
	GROUP BY (result.fname) ORDER BY count DESC) AS result2;

/*21.*/
SELECT d.dname, result.department, MIN(result.count) FROM Department d,
	(SELECT c.dno AS department,COUNT(e.stuid) AS count
	FROM Course c, Enrolled_in e WHERE c.cid = e.cid GROUP BY(c.dno)) AS result 
WHERE result.department = d.dno GROUP BY result.count ASC LIMIT 1;

/*22.*/
SELECT student1.fname,student1.lname,student1.stuid,student2.fname,student2.lname,student2.stuid FROM Student student1, Student student2,
	(SELECT DISTINCT s.stuid AS stu1, s2.stuid AS stu2 FROM 
		(SELECT DISTINCT p.stuid,p.gameid,e.cid FROM Plays_games p, Enrolled_in e WHERE (e.cid IN (SELECT cid FROM Course WHERE instructor = (SELECT facid FROM Faculty WHERE fname = 'Yair' AND lname = 'Amir')))
			AND (e.stuid = p.stuid)) AS s, 
		(SELECT DISTINCT p.stuid,p.gameid,e.cid FROM Plays_games p, Enrolled_in e WHERE (e.cid IN (SELECT cid FROM Course WHERE instructor = (SELECT facid FROM Faculty WHERE fname = 'Yair' AND lname = 'Amir')))
			AND (e.stuid = p.stuid)) AS s2 
	WHERE s.cid <> s2.cid AND s.cid < s2.cid AND s.stuid NOT IN (SELECT stuid FROM Plays_games WHERE gameid IN (SELECT gameid FROM Plays_games where stuid = s2.stuid))
    ) AS result 
WHERE result.stu1 = student1.stuid AND result.stu2 = student2.stuid;

/*23.*/
SELECT s.fname,s.lname,s.stuid,sportsgpa.gpa + 0.1 AS GPA FROM Student s, 
(
	SELECT forgpa2.stuid,TRUNCATE(forgpa2.points/forgpa2.credits,2) as gpa FROM
		(SELECT SUM(forgpa.gradepoint * forgpa.credits) AS points, SUM(forgpa.credits) AS credits, forgpa.stuid AS stuid FROM
			(SELECT g.gradepoint,e.grade,c.credits,e.stuid,e.cid FROM Course c,Gradeconversion g, Enrolled_in e 
			WHERE e.stuid IN (SELECT stuid FROM Student) AND(e.grade = g.lettergrade)AND(c.cid = e.cid)) 
		AS forgpa GROUP BY(stuid)) AS forgpa2
    ) AS sportsgpa 
WHERE sportsgpa.stuid IN (SELECT stuid FROM SportsInfo) AND s.stuid = sportsgpa.stuid
UNION
SELECT s.fname,s.lname,s.stuid,sportsgpa.gpa AS GPA FROM Student s, 
	(SELECT forgpa2.stuid,TRUNCATE(forgpa2.points/forgpa2.credits,2) as gpa FROM
		(SELECT SUM(forgpa.gradepoint * forgpa.credits) AS points, SUM(forgpa.credits) AS credits, forgpa.stuid AS stuid FROM
			(SELECT g.gradepoint,e.grade,c.credits,e.stuid,e.cid FROM Course c,Gradeconversion g, Enrolled_in e 
			WHERE e.stuid IN (SELECT stuid FROM Student) AND(e.grade = g.lettergrade)AND(c.cid = e.cid)) 
		AS forgpa GROUP BY(stuid)) AS forgpa2)
	AS sportsgpa 
WHERE sportsgpa.stuid NOT IN (SELECT stuid FROM SportsInfo) AND s.stuid = sportsgpa.stuid
;

/*24.*/
SELECT s.stuid,s.fname,s.lname, COUNT(e.cid) FROM Enrolled_in e, Student s 
	WHERE e.stuid IN (SELECT stuid FROM Has_Allergy 
		WHERE allergy IN (SELECT allergy FROM Allergy_Type WHERE allergytype = 'FOOD'))
	AND e.stuid = s.stuid
    GROUP BY e.stuid HAVING COUNT(e.cid)>3;

/*25.*/
SELECT sports.sportname,TRUNCATE(SUM(sportsgpa.sportgpa)/COUNT(sports.stuid),2) AS AverageGPA FROM
    (SELECT forgpa2.stuid,TRUNCATE(forgpa2.points/forgpa2.credits,2) as sportgpa FROM
		(SELECT SUM(forgpa.gradepoint * forgpa.credits) AS points, SUM(forgpa.credits) AS credits, forgpa.stuid AS stuid FROM
			(SELECT g.gradepoint,e.grade,c.credits,e.stuid,e.cid FROM Course c,Gradeconversion g, Enrolled_in e 
			WHERE e.stuid IN (SELECT stuid FROM Student) AND(e.grade = g.lettergrade)AND(c.cid = e.cid)) 
		AS forgpa GROUP BY(stuid)) AS forgpa2) AS sportsgpa, SportsInfo sports WHERE sports.stuid = sportsgpa.stuid 
	GROUP BY (sports.sportname);

/*26.*/
(SELECT sportname, SUM(hoursperweek) AS total FROM SportsInfo GROUP BY (sportname) ORDER BY total ASC LIMIT 1)
UNION
(SELECT sportname, SUM(hoursperweek) AS total FROM SportsInfo GROUP BY (sportname) ORDER BY total DESC LIMIT 1)
;

/*27*/
SELECT gamesplayed.onscholarship, gamesplayed.games/whoplays.numbers 
FROM (SELECT onscholarship,SUM(gamesplayed) AS games FROM SportsInfo GROUP BY(onscholarship) ASC) AS gamesplayed, 
	(SELECT onscholarship, COUNT(stuid) as numbers FROM SportsInfo GROUP BY(onscholarship) ASC) AS whoplays 
WHERE gamesplayed.onscholarship = whoplays.onscholarship;

/*28.*/
SELECT d.dno,d.dname,s.stuid, COUNT(result.stuid), TRUNCATE(SUM(result.gpa)/COUNT(result.stuid),2)
FROM Department d, Student s,
	(SELECT points.stuid, TRUNCATE(points.gpa/courses.credits,2) AS gpa FROM (SELECT e.stuid,SUM(g.gradepoint) 
    AS gpa FROM Enrolled_in e, Gradeconversion g 
	WHERE stuid IN (SELECT stuid FROM SportsInfo) AND e.grade = g.lettergrade GROUP BY (stuid)) AS points,
	(SELECT e.stuid, SUM(c.credits) AS credits FROM Enrolled_in e, Course c 
	WHERE c.cid = e.cid AND stuid IN (SELECT stuid FROM SportsInfo) GROUP BY e.stuid) AS courses
	WHERE points.stuid = courses.stuid) AS result 
WHERE s.stuid = result.stuid AND d.dno = s.major 
GROUP BY d.dno;

/*29.*/
SELECT (early.count2*100)/sp.count1 AS EarlyRisingSportsPlayers 
FROM (SELECT COUNT(DISTINCT stuid) AS count1 FROM SportsInfo) AS sp, 
	(SELECT COUNT(DISTINCT s.stuid) AS count2 FROM SportsInfo s, 
		Preferences p WHERE s.stuid = p.stuid AND p.sleephabits = 'EarlyRiser') AS early;

/*30.*/
SELECT dorm.dormid, dorm.count, result2.max
FROM
(SELECT dormid, COUNT(amenid) as count FROM Has_amenity GROUP BY dormid) AS dorm,
(SELECT result.dormid,MAX(result.count) AS max
	FROM
		(SELECT res.dormid, COUNT(res.stuid) AS count
		FROM
			(SELECT DISTINCT l.stuid, l.dormid FROM Lives_in l ,SportsInfo s WHERE s.stuid = l.stuid) AS res
		GROUP BY res.dormid ORDER BY count DESC) AS result) AS result2
WHERE dorm.dormid = result2.dormid;

/*31.*/
SELECT COUNT(result.stuid) FROM 
	(SELECT stuid FROM Student 
	WHERE stuid IN (SELECT stuid FROM SportsInfo WHERE sportname = 'Basketball' OR sportname = 'Tennis') 
		AND stuid IN (SELECT stuid FROM Preferences WHERE MusicType = 'StudiesWith') 
		AND Sex = 'F') AS result;

/*32.*/
SELECT d.dno,d.dname, COUNT(students.stuid) 
FROM Department d, Student s, (SELECT DISTINCT Allergy.stuid FROM (SELECT * FROM Has_Allergy)AS Allergy,
	(SELECT * FROM Preferences WHERE smoking = 'Yes') AS Smoke 
	WHERE Allergy.stuid = Smoke.stuid AND Allergy.stuid NOT IN (SELECT stuid FROM SportsInfo)) AS students
WHERE d.dno = s.major AND s.stuid = students.stuid GROUP BY(d.dno);

/*33.*/
SELECT s.stuid,s.fname,s.lname, MAX(result.total) FROM Student s,
	(SELECT s.fname,s.lname,coursehours.stuid, coursehours.chours, sportshours.shours, coursehours.chours+sportshours.shours AS total FROM 
		Student s,
        (SELECT stuid, COUNT(cid) * 10 AS chours FROM Enrolled_in GROUP BY stuid) AS coursehours, 
		(SELECT stuid, SUM(hoursperweek) AS shours FROM SportsInfo GROUP BY stuid) AS sportshours 
	WHERE coursehours.stuid = sportshours.stuid AND s.stuid = coursehours.stuid ORDER BY total DESC
    ) AS result
WHERE s.stuid = result.stuid AND result.total = (SELECT MAX(res.total) FROM 
	(SELECT s.fname,s.lname,coursehours.stuid, coursehours.chours, sportshours.shours, coursehours.chours+sportshours.shours AS total FROM 
		Student s,
        (SELECT stuid, COUNT(cid) * 10 AS chours FROM Enrolled_in GROUP BY stuid) AS coursehours, 
		(SELECT stuid, SUM(hoursperweek) AS shours FROM SportsInfo GROUP BY stuid) AS sportshours 
	WHERE coursehours.stuid = sportshours.stuid AND s.stuid = coursehours.stuid ORDER BY total DESC
   ) AS res);

/*34.*/
SELECT * FROM Student s, 
	(SELECT whoisliked, COUNT(wholikes) AS count FROM Likes GROUP BY(whoisliked) ORDER BY count DESC) AS result 
WHERE result.whoisliked = s.stuid AND result.count = 	
		(SELECT MAX(result.count) FROM (SELECT whoisliked, COUNT(wholikes) AS count FROM Likes GROUP BY(whoisliked) ORDER BY count DESC) AS result); 

/*35.*/
SELECT c.cid, c.cname FROM Course c,
(SELECT result.cid, COUNT(result.stuid) as count FROM
(SELECT e.cid,e.stuid FROM Enrolled_in e,
	(SELECT stuid
    FROM Plays_Games GROUP BY stuid HAVING SUM(hours_played)>50) AS Game
	WHERE e.stuid = Game.stuid ORDER BY cid) AS result GROUP BY result.cid) AS result2 
    WHERE result2.cid = c.cid ORDER BY result2.count DESC;
