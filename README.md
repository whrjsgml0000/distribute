# 실행 가이드 
1. 도커를 설치 한다.
2. 도커 네트워크를 만든다.
```docker
docker network create grid
```
3. 만든 네트워크를 이용해서 셀레니엄 허브를 만든다.
```docker
docker run -d -p 4442-4444:4442-4444 --net grid --name selenium-hub selenium/hub:latest
```
4. 셀레니엄 허브에 연결할 노드를 만든다.
```bash
docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub \
    --shm-size="2g" \
    -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
    -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
    selenium/node-firefox:latest
```
```powershall
docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub `
    --shm-size="2g" `
    -e SE_EVENT_BUS_PUBLISH_PORT=4442 `
    -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 `
    selenium/node-firefox:latest
```
5. http://localhost:4444 에 접속해 노드가 활성화 됐는지 확인한다.
6. 노드를 필요한 만큼 추가한다. (4번 코드를 그대로 다시 실행하면 된다.)