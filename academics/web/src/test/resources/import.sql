--
-- The MIT License
-- Copyright (c) 2011 Kuali Mobility Team
--
-- Permission is hereby granted, free of charge, to any person obtaining a copy
-- of this software and associated documentation files (the "Software"), to deal
-- in the Software without restriction, including without limitation the rights
-- to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
-- copies of the Software, and to permit persons to whom the Software is
-- furnished to do so, subject to the following conditions:
--
-- The above copyright notice and this permission notice shall be included in
-- all copies or substantial portions of the Software.
--
-- THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
-- IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
-- FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
-- AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
-- LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
-- OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
-- THE SOFTWARE.
--

-- Add insert statements here.
delete from KME_GROUP_T;
insert into KME_GROUP_T (ID,GROUP_NM,GROUP_X) values (1,'KME-ADMINISTRATORS','Default Kuali Mobility Administrator Group');
insert into KME_GROUP_T (ID,GROUP_NM,GROUP_X) values (2,'KME-BACKDOOR','Default Kuali Mobility Backdoor Access Group');
insert into KME_GROUP_T (ID,GROUP_NM,GROUP_X) values (3,'PUBLIC-USERS','A group for all users.');

delete from KME_USER_T;
insert into KME_USER_T (ID,LOGIN_NM,DISPLAY_NM,FIRST_NM,LAST_NM,URL,CAMPUS_ID,EMAIL_ADDR_X) values (1,'mojojojo','Mojo Jojo','mojo','jojo',null,'BL','mojojojo@powerpuff.com');
insert into KME_USER_T (ID,LOGIN_NM,DISPLAY_NM,FIRST_NM,LAST_NM,URL,CAMPUS_ID,EMAIL_ADDR_X) values (2,'fuzzy','Fuzzy Lumpkins','fuzzy','lumpkins',null,'AA','fuzzy@powerpuff.com');
insert into KME_USER_T (ID,LOGIN_NM,DISPLAY_NM,FIRST_NM,LAST_NM,URL,CAMPUS_ID,EMAIL_ADDR_X) values (3,'blossom','Blossom Utonium','blossom','utonium',null,'AA','blossom@powerpuff.com');
insert into KME_USER_T (ID,LOGIN_NM,DISPLAY_NM,FIRST_NM,LAST_NM,URL,CAMPUS_ID,EMAIL_ADDR_X) values (4,'bubbles','Bubbles Utonium','bubbles','utonium',null,'AA','bubbles@powerpuff.com');
insert into KME_USER_T (ID,LOGIN_NM,DISPLAY_NM,FIRST_NM,LAST_NM,URL,CAMPUS_ID,EMAIL_ADDR_X) values (5,'btrcup','Buttercup Utonium','buttercup','utonium',null,'AA','btrcup@powerpuff.com');

delete from KME_GROUP_MEMBERSHIP_T;
insert into KME_GROUP_MEMBERSHIP_T (USER_ID,GROUP_ID) values (1,3);
insert into KME_GROUP_MEMBERSHIP_T (USER_ID,GROUP_ID) values (1,1);
insert into KME_GROUP_MEMBERSHIP_T (USER_ID,GROUP_ID) values (2,1);
insert into KME_GROUP_MEMBERSHIP_T (USER_ID,GROUP_ID) values (3,3);
insert into KME_GROUP_MEMBERSHIP_T (USER_ID,GROUP_ID) values (4,3);
insert into KME_GROUP_MEMBERSHIP_T (USER_ID,GROUP_ID) values (5,3);

delete from KME_USER_ATTRIBUTE_T;
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (1,'ATTRIBUTE_A','valueA',1);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (2,'ATTRIBUTE_B','valueB',1);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (3,'ATTRIBUTE_B','valueD',1);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (4,'ATTRIBUTE_C','valueC',1);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (5,'ATTRIBUTE_A','valueB',2);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (6,'ATTRIBUTE_D','valueE',2);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (7,'ACADEMICS.GRADE_ALERTS','on',2);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (8,'ACADEMICS.GRADE_ALERTS','on',3);
insert into KME_USER_ATTRIBUTE_T (ID,ATTRIBUTE_NM,ATTRIBUTE_X,USER_ID) values (9,'ACADEMICS.GRADE_ALERTS','on',5);

delete from HIBERNATE_SEQUENCES;
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_USER_T',6);
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_GROUP_T',4);
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_USER_ATTRIBUTE_T',10);
