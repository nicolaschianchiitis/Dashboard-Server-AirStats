sudo apt install openssh-client openssh-server -y
sudo systemctl enable ssh
sudo ufw allow ssh
sudo systemctl start ssh
service ssh status
