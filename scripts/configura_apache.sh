sudo mkdir /var/www/sito-github
cd /var/www/sito-github
sudo git init
sudo git branch -m main
sudo git remote add origin https://github.com/nicolaschianchiitis/Sito-AirStats.git
sudo git pull origin main
sudo nemo /var/www/
cd /etc/apache2/sites-available
sudo cp 000-default.conf gci.conf
sudo nano gci.conf
sudo a2ensite gci.conf
sudo service apache2 reload
sudo a2enmod ssl
sudo service apache2 reload
sudo apt install certbot python3-certbot-apache -y
sudo certbot --apache -d airstats.it
sudo certbot renew --dry-run
