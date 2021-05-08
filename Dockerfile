FROM java:8
EXPOSE 9000
ADD MeetingApprovalBack-0.0.1-SNAPSHOT.jar meeting.jar
RUN bash -c 'touch /meeting.jar'
ENTRYPOINT["java","-jar","/app.jar"]