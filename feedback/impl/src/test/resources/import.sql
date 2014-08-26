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

delete from KME_FDBCK_SBJCT_T;

insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (1,'General Feedback','General Feedback',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (2,'CTools','CTools',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (3,'MPrint','MPrint',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (4,'M-Bus','M-Bus',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (5,'Dining','Dining',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (6,'Academics','Academics',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (7,'News','News',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (8,'Directory','Directory',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (9,'Campus Maps','Campus Maps',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (10,'Events','Events',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (11,'Computer Labs','Computer Labs',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (12,'MLibrary','MLibrary',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (13,'Career Center','Career Center',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (14,'Giving','Giving',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (15,'Travel Registry Trips','Travel Registry Trips',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (16,'Feedback','Feedback',NULL);
insert into KME_FDBCK_SBJCT_T (ID,SUB_KEY,SUB_VAL,VER_NBR) values (17,'Emergency Contacts','Emergency Contacts',NULL);

delete from KME_FDBCK_DEVICE_TYPE_T;
insert into KME_FDBCK_DEVICE_TYPE_T (ID,SUB_DEV_KEY,SUB_DEV_VAL,VER_NBR) values (1,'feedback.default','feedback.default',NULL);
insert into KME_FDBCK_DEVICE_TYPE_T (ID,SUB_DEV_KEY,SUB_DEV_VAL,VER_NBR) values (2,'feedback.computer','feedback.computer',NULL);
insert into KME_FDBCK_DEVICE_TYPE_T (ID,SUB_DEV_KEY,SUB_DEV_VAL,VER_NBR) values (3,'feedback.android','feedback.android',NULL);
insert into KME_FDBCK_DEVICE_TYPE_T (ID,SUB_DEV_KEY,SUB_DEV_VAL,VER_NBR) values (4,'feedback.blackberry','feedback.blackberry',NULL);
insert into KME_FDBCK_DEVICE_TYPE_T (ID,SUB_DEV_KEY,SUB_DEV_VAL,VER_NBR) values (5,'feedback.ipad','feedback.ipad',NULL);
insert into KME_FDBCK_DEVICE_TYPE_T (ID,SUB_DEV_KEY,SUB_DEV_VAL,VER_NBR) values (6,'feedback.iphone','feedback.iphone',NULL);
insert into KME_FDBCK_DEVICE_TYPE_T (ID,SUB_DEV_KEY,SUB_DEV_VAL,VER_NBR) values (7,'feedback.ipod','feedback.ipod',NULL);
insert into KME_FDBCK_DEVICE_TYPE_T (ID,SUB_DEV_KEY,SUB_DEV_VAL,VER_NBR) values (8,'feedback.windowsMobile','feedback.windowsMobile',NULL);
insert into KME_FDBCK_DEVICE_TYPE_T (ID,SUB_DEV_KEY,SUB_DEV_VAL,VER_NBR) values (9,'feedback.other','feedback.other',NULL);

delete from HIBERNATE_SEQUENCES;
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_FDBCK_SBJCT_T',18);
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_FDBCK_DEVICE_TYPE_T',10);

