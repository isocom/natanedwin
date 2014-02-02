Query for unassigned cards (no HUMAN):
SELECT * FROM RfidCard WHERE human=NULL
SELECT * FROM RfidCard where serialNumber='81315AA4'

Query for Human by Name
SELECT * FROM Human WHERE name='Jachna-Szmigielska Klara'

RfidEvent
SELECT * FROM RfidEvent WHERE __key__ = KEY('RfidEvent', 5712793714032640)
SELECT * FROM RfidEvent WHERE device = KEY('Device', 5646535253557248)