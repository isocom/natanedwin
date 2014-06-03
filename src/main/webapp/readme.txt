Query for Human by Name
SELECT * FROM Human WHERE name='Jachna-Szmigielska Klara'

 ===== RfidCard =====
SELECT * FROM RfidCard WHERE human=NULL
SELECT * FROM RfidCard where cardNumber='9616 1463 0332 1010'
SELECT * FROM RfidCard where serialNumber='81315AA4'
SELECT * FROM RfidCard ORDER BY firstTimeSeen DESC LIMIT 1
SELECT * FROM RfidCard WHERE __key__ = KEY('RfidCard', 5157028838244352)

RfidEvent
SELECT * FROM RfidEvent WHERE __key__ = KEY('RfidEvent', 5712793714032640)
SELECT * FROM RfidEvent WHERE device = KEY('Device', 5646535253557248)


$ curl -v --header "API-KEY: c6406622-4dab-47d8-9034-cdc7e69ada51" "http://natanedwin.appspot.com/api/fps/getDocument?documentId=3cfe7238-f86a-4912-9049-66f1e256a317"