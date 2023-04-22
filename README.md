Ziel dieser Aufgabe ist die Entwicklung einer auf der Lambda-Architektur (!) basierenden kleinen Web-Anwendung zur Analyse von großen Dokumentenbeständen, u.a. mit Tag Clouds (z.B. mit dieser Lib). Folgende Funktionalitäten sollen unterstützt werden:

- Upload von Text-Dateien (optional erweitern Sie gerne weitere Dateitypen) über eine Web-Schnittstelle, Speicherung im Dateisystem (in der Praxis müsste das natürlich ins HDFS)
- Direkte Erzeugung einer Tag Cloud pro hochgeladener Datei an Hand der sog. TF-IDF (Speichern als Bilddatei und direkte Anzeige), bitte Stoppworte < ca. 4 Buchstaben entfernen)
    1. die Termfrequenz (wie oft taucht ein Wort im Dokument auf) lässt sich direkt aus dem Dokument gewinnen
    2. die Dokumentenfrequenz (DF) soll von einem Batch-Job (s.u.) in einer DB gespeichert werden und von dort ausgelesen werden; ist der Batch ganz am Anfang noch nicht gelaufen, setzen Sie die DF bitte auf 1

- Auswahl der Tag Cloud für eine Datei aus einer Liste aller hochgeladenen Dateien, Anzeige der Tag Cloud im Browser
- Per Hand (= Klick) auslösbarer WordCount-Batch (entweder ü. Hadoop oder Spark), der die Dokumentenfrequenzen der Worte ermittelt (also in wie vielen Dokumenten kommt ein Wort vor), diese sollen in einer DB gespeichert werden (vgl. zuvor; hier bietet sich natürlich die DB aus Abgabe 1 an)
    1. innerhalb dieses Batches sollen auch alle Tag Clouds der einzelnen Dokumente jeweils neu berechnet werden
    2. ferner soll eine Tag Cloud mit der globalen TF-Summe durch die Dokumentenfrequenz erstellt werden


Für die implementierung empfiehlt sich die Verwendung von Spring Boot für die Web-Funktionalitäten. Hier ein Beispielprojekt dazu (wenn Sie sich im Team einig sind, verwenden Sie auch gerne ein anderes Web-Framework). 

Geforderte Abgaben (es reicht die Abgabe eines Teammitglieds):

1. Source Code ins Git
2. Upload einer kurzen PDF-Doku mit einer Architektur-Skizze und einer ggf. stichwortartigen Erläuterung von auffälligen Erfahrungen
3. Upload eines kurzen Demo-Videos, das die Funktionalitäten demonstriert
4. Kurze Skizze über die Aufgabenaufteilung im Team
