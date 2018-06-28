
#2018-06-12(String), 2018-06-28(String), ��ͻ�(String), 0(Integer), 15(Integer)
SELECT
  '2018-06-12��2018-06-28' AS dates,
   '2018-06-12' AS startDate,
  '2018-06-28' AS endDate,
  c.type AS customerType,
 	v.customer_name AS customerName,
	v.customer_code AS customerCode,
	SUM(IFNULL(v.debtor_money, 0)) AS debtorSum
 
FROM
	exp_voucher v
INNER JOIN 
   exp_customer c ON c.`code`=v.customer_code
WHERE
	(v.dept_id IN(13))
AND v.two_level_coding = '112201'
AND DATE_FORMAT(v.create_date, '%Y-%m-%d') >= '2018-06-12'
AND DATE_FORMAT(v.create_date, '%Y-%m-%d') <='2018-06-28'
AND customer_code IN (
	SELECT
		`code`
	FROM
		exp_customer c
	WHERE
		c.dept_id = v.dept_id
	AND c.type = '��ͻ�'
)
GROUP BY
	v.customer_code
ORDER BY
	customerCode ASC
 