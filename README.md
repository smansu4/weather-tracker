# Weather Tracker API

This project retrieves weather data from OpenWeatherMap.org

AWS services used:
- AWS CloudWatch
- AWS ElasticBeanstalk

## OpenWeatherMap
- Obtain an api key from: https://openweathermap.org/api
  - once logged in, go to the dashboard and copy API key
- The free tier allows for ~60 requests/min


To test locally:
- Set the api key env variable 
- Run: `mvn spring-boot:run`
- Navigate to : `http://localhost:8080/weather/Chicago`

Notes:
- spring: RestControllerAdvice
- Cloudwatch:
  - 2 common ways to get SpringBoot to logs into CloudWatch?
    - Using the CloudWatch Agent on the EC2 instance
    - Directly logging from Spring Boot to CloudWatch Logs via an appender
  - Configure Spring to write to the `/var/log/web.log` file in yml file
  - `.ebextensions/01-log-permissions.config` fixes log file permissions so ther app user (webapp) could write to `/var/log/web.log`
  - `.ebextensions/02-cloudwatch-agent.config` installed and configured the CloudWatch agent
  - Add `CloudWatchAgentServerPolicy` to the EB env profile so the agent can push logs to CloudWatch
  - Enable logging in EB w/proper permissions in instance profile
    - Attach an instance file (EC2 role) to EB env
      - ``{
        "Effect": "Allow",
        "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents",
        "logs:DescribeLogStreams"
        ],
        "Resource": "*"
        }``
      - Or add: `CloudWatchLogsFullAccess` and `AWSElasticBeanstalkWebTier`
    - Created `.ebextensions` directory in root of project. 
      - EB looks for `.ebextensions` when deploying and reads the config files during provisioning (not Spring or java during runtime). 
      - The config file won't get picked up by EB if added under the `src/` dir. 
      - Eb doesn't build the project from source, it unzips the deployment bundle. Looks for special files/folders (like `.ebextensions`). Then runs provisioning scripts and configs instances accordingly. 
    - Create jar: `mvn clean package`
    - Zip .jar and .ebextensions/: 
      - `cp target/weather-tracker-0.0.1-SNAPSHOT.jar weather-tracker.jar`
      - `zip -r weather-tracker.zip weather-tracker.jar .ebextensions/`
        - `ebextensions/` is hidden and may not appear in finder even if zipped (to show hidden files: `cmd + shit + .`) 



Needed to manually start and enable the agent:
```
sudo systemctl start amazon-cloudwatch-agent
sudo systemctl enable amazon-cloudwatch-agent
sudo systemctl status amazon-cloudwatch-agent
```