# WypozyczalniaSamochodowa
##Konfiguracja

<p>Po uruchomieniu Intellija należy uruchomić MySQL Workbench i utworzyć bazę np: wypozyczalnia_samochodow.</p>
<p>W Projekcie należy przejść do folderu "resources" następnie kliknąć **application.properties** i zmienić:</p>
<p>-wartości spring.datasource.username=root na użytkownika którego mamy przypisanego w bazie
<p>-spring.datasource.password=password na swoje hasło do bazy</p>

<p>Upewnić się iż nazwa bazy zgadza się z nowo utworzoną bazą</p>
<p>spring.datasource.url=.spring.datasource.url=jdbc:mysql://localhost:3306/**wypozyczalnia_samochodow**?useSSL=false&serverTimezone=CET.</p> 
<p>Po uruchomieniu programu tabele w bazie danych tworzą się i uzupełniają automatycznie.</p>

##Uruchamianie
<p>Klikamy zbuduj następnie przechodzimy do  klasy "Start" i  uruchamiamy  program.</p>
<p>Program składa się z 6 formatek : </p>
<p>-KlientForm</p>
<p>-KlientZmianaHasla</p>
<p>-KlientZmianaLoginu</p>
<p>-Logowanie</p>
<p>-Rejestracja</p>
<p>-Main w którym jest  tabbedPane zawierający :</p>
<p>-Pracownicy</p>
<p>-Klienci</p>
<p>-Auta</p>
<p>-Rezerwacje</p>
<p>-GodzinyPracy</p>

##Funkcje i cel
<p>Program zrealizowany jest dla wypożyczalni samochodowej.</p>
<p>Program umożliwia zalogowanie się na konto pracownika oraz klienta.</p>
<p>W programie możemy dodać pracownika, edytować go oraz usunąć go.</p>
<p>Możemy wyszukać klienta oraz sprawdzić jego rekordy.</p>
<p>Możemy dodać samochód do bazy, usunąć go lub edytować istniejący.</p>
<p>Możemy dodać rezerwacje lub usunąć.</p>
<p>Możemy dodać godziny Pracy pracownika, zmodyfikować je lub usunąć.</p>
<p>Logując się możemy zerknąć do bazy i sprawdzić potrzebne nam dane.</p>
<p>W bazie danych tabeli pracownik znajdują się loginy i hasła dla pracowników, natomiast w tabeli klient znajdują się loginy i hasła dla klientów.</p>

##Dane do logowania
<p>Login dla pracownika: admin<p>
<p>Hasło dla pracownika: pass123!<p>
<p></p>
<p>Login dla klienta: user1<p>
<p>Hasło dla klienta: Passuser1!<p>

