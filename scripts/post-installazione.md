# Installa le Guest Additions di VirtualBox
 - sudo apt install dkms build-essential linux-headers-$(uname -r) -y
 - Inserisci la ISO delle Guest Additions
 - Installale eseguendo "VBoxLinuxAdditions.run"
# Fai gli aggiornamenti
 - Esegui "aggiorna_pgm.sh" preferibilmente ad intervalli regolari, così sarai sempre aggiornato
# Installa i programmi essenziali
 - Esegui "installa_pgm_essenziali.sh"
# Installa VSCodium da GitHub
 - Cerca codium su Google e scarica l'ultima release
 - Aprila e installala
 - Rimuovi il file d'installazione dai Downloads
# Installa i servizi necessari
 - Esegui "instlla_ssh.sh" e premi q una volta finito
 - Esegui "installa_apache.sh" e premi q una volta finito
 - Configura Apache riferendoti alla guida "configurazione_apache.txt". Se vuoi configurare in maniera automatica (vedi comunque la guida per la documentazione di ciò che viene fatto), esegui "configura_apache.sh"
