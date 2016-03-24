Install docker machine

https://docs.docker.com/machine/

docker-machine create --driver virtualbox adcom

docker-machine env adcom

Then copy these and execute them on the console you intend to use. 
export DOCKER_TLS_VERIFY="1"
export DOCKER_HOST="tcp://192.168.99.100:2376"
export DOCKER_CERT_PATH="/Users/fpo/.docker/machine/machines/adcom"
export DOCKER_MACHINE_NAME="adcom"

Then run:
> ./run.sh wf9-kc1.8-h2.conf
