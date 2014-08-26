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
