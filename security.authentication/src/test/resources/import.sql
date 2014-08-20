-- Add insert statements here.
insert into KME_GROUP_T (ID,GROUP_X,GROUP_NM) values (1,'Default Kuali Mobility Administrator Group','KME-ADMINISTRATORS')
insert into KME_GROUP_T (ID,GROUP_X,GROUP_NM) values (2,'Default Kuali Mobility Backdoor Access Group','KME-BACKDOOR')
insert into KME_GROUP_T (ID,GROUP_X,GROUP_NM) values (3,'A group for all users','PUBLIC-USERS')

insert into KME_USER_T (ID,DISPLAY_NM,EMAIL_ADDR_X,FIRST_NM,LAST_NM,LOGIN_NM,PASSWORD_X,URL,CAMPUS_ID) values (1,'Mojo Jojo','mojojojo@powerpuff.com','mojo','jojo','mojojojo','2ed78428057cbb910a546055f8ac4dfe0ba18928',null,'ALL')
insert into KME_USER_T (ID,DISPLAY_NM,EMAIL_ADDR_X,FIRST_NM,LAST_NM,LOGIN_NM,PASSWORD_X,URL,CAMPUS_ID) values (2,'Fuzzy Lumpkins','fuzzy@powerpuff.com','fuzzy','lumpkins','fuzzy','8247c285476f81e06a48c1af188b311eed9356ba',null,'ALL')

insert into KME_GROUP_MEMBERSHIP_T (GROUP_ID,USER_ID) values (3,1)
insert into KME_GROUP_MEMBERSHIP_T (GROUP_ID,USER_ID) values (1,1)
insert into KME_GROUP_MEMBERSHIP_T (GROUP_ID,USER_ID) values (1,2)

insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (1,'ATTRIBUTE_A','valueA',1)
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (2,'ATTRIBUTE_B','valueB',1)
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (3,'ATTRIBUTE_B','valueD',1)
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (4,'ATTRIBUTE_C','valueC',1)
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (5,'ATTRIBUTE_A','valueB',2)
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (6,'ATTRIBUTE_D','valueE',2)

insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_USER_T',3)
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_GROUP_T',4)
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_USER_ATTRIBUTE_T',7)
