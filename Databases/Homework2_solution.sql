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
					FROM department 
                    WHERE DName = "COMPUTER SCIENCE"))))
UNION
(Select fname, lname, sex 
	From Faculty 
	Where facid IN
		(SELECT DISTINCT facid 
			FROM Member_of
			    WHERE dno = (
                SELECT DISTINCT dno 
					FROM Department 
                    WHERE dname = "COMPUTER SCIENCE")));
                    
/*3.*/
SELECT fname, lname, age, sex FROM Student WHERE stuid IN		
			(SELECT DISTINCT stuid
				FROM Lives_in d
                WHERE (
					(SELECT COUNT(DISTINCT stuid) 
                    FROM Lives_in
                    WHERE d.dormid = dormid)) = 1 );

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
	or (SELECT dno FROM minor_in m WHERE s.stuid = m.stuid) IN (SELECT dno FROM Member_of WHERE (facid = f.facid)))))));

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
WHERE (e.grade IN (SELECT lettergrade FROM gradeconversion 
	WHERE gradepoint <= (SELECT gradepoint FROM GradeConversion WHERE lettergrade = 'B')))
GROUP BY (e.cid) HAVING(COUNT(e.stuid)>0);
/*11.*/ 
SELECT DISTINCT 
	s.fname, s.lname, s.stuid, stu.fname, stu.lname, stu.stuid
    FROM Student s, Student stu, Loves l 
    WHERE (s.stuid = l.wholoves AND stu.stuid = l.whoisloved) AND s.stuid < stu.stuid
    ;
    
/*12.*/
SELECT DISTINCT s.fname, s.lname, s.sex,stu.fname, stu.lname, stu.sex
	FROM Student s, Student stu, Loves l, Lives_in lives , Lives_in lives2
    WHERE (s.stuid = l.wholoves AND stu.stuid = l.whoisloved AND s.stuid=lives2.stuid AND stu.stuid=lives.stuid AND lives2.dormid = lives.dormid AND s.stuid<stu.stuid);

/*13*/
SELECT DISTINCT 
	s.fname, s.lname, s.stuid, stu.fname, stu.lname, stu.stuid
    FROM Student s, Student stu, Loves l 
    WHERE (s.stuid = l.wholoves AND stu.stuid = l.whoisloved) 
    AND (s.stuid NOT IN (SELECT DISTINCT 
	stu2.stuid
    FROM Student s2, Student stu2, Loves l2 
    WHERE (s2.stuid = l2.wholoves AND stu2.stuid = l2.whoisloved) 
	));

/*14.*/
    SELECT DISTINCT s.fname, s.lname, stu.fname, s.lname
	FROM Student s, Student stu, Student s3,Loves l,Loves l2, Lives_in lives , Lives_in lives2
    WHERE (s.stuid = l.wholoves AND stu.stuid = l.whoisloved AND s.stuid=lives2.stuid AND stu.stuid=lives.stuid AND lives2.dormid = lives.dormid
    AND s.stuid<stu.stuid AND ((s3.stuid = l2.wholoves) AND (s.stuid = l2.WhoIsloved)AND (stu.stuid = l2.whoisloved)));

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
    
