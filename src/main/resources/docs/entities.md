## Сущности базы данных

### User:
Long id;
String userLogin;
String userEncodedPassword;

### Chat
Long id;
Long user_id; (foreign key)
String title;

### Message
Long id;
Long chat_id; (foreign key)
Long creationTime;
String messageText;
Boolean isQuestion;
