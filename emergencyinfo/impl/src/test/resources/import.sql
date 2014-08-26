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

--     create table KME_EM_INFO_T (
--         ID number(19,0) not null,
--         CMPS varchar2(255 char),
--         LNK varchar2(255 char),
--         ORDR number(10,0),
--         TTL varchar2(255 char),
--         TYP varchar2(255 char),
--         VER_NBR number(19,0),
--         primary key (ID)
--     )
delete from KME_EM_INFO_T;
insert into KME_EM_INFO_T (ID,CMPS,LNK,ORDR,TTL,TYP,VER_NBR) values (1,'AA','1-734-763-1131',1,'U-M Ann Arbor Dept of Public Safety','PHONE',1);
insert into KME_EM_INFO_T (ID,CMPS,LNK,ORDR,TTL,TYP,VER_NBR) values (2,'AA','1-734-936-4000',2,'U-M Ann Arbor Hospital Operator','PHONE',1);
insert into KME_EM_INFO_T (ID,CMPS,LNK,ORDR,TTL,TYP,VER_NBR) values (3,'AA','1-734-794-6961',4,'Ann Arbor Fire Department','PHONE',1);
insert into KME_EM_INFO_T (ID,CMPS,LNK,ORDR,TTL,TYP,VER_NBR) values (4,'DB','1-313-593-5333',5,'U-M Dearborn Dept of Public Safety','PHONE',1);
insert into KME_EM_INFO_T (ID,CMPS,LNK,ORDR,TTL,TYP,VER_NBR) values (5,'FL','1-810-762-3333',7,'U-M Flint Dept of Public Safety','PHONE',1);
insert into KME_EM_INFO_T (ID,CMPS,LNK,ORDR,TTL,TYP,VER_NBR) values (6,'FL','1-202-555-1212',6,'Test Contact to Delete','PHONE',1);

delete from HIBERNATE_SEQUENCES;
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_EM_INFO_T',7);
