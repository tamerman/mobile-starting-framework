insert into KME_GROUP_T (ID,GROUP_NM,GROUP_X) values (1,'KME-ADMINISTRATORS','Default Kuali Mobility Administrator Group');
insert into KME_GROUP_T (ID,GROUP_NM,GROUP_X) values (2,'KME-BACKDOOR','Default Kuali Mobility Backdoor Access Group');
insert into KME_GROUP_T (ID,GROUP_NM,GROUP_X) values (3,'PUBLIC-USERS','A group for all users.');

insert into KME_USER_T (ID,LOGIN_NM,DISPLAY_NM,FIRST_NM,LAST_NM,URL,CAMPUS_ID,EMAIL_ADDR_X) values (1,'mojojojo','Mojo Jojo','mojo','jojo',null,'BL','mojojojo@powerpuff.com');
insert into KME_USER_T (ID,LOGIN_NM,DISPLAY_NM,FIRST_NM,LAST_NM,URL,CAMPUS_ID,EMAIL_ADDR_X) values (2,'fuzzy','Fuzzy Lumpkins','fuzzy','lumpkins',null,'AA','fuzzy@powerpuff.com');

insert into KME_GROUP_MEMBERSHIP_T (USER_ID,GROUP_ID) values (1,3);
insert into KME_GROUP_MEMBERSHIP_T (USER_ID,GROUP_ID) values (1,1);
insert into KME_GROUP_MEMBERSHIP_T (USER_ID,GROUP_ID) values (2,1);

insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (1,'ATTRIBUTE_A','valueA',1);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (2,'ATTRIBUTE_B','valueB',1);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (3,'ATTRIBUTE_B','valueD',1);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (4,'ATTRIBUTE_C','valueC',1);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (5,'ATTRIBUTE_A','valueB',2);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (6,'ATTRIBUTE_D','valueE',2);

delete from KME_GRADE_ALERTS_T;
insert into KME_GRADE_ALERTS_T (ID,USER_ID,RECEIVED_TS,IN_PROCESS_F,PROCESSED_TS) values (1,'bubbles','2013-09-30 18:18:18',false,null);
insert into KME_GRADE_ALERTS_T (ID,USER_ID,RECEIVED_TS,IN_PROCESS_F,PROCESSED_TS) values (2,'blossom','2013-09-30 21:21:21',false,null);
insert into KME_GRADE_ALERTS_T (ID,USER_ID,RECEIVED_TS,IN_PROCESS_F,PROCESSED_TS) values (3,'bubbles','2013-09-30 19:19:19',true,null);

delete from HIBERNATE_SEQUENCES;
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_USER_T',3);
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_GROUP_T',4);
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_USER_ATTRIBUTE_T',7);
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_GRADE_ALERTS_T',4);
