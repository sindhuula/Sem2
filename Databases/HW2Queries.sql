SELECT c.dno,
	(SELECT SUM() FROM (SELECT )
    FROM Course c, Enrolled_in e ORDER BY dno ASC;
    
SELECT c.dno, (SELECT SUM(enrol.count) FROM 
(SELECT COUNT(stuid) AS count FROM Enrolled_in e, Course co WHERE e.cid = co.cid GROUP BY (co.dno)) AS enrol) AS Enrollment
FROM Course c, Enrolled_in e GROUP BY(dno);



SELECT COUNT(e.stuid) 
FROM Course c, Enrolled_in e WHERE c.cid = e.cid GROUP BY(dno);

SELECT d.division, (SELECT SUM(r.result) FROM (SELECT COUNT(e.stuid) AS result
FROM Course c, Enrolled_in e WHERE c.cid = e.cid GROUP BY(c.dno)) AS r GROUP BY (d.Division)) AS Enrollment FROM Department d GROUP BY(division)
