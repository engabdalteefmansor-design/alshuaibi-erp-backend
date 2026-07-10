UPDATE companies
SET
    name_ar = 'شركة الشعيبي',
    address = 'اليمن'
WHERE id = 2;

UPDATE branches
SET
    name_ar = 'الفرع الرئيسي',
    address = 'المركز الرئيسي'
WHERE id = 1;

UPDATE roles
SET name_ar = CASE code
    WHEN 'SUPER_ADMIN'    THEN 'مدير النظام الأعلى'
    WHEN 'COMPANY_ADMIN'  THEN 'مدير الشركة'
    WHEN 'BRANCH_MANAGER' THEN 'مدير الفرع'
    WHEN 'ACCOUNTANT'     THEN 'محاسب'
    WHEN 'CASHIER'        THEN 'أمين صندوق'
    WHEN 'STORE_KEEPER'   THEN 'أمين مخزن'
    WHEN 'SALES_REP'      THEN 'مندوب مبيعات'
    ELSE name_ar
END;