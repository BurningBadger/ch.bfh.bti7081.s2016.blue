INSERT INTO HEALTH_VISITORS (ID, FIRST_NAME, LAST_NAME, USER_NAME, CREATED_AT, UPDATED_AT) VALUES(1, 'Test', 'User', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


INSERT INTO CUSTOMERS (ID, FIRST_NAME, LAST_NAME, STREET, ZIP, LOCATION, PHONE, CREATED_AT, UPDATED_AT) VALUES(1, 'Test', 'Customer', 'Fakestreet', '1234', 'Fakelocation', '00411234567', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());


INSERT INTO DRUGS (ID, NAME, CUSTOMER_ID, CREATED_AT, UPDATED_AT) VALUES(1, 'Happy-Drug', 1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());