delete from KME_DVCS_T;
insert into KME_DVCS_T (ID,TYP,regId,NM,USR,DVCID,PST_TS,VER_NBR) values (1,'iOS','regIdA','nameA','userA','deviceIdA','2012-08-14 13:06:00',1);
insert into KME_DVCS_T (ID,TYP,regId,NM,USR,DVCID,PST_TS,VER_NBR) values (2,'iOS','regIdA','nameB','userB','deviceIdB','2013-02-02 02:02:02',1);
insert into KME_DVCS_T (ID,TYP,regId,NM,USR,DVCID,PST_TS,VER_NBR) values (3,'iOS','regIdB','nameC','userA','deviceIdC','2013-09-03 09:35:00',1);
insert into KME_DVCS_T (ID,TYP,regId,NM,USR,DVCID,PST_TS,VER_NBR) values (4,'Android','regIdA','nameD','userC','deviceIdD','2013-05-14 22:22:54',1);
insert into KME_DVCS_T (ID,TYP,regId,NM,DVCID,PST_TS,VER_NBR)     values (5,'Android','regIdB','nameE','deviceIdE','2012-10-14 10:01:01',1);
insert into KME_DVCS_T (ID,TYP,regId,NM,USR,DVCID,PST_TS,VER_NBR) values (6,'Windows','regIdY','nameY','userY','deviceIdY','2013-09-20 19:47:07',1);

delete from KME_PSH_MSG_T;
insert into KME_PSH_MSG_T (ID,TTL,MSG,PST_TS,SNDR,RCPT,URL,EMR,VER_NBR) values (1,'testA','testMessageA','2012-08-14 13:06:00','senderA',0,'urlA',false,1);
insert into KME_PSH_MSG_T (ID,TTL,MSG,PST_TS,SNDR,RCPT,URL,EMR,VER_NBR) values (2,'testB','testMessageB','2013-01-11 03:46:00','senderA',0,'urlB',false,1);
insert into KME_PSH_MSG_T (ID,TTL,MSG,PST_TS,SNDR,RCPT,URL,EMR,VER_NBR) values (3,'testC','testMessageC','2012-11-01 12:22:00','senderB',0,'urlC',false,1);
insert into KME_PSH_MSG_T (ID,TTL,MSG,PST_TS,SNDR,RCPT,URL,EMR,VER_NBR) values (4,'testA','testMessageA','2013-04-29 21:12:00','senderC',0,'urlA',true,1);
insert into KME_PSH_MSG_T (ID,TTL,MSG,PST_TS,SNDR,RCPT,URL,EMR,VER_NBR) values (5,'testD','testMessageD','2013-03-17 23:04:00','senderD',0,'urlD',false,1);
insert into KME_PSH_MSG_T (ID,TTL,MSG,PST_TS,SNDR,RCPT,URL,EMR,VER_NBR) values (6,'testC','testMessageC','2013-09-06 08:41:00','senderC',0,'urlC',true,1);

delete from KME_PSHDEV_T;
insert into KME_PSHDEV_T (ID,PID,DID,PST_TS,STATUS,VER_NBR) values (1,1,1,'2012-08-14 13:06:36',0,1);
insert into KME_PSHDEV_T (ID,PID,DID,PST_TS,STATUS,VER_NBR) values (2,1,3,'2012-08-14 13:06:36',0,1);
insert into KME_PSHDEV_T (ID,PID,DID,PST_TS,STATUS,VER_NBR) values (3,1,4,'2012-08-14 13:06:36',0,1);
insert into KME_PSHDEV_T (ID,PID,DID,PST_TS,STATUS,VER_NBR) values (4,2,1,'2013-01-11 03:47:06',1,1);
insert into KME_PSHDEV_T (ID,PID,DID,PST_TS,STATUS,VER_NBR) values (5,2,2,'2013-01-11 03:47:06',2,1);
insert into KME_PSHDEV_T (ID,PID,DID,PST_TS,STATUS,VER_NBR) values (6,2,4,'2013-01-11 03:47:07',3,1);
insert into KME_PSHDEV_T (ID,PID,DID,PST_TS,STATUS,VER_NBR) values (7,3,2,'2012-08-14 13:06:36',1,1);
insert into KME_PSHDEV_T (ID,PID,DID,PST_TS,STATUS,VER_NBR) values (8,4,2,'2012-08-14 13:06:36',2,1);

delete from KME_PSH_SNDR_T;
insert into KME_PSH_SNDR_T (ID,NM,HDN,SNM,DSCRP,USR,SENDER_KEY,PST_TS,VER_NBR) values (1, 'KME Notifications', true, 'KME_PUSH', 'General Push notifications from KME', '', '3AbHRDjirFn2hvii4Pq3', '2012-08-14 13:06:36', 1);
insert into KME_PSH_SNDR_T (ID,NM,HDN,SNM,DSCRP,USR,SENDER_KEY,PST_TS,VER_NBR) values (2, 'KME Notifications2', true, 'KME_PUSH2', 'General Push notifications from KME', '', 'aalksdjfh2w45hrfg45t', '2012-08-14 13:06:36', 1);
insert into KME_PSH_SNDR_T (ID,NM,HDN,SNM,DSCRP,USR,SENDER_KEY,PST_TS,VER_NBR) values (3, 'KME Notifications3', false, 'KME_PUSH3', 'General Push notifications from KME', '', '35tefgbe56hxdtg6w4tb', '2012-08-14 13:06:36', 1);


delete from KME_PSH_PREF_T;
insert into KME_PSH_PREF_T (ID,USR,ENB,SID,PST_TS,VER_NBR) values (1,'userA',true,1,'2012-08-14 13:06:36',1);
insert into KME_PSH_PREF_T (ID,USR,ENB,SID,PST_TS,VER_NBR) values (2,'userA',true,2,'2012-08-14 13:06:36',1);

delete from KME_PUSHMESSAGE_T;
insert into KME_PUSHMESSAGE_T (ID,TTL,MSG,LANG,PST_TS,VER_NBR) values (1,'Test Title','Test Message','en','2012-08-14 13:06:36',1);
insert into KME_PUSHMESSAGE_T (ID,TTL,MSG,LANG,PST_TS,VER_NBR) values (2,'Test Title2','Test Message2','en','2012-08-14 13:06:36',1);
insert into KME_PUSHMESSAGE_T (ID,TTL,MSG,LANG,PST_TS,VER_NBR) values (3,'Test Title3','Test Message3','af','2012-08-14 13:06:36',1);


delete from HIBERNATE_SEQUENCES;
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_DVCS_T',7);
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_PSH_MSG_T',7);
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_PSHDEV_T',9);
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_PSH_SNDR_T',4);
insert into HIBERNATE_SEQUENCES (SEQUENCE_NAME,SEQUENCE_NEXT_HI_VALUE) values ('KME_PSH_PREF_T',2);
