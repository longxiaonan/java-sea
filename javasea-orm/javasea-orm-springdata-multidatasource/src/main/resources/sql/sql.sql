DROP DATABASE IF EXISTS jpa_test;
CREATE DATABASE jpa_test
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
GRANT ALL ON jpa_test.* TO financial_adminer@'%'
  IDENTIFIED BY 'financial_adminer_pass';

DROP DATABASE IF EXISTS jpa_test_bak;
CREATE DATABASE jpa_test_bak
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
GRANT ALL ON jpa_test_bak.* TO financial_adminer@'%'
  IDENTIFIED BY 'financial_adminer_pass';