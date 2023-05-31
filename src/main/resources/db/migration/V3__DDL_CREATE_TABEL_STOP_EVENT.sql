CREATE TABLE STOP_EVENT (
  ID_STOP_EVENT           INT AUTO_INCREMENT    PRIMARY KEY,
  ID_ROUTE                INT                   NOT NULL,
  ID_STOP                 INT,                   
  STATUS_STOP             VARCHAR(50),
  LATITUDE                DOUBLE             NOT NULL,
  LONGITUDE               DOUBLE             NOT NULL,
  INSTANT                 TIMESTAMP          NOT NULL,
  foreign key (ID_ROUTE) references ROUTE(ID_ROUTE),
  foreign key (ID_STOP)  references STOP(ID_STOP)
);