

# Installation Notes

## Firewall Config
    ssh local@invisible.f4.htw-berlin.de
    local@invisible:/home$ su
    Password:
    root@invisible:/home# cd
    root@invisible:~# ls
    docker-compose.yml  dump  firewall-disable.sh  firewall.sh  mongo-volume
    root@invisible:~# vi firewall.sh
    root@invisible:~# ./firewall-disable.sh
    root@invisible:~# ./firewall.sh
