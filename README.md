# DNF_SchedularChatbot
> 카카오 챗봇을 사용하여 유저들에게 오늘등급을 전달해주는 챗봇 서버

## PlusFriend URL
- http://pf.kakao.com/_qFxdrT

![chatbot image](https://user-images.githubusercontent.com/38848719/64661078-28622800-d47e-11e9-9c60-79514784f5b9.gif)

## Development Environment
* Language : open JDK 1.8
* IDE : Spring Tool Suite for Eclipse v3.9.5
* FrameWork : Spring Boot
* JPA : Mybatis
* VCS : Github and Local Git
* VCS Tool : SourceTree
* DB : MariaDB v10.4.7-bionic
* DB Tool : DBeaver

## Operation Environment
* PaaS Platform : NCloud
* Server Specification : [Compact] 1vCPU, 2GB Mem
* Container System : docker

## Installed 
1. STS를 틀고 해당 프로젝트에 Run as
2. Maven Build 에 다음과 같이 작성 Goals : package, profiles : pom.xml
3. Run 하여 나온 jar파일을 서버에 FTP로 전송
4. `nohub java -jar <jar파일 명>.jar &` 명령어를 사용하여 배포

* 서버 종료시 `ps -ef | grep '<jar 파일 명>'` 으로 pid를 찾고 `kill -9 pid` 

## Release History

* 1.1.0
    * 장비등급 조회시 `<아이템 셋명> <옵션>` 으로 원하는 옵션 조회할수 있도록 기능추가
* 1.0.0
    * 기본 오늘의 등급 및 장비의 등급을 발송 할수 있도록 배포

## hotfix
-
