sudo apt install apache2 -y
sudo systemctl enable apache2
sudo systemctl start apache2
sudo ufw enable
sudo ufw allow 80
sudo ufw allow 443
service apache2 status
