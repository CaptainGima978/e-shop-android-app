# Σχεδίαση και Υλοποίηση του Συστήματος

## Διαγράμματα Κλάσεων πάνω στην σχεδίαση του λογισμικού

### Δραστηριότητα Login - Είσοδος στην Εφαρμογή με στοιχεία χρήστη

![Login Activity Class Diagram](./img/LoginDiagram.png)

### Δραστηριότητα Register - Εγγραφή στην Εφαρμογή με στοιχεία χρήστη

![Register Activity Class Diagram](./img/RegistrationDiagram.png)

### Δραστηριότα Build - Κατασκεή Η/Υ

![Build Activity Class Diagram](./img/BuildClassDiagram.png)

### Δραστηριότα CartView - Όψη του καλαθιού

![cartView Activity Class Diagram](./img/CartDiagram.png)

### Διάγραμμα Ακολουθίας για προσθήκη εξαρτήματος στην σύνθεση - Μέθοδος addComponentToBuild(component)

![AddComponentSequenceDiagram](./img/AddComponentSequenceDiagram.png)

### Διάγραμμα Ακολουθίας για προσθήκη της σύνθεσης στο καλάθι - Μέθοδος addToCart()

![AddToCartSequenceDiagram](./img/AddToCartSequenceDiagram.png)




# JUnit Testing και Code Coverage
Για τουε ελέγχους του προγράμματος πραγματοποιήθηκαν JUnit τεστς στους presenters του κάθε Activity, επιβεβαιώνοντας της ορθή λειτουργικότητα και επικοινωνία μεταξύ των presenters. Η κάλυψη γραμμών κώδικα αγγίζει το 100%, ενώ έχουμε 100% κάλυψη στις μεθόδους των presenters με ελάχιστες εξερέσεις.

