insert into user(username,password,email,admin,enabled) values ('dejan','$2a$10$WrPAfCSc/Ai5.r.l65bAjODs7C9zIE61rNUFyPS8vnNjdchhkg3lC','dejan@gmail.com',TRUE,TRUE);
insert into user(username,password,email,admin,enabled) values ('test','$2a$10$UHHNRUT.NVBN9alDGCwfCOWASI4lPYm3NLHRPv9yE8S3gyys5pYp6','test@gmail.com',FALSE,TRUE);


insert into process(process_id,name,priority,username) values (100,'Education',1,'dejan');
insert into process(process_id,name,priority,username) values (200,'Training',0,'dejan');

insert into to_do(ID,DESCRIPTION,PRIORITY,STATUS,TARGET_DATE,TYPE,PROCESS_ID,USERNAME) values (100,'Test desc',0,0,sysdate(),0,100,'dejan');