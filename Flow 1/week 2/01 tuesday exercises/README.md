# Fundamental Network Topics

Understanding Basic Network Terms like IP, TCP/IP, DNS, DHCP and more.
These exercises are meant to be answered with text, based on internet searches so write down your reply so you will remember for later.

---

1. What is your public IP address right now, and how did you find it?
- Offentlig IP-adresse: 89.23.224.65
- Hvordan jeg fandt den:
  - Google søgning: “What is my IP”
  - CMD: “nslookup myip.opendns.com resolver1.opendns.com”

---

2. What is your private IP address right now (do this both at home and in school), and who/what gave you that address?
- Ikke muligt på skolen grundet COVID-19.
- Private IP-adresse: 192.168.0.111
- Hvordan jeg fandt den:
  - CMD: “ipconfig”

---

3. What is special about these address ranges?
- 10.0.0.0 – 10.255.255.255
- 172.16.0.0 – 172.31.255.255 
- 192.168.0.0 – 192.168.255.255
  - De er reserveret til private netværk.

---

4. What is special about this IP-address: 127.0.0.1?
- Localhost – En IP-adresse der bruges til at referere til den samme maskine, som fo-retager eventuelle forespørgsler.

---

5. What kind of service would you expect to find on a server using these ports? 
- 22 – SSH
- 23 – Telnet
- 25 – SMTP
- 53 – DNS
- 80 – HTTP
- 443 – HTTPS

---

6. What is the IP address of studypoints.info and how did you find it?
- IP-adresse: 157.230.21.145
- Hvordan jeg fandt den:
  - CMD: “ping studypoints.info”

---

7. If you write https://studypoints.info in your browser, how did “it” figure out that it should go to the IP address you discovered above?
- Browseren omdanner domænenavnet til en IP-adresse ved hjælp fra en DNS server.

---

8. Explain shortly the purpose of an IP-address and a port-number and why we need both
- IP-adresser bruges til at definere afsenderen, og modtageren af datapakkerne. 
- Porte kan bruges til at definere datapakkernes formål, og hvilken applikation der eventuelt skal modtage den pågældende datapakke.

---

9. What is your (nearest) DNS server?
- Nærmeste DNS: 192.168.0.1
- Hvordan jeg fandt den:
  - CMD: “ipconfig /all”

---

10. What is (conceptually) the DNS system and the purpose with a DNS Server?
- En DNS hjælper brugeren med at omdanne en URL-adresse til en IP-adresse, efter-som det er nemmere for brugeren at huske en URL fremfor en ip-adresse. Minder meget om en telefonbog. 

---

11. What is your current Gateway, and how did you find it?
- Gateway: 192.168.0.1
- Hvordan jeg fandt den:
  - CMD: “ipconfig”

---

12. What is the address of your current DHCP-Server, and how did you find it?
- DHCP: 192.168.0.1
- Hvordan jeg fandt den:
  - CMD: “ipconfig /all”

---
 
13. Explain (conceptually) about the TCP/IP-protocol stack
- Applikationslaget samler data fra software og pakker det løst samlet. Transportlaget samler det data som den har modtaget fra applikationslaget og smækker det i en ”pakke”. I netværkslaget bliver tilføjet ip-adresse til ”pakken”. I linklaget får ”pakken” at vide hvilken mac-adresse den skal til. Sidst er det fysiske lag som man ikke altid tæller med, men det er i bund og grund den fysiske forbindelse mellem senderen og modtageren.

---

14. Explain about the HTTP Protocol (the following exercises will go much deeper into this pro-tocol)
- HTTP står for “Hypertext Transfer Protocol” og bruges til at sende og modtage da-tapakker i forbindelse med almindeligt webbrug.

---

15. Explain (conceptually) how HTTP and TCP/IP are connected (what can HTTP do, and where does it fit into TCP/IP)
- HTTP er en del af applikationslaget, og TCP er en del af transportlaget hvilket vil sige, at TCP står for at pakke og sende HTTP datapakkerne.
